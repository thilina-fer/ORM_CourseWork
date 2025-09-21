package lk.ijse.orm_coursework.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import lk.ijse.orm_coursework.bo.BOFactory;
import lk.ijse.orm_coursework.bo.BOTypes;
import lk.ijse.orm_coursework.bo.custom.CourseBO;
import lk.ijse.orm_coursework.dto.tm.CourseTM;

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
    }

    public void onClickTable(MouseEvent mouseEvent) {
    }
}
