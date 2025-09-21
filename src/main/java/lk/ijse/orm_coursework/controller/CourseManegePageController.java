package lk.ijse.orm_coursework.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.orm_coursework.bo.BOFactory;
import lk.ijse.orm_coursework.bo.BOTypes;
import lk.ijse.orm_coursework.bo.custom.CourseBO;
import lk.ijse.orm_coursework.dto.tm.CourseTM;

import java.io.IOException;

public class CourseManegePageController {
    public TableView<CourseTM> tblStudent;
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
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to open the popup!").show();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {

    }
}
