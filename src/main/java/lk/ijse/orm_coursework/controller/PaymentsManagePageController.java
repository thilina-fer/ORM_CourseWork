package lk.ijse.orm_coursework.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.orm_coursework.bo.BOFactory;
import lk.ijse.orm_coursework.bo.BOTypes;
import lk.ijse.orm_coursework.bo.custom.PaymentsBO;
import lk.ijse.orm_coursework.dto.tm.PaymentsTM;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PaymentsManagePageController implements Initializable {
    public TableView<PaymentsTM> tblPayment;
    public TableColumn<PaymentsTM, String> colPaymentId;
    public TableColumn<PaymentsTM, String> colPaymentDate;
    public TableColumn<PaymentsTM, String> colAmount;
    public TableColumn<PaymentsTM, String> colPaymentMethod;
    public TableColumn<PaymentsTM, String> colStatus;
    public TableColumn<PaymentsTM, String> colStudentId;

    private final PaymentsBO paymentsBO = (PaymentsBO) BOFactory.getInstance().getBO(BOTypes.PAYMENTS);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));

        try {
            loadAllPayments();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void loadAllPayments() {
        try {
            tblPayment.setItems(FXCollections.observableArrayList(
                        paymentsBO.getAllPayments().stream().map(dto -> new PaymentsTM(
                                dto.getPaymentId(),
                            dto.getPaymentDate(),
                            dto.getAmount(),
                            dto.getPaymentMethod(),
                            dto.getStatus(),
                            dto.getStudentId()
                    )).toList()
            ));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/PaymentsManagePOPUpPage.fxml"));
            Parent parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Payment");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL); // Block input to other windows
            stage.showAndWait();
            loadAllPayments();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to open the popup!").show();
        }
    }

    public void btnDeleteOnAAction(ActionEvent actionEvent) {
    }

    public void onClickTable(MouseEvent mouseEvent) {
    }
}
