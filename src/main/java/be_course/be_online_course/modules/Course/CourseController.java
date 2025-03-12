package be_course.be_online_course.modules.Course;

import be_course.be_online_course.DTO.ApiResponse;
import be_course.be_online_course.utils.FilterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Course>>> getCourses(@ModelAttribute FilterRequest filterRequest) {
        System.out.println("aaaa" + filterRequest.toString());
        List<Course> courses = courseService.getCourses(filterRequest);
        return ResponseEntity.ok(ApiResponse.success(courses, "Courses retrieved successfully"));
    }
}
