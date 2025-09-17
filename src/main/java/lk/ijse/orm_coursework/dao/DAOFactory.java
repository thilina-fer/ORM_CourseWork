package lk.ijse.orm_coursework.dao;

import lk.ijse.orm_coursework.dao.custom.StudentDAO;
import lk.ijse.orm_coursework.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {}

    public static DAOFactory getInstance() {
        return daoFactory == null ? (daoFactory = new DAOFactory()) : daoFactory;
    }

    public <T extends SuperDAO> T getDAO(DAOTypes daoTypes) {
        return switch (daoTypes) {

            case COURSE ->  (T) new CourseDAOImpl();

            case INSTRUCTORS ->  (T) new InstructorDAOImpl();

            case LESSONS ->  (T) new LessonsDAOImpl();

            case PAYMENTS ->   (T) new PaymentsDAOImpl();

            case STUDENT_COURSE_DETAILS ->  (T) new StudentCourseDetailsDAOImpl();

            case STUDENTS -> (T) new StudentDAOImpl();

            case USER -> (T) new UserDAOImpl();

        };
    }
}
