package Controllers;

import Entity.Transportadora;
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

public class AdicionarTransportadora {

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

    // Method to create a new Transportadora
    public void handleAdicionarTransportadora(ActionEvent event) throws IOException {
        String nome = nameField.getText();
        String contacto = phoneField.getText();
        String morada = addressField.getText();
        String email = emailField.getText();
        String website = websiteField.getText();


        // Create a new Transportadora object
        Transportadora transportadora = new Transportadora(nome, contacto, morada, email, website);

        // Insert the Transportadora into the database
        if (insertTransportadora(transportadora)) {
            System.out.println("Transportadora inserted successfully!");
            switchToPreviousScreen(event);
        } else {
            System.out.println("Failed to insert Transportadora.");
        }
    }

    // Insert the new Transportadora object into the database
    private boolean insertTransportadora(Transportadora transportadora) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "INSERT INTO \"transportadora\" (nome, contacto, morada, email, website) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, transportadora.getNome());
            stmt.setString(2, transportadora.getContacto());
            stmt.setString(3, transportadora.getMorada());
            stmt.setString(4, transportadora.getEmail());
            stmt.setString(5, transportadora.getWebsite());
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
        root = FXMLLoader.load(getClass().getResource("/fxml/ListarTransportadoras.fxml"));
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
