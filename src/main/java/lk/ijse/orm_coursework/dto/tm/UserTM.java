package lk.ijse.orm_coursework.dto.tm;
import javafx.scene.layout.Pane;
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
    private String status;
    private Pane action;
}
