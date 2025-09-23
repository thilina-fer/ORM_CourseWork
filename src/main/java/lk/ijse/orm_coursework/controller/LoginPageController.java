package lk.ijse.orm_coursework.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.orm_coursework.bo.BOFactory;
import lk.ijse.orm_coursework.bo.BOTypes;
import lk.ijse.orm_coursework.bo.custom.UserBO;
import lk.ijse.orm_coursework.config.FactoryConfiguration;
import lk.ijse.orm_coursework.dto.UserDTO;
import lk.ijse.orm_coursework.util.AuthUtil;
import lk.ijse.orm_coursework.util.PasswordUtils;
import org.hibernate.Session;

public class LoginPageController {
    public TextField txtUsername;
    public TextField txtPassword;
    public Button btnSignIn;
    public AnchorPane ancSignIn;

    private final String userNamePattern = "^[A-Za-z0-9_ ]{3,}$";
    private final String passwordPattern = "^[A-Za-z0-9@#$%^&+=]{6,}$";
    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOTypes.USER);


    private void initialize() {
        txtUsername.textProperty().addListener((observable, oldValue, newValue) -> validFields());
        txtPassword.textProperty().addListener((observable, oldValue, newValue) -> validFields());
        btnSignIn.setDisable(true);
    }

    private void validFields() {
        boolean isValidUsername = txtUsername.getText().matches(userNamePattern);
        boolean isValidPassword = txtPassword.getText().matches(passwordPattern);

        txtUsername.setStyle("-fx-border-color: #7367F0; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        txtPassword.setStyle("-fx-border-color: #7367F0; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        if (!isValidUsername)
            txtUsername.setStyle("-fx-border-color: red; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        if (!isValidPassword)
            txtPassword.setStyle("-fx-border-color: red; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        btnSignIn.setDisable(!(isValidUsername && isValidPassword));
    }

//    private void navigateTo(String path) {
//        try {
//            ancSignIn.getChildren().clear();
//            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));
//            anchorPane.prefWidthProperty().bind(ancSignIn.widthProperty());
//            anchorPane.prefHeightProperty().bind(ancSignIn.heightProperty());
//            ancSignIn.getChildren().add(anchorPane);
//        } catch (Exception e) {
//            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
//            e.printStackTrace();
//        }
//    }

    public void btnLoginOnAction(ActionEvent actionEvent) {
        String inputUsername = txtUsername.getText();
        String inputPassword = txtPassword.getText();

        if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please fill all the fields", ButtonType.OK).show();
            return;
        }

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            UserDTO user = userBO.getUserByEmail(inputUsername);

            if (user != null && PasswordUtils.checkPassword(inputPassword, user.getPassword())) {

                AuthUtil.setCurrentUser(user);

                // Login success
                Parent dashboardRoot = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
                Stage dashboardStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                dashboardStage.setScene(new Scene(dashboardRoot));
                dashboardStage.setTitle("Alpha Modifications");
                dashboardStage.setMaximized(true);
                dashboardStage.show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid Username or Password", ButtonType.OK).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Login Fail", ButtonType.OK).show();
        }
    }
}
