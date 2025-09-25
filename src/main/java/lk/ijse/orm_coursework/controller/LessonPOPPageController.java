package lk.ijse.orm_coursework.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.orm_coursework.bo.BOFactory;
import lk.ijse.orm_coursework.bo.BOTypes;
import lk.ijse.orm_coursework.bo.custom.CourseBO;
import lk.ijse.orm_coursework.bo.custom.InstructorBO;
import lk.ijse.orm_coursework.bo.custom.LessonsBO;
import lk.ijse.orm_coursework.bo.custom.StudentBO;
import lk.ijse.orm_coursework.dto.LessonsDTO;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class LessonPOPPageController implements Initializable {
    public TextField txtLessonDate;
    public TextField txtStartTime;
    public TextField txtEndTime;
    public ComboBox cmbStudentId;
    public ComboBox cmbCourseId;
    public ComboBox cmbInstructorId;
    public Button btnSave;
    public Button btnUpdate;
    public Label lblLessonId;
    public ComboBox cmbStatus;

    private final LessonsBO lessonsBO = (LessonsBO) BOFactory.getInstance().getBO(BOTypes.LESSONS);
    private final StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOTypes.STUDENT);
    private final CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOTypes.COURSE);
    private final InstructorBO instructorsBO = (InstructorBO) BOFactory.getInstance().getBO(BOTypes.INSTRUCTOR);

    public void btnSaveOncAction(ActionEvent actionEvent) {
        String lessonId = lblLessonId.getText();
        String lessonDateStr = txtLessonDate.getText();
        String startTimeStr = txtStartTime.getText();
        String endTimeStr = txtEndTime.getText();
        String status = cmbStatus.getValue() != null ?  cmbStatus.getValue().toString() : "";
        String studentId = cmbStudentId.getValue() != null ? cmbStudentId.getValue().toString() : "";
        String courseId = cmbCourseId.getValue() != null ? cmbCourseId.getValue().toString() : "";
        String instructorId = cmbInstructorId.getValue() != null ? cmbInstructorId.getValue().toString() : "";

        if (lessonId.isEmpty() || lessonDateStr.isEmpty() || startTimeStr.isEmpty() || endTimeStr.isEmpty() ||
                status.isEmpty() || studentId.isEmpty() || courseId.isEmpty() || instructorId.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").show();
            return;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


            Date lessonDate = dateFormat.parse(lessonDateStr);

            boolean isSaved = lessonsBO.saveLessons(new LessonsDTO(
                    lessonId, studentId, courseId, instructorId, lessonDate, startTimeStr,  endTimeStr, status
            ));
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Lesson saved successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save lesson!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String lessonId = lblLessonId.getText();
        String lessonDateStr = txtLessonDate.getText();
        String startTimeStr = txtStartTime.getText();
        String endTimeStr = txtEndTime.getText();
        String status = cmbStatus.getValue() != null ?  cmbStatus.getValue().toString() : "";
        String studentId = cmbStudentId.getValue() != null ? cmbStudentId.getValue().toString() : "";
        String courseId = cmbCourseId.getValue() != null ? cmbCourseId.getValue().toString() : "";
        String instructorId = cmbInstructorId.getValue() != null ? cmbInstructorId.getValue().toString() : "";

        if (lessonId.isEmpty() || lessonDateStr.isEmpty() || startTimeStr.isEmpty() || endTimeStr.isEmpty() ||
                status.isEmpty() || studentId.isEmpty() || courseId.isEmpty() || instructorId.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").show();
            return;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date lessonDate = dateFormat.parse(lessonDateStr);
            String startTime = startTimeStr;
            String endTime = endTimeStr;

            boolean isUpdated = lessonsBO.updateLessons(new LessonsDTO(
                    lessonId, studentId, courseId, instructorId, lessonDate, startTime, endTime, status
            ));
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Lesson updated successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update lesson!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    public void loadData(LessonsDTO lessonsDTO) {
        lblLessonId.setText(lessonsDTO.getLessonId());
        txtLessonDate.setText(lessonsDTO.getStartTime());
        txtStartTime.setText(lessonsDTO.getStartTime());
        txtEndTime.setText(lessonsDTO.getEndTime());
        cmbStatus.setValue(lessonsDTO.getStatus());
        cmbInstructorId.setValue(lessonsDTO.getInstructorId());
        cmbStudentId.setValue(lessonsDTO.getStudentId());
        cmbCourseId.setValue(lessonsDTO.getCourseId());
        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lblLessonId.setText(lessonsBO.generateNewLessonId());
            cmbStatus.setItems(FXCollections.observableArrayList("Scheduled", "Ongoing", "Cancelled"));
            cmbStudentId.setItems(FXCollections.observableArrayList(studentBO.getAllStudentIds()));
            cmbCourseId.setItems(FXCollections.observableArrayList(courseBO.getAllCourseIds()));
            cmbInstructorId.setItems(FXCollections.observableArrayList(instructorsBO.getAllInstructorIds
                    ()));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
