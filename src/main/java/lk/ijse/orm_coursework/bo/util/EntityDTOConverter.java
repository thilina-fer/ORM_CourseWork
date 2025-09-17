package lk.ijse.orm_coursework.bo.util;

import lk.ijse.orm_coursework.dto.*;
import lk.ijse.orm_coursework.entity.*;

public class EntityDTOConverter {
    public CourseDTO getCourseDTO(Course course){
        CourseDTO dto=new CourseDTO();
        dto.setCourse_id(course.getCourse_id());
        dto.setCourse_name(course.getCourse_name());
        dto.setDuration(course.getDuration());
        dto.setFee(course.getFee());
        dto.setDescription(course.getDescription());
        dto.setInstructor_id(course.getInstructor().getInstructor_id());
        return dto;
    }

    public Course getCourseEntity(CourseDTO dto){
        Course course=new Course();
        Instructors instructors=new Instructors();
        course.setCourse_id(dto.getCourse_id());
        course.setCourse_name(dto.getCourse_name());
        course.setDuration(dto.getDuration());
        course.setFee(dto.getFee());
        course.setDescription(dto.getDescription());
        instructors.setInstructor_id(dto.getInstructor_id());
        course.setInstructor(instructors);
        return course;
    }

    public InstructorDTO getInstructorsDTO(Instructors instructors){
        InstructorDTO dto=new InstructorDTO();
        dto.setInstructorId(instructors.getInstructor_id());
        dto.setFirstName(instructors.getFirst_name());
        dto.setLastName(instructors.getLast_name());
        dto.setEmail(instructors.getEmail());
        dto.setContact(instructors.getPhone());
        dto.setSpecialization(instructors.getSpecialization());
        dto.setAvailability(instructors.getAvailability());
        return dto;
    }

    public Instructors getInstructorsEntity(InstructorDTO dto){
        Instructors instructors=new Instructors();
        instructors.setInstructor_id(dto.getInstructorId());
        instructors.setFirst_name(dto.getFirstName());
        instructors.setLast_name(dto.getLastName());
        instructors.setEmail(dto.getEmail());
        instructors.setPhone(dto.getContact());
        instructors.setSpecialization(dto.getSpecialization());
        instructors.setAvailability(dto.getAvailability());
        return instructors;
    }

    public LessonsDTO getLessonsDTO(Lessons lessons){
        LessonsDTO dto=new LessonsDTO();
        dto.setLessonId(lessons.getLessonId());
        dto.setLessonDate(lessons.getLessonDate());
        dto.setStartTime(lessons.getStartTime());
        dto.setEndTime(lessons.getEndTime());
        dto.setStudentId(lessons.getStudent().getStudentId());
        dto.setCourseId(lessons.getCourse().getCourse_id());
        dto.setInstructorId(lessons.getInstructor().getInstructor_id());
        return dto;
    }

    public  Lessons getLessonsEntity(LessonsDTO dto){
        Lessons lessons=new Lessons();
        Instructors instructors=new Instructors();
        Course course=new Course();
        Students student=new Students();
        lessons.setLessonId(dto.getLessonId());
        lessons.setLessonDate(dto.getLessonDate());
        lessons.setStartTime(dto.getStartTime());
        lessons.setEndTime(dto.getEndTime());
        student.setStudentId(dto.getStudentId());
        lessons.setStudent(student);
        course.setCourse_id(dto.getCourseId());
        lessons.setCourse(course);
        instructors.setInstructor_id(dto.getInstructorId());
        lessons.setInstructor(instructors);
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

    public StudentCourseDetailsDTO getStudentCourseDetailsDTO(StudentCourseDetails studentCourseDetails){
        StudentCourseDetailsDTO dto=new StudentCourseDetailsDTO();
        dto.setStudentCourseId(studentCourseDetails.getStudentCourseId());
        dto.setEnrollmentDate(studentCourseDetails.getEnrollmentDate());
        dto.setStatus(studentCourseDetails.getStatus());
        dto.setGrade(studentCourseDetails.getGrade());
        dto.setStudentId(studentCourseDetails.getStudent().getStudentId());
        dto.setCourseId(studentCourseDetails.getCourse().getCourse_id());
        return dto;
    }

    public StudentCourseDetails getStudentCourseDetailsEntity(StudentCourseDetailsDTO dto){
        StudentCourseDetails studentCourseDetails=new StudentCourseDetails();
        Students students=new Students();
        Course course=new Course();
        studentCourseDetails.setStudentCourseId(dto.getStudentCourseId());
        studentCourseDetails.setEnrollmentDate(dto.getEnrollmentDate());
        studentCourseDetails.setStatus(dto.getStatus());
        studentCourseDetails.setGrade(dto.getGrade());
        students.setStudentId(dto.getStudentId());
        studentCourseDetails.setStudent(students);
        course.setCourse_id(dto.getCourseId());
        studentCourseDetails.setCourse(course);
        return studentCourseDetails;
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
        return students;
    }

    public UserDTO getUserDTO(User user){
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
