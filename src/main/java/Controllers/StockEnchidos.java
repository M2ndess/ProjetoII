package Controllers;

import Entity.Enchido;
import Entity.EnchidoInfo;
import Entity.Lote;
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
import java.util.Date;
import java.util.ResourceBundle;

public class StockEnchidos implements Initializable {
    @FXML
    private TableView<EnchidoInfo> EnchidoView;
    @FXML
    private TableColumn<Enchido, Integer> idEnchidoColumn;
    @FXML
    private TableColumn<Lote, Integer> idLoteColumn;
    @FXML
    private TableColumn<Enchido, String> tipoEnchidoColumn;
    @FXML
    private TableColumn<Lote, Integer> quantidadeColumn;
    @FXML
    private TableColumn<Lote, Double> custoColumn;
    @FXML
    private TableColumn<Enchido, String> faseProducaoColumn;
    @FXML
    private TableColumn<Lote, Date> dataCriacaoColumn;
    @FXML
    private TableColumn<Lote, Date> dataValidadeColumn;
    @FXML
    private TableColumn<Lote, String> estadoLoteColumn;

    ObservableList<EnchidoInfo>  enchidoList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadData();
    }

    private void loadData() {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT enchido.id_enchido, lote.id_lote, enchido.tipo_enchido, enchido.quantidade, lote.custo, enchido.fase_producao, lote.data_criacao, lote.data_validade, lote.estado_lote \n" +
                "FROM enchido\n" +
                "JOIN lote ON enchido.id_lote = lote.id_lote;\n";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            idEnchidoColumn.setCellValueFactory(new PropertyValueFactory<>("idEnchido"));
            idLoteColumn.setCellValueFactory(new PropertyValueFactory<>("idLote"));
            tipoEnchidoColumn.setCellValueFactory(new PropertyValueFactory<>("tipoEnchido"));
            quantidadeColumn.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
            custoColumn.setCellValueFactory(new PropertyValueFactory<>("custo"));
            faseProducaoColumn.setCellValueFactory(new PropertyValueFactory<>("faseProducao"));
            dataCriacaoColumn.setCellValueFactory(new PropertyValueFactory<>("dataCriacao"));
            dataValidadeColumn.setCellValueFactory(new PropertyValueFactory<>("dataValidade"));
            estadoLoteColumn.setCellValueFactory(new PropertyValueFactory<>("estadoLote"));

            while (rs.next()) {
                EnchidoInfo enchidoInfo = new EnchidoInfo(
                        rs.getInt("id_enchido"),
                        rs.getInt("id_lote"),
                        rs.getString("tipo_enchido"),
                        rs.getInt("quantidade"),
                        rs.getDouble("custo"),
                        rs.getString("fase_producao"),
                        rs.getDate("data_criacao"),
                        rs.getDate("data_validade"),
                        rs.getString("estado_lote")
                );

                enchidoList.add(enchidoInfo);
            }

            EnchidoView.setItems(enchidoList);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
    }

    private void refreshData() {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT enchido.id_enchido, lote.id_lote, enchido.tipo_enchido, enchido.quantidade, lote.custo, enchido.fase_producao, lote.data_criacao, lote.data_validade, lote.estado_lote \n" +
                "FROM enchido\n" +
                "JOIN lote ON enchido.id_lote = lote.id_lote;\n";

        enchidoList.clear();
        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            idEnchidoColumn.setCellValueFactory(new PropertyValueFactory<>("idEnchido"));
            idLoteColumn.setCellValueFactory(new PropertyValueFactory<>("idLote"));
            tipoEnchidoColumn.setCellValueFactory(new PropertyValueFactory<>("tipoEnchido"));
            quantidadeColumn.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
            custoColumn.setCellValueFactory(new PropertyValueFactory<>("custo"));
            faseProducaoColumn.setCellValueFactory(new PropertyValueFactory<>("faseProducao"));
            dataCriacaoColumn.setCellValueFactory(new PropertyValueFactory<>("dataCriacao"));
            dataValidadeColumn.setCellValueFactory(new PropertyValueFactory<>("dataValidade"));
            estadoLoteColumn.setCellValueFactory(new PropertyValueFactory<>("estadoLote"));

            while (rs.next()) {
                EnchidoInfo enchidoInfo = new EnchidoInfo(
                        rs.getInt("id_enchido"),
                        rs.getInt("id_lote"),
                        rs.getString("tipo_enchido"),
                        rs.getInt("quantidade"),
                        rs.getDouble("custo"),
                        rs.getString("fase_producao"),
                        rs.getDate("data_criacao"),
                        rs.getDate("data_validade"),
                        rs.getString("estado_lote")
                );
            }

            EnchidoView.setItems(enchidoList);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
    }

    @FXML
    public void handleAdicionarEnchidoButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/AdicionarEnchido.fxml"));
        switchScene(event, root);
    }

    @FXML
    public void handleDeleteButton(ActionEvent event) {
        EnchidoInfo selectedEnchido = EnchidoView.getSelectionModel().getSelectedItem();

        if (selectedEnchido != null) {
            DatabaseConnection connection = new DatabaseConnection();
            String deleteSql = "DELETE FROM enchido WHERE id_enchido = ?";

            try (Connection conn = connection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(deleteSql)) {

                stmt.setInt(1, selectedEnchido.getIdEnchido());
                stmt.executeUpdate();

                enchidoList.remove(selectedEnchido);

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
