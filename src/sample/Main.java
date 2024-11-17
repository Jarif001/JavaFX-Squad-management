package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;


public class Main extends Application {

    //Maintaining data
    public static class Database {
        public static ArrayList<Player> players = new ArrayList<>();
        public static ArrayList<Club> clubs = new ArrayList<>();
        public static ArrayList<Country> countries = new ArrayList<>();
        public static ArrayList<Player> transferList = new ArrayList<>();

        public static boolean hasPlayer(String name){
            for(Player p : players){
                if(p.getName().equalsIgnoreCase(name)){
                    return true;
                }
            }
            return false;
        }
        public static int playerIndex(String name){
            for(int i = 0; i < players.size(); i++){
                if(players.get(i).getName().equalsIgnoreCase(name)){
                    return i;
                }
            }
            return -1;
        }

        public static boolean hasClub(String name){
            for(Club c : clubs){
                if(c.getName().equalsIgnoreCase(name)){
                    return true;
                }
            }
            return false;
        }
        public static int clubIndex(String name){
            for(int i = 0; i < clubs.size(); i++){
                if(clubs.get(i).getName().equalsIgnoreCase(name)){
                    return i;
                }
            }
            return -1;
        }

        public static boolean hasCountry(String name){
            for(Country c : countries){
                if(c.getName().equalsIgnoreCase(name)){
                    return true;
                }
            }
            return false;
        }
        public static int countryIndex(String name){
            for(int i = 0; i < countries.size(); i++){
                if(countries.get(i).getName().equalsIgnoreCase(name)){
                    return i;
                }
            }
            return -1;
        }

        public static boolean hasPlayerInTransferList(String playerName){
            for(Player p : transferList){
                if(p.getName().equalsIgnoreCase(playerName)){
                    return true;
                }
            }
            return false;
        }

        public static void addPlayertoTransferList(Player p){
            transferList.add(p);
        }

        public static void removePlayerfromTransferList(Player p){
            transferList.remove(p);
        }

        public static ArrayList<Player> getTransferList(){
            return transferList;
        }

        public static void transferPlayer(String oldClub, String newClub, Player p){
            Main.Database.clubs.get(Main.Database.clubIndex(oldClub)).removePlayer(p);
            Main.Database.clubs.get(Main.Database.clubIndex(newClub)).addPlayer(p);
            p.setClub(newClub);
            p.setTransferStatus("Not Listed");
            transferList.remove(p);
        }

        public static void showPlayerWithImg(String playerName){
            String path = "/playerImages/" + playerName + ".jpg";
            Image image = new Image(path);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(450);
            imageView.setFitWidth(800);
            Label playerInfo = new Label();
            playerInfo.setText(Main.Database.players.get(Main.Database.playerIndex(playerName)).toString());
            playerInfo.setWrapText(true);
            playerInfo.setFont(new Font("Arial", 17));
            playerInfo.setPadding(new Insets(5));

            VBox vbox = new VBox(imageView, playerInfo);
            vbox.setSpacing(3);

            Scene scene1 = new Scene(vbox, 800, 510);
            Stage stage1 = new Stage();
            stage1.setTitle(playerName);
            stage1.setScene(scene1);
            stage1.show();
        }

        public static Image getClubLogo(String clubName){
            String path = "/clubLogos/" + clubName + ".jpg";
            Image clubLogo = new Image(path);
            return clubLogo;
        }

        public static void showAlert(){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not Found");
            alert.setHeaderText("No player with this criteria");
            alert.showAndWait();
        }

        public static void showAlertNotSelected(){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Player Not Selected!");
            alert.setHeaderText("Select a player");
            alert.showAndWait();
        }
    }

    public void readingFile() throws FileNotFoundException {
        //File
        File database = new File("players.txt");
        Scanner fileScan = new Scanner(database);
        //Read the data
        while(fileScan.hasNextLine()){
            String temp = fileScan.nextLine();
            String[] dataAll = temp.split(",");
            String name = dataAll[0];
            String country = dataAll[1];
            int age = Integer.parseInt(dataAll[2]);
            double height = Double.parseDouble(dataAll[3]);
            String club = dataAll[4];
            String pos = dataAll[5];
            int jNo = Integer.parseInt(dataAll[6]);
            double sal = Double.parseDouble(dataAll[7]);
            Player player = new Player(name, country, pos, age, jNo, height, sal, club);
            Main.Database.players.add(player);

            //Club adding
            boolean exist = Main.Database.hasClub(club);
            if(!exist){
                Club addClub = new Club(club);
                Main.Database.clubs.add(addClub);
            }

            //Country adding
            exist = Main.Database.hasCountry(country);
            if(!exist){
                Country addCountry = new Country(country);
                Main.Database.countries.add(addCountry);
            }

            //adding player to the club
            int idx = Main.Database.clubIndex(club);
            Main.Database.clubs.get(idx).addPlayer(player);

            //adding player to the country
            idx = Main.Database.countryIndex(country);
            Main.Database.countries.get(idx).addPlayer(player);
        }
        fileScan.close();
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/sample.fxml")));
        primaryStage.setTitle("PDS");

        Scene scene = new Scene(root);

        //adding css file to scene
        scene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("css/mainCss.css")).toExternalForm());

        //icon
        Image icon = new Image("footballIcon.png");
        primaryStage.getIcons().add(icon);

        //Read from file
        readingFile();

        primaryStage.setScene(scene);
        primaryStage.show();

    }
    static MediaPlayer mediaPlayer;
    public static void bgMusicPlay(){
        //Background music
        String fileName = "BgMusic.mp3";
        String path = Objects.requireNonNull(Main.class.getResource(fileName)).getPath();
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public static void main(String[] args) throws IOException {
        bgMusicPlay();
        new Server().activate();
        launch(args);
    }
}
