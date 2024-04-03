package Controllers;

import Entity.Entrega;
import Entity.Cliente;
import Entity.PedidoCliente;
import Entity.Transportadora;
import Entity.PedidoClienteInfo;
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

public class ListarPedidosCliente implements Initializable {

    @FXML
    private TableView<PedidoClienteInfo> PedidosClienteView;

    @FXML
    private TableColumn<PedidoCliente, Integer> idPedidoClienteColumn;

    @FXML
    private TableColumn<Entrega, Integer> idEntregaColumn;

    @FXML
    private TableColumn<Transportadora, Integer> idTransportadoraColumn;

    @FXML
    private TableColumn<Cliente, Integer> idClienteColumn;

    @FXML
    private TableColumn<PedidoCliente, Date> dataPedidoColumn;

    @FXML
    private TableColumn<PedidoCliente, String> estadoPedidoColumn;

    @FXML
    private TableColumn<PedidoCliente, Double> precoColumn;

    @FXML
    private TableColumn<PedidoCliente, Date> dataEntregaColumn;

    ObservableList<PedidoClienteInfo>  pedidoClienteList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    private void loadData() {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT pedido_cliente.id_pedido, entrega.id_entrega, transportadora.id_transportadora, pedido_cliente.id_cliente, pedido_cliente.data_pedido, pedido_cliente.estado_pedido, pedido_cliente.taxa, pedido_cliente.preco_liquido, entrega.data_entrega\n" +
                "FROM pedido_cliente\n" +
                "JOIN entrega ON pedido_cliente.id_entrega = entrega.id_entrega\n" +
                "JOIN transportadora ON entrega.id_transportadora = transportadora.id_transportadora\n" +
                "JOIN cliente ON pedido_cliente.id_cliente = cliente.id_cliente;\n";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            idPedidoClienteColumn.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
            idEntregaColumn.setCellValueFactory(new PropertyValueFactory<>("idEntrega"));
            idTransportadoraColumn.setCellValueFactory(new PropertyValueFactory<>("idTransportadora"));
            idClienteColumn.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
            dataPedidoColumn.setCellValueFactory(new PropertyValueFactory<>("dataPedido"));
            estadoPedidoColumn.setCellValueFactory(new PropertyValueFactory<>("estadoPedido"));
            precoColumn.setCellValueFactory(new PropertyValueFactory<>("preco"));
            dataEntregaColumn.setCellValueFactory(new PropertyValueFactory<>("dataEntrega"));


            while (rs.next()) {
                double precoLiquido = rs.getDouble("preco_liquido");
                double taxa = rs.getDouble("taxa");
                double preco = precoLiquido * taxa;

                PedidoClienteInfo pedidoClienteInfo = new PedidoClienteInfo(
                        rs.getInt("id_pedido"),
                        rs.getInt("id_entrega"),
                        rs.getInt("id_transportadora"),
                        rs.getInt("id_cliente"),
                        rs.getDate("data_pedido"),
                        rs.getString("estado_pedido"),
                        precoLiquido,
                        rs.getDate("data_entrega")
                );

                pedidoClienteList.add(pedidoClienteInfo);
            }

            PedidosClienteView.setItems(pedidoClienteList);

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
