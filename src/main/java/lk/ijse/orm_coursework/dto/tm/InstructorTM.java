package lk.ijse.orm_coursework.dto.tm;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class InstructorTM {
    private String instructorId;
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private String specialization;
    private String availability;
}
