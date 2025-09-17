package lk.ijse.orm_coursework.bo.custom;

import lk.ijse.orm_coursework.dto.PaymentsDTO;

import java.util.List;
import java.util.Optional;

public interface PaymentsBO {
    List<PaymentsDTO> getAllPayments() throws Exception;

    String getLastPaymentId() throws Exception;

    boolean savePayments(PaymentsDTO t) throws Exception;

    boolean updatePayments(PaymentsDTO t) throws Exception;

    boolean deletePayments(String id) throws Exception;

    List<String> getAllPaymentIds() throws Exception;

    Optional<PaymentsDTO> findByPaymentId(String id) throws Exception;

    String generateNewPaymentId();
}
