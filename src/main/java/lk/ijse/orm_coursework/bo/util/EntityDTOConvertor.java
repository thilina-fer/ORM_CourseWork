package lk.ijse.orm_coursework.bo.util;

import lk.ijse.orm_coursework.dto.InstructorDTO;
import lk.ijse.orm_coursework.dto.StudentDTO;
import lk.ijse.orm_coursework.dto.UserDTO;
import lk.ijse.orm_coursework.entity.Instructor;
import lk.ijse.orm_coursework.entity.Student;
import lk.ijse.orm_coursework.entity.User;

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

    public InstructorDTO getInstructorDTO(Instructor instructor){
        InstructorDTO instructorDTO = new InstructorDTO();
        instructorDTO.setInstructorId(instructor.getId());
        instructorDTO.setFirstName(instructor.getFirstName());
        instructorDTO.setLastName(instructor.getLastName());
        instructorDTO.setEmail(instructor.getEmail());
        instructorDTO.setContact(instructor.getContact());
        instructorDTO.setSpecialization(instructor.getSpecialization());
        instructorDTO.setAvailability(instructor.getAvailability());
        return instructorDTO;
    }

    public  Instructor getInstructor(InstructorDTO instructorDTO){
        Instructor instructor = new Instructor();
        instructor.setId(instructorDTO.getInstructorId());
        instructor.setFirstName(instructorDTO.getFirstName());
        instructor.setLastName(instructorDTO.getLastName());
        instructor.setEmail(instructorDTO.getEmail());
        instructor.setContact(instructorDTO.getContact());
        instructor.setSpecialization(instructorDTO.getSpecialization());
        instructor.setAvailability(instructorDTO.getAvailability());
        return instructor;
    }

    public User getUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public User getUser(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getUserId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}
