module lk.ijse.orm_coursework {
    requires javafx.controls;
    requires javafx.fxml;


    opens lk.ijse.orm_coursework to javafx.fxml;
    exports lk.ijse.orm_coursework;
}