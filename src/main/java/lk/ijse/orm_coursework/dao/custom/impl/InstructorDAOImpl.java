package lk.ijse.orm_coursework.dao.custom.impl;

import lk.ijse.orm_coursework.config.FactoryConfiguration;
import lk.ijse.orm_coursework.dao.custom.InstructorDAO;
import lk.ijse.orm_coursework.entity.Instructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class InstructorDAOImpl implements InstructorDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(Instructor instructor) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try{
            session.persist(instructor);
            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public List<Instructor> getAll() throws SQLException {
        Session session = factoryConfiguration.getSession();

        try{
            Query<Instructor> query = session.createQuery("FROM Instructor", Instructor.class);
            return query.list();
        }finally {
            session.close();
        }
    }

    @Override
    public boolean update(Instructor instructor) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try{
            session.merge(instructor);
            transaction.commit();
            return  true;

        }catch (Exception e){
            if (transaction != null) transaction.rollback();
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
            Instructor instructor = session.get(Instructor.class, id);
            if (instructor != null) {
                session.remove(instructor);
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
            Query<String> query = session.createQuery("SELECT ins.id FROM Instructor ins ORDER BY ins.id DESC",
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
    public Optional<Instructor> findById(String id) throws SQLException {
        Session session = factoryConfiguration.getSession();

        try{
            Instructor instructor = session.get(Instructor.class, id);
            return Optional.ofNullable(instructor);
        }finally {
            session.close();
        }
    }
}
