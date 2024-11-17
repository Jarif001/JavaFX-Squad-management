package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class ClubWindow {

    @FXML
    private Button logoutBtn;

    @FXML
    private ImageView msgIcon;

    @FXML
    private ImageView clubLogo;

    @FXML
    private Label clubNameBelow;

    private ClubClient client;
    private String clubName;
    //commands to server
    private NetworkUtil networkUtil;

    //For myPlayer
    VBox mainvBox;

    //For transferList
    ListView listviewTransfer;
    Button btnTransferView;
    Button btnTransferBuy;
    HBox hBoxTransfer;

    //For message
    Button btnAccept, btnReject;
    HBox hBox;
    ListView listViewMsg;

    public void myPlayerBtn(ActionEvent event) {
        mainvBox = new VBox();
        int clubIndex = Main.Database.clubIndex(clubName);
        int playerCount = Main.Database.clubs.get(clubIndex).playerCount();

        for(int i = 0; i < playerCount; i+=2){
            String playerName1 = Main.Database.clubs.get(clubIndex).players.get(i).getName();
            String path1 = "/playerImages/" + playerName1 + ".jpg";
            Image image1 = new Image(path1);
            ImageView imageView1 = new ImageView(image1);
            imageView1.setFitWidth(373);
            imageView1.setFitHeight(210);
            Label playerNameLabel1 = new Label();
            if(Main.Database.players.get(Main.Database.playerIndex(playerName1)).getTransferStatus().equalsIgnoreCase("listed")){
                playerNameLabel1.setText(playerName1 + " (Transfer-Listed)");
            }
            else{
                playerNameLabel1.setText(playerName1);
            }
            playerNameLabel1.setPrefWidth(373);
            playerNameLabel1.setPrefHeight(35);
            playerNameLabel1.setFont(new Font("Arial", 18));
            playerNameLabel1.setAlignment(Pos.CENTER);
            Button[] btn1 = new Button[3];
            btn1[0] = new Button("View");
            btn1[1] = new Button("Sell");
            btn1[2] = new Button("Not sell");
            for(Button b : btn1){
                b.setFont(new Font("Arial", 15));
                b.setPrefWidth(90);
                b.setPrefHeight(35);
            }
            btn1[0].setOnAction(e ->
            {
                try{
                    Main.Database.showPlayerWithImg(playerName1);
                }
                catch (Exception ex){
                    Main.Database.showAlertNotSelected();
                }

            });
            btn1[1].setOnAction(e ->
            {
                try{
                    if(Main.Database.hasPlayerInTransferList(playerName1)){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error!");
                        alert.setHeaderText("This player is already transfer listed");
                        alert.showAndWait();
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText(playerName1 + " is transfer listed");
                        alert.showAndWait();
                        networkUtil.write("sell," + playerName1);
                        playerNameLabel1.setText(playerName1 + " (Transfer-Listed)");
                    }
                }
                catch (Exception ex){
                    Main.Database.showAlertNotSelected();
                }

            });
            btn1[2].setOnAction(e ->
            {
                try{
                    String send = "not sell," + playerName1;
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText(playerName1 + " is removed from transfer list");
                    alert.showAndWait();
                    networkUtil.write(send);
                    playerNameLabel1.setText(playerName1);
                }
                catch (Exception ex){
                    Main.Database.showAlertNotSelected();
                }

            });
            HBox btnHbox1 = new HBox(btn1);
            btnHbox1.setSpacing(45);
            VBox vBox1 = new VBox(imageView1, playerNameLabel1, btnHbox1);
            vBox1.setSpacing(6);

            HBox hBox = new HBox();
            if(i+1 < playerCount){
                String playerName2 = Main.Database.clubs.get(clubIndex).players.get(i+1).getName();
                String path2 = "/playerImages/" + playerName2 + ".jpg";
                Image image2 = new Image(path2);
                ImageView imageView2 = new ImageView(image2);
                imageView2.setFitWidth(373);
                imageView2.setFitHeight(210);
                Label playerNameLabel2 = new Label();
                if(Main.Database.players.get(Main.Database.playerIndex(playerName2)).getTransferStatus().equalsIgnoreCase("listed")){
                    playerNameLabel2.setText(playerName2 + " (Transfer-Listed)");
                }
                else{
                    playerNameLabel2.setText(playerName2);
                }
                playerNameLabel2.setPrefWidth(373);
                playerNameLabel2.setPrefHeight(35);
                playerNameLabel2.setFont(new Font("Arial", 18));
                playerNameLabel2.setAlignment(Pos.CENTER);
                Button[] btn2 = new Button[3];
                btn2[0] = new Button("View");
                btn2[1] = new Button("Sell");
                btn2[2] = new Button("Not sell");
                for(Button b : btn2){
                    b.setFont(new Font("Arial", 15));
                    b.setPrefWidth(90);
                    b.setPrefHeight(35);
                }
                btn2[0].setOnAction(e ->
                {
                    try{
                        Main.Database.showPlayerWithImg(playerName2);
                    }
                    catch (Exception ex){
                        Main.Database.showAlertNotSelected();
                    }

                });
                btn2[1].setOnAction(e ->
                {
                    try{
                        if(Main.Database.hasPlayerInTransferList(playerName2)){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error!");
                            alert.setHeaderText("This player is already transfer listed");
                            alert.showAndWait();
                        }
                        else{
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setHeaderText(playerName2 + " is transfer listed");
                            alert.showAndWait();
                            networkUtil.write("sell," + playerName2);
                            playerNameLabel2.setText(playerName2 + " (Transfer-Listed)");
                        }
                    }
                    catch (Exception ex){
                        Main.Database.showAlertNotSelected();
                    }

                });
                btn2[2].setOnAction(e ->
                {
                    try{
                        String send = "not sell," + playerName2;
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText(playerName2 + " is removed from transfer list");
                        alert.showAndWait();
                        networkUtil.write(send);
                        playerNameLabel2.setText(playerName2);
                    }
                    catch (Exception ex){
                        Main.Database.showAlertNotSelected();
                    }

                });
                HBox btnHbox2 = new HBox(btn2);
                btnHbox2.setSpacing(45);
                VBox vBox2 = new VBox(imageView2, playerNameLabel2, btnHbox2);
                vBox2.setSpacing(6);

                hBox.getChildren().addAll(vBox1, vBox2);
            }
            else{
                hBox.getChildren().add(vBox1);
            }
            hBox.setSpacing(15);
            hBox.setStyle("-fx-padding:15px");
            mainvBox.getChildren().add(hBox);
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(mainvBox);
        scrollPane.setPannable(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        BorderPane squadRoot = new BorderPane();
        squadRoot.setCenter(scrollPane);

        Scene scene = new Scene(squadRoot, 805, 650);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("My Squad");
        stage.getIcons().add(Main.Database.getClubLogo(clubName));
        stage.show();
    }

    public void transferListBtn(javafx.event.ActionEvent event) {
        listviewTransfer.getItems().clear();
        listviewTransfer.getItems().addAll(Main.Database.getTransferList());
        btnTransferView.setOnAction(e ->
        {
            ObservableList<Player> topics;
            String list = "";
            topics = listviewTransfer.getSelectionModel().getSelectedItems();

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

        btnTransferBuy.setOnAction(e ->
        {
            ObservableList<Player> topics;
            String list = "";
            topics = listviewTransfer.getSelectionModel().getSelectedItems();

            for (Player m : topics) {
                list += m + ", ";
            }

            int posIdx = list.lastIndexOf(",");

            try{
                String selection = list.substring(0, posIdx);
                int idxComma = selection.indexOf(',');
                String playerName = selection.substring(0, idxComma);
                String reqToClub = Main.Database.players.get(Main.Database.playerIndex(playerName)).getClub();
                if(clubName.equalsIgnoreCase(reqToClub)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("This player is already in your squad!");
                    alert.showAndWait();
                }
                else{
                    networkUtil.write("buy," + clubName + "," + reqToClub + "," + playerName);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Buy request for *" + playerName + "* has been sent");
                    alert.showAndWait();
                }
            }
            catch (Exception ex){
                Main.Database.showAlertNotSelected();
            }

        });
        VBox vBox = new VBox(listviewTransfer, hBoxTransfer);
        Scene scene = new Scene(vBox, 1000, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Transfer-List");
        stage.getIcons().add(Main.Database.getClubLogo(clubName));
        stage.show();
    }

    public void msgIconPressed(javafx.scene.input.MouseEvent mouseEvent) {
        VBox vBox = new VBox(listViewMsg, hBox);
        Scene scene = new Scene(vBox, 800, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Your messages");
        stage.getIcons().add(Main.Database.getClubLogo(clubName));
        stage.show();
    }

    public void closeButton(javafx.event.ActionEvent event) {
        client.close();
        Stage stage = (Stage)logoutBtn.getScene().getWindow();
        stage.close();
    }

    public void init(String clubName) throws IOException {
        this.clubName = clubName;
        clubNameBelow.setText(clubName);
        clubLogo.setImage(Main.Database.getClubLogo(clubName));
        msgIcon.setImage(new Image("/msgpic/noMsg.png"));
        client = new ClubClient("localhost", 2222, clubName);
        client.activate();
        networkUtil = client.getNetworkUtil();
        networkUtil.write(clubName);
        //MsgIcon
        listViewMsg = new ListView();
        listViewMsg.setId("myListView");
        listViewMsg.lookup("#myListView").setStyle("-fx-font-size: 12pt; -fx-line-height: 120%;");
        btnAccept = new Button("Accept");
        btnAccept.setStyle("-fx-font-size:16px; -fx-background-color: linear-gradient(#f0ff35, #a9ff00); -fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #395306;");
        btnReject = new Button("Delete");
        btnReject.setStyle("-fx-font-size:16px; -fx-background-color: linear-gradient(#ff5400, #be1d00);-fx-background-radius: 10;-fx-background-insets: 0;-fx-text-fill: white;");
        btnAccept.setPrefWidth(80);
        btnAccept.setPrefHeight(90);
        btnReject.setPrefWidth(80);
        btnReject.setPrefHeight(90);
        hBox = new HBox(btnAccept, btnReject);
        hBox.setPrefWidth(800);
        hBox.setPrefHeight(100);
        hBox.setSpacing(5);

        //TransferList
        listviewTransfer = new ListView();
        listviewTransfer.setId("myListViewTransfer");
        listviewTransfer.lookup("#myListViewTransfer").setStyle("-fx-font-size: 12pt; -fx-line-height: 120%;");
        btnTransferView = new Button("View");
        btnTransferView.setPrefWidth(80);
        btnTransferView.setPrefHeight(90);
        btnTransferView.setStyle("-fx-font-size:16px;");
        btnTransferBuy = new Button("Buy");
        btnTransferBuy.setPrefWidth(80);
        btnTransferBuy.setPrefHeight(90);
        btnTransferBuy.setStyle("-fx-font-size:16px;");
        hBoxTransfer = new HBox(btnTransferView, btnTransferBuy);
        hBoxTransfer.setPrefHeight(100);
        hBoxTransfer.setPrefWidth(1000);
        hBoxTransfer.setSpacing(5);

        //MyPlayer
        mainvBox = new VBox();
        mainvBox.setSpacing(3);

        new ReadThreadClient(networkUtil, msgIcon, clubName, listViewMsg, btnAccept, btnReject, listviewTransfer, btnTransferView, btnTransferBuy, mainvBox);
    }

}