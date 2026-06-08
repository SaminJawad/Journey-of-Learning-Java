import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {
    private static final Set<PrintWriter> clients = ConcurrentHashMap.newKeySet();

    static class ClientHandler implements Runnable {
        private final Socket socket;
        private PrintWriter out;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
                out = new PrintWriter(socket.getOutputStream(), true);
                clients.add(out);

                String name = in.readLine();
                System.out.println(name + " joined.");
                broadcast("[Server] " + name + " joined the chat.", null);

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(name + ": " + message);
                    broadcast(name + ": " + message, out);
                }

                clients.remove(out);
                broadcast("[Server] " + name + " left the chat.", null);
                System.out.println(name + " disconnected.");
            } catch (IOException e) {
                if (out != null)
                    clients.remove(out);
            }
        }

        void broadcast(String message, PrintWriter exclude) {
            for (PrintWriter client : clients)
                if (client != exclude)
                    client.println(message);
        }
    }

    public static void main(String[] args) throws IOException {
        int port = 9090;
        ExecutorService pool = Executors.newCachedThreadPool();
        System.out.println("Chat server started on port " + port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("New connection: " + client.getInetAddress());
                pool.submit(new ClientHandler(client));
            }
        }
    }
}