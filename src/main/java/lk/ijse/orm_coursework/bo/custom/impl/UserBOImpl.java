package lk.ijse.orm_coursework.bo.custom.impl;

import lk.ijse.orm_coursework.bo.custom.UserBO;
import lk.ijse.orm_coursework.bo.exception.DuplicateException;
import lk.ijse.orm_coursework.bo.exception.InUseException;
import lk.ijse.orm_coursework.bo.util.EntityDTOConvertor;
import lk.ijse.orm_coursework.dao.DAOFactory;
import lk.ijse.orm_coursework.dao.DAOTypes;
import lk.ijse.orm_coursework.dao.custom.UserDAO;
import lk.ijse.orm_coursework.dto.UserDTO;
import lk.ijse.orm_coursework.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {

    private  final UserDAO userDAO = DAOFactory.getInstance().getDAO(DAOTypes.USER);
    private final EntityDTOConvertor convertor = new EntityDTOConvertor();

    @Override
    public List<UserDTO> getAllUser() throws SQLException {
        List<User> users = userDAO.getAll();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
//            users.add(convertor.getUserDTO(user));
            userDTOs.add(convertor.getUserDTO(user));
        }
        return userDTOs;
    }

    @Override
    public void saveUser(UserDTO dto) throws DuplicateException, Exception {
        User user = convertor.getUser(dto);
        userDAO.save(user);
    }

    @Override
    public void updateUser(UserDTO dto) throws SQLException {
        User user = convertor.getUser(dto);
        userDAO.update(user);

    }

    @Override
    public boolean deleteUser(String id) throws InUseException, Exception {
        return userDAO.delete(id);
    }

    @Override
    public String getNextId() throws SQLException {
        String lastId = userDAO.getLastId();
        char tableChar = 'U';
        if (lastId != null){
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format(tableChar + "%03d", nextIdNumber);
        }
        return tableChar + "001";
    }
}
