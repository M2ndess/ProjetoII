package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Entity.Cliente;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdicionarCliente {

    @FXML
    private TextField nomeField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField telefoneField;
    @FXML
    private TextField nifField;
    @FXML
    private TextField passwordField;

    @FXML
    public void handleAdicionarCliente(ActionEvent event) {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String telefone = telefoneField.getText();
        String nif = nifField.getText();
        String password = passwordField.getText();

        if (nome.isEmpty() || email.isEmpty() || username.isEmpty() || telefone.isEmpty() || nif.isEmpty() || password.isEmpty()) {
            // Handle the error: show an alert or update the UI to indicate that all fields must be filled
            System.out.println("All fields must be filled");
            return;
        }

        DatabaseConnection connection = new DatabaseConnection();
        String sql = "INSERT INTO cliente (nome, email, username, telefone, nif, password, estado_conta) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, username);
            stmt.setString(4, telefone);
            stmt.setString(5, nif);
            stmt.setString(6, password);
            stmt.setString(7, "Ativo");

            stmt.executeUpdate();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
    }

    private void clearFields() {
        nomeField.clear();
        emailField.clear();
        usernameField.clear();
        telefoneField.clear();
        nifField.clear();
        passwordField.clear();
    }

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        switchScene(event, root);
    }

    private void switchScene(ActionEvent event, Parent root) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
