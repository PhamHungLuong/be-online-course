package be_course.be_online_course.modules.Course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
}