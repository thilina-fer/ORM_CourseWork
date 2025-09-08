package lk.ijse.orm_coursework.dto.tm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class UserTM {
    private String userId;
    private String username;
    private String password;
    private String role;
    private String email;
}
