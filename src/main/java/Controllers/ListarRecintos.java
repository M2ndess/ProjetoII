package Controllers;

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
import Entity.Recinto;
import Entity.Cliente;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ListarRecintos implements Initializable {

    @FXML
    private TableView<Recinto> recintoView;

    @FXML
    private TableColumn<Recinto, Integer> idRecintoColumn;

    @FXML
    private TableColumn<Recinto, Integer> IdProprietarioColumn;

    @FXML
    private TableColumn<Recinto, String> nomeRecintoColumn;

    @FXML
    private TableColumn<Recinto, String> moradaRecintoColumn;

    @FXML
    private TableColumn<Recinto, String> horarioColumn;

    @FXML
    private TableColumn<Recinto, String> infoColumn;

    @FXML
    private TableColumn<Recinto, String> estadoRecintoColumn;

    private ObservableList<Recinto> recintoList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    private void loadData() {
        ClienteService clienteService = new ClienteService();

        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT * FROM recinto";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            idRecintoColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            IdProprietarioColumn.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
            nomeRecintoColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
            moradaRecintoColumn.setCellValueFactory(new PropertyValueFactory<>("morada"));
            horarioColumn.setCellValueFactory(new PropertyValueFactory<>("horarioFuncionamento"));
            infoColumn.setCellValueFactory(new PropertyValueFactory<>("infoExtra"));
            estadoRecintoColumn.setCellValueFactory(new PropertyValueFactory<>("estadoRecinto"));

            while (rs.next()) {
                Recinto recintoInfo = new Recinto(
                        rs.getInt("id_recinto"),
                        rs.getInt("id_cliente"),
                        rs.getString("nome"),
                        rs.getString("morada"),
                        rs.getString("horario_funcionamento"),
                        rs.getString("info_extra"),
                        rs.getString("estado_recinto")
                );

                recintoList.add(recintoInfo);
            }

            recintoView.setItems(recintoList);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
    }

    @FXML
    public void handleDeactivateButton(ActionEvent event) {
        Recinto selectedRecinto = recintoView.getSelectionModel().getSelectedItem();

        if (selectedRecinto != null) {

            DatabaseConnection connection = new DatabaseConnection();
            String updateSql = "UPDATE recinto SET estado_recinto = ? WHERE id_recinto = ?;";

            try (Connection conn = connection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(updateSql)) {

                String previousEstadoRecinto = selectedRecinto.getEstadoRecinto();

                // Verifica se o estado da conta é nulo e define como "Inativo" caso seja
                String newEstadoRecinto;
                if (previousEstadoRecinto == null) {
                    newEstadoRecinto = "Inativo";
                } else {
                    newEstadoRecinto = previousEstadoRecinto.equals("Ativo") ? "Inativo" : "Ativo";
                }

                stmt.setString(1, newEstadoRecinto);
                stmt.setInt(2, selectedRecinto.getId());
                stmt.executeUpdate();

                selectedRecinto.setEstadoRecinto(newEstadoRecinto);
                refreshData();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closeConnection();
            }
        }
    }

    private void refreshData() {
        ClienteService clienteService = new ClienteService();

        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT * FROM recinto";

        recintoList.clear();
        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            idRecintoColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            IdProprietarioColumn.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
            nomeRecintoColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
            moradaRecintoColumn.setCellValueFactory(new PropertyValueFactory<>("morada"));
            horarioColumn.setCellValueFactory(new PropertyValueFactory<>("horarioFuncionamento"));
            infoColumn.setCellValueFactory(new PropertyValueFactory<>("infoExtra"));
            estadoRecintoColumn.setCellValueFactory(new PropertyValueFactory<>("estadoRecinto"));


            while (rs.next()) {
                int idCliente = rs.getInt("id_cliente");

                Cliente cliente = clienteService.fetchClienteById(idCliente);

                // Use o cliente retornado para criar uma instância de Recinto
                Recinto recintoInfo = new Recinto(
                        rs.getInt("id_recinto"),
                        rs.getInt("id_cliente"),
                        rs.getString("nome"),
                        rs.getString("morada"),
                        rs.getString("horario_funcionamento"),
                        rs.getString("info_extra"),
                        rs.getString("estado_recinto")
                );

                recintoList.add(recintoInfo);
            }

            recintoView.setItems(recintoList);
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
