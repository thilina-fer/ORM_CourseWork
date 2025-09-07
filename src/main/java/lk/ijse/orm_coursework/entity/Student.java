package lk.ijse.orm_coursework.entity;

import jakarta.persistence.*;
import javafx.fxml.FXML;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "students")
public class Student {
    @Id
    @Column(name = "student_id")
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false , unique = true)
    private String email;

    @Column(length = 10, nullable = false)
    private String phone;

    @Column
    private String address;

    @Column
    private String dob;

    @Column
    private String registrationDate;
}
