package lk.ijse.orm_coursework.dao.custom.impl;

import lk.ijse.orm_coursework.config.FactoryConfiguration;
import lk.ijse.orm_coursework.dao.custom.PaymentDAO;
import lk.ijse.orm_coursework.entity.Payments;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class PaymentsDAOImpl implements PaymentDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(Payments payments) throws Exception {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(payments);
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
    public boolean update(Payments payments) throws Exception {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(payments);
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
            Payments payments = (Payments) session.get(Payments.class, id);
            if (payments != null) {
                session.remove(payments);
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
    public List<Payments> getAll() throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Payments> query = session.createQuery("from Payments ",Payments.class);
            List<Payments> paymentsList = query.list();
            return paymentsList;
        }finally {
            session.close();
        }
    }

    @Override
    public String getLastId() throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery("SELECT p.paymentId FROM Payments p ORDER BY p.paymentId DESC", String.class)
                    .setMaxResults(1);
            List<String> paymentList = query.list();
            if (paymentList.isEmpty()) {
                return null;
            }
            return paymentList.getFirst();
        } finally {
            session.close();
        }
    }

    @Override
    public List<String> getAllIds() throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery("SELECT p.paymentId FROM  Payments p", String.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Payments> findById(String id) throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            Payments payments = session.get(Payments.class, id);
            return Optional.ofNullable(payments);
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
            return "P-001";
        } else {
            int num = Integer.parseInt(lastId.split("-")[1]);
            num++;
            return String.format("P-%03d", num);
        }
    }
}
