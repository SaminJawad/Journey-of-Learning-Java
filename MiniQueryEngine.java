import java.util.*;
import java.util.stream.*;

public class MiniQueryEngine {
    static class Row {
        Map<String, Object> columns = new LinkedHashMap<>();

        Row(Object... pairs) {
            for (int i = 0; i < pairs.length; i += 2)
                columns.put((String) pairs[i], pairs[i + 1]);
        }

        Object get(String col) {
            return columns.get(col);
        }

        @Override
        public String toString() {
            return columns.toString();
        }
    }

    static class Table {
        String name;
        List<Row> rows = new ArrayList<>();

        Table(String name) {
            this.name = name;
        }

        void insert(Row row) {
            rows.add(row);
        }

        List<Row> select(List<String> cols, String filterCol, Object filterVal) {
            return rows.stream()
                    .filter(r -> filterCol == null || Objects.equals(r.get(filterCol), filterVal))
                    .map(r -> {
                        if (cols.contains("*"))
                            return r;
                        Row projected = new Row();
                        cols.forEach(c -> projected.columns.put(c, r.get(c)));
                        return projected;
                    })
                    .collect(Collectors.toList());
        }

        List<Row> orderBy(List<Row> rows, String col, boolean asc) {
            rows.sort((a, b) -> {
                Comparable ca = (Comparable) a.get(col);
                Comparable cb = (Comparable) b.get(col);
                return asc ? ca.compareTo(cb) : cb.compareTo(ca);
            });
            return rows;
        }

        void print(List<Row> rows) {
            if (rows.isEmpty()) {
                System.out.println("No results.");
                return;
            }
            rows.forEach(r -> System.out.println("  " + r));
        }
    }

    public static void main(String[] args) {
        Table users = new Table("users");
        users.insert(new Row("id", 1, "name", "Samin", "age", 20, "city", "Dhaka"));
        users.insert(new Row("id", 2, "name", "Jawad", "age", 25, "city", "London"));
        users.insert(new Row("id", 3, "name", "Nafis", "age", 22, "city", "Dhaka"));
        users.insert(new Row("id", 4, "name", "Mahin", "age", 28, "city", "Tokyo"));
        users.insert(new Row("id", 5, "name", "Rizwan", "age", 22, "city", "London"));

        System.out.println("SELECT * FROM users:");
        users.print(users.select(List.of("*"), null, null));

        System.out.println("\nSELECT name, age FROM users WHERE city = 'Dhaka':");
        users.print(users.select(List.of("name", "age"), "city", "Dhaka"));

        System.out.println("\nSELECT * FROM users WHERE age = 22 ORDER BY name ASC:");
        List<Row> result = users.select(List.of("*"), "age", 22);
        users.print(users.orderBy(result, "name", true));

        System.out.println("\nSELECT name FROM users ORDER BY age DESC:");
        List<Row> all = users.select(List.of("name", "age"), null, null);
        users.print(users.orderBy(all, "age", false));
    }
}