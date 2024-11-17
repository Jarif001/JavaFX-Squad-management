package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SearchByCountry {

    Parent root;
    Stage stage;

    @FXML
    TextField searchField;

    public void searchButtonCountry(ActionEvent event) throws IOException {
        //Player Name Search
        String name = searchField.getText();
        boolean found = Main.Database.hasCountry(name);
        if(found){
            int idx = Main.Database.countryIndex(name);
            String countryName = Main.Database.countries.get(idx).getName();
            Country searchedCountry = Main.Database.countries.get(idx);
            ListView listview = new ListView();
            for(Player x : Main.Database.players){
                if(x.getCountry().equalsIgnoreCase(countryName)){
                    listview.getItems().add(x);
                }
            }
            Button btn = new Button("View");
            btn.setOnAction(e ->
            {
                ObservableList<Player> topics;
                String list= "";
                topics = listview.getSelectionModel().getSelectedItems();

                for (Player m : topics)
                {
                    list += m + ", ";
                }

                try{
                    int pos= list.lastIndexOf(",");

                    String selection= list.substring(0, pos);
                    int idxComma = selection.indexOf(',');
                    String playerName = selection.substring(0, idxComma);

                    Main.Database.showPlayerWithImg(playerName);
                }
                catch (Exception ex){
                    Main.Database.showAlertNotSelected();
                }

            });
            listview.setId("myListView");
            listview.lookup("#myListView").setStyle("-fx-font-size: 12pt; -fx-line-height: 120%;");
            btn.setPrefWidth(80);
            btn.setPrefHeight(90);
            VBox vBox = new VBox(listview, btn);
            Scene scene = new Scene(vBox, 1000,400);
            Stage stage = new Stage();
            stage.setScene(scene);
            String countryIcon = "/countryLogos/" + countryName + ".jpg";
            Image icon = new Image(countryIcon);
            stage.getIcons().add(icon);
            stage.setTitle("Players of " + countryName);
            stage.show();

        }
        else{
            Main.Database.showAlert();
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
