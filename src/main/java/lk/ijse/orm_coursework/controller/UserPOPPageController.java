package lk.ijse.orm_coursework.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.orm_coursework.bo.BOFactory;
import lk.ijse.orm_coursework.bo.BOTypes;
import lk.ijse.orm_coursework.bo.custom.UserBO;
import lk.ijse.orm_coursework.dto.UserDTO;
import lk.ijse.orm_coursework.util.PasswordUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class UserPOPPageController implements Initializable {

    public Label lblId;
    public TextField txtUserName;
    public TextField txtPassword;
    public ComboBox cmbRole;
    public TextField txtEmail;
    public Button btnSave;
    public Button btnUpdate;

    private final String passwordRegex = "^.{8,}$";
    private final String usernameRegex = "^[A-Za-z_-]+$";

    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOTypes.USER);



    public void btnSaveOncAction(ActionEvent actionEvent) {
        try {
            String userId = lblId.getText();
            String username = txtUserName.getText();
            String email = txtEmail.getText();
            String role = cmbRole.getValue() != null ? cmbRole.getValue().toString() : "";
            String password = txtPassword.getText();
            String status = "Active";

            if (!username.matches(usernameRegex)) {
                new Alert(Alert.AlertType.ERROR, "Invalid username!").show();
                return;
            }
            if (!password.matches(passwordRegex)) {
                new Alert(Alert.AlertType.ERROR, "Password must be at least 8 characters!").show();
                return;
            }

            String encryptedPassword = PasswordUtils.hashPassword(password);

            boolean isSaved = userBO.saveUsers(new UserDTO(
                    userId,
                    username,
                    encryptedPassword,
                    role,
                    email,
                    status
            ));
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "User saved successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save user!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            String userId = lblId.getText();
            String username = txtUserName.getText();
            String email = txtEmail.getText();
            String role = cmbRole.getValue() != null ? cmbRole.getValue().toString() : "";
            String password = txtPassword.getText();
            String status = "Active";

            if (!username.matches(usernameRegex)) {
                new Alert(Alert.AlertType.ERROR, "Invalid username!").show();
                return;
            }
            if (!password.matches(passwordRegex)) {
                new Alert(Alert.AlertType.ERROR, "Password must be at least 8 characters!").show();
                return;
            }

            String encryptedPassword = PasswordUtils.hashPassword(password);

            boolean isUpdated = userBO.updateUsers(new UserDTO(
                    userId,
                    username,
                    encryptedPassword,
                    role,
                    email,
                    status
            ));
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "User updated successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update user!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }


    public void loadData(UserDTO userDTO){
        lblId.setText(userDTO.getUserId());
        txtUserName.setText(userDTO.getUsername());
        txtPassword.setText(userDTO.getPassword());
        cmbRole.setValue(userDTO.getRole());
        txtEmail.setText(userDTO.getEmail());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lblId.setText(userBO.generateNextUserId());
            cmbRole.setItems(FXCollections.observableArrayList("Admin", "User"));
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
