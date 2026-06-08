import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleORMMapper {
    static class User {
        int id;
        String name;
        String email;

        @Override
        public String toString() {
            return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
        }
    }

    static <T> List<T> mapResultSet(ResultSet rs, Class<T> clazz) throws Exception {
        List<T> results = new ArrayList<>();
        ResultSetMetaData meta = rs.getMetaData();
        int cols = meta.getColumnCount();

        while (rs.next()) {
            T obj = clazz.getDeclaredConstructor().newInstance();
            for (int i = 1; i <= cols; i++) {
                String col = meta.getColumnName(i).toLowerCase();
                try {
                    Field field = clazz.getDeclaredField(col);
                    field.setAccessible(true);
                    field.set(obj, rs.getObject(i));
                } catch (NoSuchFieldException ignored) {
                }
            }
            results.add(obj);
        }
        return results;
    }

    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection(
                "jdbc:sqlite:test.db");

        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS users (id INTEGER, name TEXT, email TEXT)");
        stmt.execute("INSERT OR IGNORE INTO users VALUES (1, 'Samin', 'samin@example.com')");
        stmt.execute("INSERT OR IGNORE INTO users VALUES (2, 'Alice', 'alice@example.com')");

        ResultSet rs = stmt.executeQuery("SELECT * FROM users");
        List<User> users = mapResultSet(rs, User.class);
        users.forEach(System.out::println);

        conn.close();
    }
}