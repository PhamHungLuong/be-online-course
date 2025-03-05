package be_course.be_online_course.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordDTO {
    @NotBlank(message = "Password cannot be blank")
    private String oldPassword;

    @NotBlank(message = "Password cannot be blank")
    private String newPassword;
}
