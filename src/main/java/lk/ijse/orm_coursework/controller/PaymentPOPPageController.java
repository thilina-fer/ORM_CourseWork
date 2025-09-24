package lk.ijse.orm_coursework.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.orm_coursework.bo.BOFactory;
import lk.ijse.orm_coursework.bo.BOTypes;
import lk.ijse.orm_coursework.bo.custom.PaymentsBO;
import lk.ijse.orm_coursework.bo.custom.QueryBO;
import lk.ijse.orm_coursework.bo.custom.StudentBO;
import lk.ijse.orm_coursework.dto.PaymentsDTO;
import lk.ijse.orm_coursework.dto.tm.PaymentsTM;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class PaymentPOPPageController implements Initializable {

    public TextField txtPaymentDate;
    public ComboBox cmbPaymentMethod;
    public TextField txtStatus;
    public Button btnSave;
    public Button btnUpdate;
    public Label lblPaymentId;
    public Label lblAmount;
    public ComboBox cmbStudentId;

    private final StudentBO studentsBO = (StudentBO) BOFactory.getInstance().getBO(BOTypes.STUDENT);
    private final PaymentsBO paymentsBO = (PaymentsBO) BOFactory.getInstance().getBO(BOTypes.PAYMENTS);
    private final QueryBO queryBO = (QueryBO) BOFactory.getInstance().getBO(BOTypes.QUERY);

    public void btnSaveOncAction(ActionEvent actionEvent) {
        String paymentId = lblPaymentId.getText();
        String paymentDate = txtPaymentDate.getText();
        double amount = Double.parseDouble(lblAmount.getText());
        String paymentMethod = String.valueOf(cmbPaymentMethod.getValue());
        String status = txtStatus.getText();
        String studentId = cmbStudentId.getValue().toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(paymentDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (paymentId.isEmpty() || paymentDate.isEmpty() || lblAmount.getText().isEmpty() || paymentMethod.isEmpty() || status.isEmpty()|| studentId.isEmpty()) {
            btnSave.setDisable(true);
            btnUpdate.setDisable(true);
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").show();
            return;
        }

        try {
            boolean isSaved = paymentsBO.savePayments(new PaymentsDTO(
                    paymentId,
                    date,
                    amount,
                    paymentMethod,
                    status,
                    studentId
            ));

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Payment saved successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save payment").show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lblPaymentId.setText(paymentsBO.generateNewPaymentId());
            cmbPaymentMethod.setItems(FXCollections.observableArrayList("Cash", "Card", "Online"));
            cmbStudentId.setItems(FXCollections.observableArrayList(studentsBO.getAllStudentIds()));
            cmbStudentId.valueProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    setAmountByStudentId(newVal.toString());
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setAmountByStudentId(String studentId) {
        try {
            double amount = queryBO.getTotalCourseAmountByStudentId(studentId);
            lblAmount.setText(String.valueOf(amount));
        } catch (Exception e) {
            lblAmount.setText("0.0");
            new Alert(Alert.AlertType.ERROR, "Failed to fetch amount").show();
        }
    }

}
