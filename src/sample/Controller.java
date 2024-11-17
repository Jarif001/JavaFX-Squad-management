package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button quitBtn;

    public void switchToSearchPlayerScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("fxml/searchPlayer.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        //adding css file to scene
        scene.getStylesheets().add(this.getClass().getResource("css/mainCss.css").toExternalForm());
        stage.setScene(scene);

        stage.show();
    }

    public void switchToSearchClubScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("fxml/searchByClubs.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        //adding css file to scene
        scene.getStylesheets().add(this.getClass().getResource("css/searchPlayer.css").toExternalForm());
        stage.setScene(scene);

        stage.show();
    }

    public void switchToSearchLoginScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("fxml/clubLogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        //adding css file to scene
        scene.getStylesheets().add(this.getClass().getResource("css/clubLogin.css").toExternalForm());
        stage.setScene(scene);

        stage.show();
    }

    public void exitButton(ActionEvent event){
        stage = (Stage)quitBtn.getScene().getWindow();
        stage.close();
    }
}
