package lk.ijse.orm_coursework.dao;

import lk.ijse.orm_coursework.dao.custom.StudentDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudDAO<T> extends SuperDAO {
    List<T> getAll() throws Exception;

    String getLastId() throws Exception;

    boolean save(T t) throws Exception;

    boolean update(T t) throws Exception;

    boolean delete(String id) throws Exception;

    List<String> getAllIds() throws Exception;

    Optional<T> findById(String id) throws Exception;

    String generateNewId();
}
