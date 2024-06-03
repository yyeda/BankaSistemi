package bank.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Random;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class SignUpRequest implements Serializable {

    private static final long serialVersionUID = 2499376845717117573L;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
    private Long id = new Random().nextLong();
}
