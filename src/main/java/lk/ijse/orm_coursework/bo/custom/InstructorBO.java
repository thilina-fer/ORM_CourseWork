package lk.ijse.orm_coursework.bo.custom;

import lk.ijse.orm_coursework.bo.SuperBO;
import lk.ijse.orm_coursework.bo.exception.DuplicateException;
import lk.ijse.orm_coursework.bo.exception.InUseException;
import lk.ijse.orm_coursework.dto.InstructorDTO;
import lk.ijse.orm_coursework.dto.StudentDTO;

import java.sql.SQLException;
import java.util.List;

public interface InstructorBO extends SuperBO {
    List<InstructorDTO> getAllInstructor() throws SQLException;

    void saveInstructor(InstructorDTO dto) throws DuplicateException, Exception;

    void updateInstructor(InstructorDTO dto) throws SQLException;

    boolean deleteInstructor(String id) throws InUseException, Exception;

    String getNextId() throws SQLException;
}
