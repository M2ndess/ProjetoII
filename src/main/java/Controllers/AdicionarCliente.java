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

        // Verificar o formato do email
        if (!isValidEmail(email)) {
            System.out.println("O email inserido não é válido");
            return;
        }

        // Verificar se o NIF tem exatamente 9 dígitos
        if (!isValidNIF(nif)) {
            System.out.println("O NIF deve conter exatamente 9 dígitos numéricos");
            return;
        }

        // Verificar o formato do telefone
        if (!isValidTelefone(telefone)) {
            System.out.println("O número de telefone não está em um formato válido");
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

    private boolean isValidEmail(String email) {
        // Verificar se o email contém '@'
        return email.contains("@");
    }

    private boolean isValidNIF(String nif) {
        // Verificar se o NIF contém exatamente 9 dígitos
        return nif.length() == 9 && nif.matches("[0-9]+");
    }

    private boolean isValidTelefone(String telefone) {
        // Verifica se o telefone tem exatamente 9 dígitos
        return telefone.length() == 9 && telefone.matches("[0-9]+");
    }
}
