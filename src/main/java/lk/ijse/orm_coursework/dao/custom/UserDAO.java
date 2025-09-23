package lk.ijse.orm_coursework.dao.custom;

import lk.ijse.orm_coursework.dao.CrudDAO;
import lk.ijse.orm_coursework.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    public User getUserByEmail(String email);
}
