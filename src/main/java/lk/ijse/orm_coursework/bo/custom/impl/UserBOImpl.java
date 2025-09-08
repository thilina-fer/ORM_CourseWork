package lk.ijse.orm_coursework.bo.custom.impl;

import lk.ijse.orm_coursework.bo.custom.UserBO;
import lk.ijse.orm_coursework.bo.exception.DuplicateException;
import lk.ijse.orm_coursework.bo.exception.InUseException;
import lk.ijse.orm_coursework.dto.UserDTO;

import java.sql.SQLException;
import java.util.List;

public class UserBOImpl implements UserBO {
    @Override
    public List<UserDTO> getAllUser() throws SQLException {
        return List.of();
    }

    @Override
    public void saveUser(UserDTO dto) throws DuplicateException, Exception {

    }

    @Override
    public void updateUser(UserDTO dto) throws SQLException {

    }

    @Override
    public boolean deleteUser(String id) throws InUseException, Exception {
        return false;
    }

    @Override
    public String getNextId() throws SQLException {
        return "";
    }
}
