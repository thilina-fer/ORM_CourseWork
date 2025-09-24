package lk.ijse.orm_coursework.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import lk.ijse.orm_coursework.dto.Role;
import lk.ijse.orm_coursework.util.AuthUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    public AnchorPane ancDashboard;
    public StackPane stackPaneComponent;
    public Button btnUser;
    public Button btnCourse;
    public Button btnPayments;

    void navigateTo(String path) {
        try {
            ancDashboard.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancDashboard.widthProperty());
            anchorPane.prefHeightProperty().bind(ancDashboard.heightProperty());

            ancDashboard.getChildren().add(anchorPane);

            if (path.equals("/view/DashbordPage2.fxml")) {
                loadBreadcrumb(null, "Dashboard");
            }

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private void loadBreadcrumb(Runnable run, String title) throws IOException {
        stackPaneComponent.getChildren().clear();
        FXMLLoader loader  = new FXMLLoader(DashboardController.class.getResource("/view/Breadcrumb.fxml"));
        Parent pane  = loader.load();

        BreadcrumbController breadcrumbController = loader.getController();
        breadcrumbController.init(run, title);

        stackPaneComponent.getChildren().add(pane);
    }

    public void btnStudentOnAction(ActionEvent actionEvent) throws IOException {
        navigateTo("/view/StudentManagePage.fxml");
        backDashboard("Student Management");
    }

    public void btnInstructorOnAction(ActionEvent actionEvent) throws IOException {
        navigateTo("/view/InstructorManagePage.fxml");
        backDashboard("Instructor Management");
    }

    public void btnCourseOnAction(ActionEvent actionEvent) throws IOException {
        navigateTo("/view/CourseManagePage.fxml");
        backDashboard("Course Management");
    }

    private void backDashboard(String title) throws IOException {
        loadBreadcrumb(()->{navigateTo("/view/DashbordPage2.fxml");} ,title);
    }

    public void btnUserOnAction(ActionEvent actionEvent) throws IOException {
        navigateTo("/view/UserManagePage.fxml");
        backDashboard("User Management");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/DashbordPage2.fxml");
        restrictions();
        try {
            loadBreadcrumb(null ,"Dashboard");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnPaymentOnAction(ActionEvent actionEvent) throws IOException {
        navigateTo("/view/PaymentsManagePage.fxml");
        backDashboard("Payment Management");
    }


    public void restrictions(){
        if (AuthUtil.getRole().equalsIgnoreCase(String.valueOf(Role.USER))) {
            btnCourse.setVisible(false);
            btnUser.setVisible(false);
        }
    }



}
