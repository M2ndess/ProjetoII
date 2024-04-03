package Controllers;

import Entity.Enchido;
import Entity.Funcionario;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class AdicionarEnchido implements Initializable {

    private Map<String, Integer> fornecedorMap = new HashMap<>();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField tipoEnchidoField;
    @FXML
    private TextField quantidadeField;
    @FXML
    private TextField custoUnitarioField;
    @FXML
    private ChoiceBox faseProducaoDropdown;
    @FXML
    private DatePicker dataValidadePicker;
    @FXML
    private TextField descriptionField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    // Method to create a new Enchido
    public void handleAdicionarEnchido(ActionEvent event) {
        // Create a new instance of DatabaseConnection
        DatabaseConnection connection = new DatabaseConnection();

        //Enchido attributes

        String tipoEnchido = tipoEnchidoField.getText();
        double custoUnitario = 0.0; // Default value
        try {
            custoUnitario = Double.parseDouble(custoUnitarioField.getText());
            // Use the custoUnitario value
        } catch (NumberFormatException e) {
            // Handle the case where the value is not a valid double
            System.out.println("Invalid custoUnitario value");
        }
        String faseProducao = (String) faseProducaoDropdown.getValue();
        String descricao = descriptionField.getText();

        int quantidade = 0; // Default value
        try {
            quantidade = Integer.parseInt(quantidadeField.getText());
            // Use the quantidade value
        } catch (NumberFormatException e) {
            // Handle the case where the value is not a valid integer
            System.out.println("Invalid quantidade value");
        }

        //Lote attributes

        Funcionario loggedInFuncionario = Login.getLoggedInFuncionario();
        int idFuncionario = loggedInFuncionario.getIdFuncionario();
        double custo = quantidade * custoUnitario;

        // Get the current date
        java.util.Date currentDate = new java.util.Date();
        // Convert currentDate to java.sql.Date
        java.sql.Date dataCriacao = new java.sql.Date(currentDate.getTime());

        LocalDate selectedDate = dataValidadePicker.getValue();
        // Convert LocalDate to java.sql.Date
        java.sql.Date dataValidade = java.sql.Date.valueOf(selectedDate);

        String estado_lote = "Ativo";

        // Create a new Lote object
        Lote lote = new Lote(idFuncionario, custo, dataCriacao, dataValidade, estado_lote);

        try {
            // Save the Lote object to the database
            if (insertLote(lote)) {
                System.out.println("Lote inserted successfully!");

                // Get the generated idLote from the database
                int idLote = connection.getGeneratedId("\"Lote_id_lote_seq\"");

                // Create a new Enchido object
                Enchido enchido = new Enchido(tipoEnchido, custoUnitario, faseProducao, descricao, quantidade, idLote);

                // Save the Enchido object to the database
                if (insertEnchido(enchido)) {
                    System.out.println("Enchido inserted successfully!");
                    // Go back to the previous screen
                    switchToPreviousScreen(event);
                } else {
                    System.out.println("Failed to insert Enchido.");
                }
            } else {
                System.out.println("Failed to insert Lote.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
    }

    // Insert the new Enchido object into the database
    private boolean insertEnchido(Enchido enchido) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "INSERT INTO enchido (tipo_enchido, custo_unitario, fase_producao, descricao, quantidade, id_lote) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, enchido.getTipoEnchido());
            stmt.setDouble(2, enchido.getCustoUnitario());
            stmt.setString(3, enchido.getFaseProducao());
            stmt.setString(4, enchido.getDescricao());
            stmt.setInt(5, enchido.getQuantidade());
            stmt.setInt(6, enchido.getIdLote());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            connection.closeConnection();
        }
    }

    // Insert the new Lote object into the database
    private boolean insertLote(Lote lote) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "INSERT INTO lote (id_funcionario, custo, data_criacao, data_validade, estado_lote) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lote.getIdFuncionario());
            stmt.setDouble(2, lote.getCusto());
            stmt.setDate(3, lote.getDataCriacao());
            stmt.setDate(4, lote.getDataValidade());
            stmt.setString(5, lote.getEstadoLote());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            connection.closeConnection();
        }
    }


    private void switchToPreviousScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/StockEnchidos.fxml"));
        switchScene(event);
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
