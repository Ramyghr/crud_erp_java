package com.example.crud_erp.controllers;

import com.example.crud_erp.service.SalaryService;
import com.example.crud_erp.service.PaiementService;
import com.example.crud_erp.utils.DBConnexion;
import com.example.crud_erp.model.Salary;
import com.example.crud_erp.model.Paiement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainController {

    @FXML
    private VBox body;

    @FXML
    private Button btnPaiment;

    @FXML
    private Button btnSalary;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDate1;

    @FXML
    private TableColumn<?, ?> colDeduction;

    @FXML
    private TableColumn<?, ?> colDepartement;

    @FXML
    private TableColumn<?, ?> colDue;

    @FXML
    private TableColumn<?, ?> colGrand;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colMethod;

    @FXML
    private TableColumn<?, ?> colNet;

    @FXML
    private TableColumn<?, ?> colNom;

    @FXML
    private TableColumn<?, ?> colNom1;

    @FXML
    private TableColumn<?, ?> colPaiement;

    @FXML
    private TableColumn<?, ?> colRef;

    @FXML
    private TableColumn<?, ?> colTax;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private GridPane footer;

    @FXML
    private GridPane gridTiles;

    @FXML
    private StackPane root;

    @FXML
    private TextField tCost;

    @FXML
    private TextField tEmployees;

    @FXML
    private TextField tIncome;

    @FXML
    private Text tNbrEmployee;

    @FXML
    private TextField tSales;

    @FXML
    private TableView<Salary> tableviewEMP;

    @FXML
    private TableView<Paiement> tableviewPAIM;
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    SalaryService ss = new SalaryService();
    PaiementService ps = new PaiementService();

    //Paiment
    @FXML
    void initializePAIM() throws SQLException {
        try {
            ObservableList<Paiement> observableList = FXCollections.observableList(ps.read());
            tableviewPAIM.setItems(observableList);
            colID.setCellValueFactory(new PropertyValueFactory<>("id"));
            colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
            colNom.setCellValueFactory(new PropertyValueFactory<>("name"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
            colGrand.setCellValueFactory(new PropertyValueFactory<>("grand"));
            colDue.setCellValueFactory(new PropertyValueFactory<>("due"));
            colMethod.setCellValueFactory(new PropertyValueFactory<>("method"));

            // Call the correct method to set event handlers
            tableviewPAIMEventHandler();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initializeSAL() throws SQLException {
        try {
            ObservableList<Salary> observableList = FXCollections.observableList(ss.read());

            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colRef.setCellValueFactory(new PropertyValueFactory<>("ref"));
            colNom1.setCellValueFactory(new PropertyValueFactory<>("name")); // Change this to colNom1
            colDepartement.setCellValueFactory(new PropertyValueFactory<>("departement"));
            colDate1.setCellValueFactory(new PropertyValueFactory<>("paimentdate")); // Change this to colDate1
            colPaiement.setCellValueFactory(new PropertyValueFactory<>("paiment"));
            colDeduction.setCellValueFactory(new PropertyValueFactory<>("deduction"));
            colTax.setCellValueFactory(new PropertyValueFactory<>("tax"));
            colNet.setCellValueFactory(new PropertyValueFactory<>("net"));

            // Call the correct method to set event handlers
            tableviewEMPEventHandler();
        } catch (SQLException e) {
            throw e;
        }
    }

    @FXML
    void tableviewPAIMEventHandler() {
        tableviewPAIM.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Paiement selectedPaiement = tableviewPAIM.getSelectionModel().getSelectedItem();
                // Handle the event
            }
        });
    }

    @FXML
    void tableviewEMPEventHandler() {
        tableviewEMP.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Salary selectedSalary = tableviewEMP.getSelectionModel().getSelectedItem();
                // Handle the event
            }
        });
    }



    @FXML
    void PaimentAction(ActionEvent event) throws IOException {
        // Get a reference to the current stage
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Create and show the new stage
        Stage newStage = new Stage();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/Paiement.fxml"));
        Scene scene = new Scene(parent);
        newStage.setTitle("Salary Crud");
        newStage.setScene(scene);
        newStage.show();

        // Close the current stage
        currentStage.close();
    }

    @FXML
    void SalaryAction(ActionEvent event) throws IOException {
        // Get a reference to the current stage
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Create and show the new stage
        Stage newStage = new Stage();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/Salary.fxml"));
        Scene scene = new Scene(parent);
        newStage.setTitle("Salary Crud");
        newStage.setScene(scene);
        newStage.show();

        // Close the current stage
        currentStage.close();
    }

}
