package sample;

import java.io.IOException;

public class ClubClient {
    private String clubName;
    private String localhost;
    private int port;
    private NetworkUtil networkUtil;

    public ClubClient(String localhost, int i, String clubName) throws IOException {
        this.clubName = clubName;
        this.localhost = localhost;
        this.port = i;
    }

    private void runClient(){
        try {
            networkUtil = new NetworkUtil(localhost, port);
            System.out.println("Client started");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void activate(){
        runClient();
    }

    public NetworkUtil getNetworkUtil(){
        return this.networkUtil;
    }

    public void close(){
        networkUtil.close();
    }

}
