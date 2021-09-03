package connections;

import TIP.*;
import utils.TIPConnUtils;
import utils.TIPException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

public class TIPServerConnection extends TIPConnection {

    private final Logger logger;
    private final InetAddress clientAddress;

    public TIPServerConnection(Socket clientSocket, String serverName, Logger logger) throws
            IOException, TIPException {
        super(
                new TIPConnUtils(clientSocket.getInputStream(), clientSocket.getOutputStream()),
                clientSocket
        );
        this.logger = logger;
        this.serverName = serverName;
        this.clientAddress = clientSocket.getLocalAddress();
        this.hello();
    }

    /**
     * Sends and receives a HELLO TIPMessage to start the TIP communication with the client.
     */
    @Override
    public void hello() throws TIPException {
        TIPMessage clientMess = this.recv();
        if (!(clientMess instanceof TIPHello)) {
            throw new TIPException("Received message in hello is " + clientMess.getClass());
        }
        this.clientName = ((TIPHello) clientMess).getName();
        this.idClient = ((TIPHello) clientMess).getId();
        this.idServer =  (int)(Math.random() * Math.pow(2,31));
        while (this.idClient == this.idServer) {
            this.idServer =  (int)(Math.random() * Math.pow(2,31));
        }
        TIPHello serverMess = new TIPHello(this.serverName, this.idServer);
        this.send(serverMess);
    }

    /**
     * Decides who starts writing. Sends and receives the SECRET and HASH TIP messages.
     * @return 0 if the client starts writing, 1 if the server starts, -1 if there is some problem
     */
    @Override
    public int turn(int secret) {
        int clientSecret;
        try {
            this.send(new TIPHash(String.valueOf(secret)));
            TIPMessage clientHashMess = this.recv();
            this.send(new TIPSecret(String.valueOf(secret)));
            TIPMessage clientSecretMess = this.recv();
            if (!(clientSecretMess instanceof TIPSecret)) {
                System.out.println("Secret message received format wrong");
                return -1;
            }
            if (!(clientHashMess instanceof TIPHash)) {
                System.out.println("Hash message received format wrong");
                return -1;
            }

            clientSecret = ((TIPSecret) clientSecretMess).getSecretNumber();
            //impossible in server-client games, but possible in p2p
            if (this.idServer == this.idClient) {
                System.out.println("IDs are equal!");
                return -2;
            }
            if (((TIPHash) clientHashMess).compare(String.valueOf(clientSecret))) {
                if ((clientSecret + secret) % 2 == 0) {
                    //id mes petit
                    return this.idClient < this.idServer ? 0 : 1;
                } else {
                    //id mes gran
                    return this.idClient > this.idServer ? 0 : 1;
                }
            } else{
                System.out.println("Hash of client not matching.");
                return -1;
            }
        } catch (NoSuchAlgorithmException | TIPException e) {
            System.out.println(e.getClass() + ": " + e.getMessage());
            return -1;
        }
    }

    /**
     * Sends a shout message to the server. This method will send this 3 types of messages:
     * 1. ¡He ganado, clientName!
     * 2. ¡Has ganado, clientName!
     * 3. ¡Has ganado, clientName. Eres tan bueno que podrias luchar contra la Sword Master de
     * la isla Mêlée!
     */
    @Override
    public void shout(Shout shout) {
        TIPShout shoutMess = new TIPShout(shout, clientName);
        send(shoutMess);
    }

    /**
     * Sends a TIPInsult to the client.
     */
    public void sendMessage(TIPInsult message) {
        send(message);
    }

    /**
     * Sends a TIPComeback to the client.
     */
    public void sendMessage(TIPComeback message) {
        send(message);
    }

    /**
     * Receives a TIPMessage.
     * @return The TIPMessage if it's a valid TIPMessage, or an ERROR TIPMessage otherwise.
     */
    public TIPMessage recv() {
        TIPMessage message;
        try {
            byte[] messBytes = connUtils.readStream();
            message = factory.getTIPMessage(messBytes);
            logger.info("Client " + clientName + " " + idClient + clientAddress + ": " +
                    message.toString());
        } catch (IOException | TIPException e) {
            if (e instanceof IOException) {
                System.out.println(e.getMessage());
                System.out.println("Socket closed because player has disconnected.");
            }
            message = new TIPError();
            logger.severe("Client " + clientName + idClient + clientAddress + ": " +
                    message.toString());
        }
        return message;
    }

    /**
     * Sends a TIPMessage.
     * @param message: the TIPMessage to send.
     */
    void send(TIPMessage message) {
        logger.info("Server " + serverName + " " + idServer + ": " + message.toString());
        byte[] encodedMess = message.encoded();
        try {
            connUtils.writeBytes(encodedMess);
        } catch (IOException e) {
            System.out.println("IOException: Cannot send message.");
        }
    }

}
