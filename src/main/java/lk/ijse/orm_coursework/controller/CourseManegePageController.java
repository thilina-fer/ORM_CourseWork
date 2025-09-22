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
import lk.ijse.orm_coursework.bo.custom.CourseBO;
import lk.ijse.orm_coursework.dto.CourseDTO;
import lk.ijse.orm_coursework.dto.StudentDTO;
import lk.ijse.orm_coursework.dto.tm.CourseTM;
import lk.ijse.orm_coursework.dto.tm.StudentTM;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CourseManegePageController  implements Initializable {
    public TableView<CourseTM> tblCourse;
    public TableColumn<CourseTM , String> colId;
    public TableColumn<CourseTM , String> colCourseName;
    public TableColumn<CourseTM , String> colDuration;
    public TableColumn<CourseTM , Double> colFee;
    public TableColumn<CourseTM , String> colDescription;
    public TableColumn<CourseTM , String> colInstructorId;
    public TableColumn<CourseTM , String> colAction;


    private final CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOTypes.COURSE);

    public void btnAddOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/CoursePOPPage.fxml"));
            Parent parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Course");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL); // Block input to other windows
            stage.showAndWait();

            loadAllCourses();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to open the popup!").show();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colInstructorId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));

        try {
            loadAllCourses();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllCourses() {
        try {
            tblCourse.setItems(FXCollections.observableArrayList(
                    courseBO.getAllCourses().stream().map(courseDTO -> {
                        Pane action = new Pane();
                        Button btnEdit = new Button("✏");
                        btnEdit.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
                        btnEdit.setPrefWidth(30);
                        btnEdit.setLayoutX(40);
                        btnEdit.setCursor(javafx.scene.Cursor.HAND);
                        btnEdit.setOnAction(event -> onUpdate(courseDTO));

                        Button btnDelete = new Button("🗑");
                        btnDelete.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
                        btnDelete.setPrefWidth(30);
                        btnDelete.setLayoutX(0);
                        btnDelete.setCursor(javafx.scene.Cursor.HAND);
                        btnDelete.setOnAction(event -> onDelete(courseDTO.getCourseId()));

                        action.getChildren().addAll(btnDelete, btnEdit);
                        return new CourseTM(
                                courseDTO.getCourseId(),
                                courseDTO.getCourse_name(),
                                courseDTO.getDuration(),
                                courseDTO.getFee(),
                                courseDTO.getDescription(),
                                courseDTO.getInstructorId(),
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
                "Are you sure whether you want to delete this course?",
                ButtonType.YES,
                ButtonType.NO
        );
        alert.setTitle("Delete Course");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {

            try {
                boolean isDeleted = courseBO.deleteCourses(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Course deleted successfully!").show();
                    loadAllCourses();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the course!").show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onUpdate(CourseDTO courseDTO) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/CoursePOPPage.fxml"));
            Parent parent = fxmlLoader.load();

            CoursePOPPageController controller = fxmlLoader.getController();
            controller.loadData(courseDTO);

            Stage stage = new Stage();
            stage.setTitle("Update Course");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL); // Block input to other windows
            stage.showAndWait();

            // Refresh the table after the popup is closed
            loadAllCourses();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to open the popup!").show();
        }

    }

}
