package lk.ijse.orm_coursework.dao.custom.impl;

import lk.ijse.orm_coursework.config.FactoryConfiguration;
import lk.ijse.orm_coursework.dao.custom.StudentDAO;
import lk.ijse.orm_coursework.entity.Students;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StudentDAOImpl implements StudentDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(Students students) throws Exception {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(students);
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
    public boolean update(Students students) throws Exception {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try{
            session.merge(students);
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
            Students student = session.get(Students.class,id);
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
    public List<Students> getAll() throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Students> query = session.createQuery("from Students",Students.class);
            List<Students> studentsList = query.list();
            System.out.println(studentsList.getFirst().getCourses());
            return studentsList;
        }finally {
            session.close();
        }
    }

    @Override
    public List<String> getAllIds() throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery("SELECT s.studentId FROM Students s", String.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public String getLastId() throws SQLException {
       Session session = factoryConfiguration.getSession();
       try {
           Query<String> query = session.createQuery("SELECT stu.id FROM Students stu ORDER BY stu.id DESC",
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
    public Optional<Students> findById(String id) throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            Students student = session.get(Students.class, id);
            return Optional.ofNullable(student);
        } finally {
            session.close();
        }
    }

    @Override
    public String generateNewId() {
        String lastId = null;
        try {
            lastId = getLastId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (lastId == null) {
            return "S-001";
        } else {
            int num = Integer.parseInt(lastId.split("-")[1]);
            num++;
            return String.format("S-%03d", num);
        }
    }
}
