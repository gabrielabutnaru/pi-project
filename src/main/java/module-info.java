module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.commons.codec;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports model;
    opens model to javafx.fxml;

}