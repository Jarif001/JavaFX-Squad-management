package sample;

import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    private ServerSocket serverSocket;
    private HashMap<String, NetworkUtil> clients;
    String clubName = "";

    Server(){
        System.out.println("server started");
        clients = new HashMap<>();
    }

    private void runServer(){

        try{
            serverSocket = new ServerSocket(2222);

            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("Accepted");
                NetworkUtil networkUtil = new NetworkUtil(socket);
                try{
                    clubName = (String)networkUtil.read();
                }
                catch (EOFException e){
                    System.out.println("Client is closed");
                }
                catch (Exception e){
                    System.out.println("Client is closed");
                }
                clients.put(clubName, networkUtil);
                new readWriteThread(networkUtil, clients);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void activate(){
        new Thread(()->runServer()).start();
    }
}
