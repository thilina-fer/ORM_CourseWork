package lk.ijse.orm_coursework;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.orm_coursework.config.FactoryConfiguration;
import org.hibernate.Session;

import static javafx.application.Application.launch;

public class AppInitializer extends Application {
    public static void main(String[] args) {
       // Session currentSession = FactoryConfiguration.getInstance().getCurrentSession();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/StudentPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        stage.setMaximized(true);
    }
}
