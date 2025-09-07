package lk.ijse.orm_coursework.bo.util;

import lk.ijse.orm_coursework.dto.StudentDTO;
import lk.ijse.orm_coursework.entity.Student;

public class EntityDTOConvertor {

    public StudentDTO getStudentDTO(Student student){
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId(student.getId());
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setPhone(student.getPhone());
        studentDTO.setAddress(student.getAddress());
        studentDTO.setDob(student.getDob());
        studentDTO.setRegistrationDate(student.getRegistrationDate());
        return studentDTO;

    }

    public Student getStudent(StudentDTO studentDTO){
        Student student = new Student();
        student.setId(studentDTO.getStudentId());
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setEmail(studentDTO.getEmail());
        student.setPhone(studentDTO.getPhone());
        student.setAddress(studentDTO.getAddress());
        student.setDob(studentDTO.getDob());
        student.setRegistrationDate(studentDTO.getRegistrationDate());
        return student;
    }
}
