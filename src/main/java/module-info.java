module lk.ijse.orm_coursework {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires static lombok;
    requires jakarta.persistence;
    requires java.naming;
    requires jbcrypt;

    opens lk.ijse.orm_coursework.config to jakarta.persistence;
    opens lk.ijse.orm_coursework.entity to org.hibernate.orm.core;

    opens lk.ijse.orm_coursework.controller to javafx.fxml;
    opens lk.ijse.orm_coursework.dto.tm to javafx.base;
    exports lk.ijse.orm_coursework;
}