import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexLogParser {
    public static void main(String[] args) {
        String[] logs = {
                "2024-01-15 10:23:45 ERROR NullPointerException in UserService.java:42",
                "2024-01-15 10:24:01 INFO  User login successful for user: samin",
                "2024-01-15 10:25:30 WARN  Response time exceeded threshold: 2500ms",
                "2024-01-15 10:26:15 ERROR ArrayIndexOutOfBoundsException in OrderService.java:88"
        };

        Pattern pattern = Pattern.compile(
                "(\\d{4}-\\d{2}-\\d{2}) (\\d{2}:\\d{2}:\\d{2}) (\\w+)\\s+(.+)");

        System.out.printf("%-12s %-10s %-6s %s%n", "Date", "Time", "Level", "Message");
        System.out.println("-".repeat(70));

        for (String log : logs) {
            Matcher matcher = pattern.matcher(log);
            if (matcher.matches()) {
                System.out.printf("%-12s %-10s %-6s %s%n",
                        matcher.group(1),
                        matcher.group(2),
                        matcher.group(3),
                        matcher.group(4));
            }
        }
    }
}