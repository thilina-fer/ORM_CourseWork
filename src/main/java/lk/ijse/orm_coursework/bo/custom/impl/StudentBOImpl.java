package lk.ijse.orm_coursework.bo.custom.impl;

import lk.ijse.orm_coursework.bo.custom.StudentBO;
import lk.ijse.orm_coursework.bo.exception.NotFoundException;
import lk.ijse.orm_coursework.bo.util.EntityDTOConvertor;
import lk.ijse.orm_coursework.dao.DAOFactory;
import lk.ijse.orm_coursework.dao.DAOTypes;
import lk.ijse.orm_coursework.dao.custom.StudentDAO;
import lk.ijse.orm_coursework.dto.StudentDTO;
import lk.ijse.orm_coursework.bo.exception.DuplicateException;
import lk.ijse.orm_coursework.bo.exception.InUseException;
import lk.ijse.orm_coursework.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentBOImpl implements StudentBO {

    private final StudentDAO studentDAO = DAOFactory.getInstance().getDAO(DAOTypes.STUDENT);
    private final EntityDTOConvertor convertor = new EntityDTOConvertor();
    @Override
    public List<StudentDTO> getAllStudent() throws SQLException {
        List<Student> students = studentDAO.getAll();
        List<StudentDTO> studentDTOs = new ArrayList<>();
        for (Student student : students) {
            studentDTOs.add(convertor.getStudentDTO(student));
        }
        return studentDTOs;
    }

    @Override
    public void saveStudent(StudentDTO dto) throws DuplicateException, Exception {
//        Optional<Student> optional = studentDAO.findById(dto.getStudentId());
//        if (optional.isPresent()) {
//            throw new DuplicateException("Student already exists");
//        }
//        Optional<Student> optionalStudent = studentDAO.findById(dto.getStudentId());
//        if (optionalStudent.isPresent()) {
//            throw new DuplicateException("Student already exists");
//        }
//        Optional<Student> studentOptional = studentDAO.findById(dto.getStudentId());
//        if (studentOptional.isPresent()) {
//            throw new DuplicateException("Student already exists");
//        }

        Student student = convertor.getStudent(dto);

        studentDAO.save(student);

    }

    @Override
    public void updateStudent(StudentDTO dto) throws SQLException {
        Student student = convertor.getStudent(dto);
        studentDAO.update(student);

    }

    @Override
    public boolean deleteStudent(String id) throws InUseException, Exception {
//        Optional<Student> optional = studentDAO.findById(id);
//        if (optional.isEmpty()) {
//            throw new NotFoundException("Student Not Found");
//        }
//        try {
//            studentDAO.delete(id);
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//        return true;
        return studentDAO.delete(id);
    }

    @Override
    public String getNextId() throws SQLException {
       String lastId = studentDAO.getLastId();
       char tableChar = 'S';
       if (lastId != null){
           String lastIdNumberString = lastId.substring(1);
           int lastIdNumber = Integer.parseInt(lastIdNumberString);
           int nextIdNumber = lastIdNumber + 1;
           return String.format(tableChar + "%03d", nextIdNumber);
       }
       return tableChar + "001";
    }
}
