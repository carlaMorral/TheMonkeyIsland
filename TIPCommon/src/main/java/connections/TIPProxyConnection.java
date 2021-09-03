package connections;

import TIP.TIPError;
import TIP.TIPMessage;
import TIP.TIPMessageFactory;
import utils.TIPConnUtils;
import utils.TIPException;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;


public class TIPProxyConnection implements Runnable {

    private final Logger logger;
    private final TIPConnUtils connUtils1;
    private final TIPConnUtils connUtils2;
    private final Socket s1;
    private final Socket s2;
    private static final TIPMessageFactory factory = TIPMessageFactory.getInstance();
    private final int client1ID;

    public TIPProxyConnection(Socket clientSocket1, Socket clientSocket2, Logger logger) throws IOException {
        this.logger = logger;
        this.connUtils1 = new TIPConnUtils(clientSocket1.getInputStream(), clientSocket1.getOutputStream());
        this.connUtils2 = new TIPConnUtils(clientSocket2.getInputStream(), clientSocket2.getOutputStream());
        this.s1 = clientSocket1;
        this.s2 = clientSocket2;
        this.client1ID = clientSocket1.hashCode();
    }

    public void loop() {
        while (true) {
            try {
                byte[] message1 = this.recv(connUtils1);
                this.send(connUtils2, message1);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                close();
                return;
            }
        }
    }

    void send(TIPConnUtils connUtils, byte[] message) throws IOException {
        connUtils.writeBytes(message);
    }

    byte[] recv(TIPConnUtils connUtils) throws IOException {
        TIPError message;
        byte[] messBytes;
        try {
            messBytes = connUtils.readStream();
            TIPMessage mess = factory.getTIPMessage(messBytes);
            messBytes = mess.encoded();
            logger.info(client1ID + ": " + mess.toString());
        } catch (TIPException e) {
            message = new TIPError();
            logger.severe(client1ID + ": " + message.toString());
            messBytes = message.encoded();
        }
        return messBytes;
    }

    @Override
    public void run() {
        loop();
    }

    /**
     * Closes the streams of the socket and the socket.
     */
    public void close() {
        try {
            this.connUtils1.getDataInputStream().close();
            this.connUtils1.getDataOutputStream().close();
            this.connUtils2.getDataInputStream().close();
            this.connUtils2.getDataOutputStream().close();
            this.s1.close();
            this.s2.close();
        } catch (IOException e) {
            System.out.println("IOException: Socket disconnection error");
        }
    }
}
