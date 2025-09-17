package lk.ijse.orm_coursework.bo.custom;

import lk.ijse.orm_coursework.bo.SuperBO;
import lk.ijse.orm_coursework.dto.StudentDTO;
import lk.ijse.orm_coursework.bo.exception.DuplicateException;
import lk.ijse.orm_coursework.bo.exception.InUseException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface StudentBO extends SuperBO {
    List<StudentDTO> getAllStudents() throws Exception;

    String getLastStudentId() throws Exception;

    boolean saveStudents(StudentDTO t) throws Exception;

    boolean updateStudents(StudentDTO t) throws Exception;

    boolean deleteStudents(String id) throws Exception;

    List<String> getAllStudentIds() throws Exception;

    Optional<StudentDTO> findByStudentId(String id) throws Exception;

    String generateNewStudentId();
}
