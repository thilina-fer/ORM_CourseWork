package lk.ijse.orm_coursework.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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
    @Column(name = "courseId", length = 50) // specify name & length for clarity
    private String courseId;

    @Column(nullable = false)
    private String courseName;

    @Column(nullable = false)
    private String duration;

    @Column(nullable = false)
    private double fee;

    @Column(nullable = false, length = 200)
    private String description;

    @ManyToOne
    @JoinColumn(name = "instructorId", nullable = false) // remove referencedColumnName, Hibernate maps automatically
    private Instructor instructor;

    @ManyToMany(mappedBy = "courses")
    private List<Students> student = new ArrayList<>();


    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Lessons> lessons;
}
