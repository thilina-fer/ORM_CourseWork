package lk.ijse.orm_coursework.bo.custom;

import lk.ijse.orm_coursework.bo.SuperBO;
import lk.ijse.orm_coursework.bo.exception.DuplicateException;
import lk.ijse.orm_coursework.bo.exception.InUseException;
import lk.ijse.orm_coursework.dto.StudentDTO;
import lk.ijse.orm_coursework.dto.UserDTO;
import lk.ijse.orm_coursework.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserBO extends SuperBO {
    List<UserDTO> getAllUsers() throws Exception;

    String getLastUserId() throws Exception;

    boolean saveUsers(UserDTO t) throws Exception;

    boolean updateUsers(UserDTO t) throws Exception;

    boolean deleteUsers(String id) throws Exception;

    List<String> getAllUserIds() throws Exception;

    Optional<UserDTO> findByUserId(String id) throws Exception;

    String generateNextUserId();

    public UserDTO getUserByEmail(String email) ;
}
