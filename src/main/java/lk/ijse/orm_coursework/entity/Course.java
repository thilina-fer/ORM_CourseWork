package lk.ijse.orm_coursework.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "course")
public class Course {

    @Id
    @Column
    private String courseId;

    @Column(nullable = false)
    private String course_name;

    @Column(nullable = false)
    private String duration;

    @Column(nullable = false)
    private double fee;

    @Column(nullable = false, length = 200)
    private String description;

    @ManyToOne
    @JoinColumn(name = "instructorId", referencedColumnName = "instructorId")
    private Instructor instructor;

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL
    )
    @ToString.Exclude
    private List<StudentCourseDetails> studentCourseDetails;

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL
    )
    @ToString.Exclude
    private List<Lessons> lessons;
}
