module lk.ijse.orm_coursework {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires static lombok;
    requires jakarta.persistence;


    opens lk.ijse.orm_coursework to javafx.fxml;
    exports lk.ijse.orm_coursework;
}