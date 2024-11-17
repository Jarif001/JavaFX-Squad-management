package sample;

public class WriteThreadClub implements Runnable{
    private Thread thr;
    private String name;
    private NetworkUtil networkUtil;
    private String msg;

    public WriteThreadClub(String name, NetworkUtil networkUtil, String msg) {
        this.name = name;
        this.networkUtil = networkUtil;
        this.msg = msg;
        thr = new Thread(this);
        thr.start();
    }

    @Override
    public void run() {
        try{
            while(true){

            }
        }
        catch(Exception e){

        }
    }
}
