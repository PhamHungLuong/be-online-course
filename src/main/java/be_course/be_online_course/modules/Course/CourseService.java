package be_course.be_online_course.modules.Course;

import be_course.be_online_course.utils.FilterRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getCourses(FilterRequest filterRequest) {
        return courseRepository.findAll((Specification<Course>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filterRequest.getSearchFields() != null && !filterRequest.getSearchFields().isEmpty()) {
                for (Map.Entry<String, String> entry : filterRequest.getSearchFields().entrySet()) {
                    predicates.add(criteriaBuilder.like(root.get(entry.getKey()), "%" + entry.getValue() + "%"));
                }
            } else if (filterRequest.getSearch() != null && !filterRequest.getSearch().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + filterRequest.getSearch() + "%"));
            }
            if (filterRequest.getDateField() != null) {
                predicates.add(criteriaBuilder.equal(root.get("createdDate"), filterRequest.getDateField()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}