package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchByPos implements Initializable {

    @FXML
    private ComboBox comboBox;
    
    Parent root;
    Stage stage;

    public void searchButtonPos(ActionEvent event) throws IOException {
        //Player Position
        String pos = comboBox.getSelectionModel().getSelectedItem().toString();
        ListView listview = new ListView();
        for (Player x : Main.Database.players) {
            if (x.getPos().equalsIgnoreCase(pos)) {
                listview.getItems().add(x);
            }
        }
        Button btn = new Button("View");
        btn.setOnAction(e ->
        {
            ObservableList<Player> topics;
            String list = "";
            topics = listview.getSelectionModel().getSelectedItems();

            for (Player m : topics) {
                list += m + ", ";
            }

            int posIdx = list.lastIndexOf(",");

            try{
                String selection = list.substring(0, posIdx);
                int idxComma = selection.indexOf(',');
                String playerName = selection.substring(0, idxComma);

                Main.Database.showPlayerWithImg(playerName);
            }
            catch (Exception ex){
                Main.Database.showAlertNotSelected();
            }

        });
        btn.setPrefWidth(80);
        btn.setPrefHeight(90);
        listview.setId("myListView");
        listview.lookup("#myListView").setStyle("-fx-font-size: 12pt; -fx-line-height: 120%;");
        VBox vBox = new VBox(listview, btn);
        Scene scene = new Scene(vBox, 1000, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Players of Position: " + pos);
        stage.show();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("Goalkeeper", "Defender", "Midfielder", "Forward");
        comboBox.setItems(list);
        comboBox.setPromptText("Select Position");
        comboBox.lookup("#comboBox").setStyle("-fx-font-size: 16pt;");
    }
}
