package com.example.crud_erp.controllers;

import com.example.crud_erp.model.Paiement;
import com.example.crud_erp.model.Salary;
import com.example.crud_erp.service.PaiementService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDate.parse;

public class PaiementController {

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnModifier;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnSearch;

    @FXML
    private Button btnSupprimer;

    @FXML
    private Button btnSalary;

    @FXML
    private TableColumn<Paiement, String> colCode;
    @FXML
    private TableColumn<Paiement, String> colMail;

    @FXML
    private TableColumn<Paiement, String> colDate;

    @FXML
    private TableColumn<Paiement, Double> colDue;

    @FXML
    private TableColumn<Paiement, Double> colGrand;

    @FXML
    private TableColumn<Paiement, Integer> colID;

    @FXML
    private TableColumn<Paiement, String> colMethod;

    @FXML
    private TableColumn<Paiement, String> colNom;

    @FXML
    private TableColumn<Paiement, Integer> colTotal;

    @FXML
    private TextField tCode;
    @FXML
    private TextField tMail;

    @FXML
    private DatePicker tDate;

    @FXML
    private TextField tNom;

    @FXML
    private TextField tTotal;
    @FXML
    private TextField tSearch;
    @FXML
    private TextField tMethod;

    @FXML
    private TableView<Paiement> tableview;
    PaiementService ps = new PaiementService();

    @FXML
    void initialize() throws SQLException {
        try {
            ObservableList<Paiement> observableList = FXCollections.observableList(ps.read());
            tableview.setItems(observableList);
            colID.setCellValueFactory(new PropertyValueFactory<>("id"));
            colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
            colNom.setCellValueFactory(new PropertyValueFactory<>("name"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
            colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
            colGrand.setCellValueFactory(new PropertyValueFactory<>("grand"));
            colDue.setCellValueFactory(new PropertyValueFactory<>("due"));
            colMethod.setCellValueFactory(new PropertyValueFactory<>("method"));

            tableview();
            addInputListeners();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void tableview() {
        tableview.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Paiement selectedPaiement = tableview.getSelectionModel().getSelectedItem();
                if (selectedPaiement != null) {
                    tCode.setText(selectedPaiement.getCode());
                    tMail.setText(selectedPaiement.getMail());
                    LocalDate localDate = LocalDate.parse(selectedPaiement.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    tDate.setValue(localDate);
                    tNom.setText(selectedPaiement.getName());
                    tTotal.setText(String.valueOf(selectedPaiement.getTotal()));
                    tMethod.setText(selectedPaiement.getMethod());
                }
            }
        });
    }


    @FXML
    void Add(ActionEvent event) {
        LocalDate localDate = tDate.getValue();
        String dateString = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // Get total amount from text field
        int totalAmount = Integer.parseInt(tTotal.getText());

        // Calculate grand amount as 10% of total
        int grandAmount = (int) (totalAmount * 0.1);

        // Calculate due amount as the difference between total and grand
        int dueAmount = totalAmount + grandAmount;

        // Create Paiement object with calculated values
        Paiement p = new Paiement(totalAmount, grandAmount, dueAmount, tCode.getText(), dateString, tNom.getText(), tMethod.getText(),tMail.getText());

        try {
            // Assuming ps is an instance of your PaiementService
            ps.create(p);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Procedure de Paiment");
            alert.setContentText("Paiment ajouté avec succès!");
            alert.show();

            initialize();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors de l'ajout du paiement: " + e.getMessage());
            alert.show();
            e.printStackTrace();
        }
    }


    @FXML
    void Clear(ActionEvent event) {
        tCode.setText("");
        tNom.setText("");
        tTotal.setText("");
        tMethod.setText("");
        tDate.setValue(null);
        tMail.setText("");
    }

    @FXML
    void Modifier(ActionEvent event) {
        LocalDate localDate = tDate.getValue();
        String dateString = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Paiement selectedPaiement = tableview.getSelectionModel().getSelectedItem();
        if (selectedPaiement != null) {
            int id = selectedPaiement.getId();
            int totalAmount = Integer.parseInt(tTotal.getText());
            int grandAmount = (int) (totalAmount * 0.1);
            int dueAmount = totalAmount + grandAmount;
            Paiement modifiedPaiement = new Paiement(
                    id,
                    totalAmount,
                    grandAmount,
                    dueAmount,
                    tCode.getText(),
                    dateString,
                    tNom.getText(),
                    tMethod.getText(),
                    tMail.getText()
            );
            try {
                ps.update(modifiedPaiement);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Procedure de Paiment");
                alert.setContentText("Paiement modifier avec succees");
                alert.show();
                initialize();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Erreur");
                alert.setContentText(e.getMessage());
                alert.show();
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aucune selection");
            alert.setContentText("Veuillez sellectionner un paiement");
            alert.show();


        }

    }


    @FXML
    void Salary_action(ActionEvent event) throws IOException {
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
    @FXML
    void Main(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Create and show the new stage
        Stage newStage = new Stage();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
        Scene scene = new Scene(parent);
        newStage.setTitle("Main");
        newStage.setScene(scene);
        newStage.show();

        // Close the current stage
        currentStage.close();
    }
    @FXML
    void PDF(ActionEvent event) throws IOException {
        Paiement selectedPaiment = tableview.getSelectionModel().getSelectedItem();
        if (selectedPaiment != null) {
            LocalDate localDate = tDate.getValue();
            String dateString = localDate.toString();

            int id = selectedPaiment.getId();
            int totalAmount = Integer.parseInt(tTotal.getText());
            int grandAmount = (int) (totalAmount * 0.1);
            int dueAmount = totalAmount + grandAmount;



            //CREATE DOCUMENT
            PDDocument document = new PDDocument();
            //CREATE PAGE
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            //CREATE CONTENT STREAM
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            //CONTENT AND SIZE
            contentStream.setFont(PDType1Font.COURIER, 14);

            // Define line spacing and margins
            float lineSpacing = 20f;
            float marginX = 50f; // Left margin
            float marginY = 50f; // Bottom margin (adjust as needed)
            float yPosition = page.getMediaBox().getHeight() - marginY; // Start near bottom margin

            contentStream.beginText();
            contentStream.newLineAtOffset(marginX, yPosition); // Set initial position

            contentStream.showText("Welcome to PDF File");
            yPosition -= lineSpacing; // Update position after each line

            contentStream.showText(" ID:" + selectedPaiment.getId());
            yPosition -= lineSpacing;
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Employee Name:" + selectedPaiment.getName());
            yPosition -= lineSpacing;
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Email:" + selectedPaiment.getMail());
            yPosition -= lineSpacing;
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Date :" + dateString);
            yPosition -= lineSpacing;
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Paiment avant tax:" + selectedPaiment.getTotal());
            yPosition -= lineSpacing;
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Paiement apres tax:" + dueAmount);
            yPosition -= lineSpacing;
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Tax :" + selectedPaiment.getTotal()*0.1);
            yPosition -= lineSpacing;
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Methode:" + selectedPaiment.getMethod());
            yPosition -= lineSpacing;
            contentStream.newLineAtOffset(0, -lineSpacing);


            // Add more fields as needed
            contentStream.endText();

            contentStream.close();

            document.save(".\\data\\sample.pdf");
            document.close();
            System.out.println("pdf created successfully !");

            //read
            File pdfFile = new File(".\\data\\sample.pdf");
            document = PDDocument.load(pdfFile);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            document.close();
            System.out.println(text);
        } else {
            // Handle case where no item is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setContentText("Veuillez sélectionner un paiement pour avoir le pdf.");
            alert.show();
        }
    }
    private void addInputListeners() {
        // Validate code field (should not be empty)
        tCode.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Focus lost
                if (tCode.getText().isEmpty()) {
                    showAlert("Le code ne peut pas être vide.");
                }
            }
        });
        tMail.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Focus lost
                if (!tMail.getText().endsWith("@gmail.com")) {
                    showAlert("L'adresse e-mail doit se terminer par @gmail.com.");
                }
            }
        });
        // Validate nom field (should have at least 3 characters)
        tNom.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Focus lost
                if (tNom.getText().length() < 3) {
                    showAlert("Le nom doit avoir au moins 3 caractères.");
                }
            }
        });

        // Validate total field (should be a positive number)
        tTotal.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double amount = Double.parseDouble(newValue);
                if (amount < 0) {
                    showAlert("Le total doit être un nombre positif.");
                }
            } catch (NumberFormatException e) {
                // Handle non-numeric input (optional)
            }
        });
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void Supprimer(ActionEvent event) {
        Paiement selectedPaiement = tableview.getSelectionModel().getSelectedItem();
        if (selectedPaiement != null) {
            int id = selectedPaiement.getId();
            try {
                ps.delete(id);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Procedure de Paiment");
                alert.setContentText("Paiement supprimer avec succees ");
                alert.show();
                initialize();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText(e.getMessage());
                alert.show();
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aucune selection");
            alert.setContentText("Veuillez sellectionner un paiement");
            alert.show();

        }
    }
    @FXML
    void Search(ActionEvent event) throws SQLException {
        String searchText = tSearch.getText();
        if (!searchText.isEmpty()) {
            try {
                ObservableList<Paiement> searchResults = FXCollections.observableList(ps.searchByCode(searchText));
                tableview.setItems(searchResults);
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle exception
            }
        } else {
            // If search text is empty, refresh the table view with all salaries
            initialize();
        }
    }






}



