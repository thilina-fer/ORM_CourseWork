package lk.ijse.orm_coursework.controller;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.orm_coursework.bo.BOFactory;
import lk.ijse.orm_coursework.bo.BOTypes;
import lk.ijse.orm_coursework.bo.custom.StudentBO;
import lk.ijse.orm_coursework.dto.StudentDTO;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class StudentPopUpController implements Initializable {

    private final StudentBO studentsBO = (StudentBO) BOFactory.getInstance().getBO(BOTypes.STUDENT);

    private final String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final String phoneRegex = "^07\\d{8}$";


    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtEmail;
    public TextField txtContact;
    public TextField txtAddress;
    public TextField txtDOB;
    public TextField txtRegDate;
    public Button btnSave;
    public Button btnUpdate;
    public Label lblStudentId;

    public void btnSaveOncAction(ActionEvent actionEvent) {

        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();
        String dob = txtDOB.getText();
        String regDate = txtRegDate.getText();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dobDate = null;
        Date regDateDate = null;
        try {
            dobDate = sdf.parse(dob);
            regDateDate = sdf.parse(regDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }



        boolean isValidEmail = email.matches(emailRegex);
        boolean isValidPhone = contact.matches(phoneRegex);

        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: #7367F0;");

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || contact.isEmpty() || address.isEmpty() || dob.isEmpty() || regDate.isEmpty()) {
            btnSave.setDisable(true);
            btnUpdate.setDisable(true);
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields", ButtonType.OK).show();
            return;
        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
            new Alert(Alert.AlertType.ERROR, "Invalid email format", ButtonType.OK).show();
            return;
        }
        if (!isValidPhone) {
            txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: red;");
            new Alert(Alert.AlertType.ERROR, "Invalid phone number format", ButtonType.OK).show();
            return;
        }
        try {
            boolean isSaved = studentsBO.saveStudents(StudentDTO.builder()
                    .studentId(lblStudentId.getText())
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .phone(contact)
                    .address(address)
                    .dob(dobDate)
                    .registrationDate(regDateDate)
                    .build());
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Student saved successfully", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save student", ButtonType.OK).show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();
        String dob = txtDOB.getText();
        String regDate = txtRegDate.getText();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dobDate = null;
        Date regDateDate = null;
        try {
            dobDate = sdf.parse(dob);
            regDateDate = sdf.parse(regDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        boolean isValidEmail = email.matches(emailRegex);
        boolean isValidPhone = contact.matches(phoneRegex);

        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: #7367F0;");

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || contact.isEmpty() || address.isEmpty() || dob.isEmpty() || regDate.isEmpty()) {
            btnSave.setDisable(true);
            btnUpdate.setDisable(true);
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields", ButtonType.OK).show();
            return;
        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
            new Alert(Alert.AlertType.ERROR, "Invalid email format", ButtonType.OK).show();
            return;
        }
        if (!isValidPhone) {
            txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: red;");
            new Alert(Alert.AlertType.ERROR, "Invalid phone number format", ButtonType.OK).show();
            return;
        }
        try {
            boolean isUpdated = studentsBO.updateStudents(StudentDTO.builder()
                    .studentId(lblStudentId.getText())
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .phone(contact)
                    .address(address)
                    .dob(dobDate)
                    .registrationDate(regDateDate)
                    .build());
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Student updated successfully", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update student", ButtonType.OK).show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lblStudentId.setText(studentsBO.generateNewStudentId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
