package lk.ijse.orm_coursework.dao.custom.impl;

import lk.ijse.orm_coursework.config.FactoryConfiguration;
import lk.ijse.orm_coursework.dao.custom.StudentCourseDetailDAO;
import lk.ijse.orm_coursework.dto.StudentCourseDetailsDTO;
import lk.ijse.orm_coursework.entity.StudentCourseDetails;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class StudentCourseDetailsDAOImpl implements StudentCourseDetailDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();


    @Override
    public List<StudentCourseDetails> getAll() throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            Query<StudentCourseDetails> query = session.createQuery("from StudentCourseDetails where course.id=:courseId");
            List<StudentCourseDetails> studentCourseDetailsList = query.list();
            return studentCourseDetailsList;
        }finally {
            session.close();
        }
    }

    @Override
    public String getLastId() throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery("SELECT scd.studentCourseId FROM StudentCourseDetails scd ORDER BY scd.studentCourseId DESC", String.class)
                    .setMaxResults(1);
            List<String> studentCourseList = query.list();
            if (studentCourseList.isEmpty()) {
                return null;
            }
            return studentCourseList.getFirst();
        } finally {
            session.close();
        }
    }

    @Override
    public boolean save(StudentCourseDetails studentCourseDetails) throws Exception {
       Session session = factoryConfiguration.getSession();
       Transaction transaction = session.beginTransaction();

       try {
           session.persist(studentCourseDetails);
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
    public boolean update(StudentCourseDetails studentCourseDetails) throws Exception {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(studentCourseDetails);
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
    public boolean delete(String id) throws Exception {
        Session session  = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            StudentCourseDetails studentCourseDetails = (StudentCourseDetails) session.get(StudentCourseDetails.class, id);
            if (studentCourseDetails != null) {
                session.remove(studentCourseDetails);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public List<String> getAllIds() throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery("SELECT scd.studentCourseId FROM StudentCourseDetails scd", String.class);
            return   query.list();
        }finally {
            session.close();
        }
    }

    @Override
    public Optional<StudentCourseDetails> findById(String id) throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            StudentCourseDetails studentCourseDetails = session.get(StudentCourseDetails.class, id);
            return Optional.ofNullable(studentCourseDetails);
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
            return "SCD-001";
        } else {
            int num = Integer.parseInt(lastId.split("-")[1]);
            num++;
            return String.format("SCD-%03d", num);
        }
    }
}
