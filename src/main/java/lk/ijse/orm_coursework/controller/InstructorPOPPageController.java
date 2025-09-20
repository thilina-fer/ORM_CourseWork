package lk.ijse.orm_coursework.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.orm_coursework.bo.BOFactory;
import lk.ijse.orm_coursework.bo.BOTypes;
import lk.ijse.orm_coursework.bo.custom.InstructorBO;
import lk.ijse.orm_coursework.dto.InstructorDTO;

import java.net.URL;
import java.util.ResourceBundle;

public class InstructorPOPPageController  implements Initializable {
    public Label lblId;
    public TextField txtFirstName;
    public TextField txtLastNam;
    public TextField txtEmail;
    public TextField txtContact;
    public TextField txtSpecialization;
    public TextField txtAvailability;
    public Button btnSave;
    public Button btnUpdate;



    InstructorBO instructorBO = (InstructorBO) BOFactory.getInstance().getBO(BOTypes.INSTRUCTOR) ;
    private final String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final String phoneRegex = "^07\\d{8}$";

    public void btnSaveOncAction(ActionEvent actionEvent) {
        String firstName = txtFirstName.getText();
        String lastName = txtLastNam.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String specialization = txtSpecialization.getText();
        String availability = txtAvailability.getText();

        boolean isValidEmail = email.matches(emailRegex);
        boolean isValidPhone = contact.matches(phoneRegex);

        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: #7367F0;");

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || contact.isEmpty() || specialization.isEmpty() || availability.isEmpty()) {
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
            boolean isSaved = instructorBO.saveInstructors(InstructorDTO.builder()
                    .instructorId(lblId.getText())
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .contact(contact)
                    .specialization(specialization)
                    .availability(availability)
                    .build());
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Instructor saved successfully", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save instructor", ButtonType.OK).show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

        String firstName = txtFirstName.getText();
        String lastName = txtLastNam.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String specialization = txtSpecialization.getText();
        String availability = txtAvailability.getText();

        boolean isValidEmail = email.matches(emailRegex);
        boolean isValidPhone = contact.matches(phoneRegex);

        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: #7367F0;");

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || contact.isEmpty() || specialization.isEmpty() || availability.isEmpty()) {
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
            boolean isUpdated = instructorBO.updateInstructors(InstructorDTO.builder()
                    .instructorId(lblId.getText())
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .contact(contact)
                    .specialization(specialization)
                    .availability(availability)
                    .build());
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Instructor updated successfully", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update instructor", ButtonType.OK).show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lblId.setText(instructorBO.generateNewInstructorsId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
