import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarRepository {

    Connection connection;

    UserRepository userRepository;

    public CarRepository() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=parking",
                            "postgres", "root");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        userRepository = new UserRepository();
    }

    public void saveCar(Car car) {
        try {
            PreparedStatement statement = connection.prepareStatement("insert into car (model, number, weight, user_id) values ('" +
                    car.getModel() + "','" + car.getNumber() + "','" + car.getWeight() + "', '" + car.getUser().getId() + "')");
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public List<Car> getCarByUserLogin(String login) {
        return userRepository.getUserByLogin(login)
                .map(user -> {
                    List<Car> cars = getUserCars(user);
                    user.setCarList(cars);
                    return cars;
                })
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    public List<Car> getUserCars(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from car where user_id = ?");
            statement.setLong(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                cars.add(Car.builder()
                        .id(resultSet.getLong("id"))
                        .model(resultSet.getString("model"))
                        .number(resultSet.getString("number"))
                        .weight(resultSet.getFloat("weight"))
                        .user(user)
                        .build());
            }
            return cars;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return List.of();
        }
    }
}
