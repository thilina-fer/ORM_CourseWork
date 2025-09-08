package lk.ijse.orm_coursework.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    private String id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false , unique = true)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false ,  unique = true)
    private String email;
}
