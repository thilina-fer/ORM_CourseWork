package lk.ijse.orm_coursework.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "lessons")
public class Lessons {

    @Id
    @Column
    private String lessonId;

    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "studentId")
    private Students student;

    @ManyToOne
    @JoinColumn(name = "courseId", referencedColumnName = "courseId")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "instructorId", referencedColumnName = "instructorId")
    private Instructor instructor;

    @Column
    private Date lessonDate;

    @Column
    private String startTime;

    @Column
    private String endTime;

    @Column
    private String status;
}
