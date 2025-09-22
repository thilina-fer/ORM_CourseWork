package lk.ijse.orm_coursework.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "student")
public class Students {

    @Id
    @Column
    private String studentId;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Date dob;

    @Column(nullable = false)
    private Date registrationDate;

    @ManyToMany(fetch =  FetchType.EAGER)
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "courseId")
    )
    @ToString.Exclude
    private List<Course> courses ;

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @ToString.Exclude
    private List<StudentCourseDetails> studentCourseDetails;

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    @ToString.Exclude
    private List<Lessons> lessons;

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    @ToString.Exclude
    private List<Payments> payments;
}


