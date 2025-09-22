package lk.ijse.orm_coursework.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.orm_coursework.bo.BOFactory;
import lk.ijse.orm_coursework.bo.BOTypes;
import lk.ijse.orm_coursework.bo.custom.UserBO;
import lk.ijse.orm_coursework.config.FactoryConfiguration;
import lk.ijse.orm_coursework.dto.StudentDTO;
import lk.ijse.orm_coursework.dto.UserDTO;
import lk.ijse.orm_coursework.dto.tm.StudentTM;
import lk.ijse.orm_coursework.dto.tm.UserTM;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserPageController implements Initializable {
    public Button btnAdd;
    public TableView<UserTM > tblUser;
    public TableColumn<UserTM , String > colId;
    public TableColumn<UserTM , String > colUsername;
    public TableColumn<UserTM , String > colPassword;
    public TableColumn<UserTM , String > colRole;
    public TableColumn<UserTM , String > colEmail;
    public TableColumn<UserTM , String > colStatus;
    public TableColumn<?,?> colAction;

    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOTypes.USER);


    public void btnAddOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/userPOPPage.fxml"));
            Parent parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Add User");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false); // Optional: prevent resizing
            stage.showAndWait();

            // Optionally refresh the table after closing the popup
            loadAllUsers();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to open the popup!\n" + e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));

        try {
            loadAllUsers();
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    private void loadAllUsers() {
        try {
            tblUser.setItems(FXCollections.observableArrayList(
                    userBO.getAllUsers().stream().map(userDTO -> {
                        Pane action = new Pane();
                        Button btnEdit = new Button("✏");
                        btnEdit.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
                        btnEdit.setPrefWidth(30);
                        btnEdit.setLayoutX(40);
                        btnEdit.setCursor(javafx.scene.Cursor.HAND);
                        btnEdit.setOnAction(event -> onUpdate(userDTO));

                        Button btnDelete = new Button("🗑");
                        btnDelete.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
                        btnDelete.setPrefWidth(30);
                        btnDelete.setLayoutX(0);
                        btnDelete.setCursor(javafx.scene.Cursor.HAND);
                        btnDelete.setOnAction(event -> onDelete(userDTO.getUserId()));

                        action.getChildren().addAll(btnDelete, btnEdit);
                        return new UserTM(
                                userDTO.getUserId(),
                                userDTO.getUsername(),
                                userDTO.getPassword(),
                                userDTO.getRole(),
                                userDTO.getEmail(),
                                userDTO.getStatus(),
                                action
                        );
                    }).toList()
            ));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onDelete(String id) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this user?",
                ButtonType.YES,
                ButtonType.NO
        );
        alert.setTitle("Delete User");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {

            try {
                boolean isDeleted = userBO.deleteUsers(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "User deleted successfully!").show();
                    loadAllUsers();
                } else {
                    new Alert(Alert.AlertType.ERROR, "User to delete the student!").show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onUpdate(UserDTO userDTO) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/userPOPPage.fxml"));
            Parent parent = fxmlLoader.load();

            UserPOPPageController controller = fxmlLoader.getController();
            controller.loadData(userDTO);
//            controller.btnUpdateOnAction(selectedItem);

            Stage stage = new Stage();
            stage.setTitle("Update User");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL); // Block input to other windows
            stage.showAndWait();

            // Refresh the table after the popup is closed
            loadAllUsers();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to open the popup!").show();
        }

    }
}
