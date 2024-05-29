package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashBoard implements Initializable {

    @FXML
    private TextField totalClientes;

    @FXML
    private TextField totalRecintos;

    @FXML
    private TextField totalProprietarios;

    @FXML
    private TextField totalReservas;

    @FXML
    private TextField reservas24h;

    @FXML
    private TextField reservasMes;

    @FXML
    private TextField receitaTotal;

    @FXML
    private TextField receita24h;

    @FXML
    private TextField receitaMes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDashboardData();
    }

    private void loadDashboardData() {
        DatabaseConnection connection = new DatabaseConnection();

        // Queries to get the required data
        String totalClientesQuery = "SELECT COUNT(*) AS total FROM cliente";
        String totalRecintosQuery = "SELECT COUNT(*) AS total FROM recinto";
        String totalProprietariosQuery = "SELECT COUNT(DISTINCT id_cliente) AS total FROM recinto";
        String totalReservasQuery = "SELECT COUNT(*) AS total FROM reserva";
        String reservas24hQuery = "SELECT COUNT(*) AS total FROM reserva WHERE data_reserva >= NOW() - INTERVAL '1 DAY'";
        String reservasMesQuery = "SELECT COUNT(*) AS total FROM reserva WHERE EXTRACT(MONTH FROM data_reserva) = EXTRACT(MONTH FROM CURRENT_DATE)";
        String receitaTotalQuery = "SELECT SUM(p.valor_total) AS total FROM reserva r JOIN pagamento p ON r.id_pagamento = p.id_pagamento";
        String receita24hQuery = "SELECT COALESCE(SUM(p.valor_total), 0) AS total\n" +
                "FROM reserva r\n" +
                "         LEFT JOIN pagamento p ON r.id_pagamento = p.id_pagamento\n" +
                "WHERE r.data_reserva >= NOW() - INTERVAL '1 DAY'";
        String receitaMesQuery = "SELECT COALESCE(SUM(p.valor_total), 0) AS total\n" +
                "FROM reserva r\n" +
                "         LEFT JOIN pagamento p ON r.id_pagamento = p.id_pagamento\n" +
                "WHERE EXTRACT(MONTH FROM r.data_reserva) = EXTRACT(MONTH FROM CURRENT_DATE)";

        try (Connection conn = connection.getConnection()) {
            totalClientes.setText(getSingleValueFromQuery(conn, totalClientesQuery));
            totalRecintos.setText(getSingleValueFromQuery(conn, totalRecintosQuery));
            totalProprietarios.setText(getSingleValueFromQuery(conn, totalProprietariosQuery));
            totalReservas.setText(getSingleValueFromQuery(conn, totalReservasQuery));
            reservas24h.setText(getSingleValueFromQuery(conn, reservas24hQuery));
            reservasMes.setText(getSingleValueFromQuery(conn, reservasMesQuery));
            receitaTotal.setText(getSingleValueFromQuery(conn, receitaTotalQuery));
            receita24h.setText(getSingleValueFromQuery(conn, receita24hQuery));
            receitaMes.setText(getSingleValueFromQuery(conn, receitaMesQuery));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
    }

    private String getSingleValueFromQuery(Connection conn, String query) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString(1);
            }
        }
        return "0";
    }

    @FXML
    public void handleBackButton(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        switchScene(event, root);
    }

    private void switchScene(javafx.event.ActionEvent event, Parent root) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        javafx.scene.Scene scene = new javafx.scene.Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}