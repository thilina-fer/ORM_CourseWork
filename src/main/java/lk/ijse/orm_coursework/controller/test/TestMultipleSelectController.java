package lk.ijse.orm_coursework.controller.test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import lk.ijse.orm_coursework.bo.BOFactory;
import lk.ijse.orm_coursework.bo.BOTypes;
import lk.ijse.orm_coursework.bo.custom.CourseBO;
import lk.ijse.orm_coursework.dto.CourseDTO;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TestMultipleSelectController implements Initializable {
    public ListView<CourseDTO> listView;

    private CourseBO courseBO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        courseBO = (CourseBO) BOFactory.getInstance().getBO(BOTypes.COURSE);

        try {
            // Load courses from BO
            List<CourseDTO> courses = courseBO.getAllCourses();
            ObservableList<CourseDTO> courseObservableList = FXCollections.observableArrayList(courses);

            // Bind data into ListView
            listView.setItems(courseObservableList);

            // Set multiple selection mode
            listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            // Display Course name instead of full object
//            listView.setCellFactory(param -> new javafx.scene.control.ListCell<>() {
//                @Override
//                protected void updateItem(CourseDTO course, boolean empty) {
//                    super.updateItem(course, empty);
//                    if (empty || course == null) {
//                        setText(null);
//                    } else {
//                        setText(course.getCourse_name()); // 👈 Change this to any property you want to display
//                    }
//                }
//            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onSubmit(ActionEvent actionEvent) {
        ObservableList<CourseDTO> selectedCourses = listView.getSelectionModel().getSelectedItems();

        for (CourseDTO course : selectedCourses) {
            System.out.println("Selected Course: " + course.getCourse_name() + " | ID: " + course.getCourseId());
        }

        // If you need as array
        CourseDTO[] selectedArray = selectedCourses.toArray(new CourseDTO[0]);
        System.out.println("Total selected: " + selectedArray.length);
    }
}
