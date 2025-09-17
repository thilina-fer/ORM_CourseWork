package lk.ijse.orm_coursework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class UserDTO {
    private String userId;
    private String username;
    private String password;
    private String role;
    private String email;
    private String status;
}
