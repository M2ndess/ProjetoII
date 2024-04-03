package Controllers;

import Entity.Lote;
import Entity.MateriaPrima;
import Entity.MateriaPrimaInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class StockMateriasPrimas implements Initializable {

    private Map<String, Integer> fornecedorMap = new HashMap<>();

    @FXML
    private TableView<MateriaPrimaInfo> MateriaPrimaView;

    @FXML
    private TableColumn<MateriaPrima, Integer> idMateriaPrimaColumn;

    @FXML
    private TableColumn<Lote, Integer> idLoteColumn;

    @FXML
    private TableColumn<MateriaPrima, Integer> idFornecedorColumn;

    @FXML
    private TableColumn<MateriaPrima, String> tipoMateriaPrimaColumn;

    @FXML
    private TableColumn<Lote, Integer> quantidadeColumn;

    @FXML
    private TableColumn<Lote, Double> custoColumn;

    @FXML
    private TableColumn<Lote, Date> dataCriacaoColumn;

    @FXML
    private TableColumn<Lote, Date> dataValidadeColumn;

    @FXML
    private TableColumn<Lote, String> estadoLoteColumn;

    ObservableList<MateriaPrimaInfo>  materiaPrimaList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    private void loadData() {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT materia_prima.id_materia_prima, lote.id_lote, materia_prima.id_fornecedor, materia_prima.tipo_materia_prima, materia_prima.quantidade, lote.custo, lote.data_criacao, lote.data_validade, lote.estado_lote \n" +
                "FROM materia_prima\n" +
                "JOIN lote ON materia_prima.id_lote = lote.id_lote";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            idMateriaPrimaColumn.setCellValueFactory(new PropertyValueFactory<>("idMateriaPrima"));
            idLoteColumn.setCellValueFactory(new PropertyValueFactory<>("idLote"));
            idFornecedorColumn.setCellValueFactory(new PropertyValueFactory<>("idFornecedor"));
            tipoMateriaPrimaColumn.setCellValueFactory(new PropertyValueFactory<>("tipoMateriaPrima"));
            quantidadeColumn.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
            custoColumn.setCellValueFactory(new PropertyValueFactory<>("custo"));
            dataCriacaoColumn.setCellValueFactory(new PropertyValueFactory<>("dataCriacao"));
            dataValidadeColumn.setCellValueFactory(new PropertyValueFactory<>("dataValidade"));
            estadoLoteColumn.setCellValueFactory(new PropertyValueFactory<>("estadoLote"));

            while (rs.next()) {
                MateriaPrimaInfo materiaPrimaInfo = new MateriaPrimaInfo(
                        rs.getInt("id_materia_prima"),
                        rs.getInt("id_lote"),
                        rs.getInt("id_fornecedor"),
                        rs.getString("tipo_materia_prima"),
                        rs.getInt("quantidade"),
                        rs.getDouble("custo"),
                        rs.getDate("data_criacao"),
                        rs.getDate("data_validade"),
                        rs.getString("estado_lote")
                );

                materiaPrimaList.add(materiaPrimaInfo);
            }

            MateriaPrimaView.setItems(materiaPrimaList);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
    }

    private void refreshData() {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT materia_prima.id_materia_prima, lote.id_lote, materia_prima.id_fornecedor, materia_prima.tipo_materia_prima, materia_prima.quantidade, lote.custo, lote.data_criacao, lote.data_validade, lote.estado_lote \n" +
                "FROM materia_prima\n" +
                "JOIN lote ON materia_prima.id_lote = lote.id_lote;\n";

        materiaPrimaList.clear();
        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            idMateriaPrimaColumn.setCellValueFactory(new PropertyValueFactory<>("idMateriaPrima"));
            idLoteColumn.setCellValueFactory(new PropertyValueFactory<>("idLote"));
            idFornecedorColumn.setCellValueFactory(new PropertyValueFactory<>("idFornecedor"));
            tipoMateriaPrimaColumn.setCellValueFactory(new PropertyValueFactory<>("tipoMateriaPrima"));
            quantidadeColumn.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
            custoColumn.setCellValueFactory(new PropertyValueFactory<>("custo"));
            dataCriacaoColumn.setCellValueFactory(new PropertyValueFactory<>("data_criacao"));
            dataValidadeColumn.setCellValueFactory(new PropertyValueFactory<>("data_validade"));
            estadoLoteColumn.setCellValueFactory(new PropertyValueFactory<>("estado_lote"));

            while (rs.next()) {
                MateriaPrimaInfo materiaPrimaInfo = new MateriaPrimaInfo(
                        rs.getInt("id_materia_prima"),
                        rs.getInt("id_lote"),
                        rs.getInt("id_fornecedor"),
                        rs.getString("tipo_materia_prima"),
                        rs.getInt("quantidade"),
                        rs.getDouble("custo"),
                        rs.getDate("data_criacao"),
                        rs.getDate("data_validade"),
                        rs.getString("estado_lote")
                );

                materiaPrimaList.add(materiaPrimaInfo);
            }

            MateriaPrimaView.setItems(materiaPrimaList);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
    }

    @FXML
    public void handleAdicionarMateriaPrimaButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/AdicionarMateriaPrima.fxml"));
        switchScene(event, root);
    }

    @FXML
    public void handleDeleteButton(ActionEvent event) {
        MateriaPrimaInfo selectedMateriaPrima = MateriaPrimaView.getSelectionModel().getSelectedItem();

        if (selectedMateriaPrima != null) {
            DatabaseConnection connection = new DatabaseConnection();
            String deleteSql = "DELETE FROM materia_prima WHERE id_lote = ?;" +
                    "DELETE FROM lote WHERE id_lote = ?";

            try (Connection conn = connection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(deleteSql)) {

                stmt.setInt(1, selectedMateriaPrima.getIdLote());
                stmt.setInt(2, selectedMateriaPrima.getIdLote());
                stmt.executeUpdate();

                materiaPrimaList.remove(selectedMateriaPrima);

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
