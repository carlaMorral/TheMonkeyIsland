package game;

import TIP.Shout;
import TIP.TIPComeback;
import TIP.TIPInsult;
import TIP.TIPMessage;
import data.TIPInsultContainer;
import utils.TIPException;

public class Duel {

    private final Game game;
    private int numWonInsults;
    private int numLostInsults;

    public Duel(Game game) {
        this.game = game;
    }

    /**
     * Method that implements a turn started by the Server. The server starts insulting.
     * @return Int an integer of the following turn, 0 if next turn starts the client, 1 if next
     * turn starts the server.
     * @throws TIPException If an insult is not correctly entered, or the received message is not
     * a comeback.
     */
    private int playServerTurn() throws TIPException {
        String comeback;
        String insult = game.getInsult();
        game.tipServerConn.sendMessage(new TIPInsult(insult));
        TIPMessage messReceived = game.tipServerConn.recv();
        if (!(messReceived instanceof TIPComeback)) {
            throw new TIPException("Wrong TIPMessage format received.");
        } else {
            comeback = ((TIPComeback) messReceived).getComeback();
        }
        if (comeback.equals(TIPInsultContainer.getInstance().getComeback(insult))) {
            // Server has lost
            numWonInsults = 0;
            numLostInsults ++;
            return 0;
        } else {
            numWonInsults ++;
            numLostInsults = 0;
            return 1;
        }
    }

    /**
     * Method that implements a turn started by the Client. The client starts insulting.
     * @return Int an integer of the following turn, 0 if next turn starts the client, 1 if next
     * turn starts the server.
     * @throws TIPException If a comeback is not correctly entered, or the received message is not
     * an insult, or if the insult received is not a valid one.
     */
    private int playClientTurn() throws TIPException {
        String insult;
        TIPMessage messReceived = game.tipServerConn.recv();
        if (!(messReceived instanceof TIPInsult)) {
            throw new TIPException("Wrong TIPMessage format received.");
        } else {
            insult = ((TIPInsult) messReceived).getInsult();
        }
        String comeback = game.getComeback();
        game.tipServerConn.sendMessage(new TIPComeback(comeback));
        if (!comeback.equals(TIPInsultContainer.getInstance().getComeback(insult))) {
            // Client has lost
            numWonInsults = 0;
            numLostInsults ++;
            return 0;
        } else {
            numWonInsults ++;
            numLostInsults = 0;
            return 1;
        }
    }


    /**
     * Void method that implements a duel. The duel is not finished until either the client or
     * the server has won two consecutive turns. In this case, the duel counters are updated
     * accordingly, and the correct shout message is sent, received and printed.
     * @throws TIPException If turn is not computed correctly, or has thrown an exception.
     */
    public void newDuel() throws TIPException {
        numWonInsults = 0;
        numLostInsults = 0;
        int turn = game.tipServerConn.turn(game.getSecret());
        while (numWonInsults != 2 && numLostInsults != 2) {
            if (turn == 0) {
                turn = playClientTurn();
            } else if (turn == 1){
                turn = playServerTurn();
            } else if (turn == -2) {
                throw new TIPException("Equal IDs");
            } else {
                throw new TIPException("Wrong turn number.");
            }
        }
        //According to protocol, server always receives first
        if (numWonInsults == 2) {
            game.numWonDuel ++;
            game.tipServerConn.recv();
            game.tipServerConn.shout(Shout.HEGANADO);
        } else {
            game.numLostDuel ++;
            game.tipServerConn.recv();
            if (game.numLostDuel == 3) {
                game.tipServerConn.shout(Shout.HGMELEE);
            } else {
                game.tipServerConn.shout(Shout.HASGANADO);
            }
        }
    }
}

