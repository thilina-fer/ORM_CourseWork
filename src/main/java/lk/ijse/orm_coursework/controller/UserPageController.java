package lk.ijse.orm_coursework.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.orm_coursework.bo.BOFactory;
import lk.ijse.orm_coursework.bo.BOTypes;
import lk.ijse.orm_coursework.bo.custom.UserBO;
import lk.ijse.orm_coursework.dto.UserDTO;
import lk.ijse.orm_coursework.dto.tm.UserTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserPageController implements Initializable {

    public Label lblUserId;
    public TextField txtUsername;
    public TextField txtPassword;
    public TextField txtRole;
    public TextField txtEmail;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnClean;

    public TableView<UserTM> tblUser;
    public TableColumn<UserTM , String> colId;
    public TableColumn<UserTM , String> colUsername;
    public TableColumn<UserTM , String> colPassword;
    public TableColumn<UserTM , String> colRole;
    public TableColumn<UserTM , String> colEmail;

    private final UserBO userBO = BOFactory.getInstance().getBO(BOTypes.USER);

    private final String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final String passwordPattern = "^[A-Za-z0-9@#$%^&+=]{6,}$";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));


        try {
            resetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage(),ButtonType.OK).show();
        }
    }

    private void resetPage() throws SQLException {
        loadNextId();
        loadTableData();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtPassword.setText("");
        txtUsername.setText("");
        txtRole.setText("");
        txtEmail.setText("");
    }

    private void loadTableData() throws SQLException {
        tblUser.setItems(FXCollections.observableArrayList(
                userBO.getAllUser().stream().map(userDTO -> new UserTM(
                        userDTO.getUserId(),
                        userDTO.getUsername(),
                        userDTO.getPassword(),
                        userDTO.getRole(),
                        userDTO.getEmail()
                )).toList()
        ));
    }

    private void loadNextId() throws SQLException {
        String nextId = userBO.getNextId();
        lblUserId.setText(nextId);

    }

    public void btnSaveOncAction(ActionEvent actionEvent) throws Exception {
        String userId = lblUserId.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String role = txtRole.getText();
        String email = txtEmail.getText();

        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPassword = password.matches(passwordPattern);

        txtEmail.setStyle(txtEmail.getStyle()+";-fx-border-color: #BB25B9;");
        txtPassword.setStyle(txtPassword.getStyle()+";-fx-border-color: #BB25B9;");

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle()+";-fx-border-color: red;");
        }

        if (!isValidPassword) {
            txtPassword.setStyle(txtPassword.getStyle()+";-fx-border-color: red;");
            new Alert(Alert.AlertType.ERROR, "Invalid Password format", ButtonType.OK).show();
        }

        UserDTO userDTO =  new UserDTO(
                userId,
                username,
                password,
                role,
                email
        );

        if (isValidEmail && isValidPassword) {
            try {
                userBO.saveUser(userDTO);
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "User has been saved successfully", ButtonType.OK).show();
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR,e.getMessage(),ButtonType.OK).show();
                e.printStackTrace();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String userId = lblUserId.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String role = txtRole.getText();
        String email = txtEmail.getText();

        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPassword = password.matches(passwordPattern);

        txtEmail.setStyle(txtEmail.getStyle()+";-fx-border-color: #BB25B9;");
        txtPassword.setStyle(txtPassword.getStyle()+";-fx-border-color: #BB25B9;");

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle()+";-fx-border-color: red;");
        }

        if (!isValidPassword) {
            txtPassword.setStyle(txtPassword.getStyle()+";-fx-border-color: red;");
        }

        UserDTO userDTO =  new UserDTO(
                userId,
                username,
                password,
                role,
                email
        );

        if (isValidEmail && isValidPassword) {
            try {
                userBO.updateUser(userDTO);
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "User has been saved successfully", ButtonType.OK).show();
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR,e.getMessage(),ButtonType.OK).show();
                e.printStackTrace();
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
            String userId = tblUser.getSelectionModel().getSelectedItem().getUserId();
            try{
                userBO.deleteUser(userId);
                tblUser.getItems().remove(tblUser.getSelectionModel().getSelectedItem());
                tblUser.getSelectionModel().clearSelection();
                resetPage();
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Failed to delete the user").show();
            }finally {
                new Alert(Alert.AlertType.INFORMATION, "User Deleted!").show();
            }
        }
    }

    public void btnCleanOnAction(ActionEvent actionEvent) throws SQLException {
        resetPage();
    }

    public void onClickTable(MouseEvent mouseEvent) {
        UserTM userTM = tblUser.getSelectionModel().getSelectedItem();

        if (userTM != null) {
            lblUserId.setText(userTM.getUserId());
            txtUsername.setText(userTM.getUsername());
            txtPassword.setText(userTM.getPassword());
            txtEmail.setText(userTM.getEmail());
            txtRole.setText(userTM.getRole());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }


}
