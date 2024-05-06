module com.example.crud_erp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.pdfbox;

    opens com.example.crud_erp to javafx.fxml;
    exports com.example.crud_erp;
    opens com.example.crud_erp.model to javafx.fxml;
    exports com.example.crud_erp.model;
    opens com.example.crud_erp.utils to javafx.fxml;
    exports com.example.crud_erp.utils;
    opens com.example.crud_erp.service to javafx.fxml;
    exports com.example.crud_erp.service;
    opens com.example.crud_erp.controllers to javafx.fxml;
    exports com.example.crud_erp.controllers;
}
