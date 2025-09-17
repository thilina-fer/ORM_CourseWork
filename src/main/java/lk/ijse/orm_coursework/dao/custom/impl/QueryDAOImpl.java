package lk.ijse.orm_coursework.dao.custom.impl;

import lk.ijse.orm_coursework.config.FactoryConfiguration;
import lk.ijse.orm_coursework.dao.custom.QueryDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;


public class QueryDAOImpl implements QueryDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();


    @Override
    public int getStudentCountForLesson(String lessonId) {
        Session session = factoryConfiguration.getSession();
        try {
            // Assuming there is a mapping between Lessons and Students (e.g., a Set<Student> in Lessons entity)
            String hql = "SELECT COUNT(s.studentId) FROM Students s JOIN s.lessons l WHERE l.lessonId = :lessonId";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("lessonId", lessonId);
            Long count = query.uniqueResult();
            return count != null ? count.intValue() : 0;
        } finally {
            session.close();
        }
    }
}
