package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenu {

    private Stage stage;
    private Scene scene;
    private Parent root;

    //Logout Button
    @FXML
    public void handleLogout(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        switchScene(event);
    }

    //Matérias Primas Button
    @FXML
    public void handleMateriasPrimasButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/StockMateriasPrimas.fxml"));
        switchScene(event);
    }

    //Enchidos Button
    @FXML
    public void handleEnchidosButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/StockEnchidos.fxml"));
        switchScene(event);
    }


    //Mudar fase de Producao Button
    @FXML
    public void handleMudarFaseProducaoButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/FaseProducao.fxml"));
        switchScene(event);
    }

    //Funcionarios Button
    @FXML
    public void handleFuncionariosButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/ListarFuncionarios.fxml"));
        switchScene(event);
    }

    //Fornecedores Button
    @FXML
    public void handleFornecedoresButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/ListarFornecedores.fxml"));
        switchScene(event);
    }

    //Transportadoras Button
    @FXML
    public void handleTransportadorasButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/ListarTransportadoras.fxml"));
        switchScene(event);
    }

    //Clientes Button
    @FXML
    public void handleClientesButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/ListarClientes.fxml"));
        switchScene(event);
    }

    //Pedidos de Cliente Button
    @FXML
    public void handlePedidosClienteButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/ListarPedidosCliente.fxml"));
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