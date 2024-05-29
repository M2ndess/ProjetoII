package Controllers;

import Entity.Pagamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PagamentoService {

    public Pagamento fetchPagamentoById(int idPagamento) {
        Pagamento pagamento = null;
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT * FROM pagamento WHERE id_pagamento = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPagamento);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                pagamento = new Pagamento(
                        rs.getInt("id_pagamento"),
                        rs.getString("metodo_pagamento"),
                        rs.getBigDecimal("valor_total")
                );
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }

        return pagamento;
    }
}
