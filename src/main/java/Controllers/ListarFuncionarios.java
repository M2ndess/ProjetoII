package Controllers;

import Entity.Funcionario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ListarFuncionarios implements Initializable {

    @FXML
    private TableView<Funcionario> FuncionarioView;

    @FXML
    private TableColumn<Funcionario, Integer> idFuncionarioColumn;

    @FXML
    private TableColumn<Funcionario, String> nomeColumn;

    @FXML
    private TableColumn<Funcionario, String> contactoColumn;

    @FXML
    private TableColumn<Funcionario, String> moradaColumn;

    @FXML
    private TableColumn<Funcionario, String> emailColumn;

    @FXML
    private TableColumn<Funcionario, String> nifColumn;

    @FXML
    private TableColumn<Funcionario, String> usernameColumn;

    @FXML
    private TableColumn<Funcionario, String> estadoContaColumn;


    ObservableList<Funcionario>  funcionarioList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    private void loadData() {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT * FROM funcionario";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            idFuncionarioColumn.setCellValueFactory(new PropertyValueFactory<>("idFuncionario"));
            nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
            contactoColumn.setCellValueFactory(new PropertyValueFactory<>("contacto"));
            moradaColumn.setCellValueFactory(new PropertyValueFactory<>("morada"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            nifColumn.setCellValueFactory(new PropertyValueFactory<>("nif"));
            usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
            estadoContaColumn.setCellValueFactory(new PropertyValueFactory<>("estadoConta"));


            while (rs.next()) {
                Funcionario funcionarioInfo = new Funcionario(
                        rs.getInt("id_funcionario"),
                        rs.getString("nome"),
                        rs.getString("contacto"),
                        rs.getString("morada"),
                        rs.getString("email"),
                        rs.getString("nif"),
                        rs.getString("username"),
                        rs.getString("estado_conta")
                );

                funcionarioList.add(funcionarioInfo);
            }

            FuncionarioView.setItems(funcionarioList);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
    }

    private void refreshData() {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT * FROM funcionario";

        funcionarioList.clear();
        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            idFuncionarioColumn.setCellValueFactory(new PropertyValueFactory<>("idFuncionario"));
            nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
            contactoColumn.setCellValueFactory(new PropertyValueFactory<>("contacto"));
            moradaColumn.setCellValueFactory(new PropertyValueFactory<>("morada"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            nifColumn.setCellValueFactory(new PropertyValueFactory<>("nif"));
            usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
            estadoContaColumn.setCellValueFactory(new PropertyValueFactory<>("estadoConta"));


            while (rs.next()) {
                Funcionario funcionarioInfo = new Funcionario(
                        rs.getInt("id_funcionario"),
                        rs.getString("nome"),
                        rs.getString("contacto"),
                        rs.getString("morada"),
                        rs.getString("email"),
                        rs.getString("nif"),
                        rs.getString("username"),
                        rs.getString("estado_conta")
                );

                funcionarioList.add(funcionarioInfo);
            }

            FuncionarioView.setItems(funcionarioList);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
    }

    @FXML
    public void handleAdicionarFuncionarioButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/AdicionarFuncionario.fxml"));
        switchScene(event, root);
    }

    @FXML
    public void handleDeactivateButton(ActionEvent event) {
        Funcionario selectedFuncionario = FuncionarioView.getSelectionModel().getSelectedItem();

        if (selectedFuncionario != null) {
            DatabaseConnection connection = new DatabaseConnection();
            String updateSql = "UPDATE funcionario SET estado_conta = ? WHERE id_funcionario = ?;";

            try (Connection conn = connection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(updateSql)) {

                String previousEstadoConta = selectedFuncionario.getEstadoConta();
                String newEstadoConta = previousEstadoConta.equals("Ativo") ? "Inativo" : "Ativo";

                stmt.setString(1, newEstadoConta);
                stmt.setInt(2, selectedFuncionario.getIdFuncionario());
                stmt.executeUpdate();

                selectedFuncionario.setEstadoConta(newEstadoConta);
                refreshData();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closeConnection();
            }
        }
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
