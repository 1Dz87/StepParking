import java.sql.*;

public class UserRepository {

    private Connection connection;

    public UserRepository() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=parking",
                            "postgres", "root");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void printAllUsers() {
        try {
            ResultSet set = connection
                    .createStatement()
                    .executeQuery("select * from parking_user");
            while (set.next()) {
                System.out.print(set.getLong("id"));
                System.out.print(" ".concat(set.getString("last_name")).concat(" "));
                System.out.print(set.getString("first_name").concat(" "));
                System.out.println(set.getString("role"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void printUserById(Long id) {
        try {
            PreparedStatement statement = connection
                    .prepareStatement("select * from parking_user where id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            System.out.print(resultSet.getLong("id"));
            System.out.print(" ".concat(resultSet.getString("last_name")).concat(" "));
            System.out.print(resultSet.getString("first_name").concat(" "));
            System.out.println(resultSet.getString("role"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void printUserByLastNameAndRole(String lastName, String role) {
        try {
            PreparedStatement statement = connection
                    .prepareStatement("select * from parking_user where last_name = ? " +
                            "and role = ?");
            statement.setString(1, lastName);
            statement.setString(2, role);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            System.out.print(resultSet.getLong("id"));
            System.out.print(" ".concat(resultSet.getString("last_name")).concat(" "));
            System.out.print(resultSet.getString("first_name").concat(" "));
            System.out.println(resultSet.getString("role"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
