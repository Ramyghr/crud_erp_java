package com.example.crud_erp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
//        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/Salary.fxml"));
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
//        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/Paiement.fxml"));
        if (parent == null) {
            System.err.println("FXML file not found");
            return;
        }
        Scene scene = new Scene(parent);
        stage.setTitle("Salary Crud");
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}
