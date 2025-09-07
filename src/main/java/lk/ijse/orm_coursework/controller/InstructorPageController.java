package lk.ijse.orm_coursework.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.orm_coursework.bo.BOFactory;
import lk.ijse.orm_coursework.bo.BOTypes;
import lk.ijse.orm_coursework.bo.custom.InstructorBO;
import lk.ijse.orm_coursework.dto.InstructorDTO;
import lk.ijse.orm_coursework.dto.tm.InstructorTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class InstructorPageController implements Initializable {
    public Label lblInstructorId;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtEmail;
    public TextField txtContact;
    public TextField txtSpecialization;
    public TextField txtAvailability;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnClean;

    public TableView<InstructorTM> tblInstructor;
    public TableColumn<InstructorTM , String> colId;
    public TableColumn<InstructorTM , String> colFirstName;
    public TableColumn<InstructorTM , String> colLastName;
    public TableColumn<InstructorTM , String> colEmail;
    public TableColumn<InstructorTM , String> colContact;
    public TableColumn<InstructorTM , String> colSpecialization;
    public TableColumn<InstructorTM , String> colAvailability;

    private final InstructorBO instructorBO = BOFactory.getInstance().getBO(BOTypes.INSTRUCTOR);

    private final String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));

        try{
            resetPage();

        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void resetPage() throws SQLException {
        loadNextId();
        loadTableData();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtFirstName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        txtContact.setText("");
        txtSpecialization.setText("");
        txtAvailability.setText("");
    }

    private void loadTableData() throws SQLException {
        tblInstructor.setItems(FXCollections.observableArrayList(
                instructorBO.getAllInstructor().stream().map(instructorDTO -> new InstructorTM(
                        instructorDTO.getInstructorId(),
                        instructorDTO.getFirstName(),
                        instructorDTO.getLastName(),
                        instructorDTO.getEmail(),
                        instructorDTO.getContact(),
                        instructorDTO.getSpecialization(),
                        instructorDTO.getAvailability()
                )).toList()
        ));
    }

    private void loadNextId() throws SQLException {
        String nextId = instructorBO.getNextId();
        lblInstructorId.setText(nextId);
    }

    public void btnSaveOncAction(ActionEvent actionEvent) {
        String instructorId = lblInstructorId.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String specialization = txtSpecialization.getText();
        String availability = txtAvailability.getText();

        boolean isMatchedEmail = email.matches(emailPattern);
        boolean isMatchedContact = contact.matches(phonePattern);

        txtEmail.setStyle(txtEmail.getStyle()+";-fx-border-color: #BB25B9;");
        txtContact.setStyle(txtContact.getStyle()+";-fx-border-color: #BB25B9;");

        if (!isMatchedEmail) {
            txtEmail.setStyle(txtEmail.getStyle()+";-fx-border-color: red;");
        }

        if (!isMatchedContact) {
            txtContact.setStyle(txtContact.getStyle()+";-fx-border-color: red;");
        }

        InstructorDTO instructorDTO = new InstructorDTO(
                instructorId,
                firstName,
                lastName,
                email,
                contact,
                specialization,
                availability
        );

        if (isMatchedEmail && isMatchedContact) {
            try {
                instructorBO.saveInstructor(instructorDTO);
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Instructor has been saved successfully", ButtonType.OK).show();
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String instructorId = lblInstructorId.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String specialization = txtSpecialization.getText();
        String availability = txtAvailability.getText();

        boolean isMatchedEmail = email.matches(emailPattern);
        boolean isMatchedContact = contact.matches(phonePattern);

        txtEmail.setStyle(txtEmail.getStyle()+";-fx-border-color: #BB25B9;");
        txtContact.setStyle(txtContact.getStyle()+";-fx-border-color: #BB25B9;");

        if (!isMatchedEmail) {
            txtEmail.setStyle(txtEmail.getStyle()+";-fx-border-color: red;");
        }

        if (!isMatchedContact) {
            txtContact.setStyle(txtContact.getStyle()+";-fx-border-color: red;");
        }

        InstructorDTO instructorDTO = new InstructorDTO(
                instructorId,
                firstName,
                lastName,
                email,
                contact,
                specialization,
                availability
        );

        if (isMatchedEmail && isMatchedContact) {
            try {
                instructorBO.updateInstructor(instructorDTO);
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Instructor has been saved successfully", ButtonType.OK).show();
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
            }
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this student record?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            String instructorId = tblInstructor.getSelectionModel().getSelectedItem().getInstructorId();
            try{
                instructorBO.deleteInstructor(instructorId);
                tblInstructor.getItems().remove(tblInstructor.getSelectionModel().getSelectedItem());
                tblInstructor.getSelectionModel().clearSelection();
                resetPage();
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Failed to delete the instructor").show();
            }finally {
                new Alert(Alert.AlertType.INFORMATION, "Instructor Deleted!").show();
            }
        }
    }

    public void btnCleanOnAction(ActionEvent actionEvent) throws SQLException {
        resetPage();
    }

    public void onClickTable(MouseEvent mouseEvent) {
        InstructorTM instructorTM = tblInstructor.getSelectionModel().getSelectedItem();

        if (instructorTM != null) {
            lblInstructorId.setText(instructorTM.getInstructorId());
            txtFirstName.setText(instructorTM.getFirstName());
            txtLastName.setText(instructorTM.getLastName());
            txtEmail.setText(instructorTM.getEmail());
            txtContact.setText(instructorTM.getContact());
            txtSpecialization.setText(instructorTM.getSpecialization());
            txtAvailability.setText(instructorTM.getAvailability());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }

    }


}
