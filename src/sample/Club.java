package sample;

import java.util.ArrayList;

public class Club {
    String name;
    ArrayList<Player> players = new ArrayList<>();

    Club(String _name){
        name = _name;
    }

    public double totalYrSal(){
        double s = 0;
        for(Player x : players){
            s += x.getSalary();
        }
        return 52*s;
    }
    public String getName(){
        return name;
    }

    public void addPlayer(Player p){
        players.add(p);
    }

    public void removePlayer(Player p){
        players.remove(p);
    }

    public int playerCount(){
        return players.size();
    }

    private double maxSalary(){
        double max = 0;
        for(Player x : players){
            if(x.getSalary() > max){
                max = x.getSalary();
            }
        }
        return max;
    }
    public ArrayList<Player> maxSalaryPlayer(){
        ArrayList<Player> selectedPlayers = new ArrayList<>();
        for(Player x : players){
            if(x.getSalary() == maxSalary()){
                selectedPlayers.add(x);
            }
        }
        return selectedPlayers;
    }

    private int maxAge(){
        int max = 0;
        for(Player x : players){
            if(x.getAge() > max){
                max = x.getAge();
            }
        }
        return max;
    }
    public ArrayList<Player> maxAgePlayer(){
        ArrayList<Player> selPlayers = new ArrayList<>();
        for(Player x : players){
            if(x.getAge() == maxAge()){
                selPlayers.add(x);
            }
        }
        return selPlayers;
    }

    private double maxHeight(){
        double max = 0;
        for(Player x : players){
            if(x.getHeight() > max){
                max = x.getHeight();
            }
        }
        return max;
    }
    public ArrayList<Player> maxHeightPlayer(){
        ArrayList<Player> selPlayers = new ArrayList<>();
        for(Player x : players){
            if(x.getHeight() == maxHeight()){
                selPlayers.add(x);
            }
        }
        return selPlayers;
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

}
