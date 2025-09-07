package lk.ijse.orm_coursework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class StudentDTO {
    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String dob;
    private String registrationDate;
}
