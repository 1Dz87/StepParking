public class Main {

    public static void main(String[] args) {
        UserRepository repository = new UserRepository();
        repository.printUserByLastNameAndRole("Александр", "Client");
    }
}
