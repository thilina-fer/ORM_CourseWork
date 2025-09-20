package lk.ijse.orm_coursework.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.orm_coursework.bo.BOFactory;
import lk.ijse.orm_coursework.bo.BOTypes;
import lk.ijse.orm_coursework.bo.custom.InstructorBO;
import lk.ijse.orm_coursework.dto.tm.InstructorTM;

import java.io.IOException;
import java.net.URL;
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

        try {
            loadAllInstructors();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void loadAllInstructors() {
        try {
            tblInstructor.setItems(FXCollections.observableArrayList(
                    instructorBO.getAllInstructors().stream().map(instructor -> new InstructorTM(
                            instructor.getInstructorId(),
                            instructor.getFirstName(),
                            instructor.getLastName(),
                            instructor.getEmail(),
                            instructor.getContact(),
                            instructor.getSpecialization(),
                            instructor.getAvailability()
                    )).toList()
            ));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
