package Controllers;

import javafx.beans.property.SimpleObjectProperty;
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
import Entity.Reserva;
import Entity.Cliente;
import Entity.Recinto;
import Entity.Pagamento;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ResourceBundle;

public class ListarReserva implements Initializable {

    @FXML
    private TableView<Reserva> reservaView;

    @FXML
    private TableColumn<Reserva, Integer> idReservaColumn;

    @FXML
    private TableColumn<Reserva, Integer> idClienteColumn;

    @FXML
    private TableColumn<Reserva, Integer> idRecintoColumn;

    @FXML
    private TableColumn<Reserva, BigDecimal> pagamentoColumn;

    @FXML
    private TableColumn<Reserva, Instant> horaInicioColumn;

    @FXML
    private TableColumn<Reserva, Instant> horaFimColumn;

    @FXML
    private TableColumn<Reserva, String> estadoReservaColumn;

    private ObservableList<Reserva> reservaList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    private void loadData() {
        ClienteService clienteService = new ClienteService();
        RecintoService recintoService = new RecintoService();
        PagamentoService pagamentoService = new PagamentoService();

        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT * FROM reserva";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            idReservaColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            idClienteColumn.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
            idRecintoColumn.setCellValueFactory(new PropertyValueFactory<>("idRecinto"));
            pagamentoColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPagamento().getValorTotal()));
            horaInicioColumn.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
            horaFimColumn.setCellValueFactory(new PropertyValueFactory<>("horaFim"));
            estadoReservaColumn.setCellValueFactory(new PropertyValueFactory<>("estadoReserva"));

            while (rs.next()) {

                Pagamento pagamento = pagamentoService.fetchPagamentoById(rs.getInt("id_pagamento"));

                Reserva reservaInfo = new Reserva(
                        rs.getInt("id_reserva"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_recinto"),
                        pagamento,
                        rs.getTimestamp("hora_inicio").toInstant(),
                        rs.getTimestamp("hora_fim").toInstant(),
                        rs.getString("estado_reserva")
                );

                reservaList.add(reservaInfo);
            }

            reservaView.setItems(reservaList);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
    }

    @FXML
    public void handleDeactivateButton(ActionEvent event) {
        Reserva selectedReserva = reservaView.getSelectionModel().getSelectedItem();

        if (selectedReserva != null) {

            DatabaseConnection connection = new DatabaseConnection();
            String updateSql = "UPDATE reserva SET estado_reserva = ? WHERE id_reserva = ?;";

            try (Connection conn = connection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(updateSql)) {

                String previousEstadoReserva = selectedReserva.getEstadoReserva();

                String newEstadoReserva;
                if (previousEstadoReserva == null) {
                    newEstadoReserva = "Pendente";
                } else {
                    newEstadoReserva = previousEstadoReserva.equals("Confirmada") ? "Pendente" : "Confirmada";
                }

                stmt.setString(1, newEstadoReserva);
                stmt.setInt(2, selectedReserva.getId());
                stmt.executeUpdate();

                selectedReserva.setEstadoReserva(newEstadoReserva);
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
        RecintoService recintoService = new RecintoService();
        PagamentoService pagamentoService = new PagamentoService();

        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT * FROM reserva";

        reservaList.clear();
        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            idReservaColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            idClienteColumn.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
            idRecintoColumn.setCellValueFactory(new PropertyValueFactory<>("idRecinto"));
            pagamentoColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPagamento().getValorTotal()));
            horaInicioColumn.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
            horaFimColumn.setCellValueFactory(new PropertyValueFactory<>("horaFim"));
            estadoReservaColumn.setCellValueFactory(new PropertyValueFactory<>("estadoReserva"));

            while (rs.next()) {

                Pagamento pagamento = pagamentoService.fetchPagamentoById(rs.getInt("id_pagamento"));

                Reserva reservaInfo = new Reserva(
                        rs.getInt("id_reserva"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_recinto"),
                        pagamento,
                        rs.getTimestamp("hora_inicio").toInstant(),
                        rs.getTimestamp("hora_fim").toInstant(),
                        rs.getString("estado_reserva")
                );

                reservaList.add(reservaInfo);
            }

            reservaView.setItems(reservaList);
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
