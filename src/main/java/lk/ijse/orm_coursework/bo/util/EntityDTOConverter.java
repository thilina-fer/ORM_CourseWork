package lk.ijse.orm_coursework.bo.util;

import lk.ijse.orm_coursework.dto.*;
import lk.ijse.orm_coursework.entity.*;

import java.util.ArrayList;

public class EntityDTOConverter {
    public CourseDTO getCourseDTO(Course course){
        CourseDTO dto=new CourseDTO();
        dto.setCourseId(course.getCourseId());
        dto.setCourse_name(course.getCourseName());
        dto.setDuration(course.getDuration());
        dto.setFee(course.getFee());
        dto.setDescription(course.getDescription());
        dto.setInstructorId(course.getInstructor().getInstructorId());
        return dto;
    }

    public Course getCourseEntity(CourseDTO dto){
        Course course=new Course();
        Instructor instructor =new Instructor();
        course.setCourseId(dto.getCourseId());
        course.setCourseName(dto.getCourse_name());
        course.setDuration(dto.getDuration());
        course.setFee(dto.getFee());
        course.setDescription(dto.getDescription());
        instructor.setInstructorId(dto.getInstructorId());
        course.setInstructor(instructor);
        return course;
    }

    public InstructorDTO getInstructorsDTO(Instructor instructor){
        InstructorDTO dto=new InstructorDTO();
        dto.setInstructorId(instructor.getInstructorId());
        dto.setFirstName(instructor.getFirstname());
        dto.setLastName(instructor.getLastname());
        dto.setEmail(instructor.getEmail());
        dto.setContact(instructor.getPhone());
        dto.setSpecialization(instructor.getSpecialization());
        dto.setAvailability(instructor.getAvailability());
        return dto;
    }

    public Instructor getInstructorsEntity(InstructorDTO dto){
        Instructor instructor =new Instructor();
        instructor.setInstructorId(dto.getInstructorId());
        instructor.setFirstname(dto.getFirstName());
        instructor.setLastname(dto.getLastName());
        instructor.setEmail(dto.getEmail());
        instructor.setPhone(dto.getContact());
        instructor.setSpecialization(dto.getSpecialization());
        instructor.setAvailability(dto.getAvailability());
        return instructor;
    }

    public LessonsDTO getLessonsDTO(Lessons lessons){
        LessonsDTO dto=new LessonsDTO();
        dto.setLessonId(lessons.getLessonId());
        dto.setLessonDate(lessons.getLessonDate());
        dto.setStartTime(lessons.getStartTime());
        dto.setEndTime(lessons.getEndTime());
        dto.setStudentId(lessons.getStudent().getStudentId());
        dto.setCourseId(lessons.getCourse().getCourseId());
        dto.setInstructorId(lessons.getInstructor().getInstructorId());
        return dto;
    }

    public  Lessons getLessonsEntity(LessonsDTO dto){
        Lessons lessons=new Lessons();
        Instructor instructor =new Instructor();
        Course course=new Course();
        Students student=new Students();
        lessons.setLessonId(dto.getLessonId());
        lessons.setLessonDate(dto.getLessonDate());
        lessons.setStartTime(dto.getStartTime());
        lessons.setEndTime(dto.getEndTime());
        student.setStudentId(dto.getStudentId());
        lessons.setStudent(student);
        course.setCourseId(dto.getCourseId());
        lessons.setCourse(course);
        instructor.setInstructorId(dto.getInstructorId());
        lessons.setInstructor(instructor);
        return lessons;
    }

    public PaymentsDTO getPaymentsDTO(Payments payments){
        PaymentsDTO dto=new PaymentsDTO();
        dto.setPaymentId(payments.getPaymentId());
        dto.setPaymentDate(payments.getPaymentDate());
        dto.setAmount(payments.getAmount());
        dto.setAmount(payments.getAmount());
        dto.setPaymentMethod(payments.getPaymentMethod());
        dto.setStatus(payments.getStatus());
        dto.setStudentId(payments.getStudent().getStudentId());
        return dto;
    }

    public Payments getPaymentsEntity(PaymentsDTO dto){
        Payments payments=new Payments();
        Students students=new Students();
        payments.setPaymentId(dto.getPaymentId());
        payments.setPaymentDate(dto.getPaymentDate());
        payments.setAmount(dto.getAmount());
        payments.setPaymentMethod(dto.getPaymentMethod());
        payments.setStatus(dto.getStatus());
        students.setStudentId(dto.getStudentId());
        payments.setStudent(students);
        return payments;
    }


    public StudentDTO getStudentsDTO(Students students){
        StudentDTO dto=new StudentDTO();
        dto.setStudentId(students.getStudentId());
        dto.setFirstName(students.getFirstName());
        dto.setLastName(students.getLastName());
        dto.setEmail(students.getEmail());
        dto.setPhone(students.getPhone());
        dto.setAddress(students.getAddress());
        dto.setDob(students.getDob());
        dto.setRegistrationDate(students.getRegistrationDate());
        dto.setCourses(
                students.getCourses()
                        .stream()
                        .map(this::getCourseDTO) // convert entity → DTO
                        .toList() // collect into List
        );

        return dto;
    }

    public Students getStudentsEntity(StudentDTO dto){
        Students students=new Students();
        students.setStudentId(dto.getStudentId());
        students.setFirstName(dto.getFirstName());
        students.setLastName(dto.getLastName());
        students.setEmail(dto.getEmail());
        students.setPhone(dto.getPhone());
        students.setAddress(dto.getAddress());
        students.setDob(dto.getDob());
        students.setRegistrationDate(dto.getRegistrationDate());
        students.setCourses(
                dto.getCourses()
                        .stream()
                        .map(this::getCourseEntity) // convert entity → DTO
                        .toList() // collect into List
        );
        return students;
    }

    public UserDTO getUserDTO(User user){
        if (user==null){
            return null;
        }
        UserDTO dto=new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUserName());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        dto.setEmail(user.getEmail());
        dto.setStatus(user.getStatus());
        return dto;
    }

    public User getUserEntity(UserDTO dto){
        User user=new User();
        user.setUserId(dto.getUserId());
        user.setUserName(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setEmail(dto.getEmail());
        user.setStatus(dto.getStatus());
        return user;
    }
}
