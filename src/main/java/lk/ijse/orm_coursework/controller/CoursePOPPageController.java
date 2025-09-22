package lk.ijse.orm_coursework.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.orm_coursework.bo.BOFactory;
import lk.ijse.orm_coursework.bo.BOTypes;
import lk.ijse.orm_coursework.bo.custom.CourseBO;
import lk.ijse.orm_coursework.bo.custom.InstructorBO;
import lk.ijse.orm_coursework.dto.CourseDTO;

import java.net.URL;
import java.util.ResourceBundle;

public class CoursePOPPageController implements Initializable {

    public Label lblCourseId;
    public TextField txtCourseName;
    public TextField txtDuration;
    public TextField txtCourseFee;
    public TextField txtDescription;
    public ComboBox cmbInstructorId;
    public Label instructorName;
    public Button btnSave;
    public Button btnUpdate;


    private final CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOTypes.COURSE);
    private final InstructorBO instructorBO = (InstructorBO) BOFactory.getInstance().getBO(BOTypes.INSTRUCTOR);


    private boolean validateCourseInputs() {

        if (txtCourseName.getText().isEmpty() || txtDescription.getText().isEmpty() ||
                txtCourseFee.getText().isEmpty() || txtDuration.getText().isEmpty() ||
                cmbInstructorId.getValue() == null) {
            btnSave.setDisable(true);
            btnUpdate.setDisable(true);
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").show();
            return false;
        }
        return true;
    }

    public void btnSaveOncAction(ActionEvent actionEvent) {
        if (!validateCourseInputs())
            return;

        try {
            String courseId = lblCourseId.getText();
            String courseName = txtCourseName.getText();
            String description = txtDescription.getText();
            double courseFee = Double.parseDouble(txtCourseFee.getText());
            String duration = txtDuration.getText();
            String instructorId = cmbInstructorId.getValue().toString();


            boolean isSaved = courseBO.saveCourses(CourseDTO.builder()
                    .courseId(courseId)
                    .course_name(courseName)
                    .description(description)
                    .fee(courseFee)
                    .duration(duration)
                    .instructorId(instructorId)
                    .build());


            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Course saved successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save course").show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (!validateCourseInputs()) return;

        try {
            String courseId = lblCourseId.getText();
            String courseName = txtCourseName.getText();
            String description = txtDescription.getText();
            double courseFee = Double.parseDouble(txtCourseFee.getText());
            String duration = txtDuration.getText();
            String instructorId = cmbInstructorId.getValue().toString();

            boolean isUpdated = courseBO.updateCourses(CourseDTO.builder()
                    .courseId(courseId)
                    .course_name(courseName)
                    .description(description)
                    .fee(courseFee)
                    .duration(duration)
                    .instructorId(instructorId)
                    .build());

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Course updated successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update course").show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void loadData(CourseDTO courseDTO) {
        lblCourseId.setText(courseDTO.getCourseId());
        txtCourseName.setText(courseDTO.getCourse_name());
        txtDescription.setText(courseDTO.getDescription());
        txtCourseFee.setText(String.valueOf(courseDTO.getFee()));
        txtDuration.setText(courseDTO.getDuration());
        cmbInstructorId.setValue(courseDTO.getInstructorId());
        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lblCourseId.setText(courseBO.generateNewCourseId());
            cmbInstructorId.setItems(FXCollections.observableArrayList(instructorBO.getAllInstructorIds()));
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
