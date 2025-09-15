package lk.ijse.orm_coursework.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.orm_coursework.bo.BOFactory;
import lk.ijse.orm_coursework.bo.BOTypes;
import lk.ijse.orm_coursework.bo.custom.StudentBO;
import lk.ijse.orm_coursework.bo.exception.DuplicateException;
import lk.ijse.orm_coursework.dto.StudentDTO;

import java.sql.SQLException;

public class StudentPopUpPageController {
    public Label lblStudentId;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtEmail;
    public TextField txtContact;
    public TextField txtAddress;
    public TextField txtDOB;
    public TextField txtRegDate;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnClean;

    private final StudentBO studentBO = BOFactory.getInstance().getBO(BOTypes.STUDENT);

    public void btnCleanOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveOncAction(ActionEvent actionEvent) {
        String studentId = lblStudentId.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();
        String dob = txtDOB.getText();
        String regDate = txtRegDate.getText();

//        boolean isValidEmail = email.matches(emailPattern);
//        boolean isValidPhone = contact.matches(phonePattern);

        txtEmail.setStyle(txtEmail.getStyle()+";-fx-border-color: #BB25B9;");
        txtContact.setStyle(txtContact.getStyle()+";-fx-border-color: #BB25B9;");

//        if (!isValidEmail) {
//            txtEmail.setStyle(txtEmail.getStyle()+";-fx-border-color: red;");
//        }
//
//        if (!isValidPhone) {
//            txtContact.setStyle(txtContact.getStyle()+";-fx-border-color: red;");
//        }

        StudentDTO studentDTO = new StudentDTO(
                studentId,
                firstName,
                lastName,
                email,
                contact,
                address,
                dob,
                regDate
        );

//        if (isValidEmail && isValidPhone) {
            try {
                studentBO.saveStudent(studentDTO);
//                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Student Saved!").show();
            }catch (DuplicateException e){
                System.out.println(e.getMessage());
                new Alert(Alert.AlertType.ERROR, "Student save failed").show();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
//            }
        }

    }

    public void loadNextId() throws SQLException {
        String nextId = studentBO.getNextId();
        lblStudentId.setText(nextId);
    }

    private void resetPage() throws SQLException {
        StudentPopUpPageController studentPopUpPageController = new StudentPopUpPageController();
        studentPopUpPageController.loadNextId();
//        loadTableData();
        resetPage();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtFirstName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        txtContact.setText("");
        txtAddress.setText("");
        txtDOB.setText("");
        txtRegDate.setText("");
    }

}
