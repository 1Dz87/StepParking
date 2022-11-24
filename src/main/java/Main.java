import java.util.List;

public class Main {

    public static void main(String[] args) {
        CarRepository repository = new CarRepository();
        UserRepository userRepository = new UserRepository();
        User user = userRepository.getUserByLogin("doctor").orElse(null);
        Car car = Car.builder()
                .model("Ferrari")
                .number("7777 IT-2")
                .weight(1000F)
                .user(user)
                .build();
        repository.saveCar(car);
        repository.getCarByUserLogin("doctor").forEach(System.out::println);

    }
}
