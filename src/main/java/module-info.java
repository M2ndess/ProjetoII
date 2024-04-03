module com.example.proj2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.persistence;


    opens Controllers to javafx.fxml;
    opens Entity to javafx.base;
    exports Controllers;
}