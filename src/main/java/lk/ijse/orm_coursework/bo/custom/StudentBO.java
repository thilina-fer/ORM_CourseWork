package lk.ijse.orm_coursework.bo.custom;

import lk.ijse.orm_coursework.bo.SuperBO;
import lk.ijse.orm_coursework.dto.StudentDTO;
import lk.ijse.orm_coursework.bo.exception.DuplicateException;
import lk.ijse.orm_coursework.bo.exception.InUseException;

import java.sql.SQLException;
import java.util.List;

public interface StudentBO extends SuperBO {
    List<StudentDTO> getAllStudent() throws SQLException;

    void saveStudent(StudentDTO dto) throws DuplicateException, Exception;

    void updateStudent(StudentDTO dto) throws SQLException;

    boolean deleteStudent(String id) throws InUseException, Exception;

    String getNextId() throws SQLException;
}
