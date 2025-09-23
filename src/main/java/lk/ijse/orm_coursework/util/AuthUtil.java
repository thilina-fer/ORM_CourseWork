package lk.ijse.orm_coursework.util;


import lk.ijse.orm_coursework.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;


public class AuthUtil {
    @Getter
    @Setter
    private static UserDTO currentUser;

    public static String getRole() {
        return currentUser != null ? currentUser.getRole() : null;
    }

    public static void clear() {
        currentUser = null;
    }
}