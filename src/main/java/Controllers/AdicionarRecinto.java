package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Entity.Cliente;
import javafx.util.StringConverter;


import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdicionarRecinto {

    @FXML
    private TextField nomeField;
    @FXML
    private TextField moradaField;
    @FXML
    private TextField horarioFuncionamentoField;
    @FXML
    private TextField infoExtraField;
    @FXML
    private TextField precoField;
    @FXML
    private ComboBox<Cliente> comboBoxNomeProprietario;

    @FXML
    public void initialize() {
        populateComboBox();
    }

    private void populateComboBox() {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT id_cliente, nome FROM cliente";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Integer id = rs.getInt("id_cliente");
                String nome = rs.getString("nome");
                comboBoxNomeProprietario.getItems().add(new Cliente(id, nome));
            }

            // Set default selection if needed
            if (!comboBoxNomeProprietario.getItems().isEmpty()) {
                comboBoxNomeProprietario.setValue(comboBoxNomeProprietario.getItems().get(0));
            }

            // Define como os itens serão exibidos na ComboBox
            comboBoxNomeProprietario.setConverter(new StringConverter<Cliente>() {
                @Override
                public String toString(Cliente cliente) {
                    return cliente.getNome();
                }

                @Override
                public Cliente fromString(String string) {
                    // Se necessário apenas
                    return null;
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
    }


    @FXML
    public void handleAdicionarRecinto(ActionEvent event) {
        String nome = nomeField.getText();
        String morada = moradaField.getText();
        String horarioFuncionamento = horarioFuncionamentoField.getText();
        String infoExtra = infoExtraField.getText();
        String preco = precoField.getText();
        Cliente clienteSelecionado = comboBoxNomeProprietario.getValue();

        if (nome.isEmpty() || morada.isEmpty() || horarioFuncionamento.isEmpty() || preco.isEmpty() || clienteSelecionado == null) {
            // Handle the error: show an alert or update the UI to indicate that all fields must be filled
            System.out.println("All fields must be filled");
            return;
        }

        DatabaseConnection connection = new DatabaseConnection();
        String sql = "INSERT INTO recinto (nome, morada, horario_funcionamento, info_extra, preco_hora, id_cliente, estado_recinto) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, morada);
            stmt.setString(3, horarioFuncionamento);
            stmt.setString(4, infoExtra);
            BigDecimal precoNumeric = new BigDecimal(preco);
            stmt.setBigDecimal(5, precoNumeric);
            stmt.setInt(6, clienteSelecionado.getId());
            stmt.setString(7, "Ativo");

            stmt.executeUpdate();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
    }

    private void clearFields() {
        nomeField.clear();
        moradaField.clear();
        horarioFuncionamentoField.clear();
        infoExtraField.clear();
        precoField.clear();
        comboBoxNomeProprietario.setValue(comboBoxNomeProprietario.getItems().get(0));
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
