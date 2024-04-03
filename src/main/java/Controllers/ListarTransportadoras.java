package Controllers;

import Entity.Transportadora;
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

public class ListarTransportadoras implements Initializable {

    @FXML
    private TableView<Transportadora> TransportadoraView;

    @FXML
    private TableColumn<Transportadora, Integer> idTransportadoraColumn;

    @FXML
    private TableColumn<Transportadora, String> nomeColumn;

    @FXML
    private TableColumn<Transportadora, String> contactoColumn;

    @FXML
    private TableColumn<Transportadora, String> moradaColumn;

    @FXML
    private TableColumn<Transportadora, String> emailColumn;

    @FXML
    private TableColumn<Transportadora, String> websiteColumn;


    ObservableList<Transportadora>  transportadoraList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    private void loadData() {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT * FROM transportadora";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            idTransportadoraColumn.setCellValueFactory(new PropertyValueFactory<>("idTransportadora"));
            nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
            contactoColumn.setCellValueFactory(new PropertyValueFactory<>("contacto"));
            moradaColumn.setCellValueFactory(new PropertyValueFactory<>("morada"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            websiteColumn.setCellValueFactory(new PropertyValueFactory<>("website"));


            while (rs.next()) {
                Transportadora transportadoraInfo = new Transportadora(
                        rs.getInt("id_transportadora"),
                        rs.getString("nome"),
                        rs.getString("contacto"),
                        rs.getString("morada"),
                        rs.getString("email"),
                        rs.getString("website")
                );

                transportadoraList.add(transportadoraInfo);
            }

            TransportadoraView.setItems(transportadoraList);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
    }

    private void refreshData() {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT * FROM transportadora";

        transportadoraList.clear();
        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            idTransportadoraColumn.setCellValueFactory(new PropertyValueFactory<>("idTransportadora"));
            nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
            contactoColumn.setCellValueFactory(new PropertyValueFactory<>("contacto"));
            moradaColumn.setCellValueFactory(new PropertyValueFactory<>("morada"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            websiteColumn.setCellValueFactory(new PropertyValueFactory<>("website"));

            while (rs.next()) {
                Transportadora transportadoraInfo = new Transportadora(
                        rs.getInt("id_transportadora"),
                        rs.getString("nome"),
                        rs.getString("contacto"),
                        rs.getString("morada"),
                        rs.getString("email"),
                        rs.getString("website")
                );

                transportadoraList.add(transportadoraInfo);
            }

            TransportadoraView.setItems(transportadoraList);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
    }

    @FXML
    public void handleAdicionarTransportadoraButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/AdicionarTransportadora.fxml"));
        switchScene(event, root);
    }

    @FXML
    public void handleDeleteButton(ActionEvent event) {
        Transportadora selectedTransportadora = TransportadoraView.getSelectionModel().getSelectedItem();

        if (selectedTransportadora != null) {
            DatabaseConnection connection = new DatabaseConnection();
            String deleteSql = "DELETE FROM transportadora WHERE id_transportadora = ?;";

            try (Connection conn = connection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(deleteSql)) {

                stmt.setInt(1, selectedTransportadora.getIdTransportadora());
                stmt.executeUpdate();

                transportadoraList.remove(selectedTransportadora);

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
