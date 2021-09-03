package connections;

import TIP.Shout;
import TIP.TIPError;
import TIP.TIPMessage;
import TIP.TIPMessageFactory;
import utils.TIPConnUtils;
import utils.TIPException;
import java.io.IOException;
import java.net.Socket;

abstract class TIPConnection {
    TIPMessageFactory factory = TIPMessageFactory.getInstance();
    TIPConnUtils connUtils;
    Socket socket;
    protected String serverName;
    protected String clientName;
    protected int idClient;
    protected int idServer;

    TIPConnection(TIPConnUtils connUtils, Socket socket) {
        this.connUtils = connUtils;
        this.socket = socket;
    }

    public void setClientName(String newName) {
        this.clientName = newName;
    }

    public void setClientId(int newId) {
        this.idClient = newId;
    }

    public void setServerName(String newName) {
        this.serverName = newName;
    }

    public void setServerId(int newId) {
        this.idServer = newId;
    }

    public String getClientName() {
        return this.clientName;
    }

    public String getServerName() {
        return this.serverName;
    }

    /**
     * Sends and receives a HELLO TIPMessage to start the TIP communication.
     */
    abstract void hello() throws TIPException ;

    /**
     * Decides who starts writing. Sends and receives the SECRET and HASH TIP messages.
     * @return 0 if the client starts writing, 1 if the server starts, -1 if there is some problem
     */
    public abstract int turn(int secret);

    /**
     * Sends a shout message. This method will send this 3 types of messages:
     * 1. ¡He ganado, clientName!
     * 2. ¡Has ganado, clientName!
     * 3. ¡Has ganado, clientName. Eres tan bueno que podrias luchar contra la Sword Master de
     * la isla Mêlée!
     */
    public abstract void shout(Shout shout);

    /**
     * Sends a TIPMessage.
     * @param message: the TIPMessage to send.
     */
    void send(TIPMessage message) {
        byte[] encodedMess = message.encoded();
        try {
            connUtils.writeBytes(encodedMess);
        } catch (IOException e) {
            System.out.println("IOException: Cannot send message.");
        }
    }

    /**
     * Receives a TIPMessage.
     * @return The TIPMessage if it's a valid TIPMessage, or an ERROR TIPMessage otherwise.
     */
    TIPMessage recv() {
        TIPMessage message;
        try {
            byte[] messBytes = connUtils.readStream();
            message = factory.getTIPMessage(messBytes);
        } catch (IOException | TIPException e) {
            message = new TIPError();
        }
        return message;
    }

    /**
     * Closes the streams of the socket and the socket.
     */
    public void close() {
        try {
            this.connUtils.getDataInputStream().close();
            this.connUtils.getDataOutputStream().close();
            this.socket.close();
        } catch (IOException e) {
            System.out.println("IOException: Socket disconnection error");
        }
    }
}
