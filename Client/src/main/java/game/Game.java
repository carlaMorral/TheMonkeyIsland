package game;

import TIP.*;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import connections.TIPClientConnection;
import data.TIPInsultContainer;
import utils.TIPException;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

import static java.lang.Math.min;

public abstract class Game {

    TIPClientConnection tipClientConn;
    static final TIPInsultContainer container = TIPInsultContainer.getInstance();
    private int numWonDuel;
    private int numLostDuel;
    private int currentId;
    private String currentName;
    protected HashMap<String, String> insults;
    protected ArrayList<String> insultsArr;
    protected ArrayList<String> comebacksArr;
    private final Duel duel = new Duel(this);

    public Game(Socket socket) {
        this.currentName = getPlayerName();
        this.currentId = getPlayerId();
        insults = new HashMap<>();
        insultsArr = new ArrayList<>();
        comebacksArr = new ArrayList<>();
        try {
            tipClientConn = new TIPClientConnection(socket, this.currentName, this.currentId);
            playGame();
        } catch (IOException | TIPException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getCurrentName() {
        return currentName;
    }

    public int getNumLostDuel() {
        return numLostDuel;
    }

    public void incrNumWonDuel() {
        numWonDuel ++;
    }

    public void incrNumLostDuel() {
        numLostDuel ++;
    }

    /**
     * Gets a player's name.
     * @return String the player's name.
     */
    public abstract String getPlayerName();

    /**
     * Gets a player's id.
     * @return int the player's id.
     */
    public abstract int getPlayerId();

    /**
     * Gets a secret number.
     * @return Int the secret number.
     */
    public abstract int getSecret();

    /**
     * Gets a insult.
     * @return String insult to send.
     */
    public abstract String getInsult();

    /**
     * Gets a comeback.
     * @return String comeback to send.
     */
    public abstract String getComeback();

    /**
     * Asks the player if they want to play another game after finishing the previous.
     * @return Boolean: true if they want to play more, false if they do not.
     */
    public abstract boolean askToPlayMoreGames();

    /**
     * Asks the player if they want to use the same name and id of the previous game.
     * @return Boolean: true if they want to use the same data, false if they do not.
     */
    public abstract boolean useSameData();

    /**
     * Adds a certain number of pairs insult-comeback to the known list.
     * @param num Int number of pairs insult-comeback to add.
     */
    private void addUnknownInsultComeback(int num) {
        MapDifference<String, String> diff = Maps.difference(this.insults, container.getInsults());
        Map<String, String> unknownInsults = diff.entriesOnlyOnRight();
        List<String> keys = new ArrayList<>(unknownInsults.keySet());
        Collections.shuffle(keys);
        int size = min(num, unknownInsults.size());
        for (int i = 0; i < size; i++) {
            this.insults.put(keys.get(i), unknownInsults.get(keys.get(i)));
            this.insultsArr.add(keys.get(i));
            this.comebacksArr.add(unknownInsults.get(keys.get(i)));
        }
    }


    /**
     * Determines if the user wants to play another game.
     * @return true if the user wants to play again, false otherwise.
     * @throws TIPException if the server doesn't want to play more.
     */
    boolean newGame() throws TIPException {
        boolean playMore = askToPlayMoreGames();
        if (!playMore) {
            return false;
        } else {
            boolean withSameData = useSameData();
            if (!withSameData) {
                String newName = getPlayerName();
                int newId = getPlayerId();
                if (!(newId == this.currentId && newName.equals(this.currentName))) {
                    insults.clear();
                    insultsArr.clear();
                    comebacksArr.clear();
                    this.currentName = newName;
                    this.currentId = newId;
                    tipClientConn.setClientName(this.currentName);
                    tipClientConn.setClientId(this.currentId);
                }
            }
            tipClientConn.hello();
            return true;
        }
    }

    /**
     * Main method, plays the game. Until three duels are not won by the client or the server, the
     * game does not end. At the beginning, two random insult-comeback pairs are added to the
     * known list, and when a duel is started, another pair is added.
     */
    void playGame() {
        int numGamesPlayed = 0;
        boolean anotherGame;
        do {
            gameSummary(numGamesPlayed);
            numWonDuel = 0;
            numLostDuel = 0;
            addUnknownInsultComeback(1);
            while (numWonDuel < 3 && numLostDuel < 3) {
                addUnknownInsultComeback(1);
                try {
                    duelSummary();
                    duel.newDuel();
                } catch (TIPException e) {
                    System.out.println(e.getMessage());
                    if ("Equal IDs".equals(e.getMessage())) {
                        System.out.println((new TIPError(-1)).toString());
                    } else {
                        System.out.println((new TIPError()).toString());
                    }
                    return;
                }
            }
            numGamesPlayed++;
            try {
                anotherGame = newGame();
            } catch (TIPException e) {
                System.out.println(e.getMessage());
                System.out.println((new TIPError()).toString());
                return;
            }
        } while (anotherGame);
    }

    /**
     * Prints the number of games played and two greeting messages.
     */
    void gameSummary(int numGamesPlayed) {
        System.out.println("Numero de partidas jugadas: " + numGamesPlayed);
        System.out.println("\n¡Soy el temible " + currentName + "!");
        System.out.println("¡No te tengo miedo, yo soy el archiconocido " +
                tipClientConn.getServerName() + "!");
    }

    /**
     * Prints the number of duels won and lost by the client.
     */
    void duelSummary() {
        String out = "\n------------------------------------------------\n" +
                "Duelos ganados: " + numWonDuel + "  |  Duelos perdidos: " +
                numLostDuel + "\n" +
                "------------------------------------------------";
        System.out.println(out);
    }
}
