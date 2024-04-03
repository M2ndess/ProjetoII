package Controllers;

import Entity.Cliente;
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

public class ListarClientes implements Initializable {

    @FXML
    private TableView<Cliente> ClienteView;

    @FXML
    private TableColumn<Cliente, Integer> idClienteColumn;

    @FXML
    private TableColumn<Cliente, String> nomeColumn;

    @FXML
    private TableColumn<Cliente, String> contactoColumn;

    @FXML
    private TableColumn<Cliente, String> moradaColumn;

    @FXML
    private TableColumn<Cliente, String> emailColumn;

    @FXML
    private TableColumn<Cliente, String> nifColumn;

    @FXML
    private TableColumn<Cliente, String> usernameColumn;

    @FXML
    private TableColumn<Cliente, String> estadoContaColumn;


    ObservableList<Cliente>  clienteList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    private void loadData() {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT * FROM cliente";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            idClienteColumn.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
            nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
            contactoColumn.setCellValueFactory(new PropertyValueFactory<>("contacto"));
            moradaColumn.setCellValueFactory(new PropertyValueFactory<>("morada"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            nifColumn.setCellValueFactory(new PropertyValueFactory<>("nif"));
            usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
            estadoContaColumn.setCellValueFactory(new PropertyValueFactory<>("estadoConta"));


            while (rs.next()) {
                Cliente clienteInfo = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nome"),
                        rs.getString("contacto"),
                        rs.getString("morada"),
                        rs.getString("email"),
                        rs.getString("nif"),
                        rs.getString("username"),
                        rs.getString("estado_conta")
                );

                clienteList.add(clienteInfo);
            }

            ClienteView.setItems(clienteList);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
    }

    @FXML
    public void handleDeactivateButton(ActionEvent event) {
        Cliente selectedCliente = ClienteView.getSelectionModel().getSelectedItem();

        if (selectedCliente != null) {
            DatabaseConnection connection = new DatabaseConnection();
            String updateSql = "UPDATE cliente SET estado_conta = ? WHERE id_cliente = ?;";

            try (Connection conn = connection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(updateSql)) {

                String previousEstadoConta = selectedCliente.getEstadoConta();
                String newEstadoConta = previousEstadoConta.equals("Ativo") ? "Inativo" : "Ativo";

                stmt.setString(1, newEstadoConta);
                stmt.setInt(2, selectedCliente.getIdCliente());
                stmt.executeUpdate();

                selectedCliente.setEstadoConta(newEstadoConta);
                refreshData();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closeConnection();
            }
        }
    }
    private void refreshData() {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT * FROM cliente";

        clienteList.clear();
        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            idClienteColumn.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
            nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
            contactoColumn.setCellValueFactory(new PropertyValueFactory<>("contacto"));
            moradaColumn.setCellValueFactory(new PropertyValueFactory<>("morada"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            nifColumn.setCellValueFactory(new PropertyValueFactory<>("nif"));
            usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
            estadoContaColumn.setCellValueFactory(new PropertyValueFactory<>("estadoConta"));


            while (rs.next()) {
                Cliente clienteInfo = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nome"),
                        rs.getString("contacto"),
                        rs.getString("morada"),
                        rs.getString("email"),
                        rs.getString("nif"),
                        rs.getString("username"),
                        rs.getString("estado_conta")
                );

                clienteList.add(clienteInfo);
            }

            ClienteView.setItems(clienteList);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
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
