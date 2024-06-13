package Controllers;

import Entity.Cliente;
import Entity.Recinto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.time.Duration;

public class AdicionarReserva {

    @FXML
    private ComboBox<Recinto> comboBoxNomeRecinto;

    @FXML
    private ComboBox<Cliente> comboBoxNomeCliente;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> comboBoxHoraInicio;

    @FXML
    private ComboBox<String> comboBoxHoraFim;

    @FXML
    public void initialize() {
        populateComboBoxes();
    }

    private void populateComboBoxes() {
        getNomeRecinto();
        getNomeClientes();
        comboBoxHoraInicio.getItems().addAll(getHoras());
        comboBoxHoraFim.getItems().addAll(getHoras());
    }

    private String[] getHoras() {
        String[] horas = new String[48];
        int index = 0;
        for (int hora = 0; hora <= 23; hora++) {
            for (int minuto = 0; minuto < 60; minuto += 30) {
                String horaString = String.format("%02d:%02d", hora, minuto);
                horas[index++] = horaString;
            }
        }
        return horas;
    }

    private void getNomeClientes() {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT id_cliente, nome FROM cliente";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Integer id = rs.getInt("id_cliente");
                String nome = rs.getString("nome");
                comboBoxNomeCliente.getItems().add(new Cliente(id, nome));
            }

            if (!comboBoxNomeCliente.getItems().isEmpty()) {
                comboBoxNomeCliente.setValue(comboBoxNomeCliente.getItems().get(0));
            }

            comboBoxNomeCliente.setConverter(new StringConverter<Cliente>() {
                @Override
                public String toString(Cliente cliente) {
                    return cliente != null ? cliente.getNome() : "";
                }

                @Override
                public Cliente fromString(String string) {
                    return null;
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
    }

    private void getNomeRecinto() {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT id_recinto, nome FROM recinto";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Integer id = rs.getInt("id_recinto");
                String nome = rs.getString("nome");
                comboBoxNomeRecinto.getItems().add(new Recinto(id, nome));
            }

            if (!comboBoxNomeRecinto.getItems().isEmpty()) {
                comboBoxNomeRecinto.setValue(comboBoxNomeRecinto.getItems().get(0));
            }

            comboBoxNomeRecinto.setConverter(new StringConverter<Recinto>() {
                @Override
                public String toString(Recinto recinto) {
                    return recinto != null ? recinto.getNome() : "";
                }

                @Override
                public Recinto fromString(String string) {
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
    private void handleAdicionarReserva(ActionEvent event) {
        Recinto recintoSelecionado = comboBoxNomeRecinto.getValue();
        Cliente clienteSelecionado = comboBoxNomeCliente.getValue();
        LocalDate data = datePicker.getValue();
        String horaInicio = comboBoxHoraInicio.getValue();
        String horaFim = comboBoxHoraFim.getValue();
        String estado_reserva = "Confirmada";
        Integer id_pagamento = 0;
        Double duracao = null;

        LocalDateTime inicioDateTime = LocalDateTime.of(data, LocalTime.parse(horaInicio));
        LocalDateTime fimDateTime = LocalDateTime.of(data, LocalTime.parse(horaFim));
        Timestamp inicioTimestamp = Timestamp.valueOf(inicioDateTime);
        Timestamp fimTimestamp = Timestamp.valueOf(fimDateTime);

        duracao = Duration.between(inicioDateTime, fimDateTime).toHours() * 1.0;

        // Verificação se a data é posterior à data atual
        if (data.isBefore(LocalDate.now())) {
            System.out.println("A data deve ser posterior à data atual");
            return;
        }

        // Verificação se a hora de início é antes da hora de fim
        if (!inicioDateTime.isBefore(fimDateTime)) {
            System.out.println("A hora de início deve ser antes da hora de fim");
            return;
        }

        if (recintoSelecionado == null || clienteSelecionado == null || data == null || horaInicio == null || horaFim == null) {
            System.out.println("Todos os campos devem ser preenchidos");
            return;
        }

        DatabaseConnection connection = new DatabaseConnection();
        String sql = "INSERT INTO reserva (id_recinto, id_cliente, data_reserva, hora_inicio, hora_fim, estado_reserva, id_pagamento, duracao) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recintoSelecionado.getId());
            stmt.setInt(2, clienteSelecionado.getId());
            stmt.setDate(3, java.sql.Date.valueOf(data));
            stmt.setTimestamp(4, inicioTimestamp);
            stmt.setTimestamp(5, fimTimestamp);
            stmt.setString(6, estado_reserva);
            stmt.setInt(7, id_pagamento);
            stmt.setDouble(8, duracao);

            stmt.executeUpdate();
            clearFields();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        comboBoxNomeRecinto.getSelectionModel().clearSelection();
        comboBoxNomeCliente.getSelectionModel().clearSelection();
        datePicker.getEditor().clear();
        comboBoxHoraInicio.getSelectionModel().clearSelection();
        comboBoxHoraFim.getSelectionModel().clearSelection();
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
