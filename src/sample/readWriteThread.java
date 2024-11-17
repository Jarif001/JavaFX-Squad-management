package sample;

import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;

public class readWriteThread implements Runnable{

    private Thread thr;
    private NetworkUtil networkUtil;
    private HashMap<String, NetworkUtil> clients;
    boolean stop = false;
    public readWriteThread(NetworkUtil networkUtil, HashMap<String, NetworkUtil> clients) {
        this.networkUtil = networkUtil;
        this.clients = clients;
        thr = new Thread(this);
        thr.start();
    }

    @Override
    public void run() {

        try{
            while(!stop){
                String fromClient = "";
                try{
                    fromClient = (String)networkUtil.read();
                }
                catch (EOFException e){
                    stop = true;
                }
                catch (Exception e){
                    stop = true;
                    System.out.println("An exception");
                }
                if(fromClient.contains("not sell")){
                    String whole[] = fromClient.split(",");
                    int playerIdx = Main.Database.playerIndex(whole[1]);
                    Player player = Main.Database.players.get(playerIdx);
                    player.setTransferStatus("Not Listed");
                    Main.Database.removePlayerfromTransferList(player);
                }
                else if(fromClient.contains("sell")){
                    String[] whole = fromClient.split(",");
                    String playerName = whole[1];
                    int playerIdx = Main.Database.playerIndex(playerName);
                    Player player = Main.Database.players.get(playerIdx);
                    player.setTransferStatus("Listed");
                    Main.Database.addPlayertoTransferList(player);
                    for(NetworkUtil nu : clients.values()){
                        nu.write(Main.Database.getTransferList());
                    }
                }
                else if(fromClient.contains("buy")){
                    NetworkUtil networkUtilPrev = networkUtil;
                    String whole[] = fromClient.split(",");
                    String reqFrom = whole[1];
                    String reqTo = whole[2];
                    String playerName = whole[3];
                    networkUtil = clients.get(reqTo);
                    networkUtil.write(reqFrom + " wants to buy " + playerName);
                    networkUtil = networkUtilPrev;
                }
                else if(fromClient.contains("accept")){
                    NetworkUtil networkUtilPrev = networkUtil;
                    String whole[] = fromClient.split(",");
                    String oldClub = whole[1];
                    String newClub = whole[2];
                    String playerName = whole[3];
                    Player player = Main.Database.players.get(Main.Database.playerIndex(playerName));
                    Main.Database.transferPlayer(oldClub, newClub, player);
                    for(NetworkUtil nu : clients.values()){
                        nu.write(Main.Database.getTransferList());
                    }
                    networkUtil = clients.get(newClub);
                    networkUtil.write(oldClub + " accepted your request. " + playerName + " is added in your squad");
                    networkUtil = networkUtilPrev;
                    networkUtil.write("Player sold");
                }
                else if(fromClient.contains("has rejected")){
                    NetworkUtil networkUtilPrev = networkUtil;
                    String whole[] = fromClient.split(",");
                    String oldClub = whole[1];
                    String newClub = whole[2];
                    String playerName = whole[3];
                    Player player = Main.Database.players.get(Main.Database.playerIndex(playerName));
                    Main.Database.transferPlayer(oldClub, newClub, player);
                    networkUtil = clients.get(newClub);
                    networkUtil.write(oldClub + " accepted your request. " + playerName + " is added in your squad");
                    networkUtil = networkUtilPrev;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
