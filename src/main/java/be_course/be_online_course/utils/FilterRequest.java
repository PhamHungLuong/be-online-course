package be_course.be_online_course.utils;

import lombok.Data;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class FilterRequest {
    private Map<String, String> searchFields;
    private String search;
    private LocalDate dateField;
    private int offset = 0;
    private int limit = 10;
    private List<Sort.Order> sort = List.of(new Sort.Order(Sort.Direction.ASC, "id"));
}
