package lk.ijse.orm_coursework.config;

import lk.ijse.orm_coursework.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;

    private FactoryConfiguration() {
        Configuration configuration = new Configuration();
        configuration.configure();

        configuration.addAnnotatedClass(Students.class);
        configuration.addAnnotatedClass(StudentCourseDetails.class);
        configuration.addAnnotatedClass(Course.class);
        configuration.addAnnotatedClass(Instructor.class);
        configuration.addAnnotatedClass(Lessons.class);
        configuration.addAnnotatedClass(Payments.class);
        configuration.addAnnotatedClass(User.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    public static FactoryConfiguration getInstance() {
        return factoryConfiguration == null ?
                factoryConfiguration = new FactoryConfiguration()
                :
                factoryConfiguration;
    }

    public Session getSession(){
        Session session = sessionFactory.openSession();
        return session;
    }

    public Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
}
