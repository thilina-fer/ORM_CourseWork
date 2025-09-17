package lk.ijse.orm_coursework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class CourseDTO {
    private String courseId;
    private String course_name;
    private String duration;
    private double fee;
    private String description;
    private String instructorId;
    @Builder.Default
    private ArrayList<StudentCourseDetailsDTO> studentCourseDetails = new ArrayList<>();
    @Builder.Default
    private ArrayList<LessonsDTO> lessons = new ArrayList<>();
}
