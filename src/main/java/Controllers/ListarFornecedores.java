package Controllers;

import Entity.Fornecedor;
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

public class ListarFornecedores implements Initializable {

    @FXML
    private TableView<Fornecedor> FornecedorView;

    @FXML
    private TableColumn<Fornecedor, Integer> idFornecedorColumn;

    @FXML
    private TableColumn<Fornecedor, String> nomeColumn;

    @FXML
    private TableColumn<Fornecedor, String> contactoColumn;

    @FXML
    private TableColumn<Fornecedor, String> moradaColumn;

    @FXML
    private TableColumn<Fornecedor, String> emailColumn;

    @FXML
    private TableColumn<Fornecedor, String> websiteColumn;


    ObservableList<Fornecedor>  fornecedorList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    private void loadData() {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT * FROM fornecedor";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            idFornecedorColumn.setCellValueFactory(new PropertyValueFactory<>("idFornecedor"));
            nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
            contactoColumn.setCellValueFactory(new PropertyValueFactory<>("contacto"));
            moradaColumn.setCellValueFactory(new PropertyValueFactory<>("morada"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            websiteColumn.setCellValueFactory(new PropertyValueFactory<>("website"));


            while (rs.next()) {
                Fornecedor fornecedorInfo = new Fornecedor(
                        rs.getInt("id_fornecedor"),
                        rs.getString("nome"),
                        rs.getString("contacto"),
                        rs.getString("morada"),
                        rs.getString("email"),
                        rs.getString("website")
                );

                fornecedorList.add(fornecedorInfo);
            }

            FornecedorView.setItems(fornecedorList);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
    }

    private void refreshData() {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT * FROM fornecedor";

        fornecedorList.clear();
        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            idFornecedorColumn.setCellValueFactory(new PropertyValueFactory<>("idFornecedor"));
            nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
            contactoColumn.setCellValueFactory(new PropertyValueFactory<>("contacto"));
            moradaColumn.setCellValueFactory(new PropertyValueFactory<>("morada"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            websiteColumn.setCellValueFactory(new PropertyValueFactory<>("website"));

            while (rs.next()) {
                Fornecedor fornecedorInfo = new Fornecedor(
                        rs.getInt("id_fornecedor"),
                        rs.getString("nome"),
                        rs.getString("contacto"),
                        rs.getString("morada"),
                        rs.getString("email"),
                        rs.getString("website")
                );

                fornecedorList.add(fornecedorInfo);
            }

            FornecedorView.setItems(fornecedorList);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
    }

    @FXML
    public void handleAdicionarFornecedorButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/AdicionarFornecedor.fxml"));
        switchScene(event, root);
    }

    @FXML
    public void handleDeleteButton(ActionEvent event) {
        Fornecedor selectedFornecedor = FornecedorView.getSelectionModel().getSelectedItem();

        if (selectedFornecedor != null) {
            DatabaseConnection connection = new DatabaseConnection();
            String deleteSql = "DELETE FROM fornecedor WHERE id_fornecedor = ?;";

            try (Connection conn = connection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(deleteSql)) {

                stmt.setInt(1, selectedFornecedor.getIdFornecedor());
                stmt.executeUpdate();

                fornecedorList.remove(selectedFornecedor);

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
