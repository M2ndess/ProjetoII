package Controllers;

import Entity.Recinto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecintoService {

    public Recinto fetchRecintoById(int idRecinto) {
        Recinto recinto = null;
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT * FROM recinto WHERE id_recinto = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRecinto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                recinto = new Recinto(
                        rs.getInt("id_recinto"),
                        null,
                        rs.getString("nome"),
                        rs.getString("morada"),
                        rs.getString("horario_funcionamento"),
                        rs.getString("info_extra"),
                        rs.getString("estado_recinto")
                );
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }

        return recinto;
    }
}
