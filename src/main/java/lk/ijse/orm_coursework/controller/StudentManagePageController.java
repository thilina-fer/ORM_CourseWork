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
import lk.ijse.orm_coursework.bo.custom.StudentBO;
import lk.ijse.orm_coursework.dto.tm.StudentTM;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentManagePageController implements Initializable {
    public TableView<StudentTM> tblStudent;
    public TableColumn<StudentTM, String> colId;
    public TableColumn<StudentTM, String> colFirstName;
    public TableColumn<StudentTM, String> colLastName;
    public TableColumn<StudentTM, String> colEmail;
    public TableColumn<StudentTM, String> colContact;
    public TableColumn<StudentTM, String> colAddress;
    public TableColumn<StudentTM, String> colDOB;
    public TableColumn<StudentTM, String> colRegDate;
    public TableColumn<? , ?> colAction;


    private final StudentBO studentsBO = (StudentBO) BOFactory.getInstance().getBO(BOTypes.STUDENT);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colRegDate.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("delete"));

        try {
            loadAllStudents();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllStudents() {
        try {
            tblStudent.setItems(FXCollections.observableArrayList(
                    studentsBO.getAllStudents().stream().map(studentDTO -> new StudentTM(
                            studentDTO.getStudentId(),
                            studentDTO.getFirstName(),
                            studentDTO.getLastName(),
                            studentDTO.getEmail(),
                            studentDTO.getPhone(),
                            studentDTO.getAddress(),
                            studentDTO.getDob(),
                            studentDTO.getRegistrationDate()
                    )).toList()
            ));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



//    public void btnAddOnAction(ActionEvent actionEvent) {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/StudentManagePopPage.fxml"));
//            Parent parent = fxmlLoader.load();
//
//            Stage stage = new Stage();
//            stage.setTitle("Add Student");
//            stage.setScene(new Scene(parent));
//            stage.initModality(Modality.APPLICATION_MODAL); // Block input to other windows
//            stage.showAndWait();
//        } catch (IOException e) {
//            new Alert(Alert.AlertType.ERROR, "Failed to open the popup!").show();
//        }
//    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/StudentManagePopPage.fxml"));
            Parent parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Student");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false); // Optional: prevent resizing
            stage.showAndWait();

            // Optionally refresh the table after closing the popup
            loadAllStudents();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to open the popup!\n" + e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {

    }

//    public void btnDeleteOnAAction(ActionEvent actionEvent) {
//        Alert alert = new Alert(
//                Alert.AlertType.CONFIRMATION,
//                "Are you sure whether you want to delete this student?",
//                ButtonType.YES,
//                ButtonType.NO
//        );
//        alert.setTitle("Delete Student");
//
//        Optional<ButtonType> result = alert.showAndWait();
//
//        if (result.isPresent() && result.get() == ButtonType.YES) {
//            StudentTM selectedItem = tblStudent.getSelectionModel().getSelectedItem();
//            if (selectedItem == null) {
//                new Alert(Alert.AlertType.WARNING, "Please select a student to delete!").show();
//                return;
//            }
//
//            try {
//                boolean isDeleted = studentsBO.deleteStudent(selectedItem.getStudentId());
//                if (isDeleted) {
//                    new Alert(Alert.AlertType.INFORMATION, "Student deleted successfully!").show();
//                    loadAllStudents();
//                } else {
//                    new Alert(Alert.AlertType.ERROR, "Failed to delete the student!").show();
//                }
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

//    public void onClickTable(MouseEvent mouseEvent) {
//        if (mouseEvent.getClickCount() == 2) {
//            StudentTM selectedItem = tblStudent.getSelectionModel().getSelectedItem();
//            if (selectedItem == null) {
//                new Alert(Alert.AlertType.WARNING, "Please select a student to update!").show();
//                return;
//            }
//
//            try {
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/StudentManagePopPage.fxml"));
//                Parent parent = fxmlLoader.load();
//
//                StudentPopUpController controller = fxmlLoader.getController();
//                controller.btnUpdateOnAction(selectedItem);
//
//                Stage stage = new Stage();
//                stage.setTitle("Update Student");
//                stage.setScene(new Scene(parent));
//                stage.initModality(Modality.APPLICATION_MODAL); // Block input to other windows
//                stage.showAndWait();
//
//                // Refresh the table after the popup is closed
//                loadAllStudents();
//            } catch (IOException e) {
//                new Alert(Alert.AlertType.ERROR, "Failed to open the popup!").show();
//            }
//        }
//    }
}