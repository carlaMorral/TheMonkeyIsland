package connections;

import TIP.*;
import utils.TIPConnUtils;
import utils.TIPException;
import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

public class TIPClientConnection extends TIPConnection {

    public TIPClientConnection(Socket socket, String clientName, int idClient) throws
            IOException, TIPException {
        super(
                new TIPConnUtils(socket.getInputStream(), socket.getOutputStream()),
                socket
        );
        this.clientName = clientName;
        this.idClient = idClient;
        this.hello();
    }

    /**
     * Sends and receives a HELLO TIPMessage to start the TIP communication with the server.
     */
    @Override
    public void hello() throws TIPException {
        TIPHello clientMess = new TIPHello(this.clientName, this.idClient);
        this.send(clientMess);
        TIPMessage serverMess = this.recv();
        if (!(serverMess instanceof TIPHello)) {
            throw new TIPException("Received message in hello is " + serverMess.getClass());
        }
        this.serverName = ((TIPHello) serverMess).getName();
        this.idServer = ((TIPHello) serverMess).getId();
    }

    /**
     * Decides who starts writing. Sends and receives the SECRET and HASH TIP messages.
     * @return 0 if the client starts writing, 1 if the server starts, -1 if there is some problem
     */
    @Override
    public int turn(int secret) {
        int serverSecret;
        try {
            this.send(new TIPHash(String.valueOf(secret)));
            TIPMessage serverHashMess = this.recv();
            this.send(new TIPSecret(String.valueOf(secret)));
            TIPMessage serverSecretMess = this.recv();
            if (!(serverSecretMess instanceof TIPSecret)) {
                System.out.println("Secret message received format wrong");
                return -1;
            }
            if (!(serverHashMess instanceof TIPHash)) {
                System.out.println("Hash message received format wrong");
                return -1;
            }

            serverSecret = ((TIPSecret) serverSecretMess).getSecretNumber();

            //impossible in server-client games, but possible in p2p
            if (this.idServer == this.idClient) {
                System.out.println("IDs are equal!");
                return -2;
            }
            if (((TIPHash) serverHashMess).compare(String.valueOf(serverSecret))) {
                if ((serverSecret + secret) % 2 == 0) {
                    //id mes petit
                    return this.idClient < this.idServer ? 0 : 1;
                } else {
                    //id mes gran
                    return this.idClient > this.idServer ? 0 : 1;
                }
            } else{
                System.out.println("Hash of server not matching.");
                return -1;
            }
        } catch (NoSuchAlgorithmException | TIPException e) {
            System.out.println(e.getClass() + ": " + e.getMessage());
            return -1;
        }
    }

    /**
     * Sends a shout message to the server. This method will send this 3 types of messages:
     * 1. ¡He ganado, serverName!
     * 2. ¡Has ganado, serverName!
     * 3. ¡Has ganado, serverName. Eres tan bueno que podrias luchar contra la Sword Master de
     * la isla Mêlée!
     */
    @Override
    public void shout(Shout shout) {
        TIPShout shoutMess = new TIPShout(shout, serverName);
        System.out.println(shoutMess.toString());
        send(shoutMess);
    }

    /**
     * Sends a TIPMessage to the server.
     * @param message: the TIPMessage to send.
     */
    @Override
    void send(TIPMessage message) {
        byte[] encodedMess = message.encoded();
        try {
            connUtils.writeBytes(encodedMess);
        } catch (IOException e) {
            System.out.println("IOException: Cannot send message.");
        }
    }

    /**
     * Sends a TIPInsult to the server.
     */
    public void sendMessage(TIPInsult message) {
        send(message);
    }

    /**
     * Sends a TIPComeback to the server.
     */
    public void sendMessage(TIPComeback message) {
        send(message);
    }

    /**
     * Receives a TIPMessage from the server.
     * @return The TIPMessage sent by the server if it's a valid TIPMessage, or an ERROR
     * TIPMessage otherwise.
     */
    @Override
    public TIPMessage recv() {
        TIPMessage message;
        try {
            byte[] messBytes = connUtils.readStream();
            message = factory.getTIPMessage(messBytes);
        } catch (IOException | TIPException e) {
            if (e instanceof IOException) {
                System.out.println(e.getMessage());
            }
            message = new TIPError();
        }
        return message;
    }
}
