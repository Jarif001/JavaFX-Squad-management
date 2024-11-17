package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class AllData {
    public static class Database {
        static ArrayList<Player> players = new ArrayList<>();
        static ArrayList<Club> clubs = new ArrayList<>();
        static ArrayList<Country> countries = new ArrayList<>();

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
            Database.players.add(player);

            //Club adding
            boolean exist = Database.hasClub(club);
            if(!exist){
                Club addClub = new Club(club);
                Database.clubs.add(addClub);
            }

            //Country adding
            exist = Database.hasCountry(country);
            if(!exist){
                Country addCountry = new Country(country);
                Database.countries.add(addCountry);
            }

            //adding player to the club
            int idx = Database.clubIndex(club);
            Database.clubs.get(idx).addPlayer(player);

            //adding player to the country
            idx = Database.countryIndex(country);
            Database.countries.get(idx).addPlayer(player);
        }
        fileScan.close();
    }

}
