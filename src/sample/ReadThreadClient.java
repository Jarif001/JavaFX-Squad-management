package sample;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

public class ReadThreadClient implements Runnable{

    private Thread thr;
    private NetworkUtil networkUtil;
    private String clubName;

    //MyPlayerList
    VBox mainvBox;

    //TransferList
    private ListView listviewTransfer;
    private Button btnTransferView;
    private Button btnTransferBuy;

    //Message icon
    private ImageView msgIcon;
    private ListView listViewMsg;
    private Button btnAccept, btnReject;

    boolean stop = false;

    ReadThreadClient(NetworkUtil networkUtil, ImageView msgIcon, String clubName, ListView listViewMsg, Button btnAccept, Button btnReject, ListView listviewTransfer, Button btnTransferView, Button btnTransferBuy, VBox mainvBox){
        this.networkUtil = networkUtil;
        this.msgIcon = msgIcon;
        this.clubName = clubName;
        this.listViewMsg = listViewMsg;
        this.btnAccept = btnAccept;
        this.btnReject = btnReject;
        this.listviewTransfer = listviewTransfer;
        this.btnTransferView = btnTransferView;
        this.btnTransferBuy = btnTransferBuy;
        this.mainvBox = mainvBox;
        thr = new Thread(this);
        thr.start();
    }

    @Override
    public void run() {
        while(!stop) {
            if (networkUtil != null) {
                try{
                    Object o = networkUtil.read();
                    if (o instanceof ArrayList) {
                        ArrayList transferList = (ArrayList)o;
                        listviewTransfer.getItems().clear();
                        listviewTransfer.getItems().addAll(transferList);
                    } else {
                        String msgFromServer = (String) o;
                        if (msgFromServer.contains("wants to buy")) {
                            msgIcon.setImage(new Image("/msgpic/msg.png"));
                            ScaleTransition scale = new ScaleTransition();
                            scale.setNode(msgIcon);
                            scale.setDuration(Duration.millis(1000));
                            scale.setCycleCount(8);
                            scale.setInterpolator(Interpolator.LINEAR);
                            scale.setByX(0.5);
                            scale.setByY(0.5);
                            scale.setAutoReverse(true);
                            scale.play();
                            listViewMsg.getItems().add(msgFromServer);
                            btnAccept.setOnAction(e ->
                            {
                                ObservableList<String> topics;
                                String list = "";
                                topics = listViewMsg.getSelectionModel().getSelectedItems();

                                for (String m : topics) {
                                    list += m + ", ";
                                }

                                int posIdx = list.lastIndexOf(",");

                                try {
                                    String selection = list.substring(0, posIdx);
                                    int idx = selection.indexOf("buy");
                                    idx += 4;
                                    String playerName = selection.substring(idx);
                                    idx = selection.indexOf("wants");
                                    idx -= 1;
                                    String reqClub = selection.substring(0, idx);
                                    networkUtil.write("accept," + clubName + "," + reqClub + "," + playerName);
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Accepted");
                                    alert.setHeaderText("Player has been sold: " + playerName);
                                    alert.showAndWait();
                                    int selectId = listViewMsg.getSelectionModel().getSelectedIndex();
                                    listViewMsg.getItems().remove(selectId);
                                    if (listViewMsg.getItems().isEmpty()) {
                                        msgIcon.setImage(new Image("/msgpic/noMsg.png"));
                                    }
                                } catch (Exception ex) {
                                    Main.Database.showAlertNotSelected();
                                }

                            });
                            btnReject.setOnAction(e ->
                            {
                                int selectId = listViewMsg.getSelectionModel().getSelectedIndex();
                                try {
                                    listViewMsg.getItems().remove(selectId);
                                    if (listViewMsg.getItems().isEmpty()) {
                                        msgIcon.setImage(new Image("/msgpic/noMsg.png"));
                                    }
                                } catch (Exception ex) {
                                    Main.Database.showAlertNotSelected();
                                }
                            });

                        } else if (msgFromServer.contains("accepted your request")) {
                            msgIcon.setImage(new Image("/msgpic/msg.png"));
                            ScaleTransition scale = new ScaleTransition();
                            scale.setNode(msgIcon);
                            scale.setDuration(Duration.millis(1000));
                            scale.setCycleCount(8);
                            scale.setInterpolator(Interpolator.LINEAR);
                            scale.setByX(0.5);
                            scale.setByY(0.5);
                            scale.setAutoReverse(true);
                            scale.play();
                            listViewMsg.getItems().add(msgFromServer);
                            int clubIndex = Main.Database.clubIndex(clubName);
                            int playerCount = Main.Database.clubs.get(clubIndex).playerCount();
                            System.out.println(playerCount);
                            mainvBox = new VBox();
                            mainvBox.setSpacing(3);
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
                            btnReject.setOnAction(e ->
                            {
                                int selectId = listViewMsg.getSelectionModel().getSelectedIndex();
                                try {
                                    listViewMsg.getItems().remove(selectId);
                                    if (listViewMsg.getItems().isEmpty()) {
                                        msgIcon.setImage(new Image("/msgpic/noMsg.png"));
                                    }
                                } catch (Exception ex) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Error!");
                                    alert.setHeaderText("Select a thread");
                                    alert.showAndWait();
                                }

                            });
                        }
                        else if(msgFromServer.contains("Player sold")){
                            int clubIndex = Main.Database.clubIndex(clubName);
                            int playerCount = Main.Database.clubs.get(clubIndex).playerCount();
                            mainvBox = new VBox();
                            mainvBox.setSpacing(3);
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
                        }
                    }
                } catch (SocketException e){
                    System.out.println("Client logged out");
                    stop = true;
                }
                catch (IOException e) {
                    e.printStackTrace();
                    stop = true;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    stop = true;
                }
                catch (Exception e){
                    System.out.println("Client logged out");
                    stop = true;
                }
            }
        }
    }
}
