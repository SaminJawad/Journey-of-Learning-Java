import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OAuthSimulation {
    private static final Map<String, String> authCodes = new ConcurrentHashMap<>();
    private static final Map<String, String> accessTokens = new ConcurrentHashMap<>();
    private static final Map<String, String> userStore = Map.of(
            "samin", "password123",
            "alice", "securepass");

    static String generateCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    static String generateToken() {
        return "tok_" + UUID.randomUUID().toString().replace("-", "");
    }

    static Optional<String> authorize(String username, String password, String clientId) {
        if (!userStore.containsKey(username) || !userStore.get(username).equals(password))
            return Optional.empty();
        String code = generateCode();
        authCodes.put(code, username + ":" + clientId);
        System.out.println("[Auth Server] Auth code issued: " + code);
        return Optional.of(code);
    }

    static Optional<String> exchangeToken(String code, String clientId) {
        String value = authCodes.remove(code);
        if (value == null || !value.endsWith(":" + clientId))
            return Optional.empty();
        String token = generateToken();
        String username = value.split(":")[0];
        accessTokens.put(token, username);
        System.out.println("[Auth Server] Access token issued for: " + username);
        return Optional.of(token);
    }

    static Optional<String> validateToken(String token) {
        return Optional.ofNullable(accessTokens.get(token));
    }

    public static void main(String[] args) {
        String clientId = "client_app_001";

        System.out.println("=== Step 1: User Login & Authorization ===");
        Optional<String> code = authorize("samin", "password123", clientId);
        code.ifPresentOrElse(
                c -> System.out.println("Client received auth code: " + c),
                () -> System.out.println("Authorization failed."));

        System.out.println("\n=== Step 2: Exchange Code for Token ===");
        Optional<String> token = code.flatMap(c -> exchangeToken(c, clientId));
        token.ifPresentOrElse(
                t -> System.out.println("Client received token: " + t),
                () -> System.out.println("Token exchange failed."));

        System.out.println("\n=== Step 3: Access Protected Resource ===");
        token.flatMap(OAuthSimulation::validateToken)
                .ifPresentOrElse(
                        user -> System.out.println("Access granted for user: " + user),
                        () -> System.out.println("Access denied — invalid token."));

        System.out.println("\n=== Step 4: Invalid Token Attempt ===");
        validateToken("fake_token_xyz")
                .ifPresentOrElse(
                        user -> System.out.println("Access granted for: " + user),
                        () -> System.out.println("Access denied — invalid token."));
    }
}