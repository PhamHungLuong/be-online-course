package be_course.be_online_course.modules.role;

import be_course.be_online_course.modules.user.User;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 60)
    private String name;

    // Back reference to prevent circular JSON serialization
    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private Set<User> users;
}
