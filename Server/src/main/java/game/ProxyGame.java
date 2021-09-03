package game;

import connections.TIPProxyConnection;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class ProxyGame implements Runnable {

    private final Socket client1;
    private final Socket client2;

    public ProxyGame(Socket s1, Socket s2) {
        this.client1 = s1;
        this.client2 = s2;
    }

    @Override
    public void run() {
        try {
            Logger logger = Logger.getLogger("Server"+Thread.currentThread().getName()+".log");
            Thread t1 = new Thread(new TIPProxyConnection(client1, client2, logger));
            t1.start();
            Thread t2 = new Thread(new TIPProxyConnection(client2, client1, logger));
            t2.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
