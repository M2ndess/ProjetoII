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
import javafx.scene.control.ChoiceBox;
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

public class FaseProducao implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ChoiceBox faseProducaoDropdown;
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
        populateFaseProducaoDropdown();
    }

    private void populateFaseProducaoDropdown() {
        ObservableList<String> faseProducaoOptions = FXCollections.observableArrayList(
                "Preparação",
                "Cura",
                "Fumagem"
        );
        faseProducaoDropdown.setItems(faseProducaoOptions);
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

                enchidoList.add(enchidoInfo);
            }

            EnchidoView.setItems(enchidoList);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
    }

    //Mudar Fase Producao Button
    @FXML
    public void handleMudarFaseProducaoButton(ActionEvent event) {
        EnchidoInfo selectedEnchido = EnchidoView.getSelectionModel().getSelectedItem();

        if (selectedEnchido != null) {
            DatabaseConnection connection = new DatabaseConnection();
            String deleteSql = "UPDATE enchido SET fase_producao = ? WHERE id_enchido = ?";
            String newFaseProducao = (String) faseProducaoDropdown.getValue();

            try (Connection conn = connection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(deleteSql)) {

                stmt.setString(1, newFaseProducao);
                stmt.setInt(2, selectedEnchido.getIdEnchido());
                stmt.executeUpdate();
                refreshData();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closeConnection();
            }
        }
    }

    //Go Back Button
    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        switchScene(event);
    }

    //Method to switch scenes
    private void switchScene(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
