package be_course.be_online_course.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignUpDto {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "User name cannot be blank")
    private String username;

    @NotBlank(message = "Username or Email cannot be blank")
    @Email(message = "Invalid email format")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "Invalid email format"
    )
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;
}
