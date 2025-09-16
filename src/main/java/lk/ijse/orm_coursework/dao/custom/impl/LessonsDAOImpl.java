package lk.ijse.orm_coursework.dao.custom.impl;

import lk.ijse.orm_coursework.config.FactoryConfiguration;
import lk.ijse.orm_coursework.dao.custom.LessonsDAO;
import lk.ijse.orm_coursework.dto.LessonsDTO;
import lk.ijse.orm_coursework.entity.Lessons;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class LessonsDAOImpl implements LessonsDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public List<Lessons> getAll() throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Lessons> query = session.createQuery("from Lessons ",Lessons.class);
            List<Lessons> lessonsList = query.list();
            return lessonsList;
        }finally {
            session.close();
        }

    }

    @Override
    public String getLastId() throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery("SELECT l.lessonId FROM Lessons l ORDER BY l.lessonId DESC", String.class)
                    .setMaxResults(1);
            List<String> lessonsList = query.list();
            if (lessonsList.isEmpty()) {
                return null;
            }
            return lessonsList.getFirst();
        } finally {
            session.close();
        }
    }

    @Override
    public boolean save(Lessons lessons) throws Exception {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(lessons);
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
    public boolean update(Lessons lessons) throws Exception {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(lessons);
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
            Lessons lessons = (Lessons) session.get(Lessons.class, id);
            if (lessons != null) {
                session.remove(lessons);
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
            Query<String> query = session.createQuery("SELECT l.lessonId FROM Lessons l", String.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Lessons> findById(String id) throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            Lessons lessons = session.get(Lessons.class, id);
            return Optional.ofNullable(lessons);
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
            return "L-001";
        } else {
            int num = Integer.parseInt(lastId.split("-")[1]);
            num++;
            return String.format("L-%03d", num);
        }
    }
}