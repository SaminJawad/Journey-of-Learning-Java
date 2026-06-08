import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleRESTAPI {
    static Map<Integer, String> users = new HashMap<>();
    static AtomicInteger idCounter = new AtomicInteger(1);

    static String readBody(HttpExchange exchange) throws IOException {
        return new String(exchange.getRequestBody().readAllBytes());
    }

    static void sendResponse(HttpExchange exchange, int status, String body) throws IOException {
        byte[] bytes = body.getBytes();
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.getResponseBody().close();
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/users", exchange -> {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String[] parts = path.split("/");

            if (method.equals("GET") && parts.length == 2) {
                StringBuilder sb = new StringBuilder("[");
                users.forEach(
                        (k, v) -> sb.append("{\"id\":").append(k).append(",\"name\":\"").append(v).append("\"},"));
                if (sb.length() > 1)
                    sb.deleteCharAt(sb.length() - 1);
                sb.append("]");
                sendResponse(exchange, 200, sb.toString());

            } else if (method.equals("POST")) {
                String body = readBody(exchange);
                String name = body.replace("{\"name\":\"", "").replace("\"}", "").trim();
                int id = idCounter.getAndIncrement();
                users.put(id, name);
                sendResponse(exchange, 201, "{\"id\":" + id + ",\"name\":\"" + name + "\"}");

            } else if (method.equals("DELETE") && parts.length == 3) {
                int id = Integer.parseInt(parts[2]);
                if (users.remove(id) != null)
                    sendResponse(exchange, 200, "{\"message\":\"Deleted\"}");
                else
                    sendResponse(exchange, 404, "{\"error\":\"Not found\"}");

            } else {
                sendResponse(exchange, 405, "{\"error\":\"Method not allowed\"}");
            }
        });

        server.setExecutor(Executors.newFixedThreadPool(4));
        server.start();
        System.out.println("Server running on http://localhost:8080");
        System.out.println("POST   /users        -> create user");
        System.out.println("GET    /users        -> list users");
        System.out.println("DELETE /users/{id}   -> delete user");
    }
}