package lk.ijse.orm_coursework.dao.custom.impl;

import lk.ijse.orm_coursework.config.FactoryConfiguration;
import lk.ijse.orm_coursework.dao.custom.StudentDAO;
import lk.ijse.orm_coursework.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StudentDAOImpl implements StudentDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(Student student) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try{
            session.persist(student);
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
    }


    @Override
    public List<Student> getAll() throws SQLException {
       Session session = factoryConfiguration.getSession();
       try {
           Query<Student> query = session.createQuery("from Student", Student.class);
           List<Student> studentList = query.list();
           return studentList;
       }finally {
           session.close();
       }
    }

    @Override
    public boolean update(Student student) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try{
            session.merge(student);
            transaction.commit();
            return true;
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
            Student student = session.get(Student.class,id);
            if (student != null){
                session.remove(student);
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
           Query<String> query = session.createQuery("SELECT cus.id FROM Student cus ORDER BY cus.id DESC",
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
    public List<String> getAllIds() throws SQLException {
        return List.of();
    }

    @Override
    public Optional<Student> findById(String id) throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            Student student = session.get(Student.class, id);
            return Optional.ofNullable(student);
        } finally {
            session.close();
        }
    }
}
