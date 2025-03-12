package be_course.be_online_course.modules.Course;

import be_course.be_online_course.modules.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "id", nullable = false)
    private User instructor;
}
