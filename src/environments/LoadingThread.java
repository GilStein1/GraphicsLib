package environments;

public class LoadingThread {

    private static LoadingThread instance;
    private Thread t;

    private LoadingThread(Runnable run) {
        t = new Thread(run);
    }
    public static LoadingThread getInstance(Runnable run) {
        if(instance == null) {
            instance = new LoadingThread(run);
        }
        return instance;
    }
    public void start() {
        t.start();

    }
    public static LoadingThread getInstance() {
        return instance;
    }

}
