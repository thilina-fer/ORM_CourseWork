package lk.ijse.orm_coursework.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.orm_coursework.bo.BOFactory;
import lk.ijse.orm_coursework.bo.BOTypes;
import lk.ijse.orm_coursework.bo.custom.StudentBO;
import lk.ijse.orm_coursework.bo.exception.DuplicateException;
import lk.ijse.orm_coursework.dto.StudentDTO;
import lk.ijse.orm_coursework.dto.tm.StudentTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentPageController implements Initializable {
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
    
    public TableView<StudentTM> tblStudent;
    public TableColumn<StudentTM , String> colId;
    public TableColumn<StudentTM , String> colFirstName;
    public TableColumn<StudentTM , String> colLastName;
    public TableColumn<StudentTM , String> colEmail;
    public TableColumn<StudentTM , String> colContact;
    public TableColumn<StudentTM , String> colAddress;
    public TableColumn<StudentTM , String> colDOB;
    public TableColumn<StudentTM , String> colRegDate;

    private final StudentBO studentBO = BOFactory.getInstance().getBO(BOTypes.STUDENT);

    private final String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

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

        try {
            resetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to load data").show();
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
        txtAddress.setText("");
        txtDOB.setText("");
        txtRegDate.setText("");
    }

    private void loadNextId() throws SQLException {
        String nextId = studentBO.getNextId();
        lblStudentId.setText(nextId);
    }

    private void loadTableData() throws SQLException {
        tblStudent.setItems(FXCollections.observableArrayList(
                studentBO.getAllStudent().stream().map(studentDTO -> new StudentTM(
                        studentDTO.getStudentId(),
                        studentDTO.getFirstName(),
                        studentDTO.getLastName(),
                        studentDTO.getEmail(),
                        studentDTO.getPhone(),
                        studentDTO.getAddress(),
                        studentDTO.getDob(),
                        studentDTO.getRegistrationDate()
                ) ).toList()
        ));
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

        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = contact.matches(phonePattern);

        txtEmail.setStyle(txtEmail.getStyle()+";-fx-border-color: #BB25B9;");
        txtContact.setStyle(txtContact.getStyle()+";-fx-border-color: #BB25B9;");

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle()+";-fx-border-color: red;");
        }

        if (!isValidPhone) {
            txtContact.setStyle(txtContact.getStyle()+";-fx-border-color: red;");
        }

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

        if (isValidEmail && isValidPhone) {
            try {
                studentBO.saveStudent(studentDTO);
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Student Saved!").show();
            }catch (DuplicateException e){
                System.out.println(e.getMessage());
                new Alert(Alert.AlertType.ERROR, "Student save failed").show();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String studentId = lblStudentId.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();
        String dob = txtDOB.getText();
        String regDate = txtRegDate.getText();

        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = contact.matches(phonePattern);

        txtEmail.setStyle(txtEmail.getStyle()+";-fx-border-color: #BB25B9;");
        txtContact.setStyle(txtContact.getStyle()+";-fx-border-color: #BB25B9;");

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle()+";-fx-border-color: red;");
        }

        if (!isValidPhone) {
            txtContact.setStyle(txtContact.getStyle()+";-fx-border-color: red;");
        }

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

        if (isValidEmail && isValidPhone) {
            try {
                studentBO.updateStudent(studentDTO);
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Student Updated!").show();
            }catch (DuplicateException e){
                System.out.println(e.getMessage());
                new Alert(Alert.AlertType.ERROR, "Student update failed").show();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
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
            String studentId = tblStudent.getSelectionModel().getSelectedItem().getStudentId();
            try{
                studentBO.deleteStudent(studentId);
                tblStudent.getItems().remove(tblStudent.getSelectionModel().getSelectedItem());
                tblStudent.getSelectionModel().clearSelection();
                resetPage();
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Failed to delete the student").show();
            }finally {
                new Alert(Alert.AlertType.INFORMATION, "Student Deleted!").show();
            }
        }
    }

    public void btnCleanOnAction(ActionEvent actionEvent) throws SQLException {
        resetPage();
    }

    public void onClickTable(MouseEvent mouseEvent) {
        StudentTM studentTm = tblStudent.getSelectionModel().getSelectedItem();
        if (studentTm != null) {
            lblStudentId.setText(studentTm.getStudentId());
            txtFirstName.setText(studentTm.getFirstName());
            txtLastName.setText(studentTm.getLastName());
            txtEmail.setText(studentTm.getEmail());
            txtContact.setText(studentTm.getPhone());
            txtAddress.setText(studentTm.getAddress());
            txtDOB.setText(studentTm.getDob());
            txtRegDate.setText(studentTm.getRegistrationDate());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
    }


