package lk.ijse.orm_coursework;

import lk.ijse.orm_coursework.config.FactoryConfiguration;
import org.hibernate.Session;

import static javafx.application.Application.launch;

public class AppInitializer {
    public static void main(String[] args) {
        Session currentSession = FactoryConfiguration.getInstance().getCurrentSession();

        launch(args);
    }
}
