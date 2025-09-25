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
import lk.ijse.orm_coursework.bo.custom.LessonsBO;
import lk.ijse.orm_coursework.dto.InstructorDTO;
import lk.ijse.orm_coursework.dto.LessonsDTO;
import lk.ijse.orm_coursework.dto.tm.LessonsTM;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LessonsManagePageController implements Initializable {

    public Button btnAdd;

    public TableView<LessonsTM> tblLessons;
    public TableColumn<LessonsTM,String> colId;
    public TableColumn<LessonsTM,String> colLessonDate;
    public TableColumn<LessonsTM,String> colStartTime;
    public TableColumn<LessonsTM,String> colEndTime;
    public TableColumn<LessonsTM,String> colStatus;
    public TableColumn<LessonsTM,String> colStudentId;
    public TableColumn<LessonsTM,String> colCourseId;
    public TableColumn<LessonsTM,String> colInstructorId;
    public TableColumn<?,?> colAction;


    private final LessonsBO lessonsBO = (LessonsBO) BOFactory.getInstance().getBO(BOTypes.LESSONS);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("lessonId"));
        colLessonDate.setCellValueFactory(new PropertyValueFactory<>("lessonDate"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colInstructorId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));

        try {
            loadAllLessons();
        } catch (Exception e) {
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/LessonsPOPPage.fxml"));
            Parent parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Lessons");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL); // Block input to other windows
            stage.showAndWait();
            loadAllLessons();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to open the popup!").show();
        }
    }


        private void loadAllLessons() {
            try {
                tblLessons.setItems(FXCollections.observableArrayList(
                        lessonsBO.getAllLessons().stream().map(lessonsDTO -> {
                            Pane action = new Pane();
                            Button btnEdit = new Button("✏");
                            btnEdit.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
                            btnEdit.setPrefWidth(30);
                            btnEdit.setLayoutX(40);
                            btnEdit.setCursor(javafx.scene.Cursor.HAND);
                            btnEdit.setOnAction(event -> onUpdate(lessonsDTO));

                            Button btnDelete = new Button("🗑");
                            btnDelete.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
                            btnDelete.setPrefWidth(30);
                            btnDelete.setLayoutX(0);
                            btnDelete.setCursor(javafx.scene.Cursor.HAND);
                            btnDelete.setOnAction(event -> onDelete(lessonsDTO.getLessonId()));

                            action.getChildren().addAll(btnDelete, btnEdit);
                            return new LessonsTM(
                                    lessonsDTO.getLessonId(),
                                    lessonsDTO.getStudentId(),
                                    lessonsDTO.getCourseId(),
                                    lessonsDTO.getInstructorId(),
                                    lessonsDTO.getLessonDate(),
                                    lessonsDTO.getStartTime(),
                                    lessonsDTO.getEndTime(),
                                    lessonsDTO.getStatus(),
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
                "Are you sure whether you want to delete this Lesson?",
                ButtonType.YES,
                ButtonType.NO
        );
        alert.setTitle("Delete Lesson");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {

            try {
                boolean isDeleted = lessonsBO.deleteLessons(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Lesson deleted successfully!").show();
                    loadAllLessons();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the lesson!").show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onUpdate(LessonsDTO lessonsDTO) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/LessonsPOPPage.fxml"));
            Parent parent = fxmlLoader.load();

            LessonPOPPageController controller = fxmlLoader.getController();
            controller.loadData(lessonsDTO);

            Stage stage = new Stage();
            stage.setTitle("Update Lesson");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL); // Block input to other windows
            stage.showAndWait();

            loadAllLessons();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to open the popup!").show();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {

    }
}
