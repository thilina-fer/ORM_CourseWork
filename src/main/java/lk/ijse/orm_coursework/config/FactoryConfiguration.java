package lk.ijse.orm_coursework.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;

    private FactoryConfiguration() {
        Configuration configuration = new Configuration();
        configuration.configure();
    }

    public static FactoryConfiguration getInstance() {
        return factoryConfiguration == null ?
                factoryConfiguration = new FactoryConfiguration()
                :
                factoryConfiguration;
    }

    public Session session(){
        Session session = sessionFactory.openSession();
        return session;
    }

    public Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
}
