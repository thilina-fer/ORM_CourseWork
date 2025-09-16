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

public class StudentDTO {
    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String dob;
    private String registrationDate;
    @Builder.Default
    private ArrayList<StudentCourseDetailsDTO> studentCourseDetails = new ArrayList<>();
    @Builder.Default
    private ArrayList<LessonsDTO> lessons =new ArrayList<>();
    @Builder.Default
    private ArrayList<PaymentsDTO> payments =new ArrayList<>();
}
