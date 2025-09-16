package lk.ijse.orm_coursework.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "course")
public class Course {

    @Id
    @Column
    private String course_id;

    @Column(nullable = false)
    private String course_name;

    @Column(nullable = false)
    private String duration;

    @Column(nullable = false)
    private double fee;

    @Column(nullable = false, length = 200)
    private String description;

    @ManyToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "instructor_id")
    private Instructors instructor;

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL
    )
    private List<StudentCourseDetails> studentCourseDetails;

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL
    )
    private List<Lessons> lessons;
}
