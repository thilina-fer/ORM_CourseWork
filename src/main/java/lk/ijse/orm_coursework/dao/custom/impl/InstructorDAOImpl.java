package lk.ijse.orm_coursework.dao.custom.impl;

import lk.ijse.orm_coursework.config.FactoryConfiguration;
import lk.ijse.orm_coursework.dao.custom.InstructorDAO;
import lk.ijse.orm_coursework.entity.Instructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class InstructorDAOImpl implements InstructorDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();


    @Override
    public boolean save(Instructor instructor) throws Exception {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(instructor);
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
    public boolean update(Instructor instructor) throws Exception {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(instructor);
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
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Instructor instructor = (Instructor) session.get(Instructor.class, id);
            if (instructor != null) {
                session.remove(instructor);
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
    public List<Instructor> getAll() throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Instructor> query = session.createQuery("from Instructor ", Instructor.class);
            List<Instructor> instructorList = query.list();
            return instructorList;
        }finally {
            session.close();
        }
    }

    @Override
    public String getLastId() throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery("SELECT i.instructorId FROM Instructor i ORDER BY i.instructorId DESC", String.class)
                    .setMaxResults(1);
            List<String> instructorsList = query.list();
            if (instructorsList.isEmpty()) {
                return null;
            }
            return instructorsList.getFirst();
        } finally {
            session.close();
        }
    }


    @Override
    public List<String> getAllIds() throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery("SELECT i.instructorId FROM Instructor i", String.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Instructor> findById(String id) throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            Instructor instructor = session.get(Instructor.class, id);
            return Optional.ofNullable(instructor);
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
            return "I-001";
        } else {
            int num = Integer.parseInt(lastId.split("-")[1]);
            num++;
            return String.format("I-%03d", num);
        }
    }
}
