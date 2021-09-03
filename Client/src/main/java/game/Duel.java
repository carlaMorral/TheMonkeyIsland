package game;

import TIP.Shout;
import TIP.TIPComeback;
import TIP.TIPInsult;
import TIP.TIPMessage;
import data.TIPInsultContainer;
import utils.TIPException;

public class Duel {

    private int numWonInsults;
    private int numLostInsults;
    private final Game game;

    public Duel(Game game) {
        numWonInsults = 0;
        numLostInsults = 0;
        this.game = game;
    }

    /**
     * Method that implements a turn started by the Client. The client starts insulting.
     * @return Int an integer of the following turn, 0 if next turn starts the client, 1 if next
     * turn starts the server.
     * @throws TIPException If an insult is not correctly entered, or the received message is not
     * a comeback.
     */
    int playClientTurn() throws TIPException {
        System.out.println("¡Es tu turno, " + game.getCurrentName() + "! ¡Demuestra lo que vales!");
        String comeback;
        String insult = game.getInsult();
        game.tipClientConn.sendMessage(new TIPInsult(insult));
        System.out.println("Tu insulto ha sido:");
        System.out.println(insult);
        TIPMessage messReceived = game.tipClientConn.recv();
        if (!(messReceived instanceof TIPComeback)) {
            throw new TIPException("Wrong TIPMessage format received.");
        } else {
            comeback = ((TIPComeback) messReceived).getComeback();
        }
        System.out.println(game.tipClientConn.getServerName() + " ha enviado la siguiente respuesta:");
        System.out.println(comeback);
        if (comeback.equals(TIPInsultContainer.getInstance().getComeback(insult))) {
            // Client has lost
            System.out.println("¡Maldita sea! ¿Qué se ha creído," +
                    " con esta respuesta tan acertada?\n");
            numWonInsults = 0;
            numLostInsults ++;
            return 1;
        } else {
            System.out.println("¡No sabe ni lo que dice! ¡Lo estás destrozando, sigue así!\n");
            numWonInsults ++;
            numLostInsults = 0;
            return 0;
        }

    }

    /**
     * Method that implements a turn started by the Server. The server starts insulting.
     * @return Int an integer of the following turn, 0 if next turn starts the client, 1 if next
     * turn starts the server.
     * @throws TIPException If a comeback is not correctly entered, or the received message is not
     * an insult, or if the insult received is not a valid one.
     */
    int playServerTurn() throws TIPException {
        System.out.println("¡Es el turno de " + game.tipClientConn.getServerName() + "!");
        String insult;
        TIPMessage messReceived = game.tipClientConn.recv();
        if (!(messReceived instanceof TIPInsult)) {
            throw new TIPException("Wrong TIPMessage format received.");
        } else {
            insult = ((TIPInsult) messReceived).getInsult();
        }
        System.out.println(game.tipClientConn.getServerName() + " ha enviado el siguiente insulto:");
        System.out.println(insult);
        String comeback = game.getComeback();
        game.tipClientConn.sendMessage(new TIPComeback(comeback));
        System.out.println("Tu respuesta ha sido:");
        System.out.println(comeback);
        if (!comeback.equals(TIPInsultContainer.getInstance().getComeback(insult))) {
            // Client has lost
            System.out.println("¡No ha sido lo suficiente humillante! ¡Qué pena!\n");
            numWonInsults = 0;
            numLostInsults ++;
            return 1;
        } else {
            System.out.println("¡Así se responde, no hay quien te pare!\n");
            numWonInsults ++;
            numLostInsults = 0;
            return 0;
        }
    }

    /**
     * Void method that implements a duel. The duel is not finished until either the client or
     * the server has won two consecutive turns. In this case, the duel counters are updated
     * accordingly, and the correct shout message is sent, received and printed.
     * @throws TIPException If turn is not computed correctly, or has thrown an exception.
     */
    void newDuel() throws TIPException {
        numWonInsults = 0;
        numLostInsults = 0;
        int turn = game.tipClientConn.turn(game.getSecret());
        while (numWonInsults != 2 && numLostInsults != 2) {
            if (turn == 0) {
                turn = playClientTurn();
            } else if (turn == 1) {
                turn = playServerTurn();
            } else if (turn == -2) {
                throw new TIPException("Equal IDs");
            } else {
                throw new TIPException("Wrong turn number.");
            }
        }
        //According to protocol, client always shout first
        if (numWonInsults == 2) {
            game.incrNumWonDuel();
            game.tipClientConn.shout(Shout.HEGANADO);
            TIPMessage shoutReceived = game.tipClientConn.recv();
            System.out.println(shoutReceived.toString());
        } else {
            game.incrNumLostDuel();
            if (game.getNumLostDuel() == 3) {
                game.tipClientConn.shout(Shout.HGMELEE);
            } else {
                game.tipClientConn.shout(Shout.HASGANADO);
            }
            TIPMessage shoutReceived = game.tipClientConn.recv();
            System.out.println(shoutReceived.toString());
        }
    }

}
