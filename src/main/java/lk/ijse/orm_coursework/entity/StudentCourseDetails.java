package lk.ijse.orm_coursework.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "studentCourseDetails")
public class StudentCourseDetails {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private String studentCourseId;

    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "studentId")
    private Students student;

    @ManyToOne
    @JoinColumn(name = "courseId", referencedColumnName = "courseId")
    private Course course;

    @Column
    private Date enrollmentDate;

    @Column
    private String status;

    @Column
    private String grade;
}
