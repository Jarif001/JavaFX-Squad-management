package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;

public class SearchByClubs {

    Parent root;
    Stage stage;
    @FXML
    TextField searchField;
    String clubName;
    boolean found = false;
    int idxClub;

    public void maxSalary(ActionEvent event){
        functionality(1);
    }

    public void maxAge(ActionEvent event){
        functionality(2);
    }

    public void maxHeight(ActionEvent event){
        functionality(3);
    }

    public void totalSalYr(ActionEvent event){
        clubName = searchField.getText();
        found = Main.Database.hasClub(clubName);
        if(found){
            idxClub = Main.Database.clubIndex(clubName);
            Label sal = new Label(new BigDecimal(Main.Database.clubs.get(idxClub).totalYrSal()).toPlainString());
            sal.setAlignment(Pos.CENTER);
            sal.setPrefHeight(80);
            sal.setPrefWidth(500);
            sal.setFont(Font.font("Verdana", 20));
            Scene scene = new Scene(sal);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Yearly Salary of " + clubName);
            stage.getIcons().add(Main.Database.getClubLogo(clubName));
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not Found!");
            alert.setHeaderText("No club with this name");
            alert.showAndWait();
        }
    }

    public void functionality(int step){
        clubName = searchField.getText();
        found = Main.Database.hasClub(clubName);
        if(found && step == 1){
            idxClub = Main.Database.clubIndex(clubName);
            ListView listview = new ListView();
            listview.getItems().addAll(Main.Database.clubs.get(idxClub).maxSalaryPlayer());
            listShowWithButton(listview, "Max Salary");
        }
        else if(found && step == 2){
            idxClub = Main.Database.clubIndex(clubName);
            ListView listview = new ListView();
            listview.getItems().addAll(Main.Database.clubs.get(idxClub).maxAgePlayer());
            listShowWithButton(listview, "Max Age");
        }
        else if(found && step == 3){
            idxClub = Main.Database.clubIndex(clubName);
            ListView listview = new ListView();
            listview.getItems().addAll(Main.Database.clubs.get(idxClub).maxHeightPlayer());
            listShowWithButton(listview, "Max Height");
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not Found!");
            alert.setHeaderText("No club with this name");
            alert.showAndWait();
        }
    }

    public void listShowWithButton(ListView listview, String title){
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
        stage.setTitle(title + " : " + clubName);
        stage.getIcons().add(Main.Database.getClubLogo(clubName));
        stage.show();
    }

    public void backButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("fxml/sample.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        //adding css file to scene
        scene.getStylesheets().add(this.getClass().getResource("css/mainCss.css").toExternalForm());
        stage.setScene(scene);

        stage.show();
    }
}
