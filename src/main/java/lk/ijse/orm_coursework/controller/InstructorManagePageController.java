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
import lk.ijse.orm_coursework.bo.custom.InstructorBO;
import lk.ijse.orm_coursework.dto.InstructorDTO;
import lk.ijse.orm_coursework.dto.tm.InstructorTM;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class InstructorManagePageController implements Initializable {
    public Button btnAdd;
    public TableView<InstructorTM> tblInstructor;
    public TableColumn<InstructorTM , String> colId;
    public TableColumn<InstructorTM , String> colFirstName;
    public TableColumn<InstructorTM , String> colLastName;
    public TableColumn<InstructorTM , String> colEmail;
    public TableColumn<InstructorTM , String> colContact;
    public TableColumn<InstructorTM , String> colSpecialization;
    public TableColumn<InstructorTM , String> colAvailability;


    private final InstructorBO instructorBO = (InstructorBO) BOFactory.getInstance().getBO(BOTypes.INSTRUCTOR);

    public void btnAddOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/InstructorManagePOPPage.fxml"));
            Parent parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Instructor");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL); // Block input to other windows
            stage.showAndWait();

            loadAllInstructors();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to open the popup!").show();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("action"));

        try {
            loadAllInstructors();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void loadAllInstructors() {
        try {
            tblInstructor.setItems(FXCollections.observableArrayList(
                    instructorBO.getAllInstructors().stream().map(instructorDTO -> {
                        Pane action = new Pane();
                        Button btnEdit = new Button("✏");
                        btnEdit.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
                        btnEdit.setPrefWidth(30);
                        btnEdit.setLayoutX(40);
                        btnEdit.setCursor(javafx.scene.Cursor.HAND);
                        btnEdit.setOnAction(event -> onUpdate(instructorDTO));

                        Button btnDelete = new Button("🗑");
                        btnDelete.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
                        btnDelete.setPrefWidth(30);
                        btnDelete.setLayoutX(0);
                        btnDelete.setCursor(javafx.scene.Cursor.HAND);
                        btnDelete.setOnAction(event -> onDelete(instructorDTO.getInstructorId()));

                        action.getChildren().addAll(btnDelete, btnEdit);
                        return new InstructorTM(
                                instructorDTO.getInstructorId(),
                                instructorDTO.getFirstName(),
                                instructorDTO.getLastName(),
                                instructorDTO.getEmail(),
                                instructorDTO.getContact(),
                                instructorDTO.getSpecialization(),
                                instructorDTO.getAvailability(),
                                action
                        );
                    }).toList()
            ));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onDelete(String id){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this student?",
                ButtonType.YES,
                ButtonType.NO
        );
        alert.setTitle("Delete Student");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {

            try {
                boolean isDeleted = instructorBO.deleteInstructors(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Instructor deleted successfully!").show();
                    loadAllInstructors();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the instructor!").show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onUpdate(InstructorDTO instructorDTO) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/InstructorManagePOPPage.fxml"));
            Parent parent = fxmlLoader.load();

            InstructorPOPPageController controller = fxmlLoader.getController();
            controller.loadData(instructorDTO);

            Stage stage = new Stage();
            stage.setTitle("Update Instructor");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL); // Block input to other windows
            stage.showAndWait();

            loadAllInstructors();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to open the popup!").show();
        }
    }


}
