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

public class InstructorDTO {
    private String instructorId;
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private String specialization;
    private String availability;
    @Builder.Default
    private ArrayList<LessonsDTO> lessons = new  ArrayList<>();
    @Builder.Default
    private ArrayList<CourseDTO> courses = new ArrayList<>();
}
