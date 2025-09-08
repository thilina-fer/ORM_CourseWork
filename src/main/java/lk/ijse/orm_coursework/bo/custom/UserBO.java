package lk.ijse.orm_coursework.bo.custom;

import lk.ijse.orm_coursework.bo.SuperBO;
import lk.ijse.orm_coursework.bo.exception.DuplicateException;
import lk.ijse.orm_coursework.bo.exception.InUseException;
import lk.ijse.orm_coursework.dto.StudentDTO;
import lk.ijse.orm_coursework.dto.UserDTO;

import java.sql.SQLException;
import java.util.List;

public interface UserBO extends SuperBO {
    List<UserDTO> getAllUser() throws SQLException;

    void saveUser(UserDTO dto) throws DuplicateException, Exception;

    void updateUser(UserDTO dto) throws SQLException;

    boolean deleteUser(String id) throws InUseException, Exception;

    String getNextId() throws SQLException;
}
