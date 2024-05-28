package Controllers;

import Entity.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteService {

    public Cliente fetchClienteById(int idCliente) {
        Cliente cliente = null;
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente); // Define o ID do cliente no PreparedStatement
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Se houver um resultado, crie uma instância de Cliente com os dados do resultado
                cliente = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("nif"),
                        rs.getString("username"),
                        rs.getString("estado_conta")
                );
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }

        return cliente; // Retorna o objeto Cliente (ou null se não encontrado)
    }
}
