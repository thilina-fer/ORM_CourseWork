package lk.ijse.orm_coursework.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
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
import lk.ijse.orm_coursework.bo.custom.StudentBO;
import lk.ijse.orm_coursework.dto.CourseDTO;
import lk.ijse.orm_coursework.dto.StudentDTO;
import lk.ijse.orm_coursework.dto.tm.StudentTM;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentManagePageController implements Initializable {
    public TableView<StudentTM> tblStudent;
    public TableColumn<StudentTM, String> colId;
    public TableColumn<StudentTM, String> colFirstName;
    public TableColumn<StudentTM, String> colLastName;
    public TableColumn<StudentTM, String> colEmail;
    public TableColumn<StudentTM, String> colContact;
    public TableColumn<StudentTM, String> colAddress;
    public TableColumn<StudentTM, String> colDOB;
    public TableColumn<StudentTM, String> colRegDate;
    public TableColumn<StudentTM, String> colCourses;
    public TableColumn<?, ?> colAction;

    private final StudentBO studentsBO =
            (StudentBO) BOFactory.getInstance().getBO(BOTypes.STUDENT);

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
        colCourses.setCellValueFactory(new PropertyValueFactory<>("courseIds"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));

        try {
            loadAllStudents();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void loadAllStudents() {
        try {

            tblStudent.setItems(FXCollections.observableArrayList(
                    studentsBO.getAllStudents().stream().map(studentDTO -> {
                        Pane action = new Pane();

                        Button btnEdit = new Button("✏");
                        btnEdit.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
                        btnEdit.setPrefWidth(30);
                        btnEdit.setLayoutX(40);
                        btnEdit.setCursor(Cursor.HAND);
                        btnEdit.setOnAction(event -> onUpdate(studentDTO));

                        Button btnDelete = new Button("🗑");
                        btnDelete.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
                        btnDelete.setPrefWidth(30);
                        btnDelete.setLayoutX(0);
                        btnDelete.setCursor(Cursor.HAND);
                        btnDelete.setOnAction(event -> onDelete(studentDTO.getStudentId()));

                        action.getChildren().addAll(btnDelete, btnEdit);

                        System.out.println(studentDTO.getCourses());

                        // Store as one string with comma separation
                        String selectedCourseNamesString =  studentDTO.getCourses() == null ? "" :
                                String.join(", ", studentDTO.getCourses().stream()
                                        .map(CourseDTO::getCourse_name)
                                        .toList());


                        return new StudentTM(
                                studentDTO.getStudentId(),
                                studentDTO.getFirstName(),
                                studentDTO.getLastName(),
                                studentDTO.getEmail(),
                                studentDTO.getPhone(),
                                studentDTO.getAddress(),
                                studentDTO.getDob(),
                                studentDTO.getRegistrationDate(),
                                selectedCourseNamesString,
                                action
                        );
                    }).toList()
            ));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/StudentManagePopPage.fxml"));
            Parent parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Student");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();

            // refresh table after closing popup
            loadAllStudents();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to open the popup!\n" + e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        // Optional: handle row clicks here if needed
    }

    public void onDelete(String id) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this student?",
                ButtonType.YES,
                ButtonType.NO
        );
        alert.setTitle("Delete Student");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDeleted = studentsBO.deleteStudents(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Student deleted successfully!").show();
                    loadAllStudents();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the student!").show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onUpdate(StudentDTO studentDTO) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/StudentManagePopPage.fxml"));
            Parent parent = fxmlLoader.load();

            StudentPopUpController controller = fxmlLoader.getController();
            controller.loadData(studentDTO);

            Stage stage = new Stage();
            stage.setTitle("Update Student");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // refresh after update
            loadAllStudents();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to open the popup!").show();
        }
    }
}
