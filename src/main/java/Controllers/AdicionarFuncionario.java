package Controllers;

import Entity.Funcionario;
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

public class AdicionarFuncionario {

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
    private TextField nifField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    // Method to create a new Funcionario
    public void handleAdicionarFuncionario(ActionEvent event) throws IOException {
        String nome = nameField.getText();
        String contacto = phoneField.getText();
        String morada = addressField.getText();
        String email = emailField.getText();
        String nif = nifField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String estado_lote = "Ativo";


        // Create a new Funcionario object
        Funcionario funcionario = new Funcionario(nome, contacto, morada, email, nif, username, password, estado_lote);

        // Insert the Funcionario into the database
        if (insertFuncionario(funcionario)) {
            System.out.println("Funcionario inserted successfully!");
            switchToPreviousScreen(event);
        } else {
            System.out.println("Failed to insert Funcionario.");
        }
    }

    // Insert the new Funcionario object into the database
    private boolean insertFuncionario(Funcionario funcionario) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "INSERT INTO \"funcionario\" (nome, contacto, morada, email, nif, username, password, estado_conta) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getContacto());
            stmt.setString(3, funcionario.getMorada());
            stmt.setString(4, funcionario.getEmail());
            stmt.setString(5, funcionario.getNif());
            stmt.setString(6, funcionario.getUsername());
            stmt.setString(7, funcionario.getPassword());
            stmt.setString(8, funcionario.getEstadoConta());
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
        root = FXMLLoader.load(getClass().getResource("/fxml/ListarFuncionarios.fxml"));
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
