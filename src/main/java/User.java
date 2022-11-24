import lombok.*;

import java.util.List;

@Data
@Builder
public class User {

    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phoneNumber;
    private Role role;
    private String cardNumber;
    private List<Car> carList;

}
