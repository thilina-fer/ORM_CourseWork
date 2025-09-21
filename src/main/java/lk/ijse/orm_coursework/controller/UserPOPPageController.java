package lk.ijse.orm_coursework.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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


    public void btnSaveOncAction(ActionEvent actionEvent) {

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
