package sample;

public class ReadThreadClub implements Runnable{

    private Thread thr;
    private NetworkUtil networkUtil;

    public ReadThreadClub(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
        thr = new Thread(this);
        thr.start();
    }

    @Override
    public void run() {

    }
}
