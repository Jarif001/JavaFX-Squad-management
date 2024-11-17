package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SearchBySal {

    Parent root;
    Stage stage;
    @FXML
    private TextField highRange;

    @FXML
    private TextField lowRange;
    @FXML
    private Label highText;

    @FXML
    private Label lowText;

    public void searchButtonSal(ActionEvent event){
        Double highSal = 0.0, lowSal = 0.0;
        boolean validInput = false;
        try{
            highSal = Double.parseDouble(highRange.getText());
            highText.setText("Enter Higher Range");
            validInput = true;
        }
        catch (Exception e){
            highText.setText("Enter number for higher range!");
            validInput = false;
        }

        try{
            lowSal = Double.parseDouble(lowRange.getText());
            lowText.setText("Enter Lower Range");
            validInput = true;
        }
        catch (Exception e){
            lowText.setText("Enter number for lower range!");
            validInput = false;
        }

        if(validInput){
            boolean hasPlayer = false;
            ListView listview = new ListView();
            for (Player x : Main.Database.players) {
                if (x.getSalary() <= highSal && x.getSalary() >= lowSal) {
                    listview.getItems().add(x);
                    hasPlayer = true;
                }
            }
            if(hasPlayer){
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

                    String selection = list.substring(0, posIdx);
                    int idxComma = selection.indexOf(',');
                    String playerName = selection.substring(0, idxComma);

                    Main.Database.showPlayerWithImg(playerName);

                });
                btn.setPrefWidth(80);
                btn.setPrefHeight(90);
                listview.setId("myListView");
                listview.lookup("#myListView").setStyle("-fx-font-size: 12pt; -fx-line-height: 120%;");
                VBox vBox = new VBox(listview, btn);
                Scene scene = new Scene(vBox, 1000, 400);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Players of Salary Range from " + lowSal + " to " + highSal);
                stage.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Not Found");
                alert.setHeaderText("No player with this criteria");
                alert.showAndWait();
            }
        }
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
