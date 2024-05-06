package com.example.crud_erp.controllers;

import com.example.crud_erp.model.Salary;
import com.example.crud_erp.utils.DBConnexion;
import com.example.crud_erp.service.SalaryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Integer.getInteger;
import static java.lang.Integer.parseInt;

public class SalaryController {


    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnPDF;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnClear;
    @FXML
    private Button btnBack;

    @FXML
    private Button btnModifier;


    @FXML
    private TableColumn<Salary, String> colDate;

    @FXML
    private TableColumn<Salary, Double> colDeduction;

    @FXML
    private TableColumn<Salary, String> colDepartement;

    @FXML
    private TableColumn<Salary, Double> colNet;

    @FXML
    private TableColumn<Salary, String> colNom;
    @FXML
    private TableColumn<Salary, Integer> colId;


    @FXML
    private TableColumn<Salary, Integer> colPaiement;

    @FXML
    private TableColumn<Salary, Integer> colRef;

    @FXML
    private TableColumn<Salary, Double> colTax;

    @FXML
    private DatePicker tDate;
    @FXML
    private Button btnPaiment;

    @FXML
    private TextField tSearch;

    @FXML
    private ChoiceBox<String> tDepartement;

    @FXML
    private TextField tNom;

    @FXML
    private TextField tPaiement;

    @FXML
    private TextField tRef;
    @FXML
    private TableView<Salary> tableview;



    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    SalaryService ss = new SalaryService();

    @FXML
    public void initialize() throws SQLException{
        try{
            ObservableList<Salary> observableliste = FXCollections.observableList(ss.read());
            tableview.setItems(observableliste);
            ObservableList<String> departmentOptions = FXCollections.observableArrayList(
                    "RH",
                    "Finance",
                    "IT",
                    "Sales",
                    "Marketing"
            );
                tDepartement.setItems(departmentOptions);


            // Call a separate method to fetch and display salaries based on department
            updateTableViewByDepartment("");
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colRef.setCellValueFactory(new PropertyValueFactory<>("ref"));
            colNom.setCellValueFactory(new PropertyValueFactory<>("name"));
            colDepartement.setCellValueFactory(new PropertyValueFactory<>("department"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("paimentdate"));
            colPaiement.setCellValueFactory(new PropertyValueFactory<>("paiment"));
            colDeduction.setCellValueFactory(new PropertyValueFactory<>("deduction"));
            colTax.setCellValueFactory(new PropertyValueFactory<>("tax"));
            colNet.setCellValueFactory(new PropertyValueFactory<>("net"));

            // Configurer la gestion des événements de clic sur la TableView
            tableview();
            addInputListeners();
        }catch (SQLException e) {
            throw e;
        }
    }
    private void updateTableViewByDepartment(String department) throws SQLException {
        ObservableList<Salary> observableliste = FXCollections.observableList(ss.read());
        tableview.setItems(observableliste);
        // ... rest of your initialization code
    }

    private void addInputListeners() {
        // Validate ref field (should be a number)
        tRef.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tRef.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Validate name field (should have a minimum of 3 characters)
        tNom.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Focus lost
                if (tNom.getText().length() < 3) {
                    showAlert("Le nom doit avoir au moins 3 caractères.");
                }
            }
        });



        // Validate paiment field (should be a positive number)
        tPaiement.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double amount = Double.parseDouble(newValue);
                if (amount < 0) {
                    showAlert("Le paiement doit être un nombre positif.");
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

//    public ObservableList<Salary> getSalary(){
//        ObservableList<Salary> salaries = FXCollections.observableArrayList();
//        String query = "SELECT id, ref, name, department, paiment, paimentdate FROM emp_paiment";
//        con = DBConnexion.getInstance().getConnection();
//        try {
//            st = con.prepareStatement(query);
//            rs = st.executeQuery();
//            while (rs.next()){
//                Salary salary = new Salary();
//                salary.setId(rs.getInt("id"));  // Set the ID property
//                salary.setRef(rs.getInt("ref"));
//                salary.setName(rs.getString("name"));
//                salary.setDepartment(rs.getString("department"));
//                salary.setPaiment(rs.getInt("paiment"));
//                salary.setPaimentdate(rs.getString("paimentdate"));
//                salaries.add(salary);
//            }
//        } catch (SQLException e){
//            throw new RuntimeException(e);
//        }
//        return salaries;
//    }

//    public void afficher(){
//        ObservableList<Salary> salaries = getSalary();
//        tableview.setItems(salaries);
//        colRef.setCellValueFactory(new PropertyValueFactory<Salary, Integer>("ref"));
//        colNom.setCellValueFactory(new PropertyValueFactory<Salary,String>("name"));
//        colDepartement.setCellValueFactory(new PropertyValueFactory<Salary,String>("department"));
//        colPaiement.setCellValueFactory(new PropertyValueFactory<Salary,Integer>("paiment"));
//        colDate.setCellValueFactory(new PropertyValueFactory<Salary,String>("paimentdate"));
//        colTax.setCellValueFactory(new PropertyValueFactory<Salary,Double>("tax"));
//        colDeduction.setCellValueFactory(new PropertyValueFactory<Salary,Double>("deduction"));
//        colNet.setCellValueFactory(new PropertyValueFactory<Salary,Double>("net"));
//    }

    @FXML
    void Add(ActionEvent event) {
        LocalDate localDate = tDate.getValue();
        String dateString = localDate.toString();
        double paiment = Double.parseDouble(tPaiement.getText());
        double tax = paiment*0.1;
        double deduction = tax*0.05;
        double net = paiment - tax - deduction;
        String selectedDepartmentText = (String) tDepartement.getValue(); // Get the selected department

        Salary s = new Salary(
                parseInt(tRef.getText()),
                parseInt(tPaiement.getText()),
                tax,
                deduction,
                net,
                tNom.getText(),
                selectedDepartmentText,
                dateString
        );
        try{
            ss.create(s);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Procedure du salaire");
            alert.setContentText("Salaire ajouter avec succées");
            alert.show();
            initialize();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void Clear(ActionEvent event) {
        tRef.setText("");
        tNom.setText("");
        tDepartement.setValue("");
        tPaiement.setText("");
        tDate.setValue(null);
    }

    @FXML
    void Supprimer(ActionEvent event) {
        Salary selectedSalary = tableview.getSelectionModel().getSelectedItem();
        if (selectedSalary != null) {
            int id = selectedSalary.getId(); // Assuming getId() returns the ID
            try {

                ss.delete(id);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Procedure de Paiment");
                alert.setContentText("Paiment supprimé avec succès !");
                alert.show();

                initialize(); // Refresh TableView
            } catch (SQLException e) {
                // Show error message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Procedure de Paiment");
                alert.setContentText("Une erreur s'est produite : " + e.getMessage());
                alert.show();
                throw new RuntimeException(e);
            }
        } else {
            // Handle case where no Salary is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setContentText("Veuillez sélectionner un élément à supprimer !");
            alert.show();
        }
    }

    @FXML
    void Modifier(ActionEvent event) {
        Salary selectedSalary = tableview.getSelectionModel().getSelectedItem();
        if (selectedSalary != null) {
            int id = selectedSalary.getId(); // Retrieve the ID of the selected Salary
            LocalDate localDate = tDate.getValue();
            String dateString = localDate.toString();

            double tax = selectedSalary.calculateTax(selectedSalary.getPaiment());
            double deduction = selectedSalary.calculateDeduction(selectedSalary.getPaiment());
            double net = selectedSalary.calculateNet(selectedSalary.getPaiment());
            Salary modifiedSalary = new Salary(
                    id, // Provide the ID for the modified Salary
                    Integer.parseInt(tRef.getText()),
                    Integer.parseInt(tPaiement.getText()),
                    tax,
                    deduction,
                    net,
                    tNom.getText(),
                    tDepartement.getValue(),
                    dateString);

            try {
                // Update the item in the database
                ss.update(modifiedSalary);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Modification réussie");
                alert.setContentText("Paiement modifié avec succès !");
                alert.show();
                initialize();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Erreur");
                alert.setContentText("Erreur lors de la modification du paiement : " + e.getMessage());
                alert.show();
                e.printStackTrace();
            }
        } else {
            // Handle case where no item is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setContentText("Veuillez sélectionner un paiement à modifier.");
            alert.show();
        }
    }



    @FXML
    void tableview() {
        tableview.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Salary selectedSalary = tableview.getSelectionModel().getSelectedItem();
                String dep = selectedSalary.getDepartment();
                if (selectedSalary != null) {
                    tRef.setText(String.valueOf(selectedSalary.getRef()));
                    LocalDate localDate = LocalDate.parse(selectedSalary.getPaimentdate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    tDate.setValue(localDate);
                    tNom.setText(selectedSalary.getName());
                    tDepartement.setValue(dep);
                    tPaiement.setText(String.valueOf(selectedSalary.getPaiment()));
                } else {
                    // Clear text fields if no item is selected
                    tRef.clear();
                    tDate.setValue(null);
                    tNom.clear();
                    tDepartement.setValue(null);
                    tPaiement.clear();
                }
            }
        });
    }
    @FXML
    void Paiment_Action(ActionEvent event) throws IOException {
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
        Salary selectedSalary = tableview.getSelectionModel().getSelectedItem();
        if (selectedSalary != null) {
            LocalDate localDate = tDate.getValue();
            String dateString = localDate.toString();

            double tax = selectedSalary.calculateTax(selectedSalary.getPaiment());
            double deduction = selectedSalary.calculateDeduction(selectedSalary.getPaiment());
            double net = selectedSalary.calculateNet(selectedSalary.getPaiment());

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

            contentStream.showText(" ID:" + selectedSalary.getId());
            yPosition -= lineSpacing;
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Employee Name:" + selectedSalary.getName());
            yPosition -= lineSpacing;
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Departement:" + selectedSalary.getDepartment());
            yPosition -= lineSpacing;
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Paiment avant tax:" + selectedSalary.getPaiment());
            yPosition -= lineSpacing;
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Paiement apres tax:" + net);
            yPosition -= lineSpacing;
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Tax + Deduction:" + (tax + deduction));
            yPosition -= lineSpacing;
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Date:" + dateString);
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

    @FXML
    void Search(ActionEvent event) throws SQLException {
        String searchText = tSearch.getText();
        if (!searchText.isEmpty()) {
            try {
                ObservableList<Salary> searchResults = FXCollections.observableList(ss.searchByCode(searchText));
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

    private List<Salary> searchList(String searchText) throws SQLException {
        // Implement the search logic using the SalaryService
        return ss.searchByCode(searchText);
    }
}



