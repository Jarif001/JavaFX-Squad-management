package sample;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClubLogin implements Initializable {

    Parent root;
    Scene scene;
    Stage stage;

    @FXML
    private TextField nameField;

    @FXML
    private Label lowerText;

    private String clubName;
    boolean found = false;

    public void loginBtn(ActionEvent event) throws IOException {
        clubName = nameField.getText();
        found = Main.Database.hasClub(clubName);
        if(found){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("fxml/clubClient.fxml"));
            Parent root = fxmlLoader.load();
            ClubWindow controller = fxmlLoader.getController();
            clubName = Main.Database.clubs.get(Main.Database.clubIndex(clubName)).getName();
            controller.init(clubName);
            Scene scene = new Scene(root);
            //adding css file to scene
            scene.getStylesheets().add(this.getClass().getResource("css/clubClient.css").toExternalForm());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.getIcons().add(Main.Database.getClubLogo(clubName));
            stage.setTitle(clubName);
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not found!");
            alert.setHeaderText("No club with this name!");
            alert.showAndWait();
        }
    }

    public void backButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("fxml/sample.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        //adding css file to scene
        scene.getStylesheets().add(this.getClass().getResource("css/mainCss.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(lowerText);
        translate.setDuration(Duration.seconds(10));
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setByX(1500);
        translate.play();
    }

}
