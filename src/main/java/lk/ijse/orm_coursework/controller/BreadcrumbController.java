package lk.ijse.orm_coursework.controller;

//import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class BreadcrumbController implements Initializable {
    public Label txtBreadcrumbTitle;
    public Label txtNowDate;
    public Button btnBack;
    private Runnable run;

//    private Timeline timeline;

    public void btnBackOnAction(ActionEvent actionEvent) {
        if (run != null) {
            run.run();
        }
    }

    public void init(Runnable run, String title) {
        this.run = run;
        txtBreadcrumbTitle.setText(title);
        if (run != null) {
            Image image = new Image(BreadcrumbController.class.getResource("/images/arrow-right.jpg").toExternalForm());
            ImageView icon = new ImageView(image);
            icon.setFitWidth(15);
            icon.setFitHeight(15);
            icon.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            btnBack.setGraphic(icon);
        } else {
            Image image = new Image(BreadcrumbController.class.getResource("/images/home.jpg").toExternalForm());
            ImageView icon = new ImageView(image);
            icon.setFitWidth(15);
            icon.setFitHeight(15);
            icon.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            btnBack.setGraphic(icon);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtBreadcrumbTitle.setText("Dashboard");
        Runnable run = null;
//        // Start live time updater
//        startLiveTimeUpdater();
    }
}