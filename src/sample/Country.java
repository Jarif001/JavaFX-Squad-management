package sample;

import java.util.ArrayList;

public class Country {
    String name;
    ArrayList<Player> players = new ArrayList<>();

    Country(String _name){
        name = _name;
    }

    public String getName(){
        return name;
    }
    public void addPlayer(Player p){
        players.add(p);
    }
    public void showPlayers(){
        for(Player x : players){
            System.out.println(x);
        }
    }
    public int playerCount(){
        return players.size();
    }
}
