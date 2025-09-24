package lk.ijse.orm_coursework.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PaymentsTM {
    private String paymentId;
    private Date paymentDate;
    private double amount;
    private String paymentMethod;
    private String status;
    private String studentId;

}
