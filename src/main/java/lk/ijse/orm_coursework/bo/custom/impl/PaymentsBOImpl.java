package lk.ijse.orm_coursework.bo.custom.impl;

import lk.ijse.orm_coursework.bo.custom.PaymentsBO;
import lk.ijse.orm_coursework.bo.exception.DuplicateException;
import lk.ijse.orm_coursework.bo.exception.NotFoundException;
import lk.ijse.orm_coursework.bo.util.EntityDTOConverter;
import lk.ijse.orm_coursework.dao.DAOFactory;
import lk.ijse.orm_coursework.dao.DAOTypes;
import lk.ijse.orm_coursework.dao.custom.PaymentDAO;
import lk.ijse.orm_coursework.dto.PaymentsDTO;
import lk.ijse.orm_coursework.entity.Payments;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentsBOImpl implements PaymentsBO {

    private final PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOTypes.PAYMENTS);
    private final EntityDTOConverter converter = new EntityDTOConverter();

    @Override
    public List<PaymentsDTO> getAllPayments() throws Exception {
        List<Payments> paymentsList = paymentDAO.getAll();
        List<PaymentsDTO> list = new ArrayList<>();
        for (Payments payments : paymentsList) {
            list.add(converter.getPaymentsDTO(payments));
        }
        return list;
    }

    @Override
    public String getLastPaymentId() throws Exception {
        return paymentDAO.getLastId();
    }

    @Override
    public boolean savePayments(PaymentsDTO t) throws Exception {
        Optional<Payments> payments = paymentDAO.findById(t.getPaymentId());
        if (payments.isPresent()) {
            throw new DuplicateException("Payment already exists");
        }
        if (t.getStudentId() == null) {
            throw new NotFoundException("Student id is null");
        }
        return paymentDAO.save(converter.getPaymentsEntity(t));
    }

    @Override
    public boolean updatePayments(PaymentsDTO t) throws Exception {
        Optional<Payments> payments = paymentDAO.findById(t.getPaymentId());
        if (payments.isEmpty()) {
            throw new DuplicateException("Payment Not Found");
        }
        return paymentDAO.update(converter.getPaymentsEntity(t));
    }

    @Override
    public boolean deletePayments(String id) throws Exception {
        Optional<Payments> payments = paymentDAO.findById(id);
        if (payments.isEmpty()) {
            throw new DuplicateException("Payment Not Found");
        }
        return paymentDAO.delete(id);
    }

    @Override
    public List<String> getAllPaymentIds() throws Exception {
        return paymentDAO.getAllIds();
    }

    @Override
    public Optional<PaymentsDTO> findByPaymentId(String id) throws Exception {
        Optional<Payments> payments = paymentDAO.findById(id);
        if (payments.isPresent()) {
            return Optional.of(converter.getPaymentsDTO(payments.get()));
        }
        return Optional.empty();
    }

    @Override
    public String generateNewPaymentId() {
        return paymentDAO.generateNewId();
    }
}
