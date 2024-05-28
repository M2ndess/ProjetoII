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

    //Reservas Button
    @FXML
    public void handleReservasButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/ListarReserva.fxml"));
        switchScene(event);
    }

    //DashBoard Button
    @FXML
    public void handleDashBoardButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/ListarTransportadoras.fxml"));
        switchScene(event);
    }

    //Recintos Button
    @FXML
    public void handleRecintosButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/ListarRecintos.fxml"));
        switchScene(event);
    }

    //Clientes Button
    @FXML
    public void handleClientesButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/ListarClientes.fxml"));
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