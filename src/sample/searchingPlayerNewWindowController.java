package sample;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class searchingPlayerNewWindowController {

    Parent root;
    Stage stage;

    //For all search
    boolean found = false;
    String name;
    @FXML
    private ImageView playerImg;
    @FXML
    private Label playerInfo;
    @FXML
    private TextField searchField;

    public void searchButtonPlayer(ActionEvent event) throws IOException {
        //Player Name Search
        name = searchField.getText();
        found = Main.Database.hasPlayer(name);
        if(found){
            int idx = Main.Database.playerIndex(name);
            String playerName = Main.Database.players.get(idx).getName();
            Image pImg = new Image("/playerImages/" + playerName + ".jpg");
            playerImg.setImage(pImg);
            playerInfo.setText(Main.Database.players.get(idx).toString());
            playerInfo.setWrapText(true);
            FadeTransition transition = new FadeTransition();
            transition.setNode(playerImg);
            transition.setDuration(Duration.seconds(2));
            transition.setInterpolator(Interpolator.LINEAR);
            transition.setFromValue(0);
            transition.setToValue(1);
            transition.play();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not Found");
            alert.setHeaderText("No player with this criteria");
            alert.showAndWait();
        }
        searchField.clear();
    }

    public void backButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("fxml/searchPlayer.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        //adding css file to scene
        scene.getStylesheets().add(this.getClass().getResource("css/mainCss.css").toExternalForm());
        stage.setScene(scene);

        stage.show();
    }
}
