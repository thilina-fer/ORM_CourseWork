package lk.ijse.orm_coursework.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.orm_coursework.bo.BOFactory;
import lk.ijse.orm_coursework.bo.BOTypes;
import lk.ijse.orm_coursework.bo.custom.PaymentsBO;
import lk.ijse.orm_coursework.dto.InstructorDTO;
import lk.ijse.orm_coursework.dto.PaymentsDTO;
import lk.ijse.orm_coursework.dto.tm.InstructorTM;
import lk.ijse.orm_coursework.dto.tm.PaymentsTM;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentsManagePageController implements Initializable {
    public TableView<PaymentsTM> tblPayment;
    public TableColumn<PaymentsTM, String> colPaymentId;
    public TableColumn<PaymentsTM, String> colPaymentDate;
    public TableColumn<PaymentsTM, String> colAmount;
    public TableColumn<PaymentsTM, String> colPaymentMethod;
    public TableColumn<PaymentsTM, String> colStatus;
    public TableColumn<PaymentsTM, String> colStudentId;
    public TableColumn<?, ?> colAction;

    private final PaymentsBO paymentsBO = (PaymentsBO) BOFactory.getInstance().getBO(BOTypes.PAYMENTS);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));

        try {
            loadAllPayments();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void loadAllPayments() {
        try {
            tblPayment.setItems(FXCollections.observableArrayList(
                    paymentsBO.getAllPayments().stream().map(paymentsDTO -> {
                        Pane action = new Pane();
                        Button btnEdit = new Button("✏");
                        btnEdit.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
                        btnEdit.setPrefWidth(30);
                        btnEdit.setLayoutX(40);
                        btnEdit.setCursor(javafx.scene.Cursor.HAND);
                        btnEdit.setOnAction(event -> onUpdate(paymentsDTO));

                        Button btnDelete = new Button("🗑");
                        btnDelete.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
                        btnDelete.setPrefWidth(30);
                        btnDelete.setLayoutX(0);
                        btnDelete.setCursor(javafx.scene.Cursor.HAND);
                        btnDelete.setOnAction(event -> onDelete(paymentsDTO.getPaymentId()));

                        action.getChildren().addAll(btnDelete, btnEdit);
                        return new PaymentsTM(
                                paymentsDTO.getPaymentId(),
                            paymentsDTO.getPaymentDate(),
                            paymentsDTO.getAmount(),
                            paymentsDTO.getPaymentMethod(),
                            paymentsDTO.getStatus(),
                            paymentsDTO.getStudentId(),
                                action
                        );
                    }).toList()
            ));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        try {
//            tblPayment.setItems(FXCollections.observableArrayList(
//                        paymentsBO.getAllPayments().stream().map(dto -> new PaymentsTM(
//                                dto.getPaymentId(),
//                            dto.getPaymentDate(),
//                            dto.getAmount(),
//                            dto.getPaymentMethod(),
//                            dto.getStatus(),
//                            dto.getStudentId()
//                    )).toList()
//            ));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }


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

    public void onDelete(String id){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this payment?",
                ButtonType.YES,
                ButtonType.NO
        );
        alert.setTitle("Delete Payment");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {

            try {
                boolean isDeleted = paymentsBO.deletePayments(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Instructor deleted successfully!").show();
                    loadAllPayments();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the instructor!").show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onUpdate(PaymentsDTO paymentsDTO) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/PaymentsManagePOPUpPage.fxml"));
            Parent parent = fxmlLoader.load();

            PaymentPOPPageController controller = fxmlLoader.getController();
            controller.loadData(paymentsDTO);

            Stage stage = new Stage();
            stage.setTitle("Update Payments");
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
