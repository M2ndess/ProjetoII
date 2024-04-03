package Controllers;

import Entity.Fornecedor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdicionarFornecedor {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField websiteField;

    // Method to create a new Fornecedor
    public void handleAdicionarFornecedor(ActionEvent event) throws IOException {
        String nome = nameField.getText();
        String contacto = phoneField.getText();
        String morada = addressField.getText();
        String email = emailField.getText();
        String website = websiteField.getText();


        // Create a new Fornecedor object
        Fornecedor fornecedor = new Fornecedor(nome, contacto, morada, email, website);

        // Insert the Fornecedor into the database
        if (insertFornecedor(fornecedor)) {
            System.out.println("Fornecedor inserted successfully!");
            switchToPreviousScreen(event);
        } else {
            System.out.println("Failed to insert Fornecedor.");
        }
    }

    // Insert the new Fornecedor object into the database
    private boolean insertFornecedor(Fornecedor fornecedor) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "INSERT INTO \"fornecedor\" (nome, contacto, morada, email, website) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getContacto());
            stmt.setString(3, fornecedor.getMorada());
            stmt.setString(4, fornecedor.getEmail());
            stmt.setString(5, fornecedor.getWebsite());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            connection.closeConnection();
        }
    }

    //Switch to Previous Screen
    private void switchToPreviousScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/ListarFornecedores.fxml"));
        switchScene(event);
    }

    //Go Back Button
    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        switchScene(event);
    }

    //Method to switch scenes
    private void switchScene(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
