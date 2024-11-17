package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class searchPlayerController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToMainScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("fxml/sample.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        //adding css file to scene
        scene.getStylesheets().add(this.getClass().getResource("css/mainCss.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void searchByName(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/searchingPlayerNewWindow.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Search by Name");
        scene.getStylesheets().add(this.getClass().getResource("css/searchPlayer.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }


    public void searchByCountry(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/searchByCountry.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Search by Country");
        stage.setScene(scene);
        stage.show();
    }

    public void searchByPosition(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/searchByPos.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Search by Position");
        scene.getStylesheets().add(this.getClass().getResource("css/searchPlayer.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void searchBySalaryRange(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/searchBySal.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Search by Salary-Range");
        scene.getStylesheets().add(this.getClass().getResource("css/searchBySal.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void countryWisePlayerCount(ActionEvent event){
        ListView listView = new ListView();
        for(Country x : Main.Database.countries){
            listView.getItems().add(x.getName() + " - " + x.playerCount());
        }
        listView.setId("myListView");
        listView.lookup("#myListView").setStyle("-fx-font-size: 15pt; -fx-line-height: 120%;");
        VBox vBox = new VBox(listView);
        Scene scene = new Scene(vBox, 750,400);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Number of players - Country-wise");
        stage.show();
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

}
