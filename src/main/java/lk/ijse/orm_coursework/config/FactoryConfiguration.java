package lk.ijse.orm_coursework.config;

import lk.ijse.orm_coursework.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;


    private FactoryConfiguration()  {
        Properties prop = new Properties();

        try {
            prop.load(
                    FactoryConfiguration.class.getClassLoader().getResourceAsStream("hibernate.properties")
            );

        Configuration configuration = new Configuration();
        configuration.addProperties(prop);

        configuration.addAnnotatedClass(Students.class);
        configuration.addAnnotatedClass(Course.class);
        configuration.addAnnotatedClass(Instructor.class);
        configuration.addAnnotatedClass(Lessons.class);
        configuration.addAnnotatedClass(Payments.class);
        configuration.addAnnotatedClass(User.class);

        sessionFactory = configuration.buildSessionFactory();

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error in hibernate properties",e);
        }
    }

    public static FactoryConfiguration getInstance()  {
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
