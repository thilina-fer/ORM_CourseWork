package lk.ijse.orm_coursework.dto.tm;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class StudentTM {
    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private Date dob;
    private Date registrationDate;
    private String courseIds;
    private Pane action;

    public StudentTM(String studentId, String firstName, String lastName, String email, String phone, String address, Date dob, Date registrationDate) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
        this.registrationDate = registrationDate;
    }
}
