package lk.ijse.orm_coursework.dao.custom.impl;

import lk.ijse.orm_coursework.config.FactoryConfiguration;
import lk.ijse.orm_coursework.dao.custom.UserDAO;
import lk.ijse.orm_coursework.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public List<User> getAll() throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            Query<User> query = session.createQuery("from User", User.class);
            List<User> list = query.getResultList();
            return list;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean save(User user) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(user);
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean update(User user) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(user);
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try{
            User user = session.get(User.class,id);
            if (user != null) {
                session.remove(user);
                transaction.commit();
                return true;
            }
            return false;
        }catch (Exception e){
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public String getLastId() throws SQLException {
        Session session = factoryConfiguration.getSession();

        try {
            Query<String> query = session.createQuery("SELECT use.id FROM User use ORDER BY use.id DESC",
                    String.class).setMaxResults(1);
            List<String> studentList = query.list();
            if (studentList.isEmpty()) {
                return null;

            }
            return studentList.get(0);
        }finally {
            session.close();
        }
    }



    @Override
    public Optional<User> findById(String id) throws SQLException {
        Session session = factoryConfiguration.getSession();

        try {
            User user = session.get(User.class,id);
            return Optional.ofNullable(user);
        }finally {
            session.close();
        }
    }
}
