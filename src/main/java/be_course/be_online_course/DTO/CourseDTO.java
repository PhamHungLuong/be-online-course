package be_course.be_online_course.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CourseDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    private String description;

    @NotBlank(message = "instructor id cannot be blank")
    private  String instructor_id;
}

