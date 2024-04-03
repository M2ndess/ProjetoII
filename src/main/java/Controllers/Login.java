package Controllers;

import Entity.Funcionario;
import Entity.FuncionarioNotFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    private static Funcionario loggedInFuncionario;

    @FXML
    private TextField UsernameField;
    @FXML
    private PasswordField PasswordField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    //Login Method
    @FXML
    public void handleLogin(ActionEvent event) throws IOException, FuncionarioNotFoundException {
        String username = UsernameField.getText();
        String password = PasswordField.getText();

        if (authenticate(username, password)) {
            // Successful login
            // Set the logged-in Funcionario
            loggedInFuncionario = getFuncionarioByUsername(username);

            // Navigate to the main menu or another screen
            root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
            switchScene(event);
        } else {
            // Failed login
            showAlert(Alert.AlertType.ERROR, "Login Error", "Invalid username or password.");
        }
    }

    // Database Funcionario Authentication
    private boolean authenticate(String username, String password) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT * FROM funcionario WHERE username = ? AND password = ? AND estado_conta = 'Ativo'";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connection.closeConnection();
        }
    }

    // Retrieve Funcionario by username from the database
    private Funcionario getFuncionarioByUsername(String username) throws FuncionarioNotFoundException {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT * FROM \"funcionario\" WHERE username = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Create a new Funcionário object and populate its properties from the ResultSet
                Funcionario funcionario = new Funcionario();
                funcionario.setIdFuncionario(rs.getInt("id_funcionario"));
                return funcionario;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }

        throw new FuncionarioNotFoundException("Error occurred while retrieving Funcionario for username: " + username);
    }

    public static Funcionario getLoggedInFuncionario() {
        return loggedInFuncionario;
    }

    //Quit Button
    @FXML
    public void handleQuit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    //Method to switch scenes
    private void switchScene(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Wrong username and password alert
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
