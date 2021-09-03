package game;

import TIP.*;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import connections.TIPServerConnection;
import data.TIPInsultContainer;
import data.TIPNamesContainer;
import utils.TIPException;
import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import static java.lang.Math.min;

public class Game implements Runnable {

    TIPServerConnection tipServerConn;
    static final TIPInsultContainer container = TIPInsultContainer.getInstance();
    int numWonDuel;
    int numLostDuel;
    private HashMap<String, String> insults;
    private ArrayList<String> insultsArr;
    private ArrayList<String> comebacksArr;
    private Duel duel;

    static final List<String> names = TIPNamesContainer.getInstance().getNames();


    public Game(Socket socket) {
        String name = getPlayerName();
        insults = new HashMap<>();
        insultsArr = new ArrayList<>();
        comebacksArr = new ArrayList<>();
        duel = new Duel(this);
        Logger logger = Logger.getLogger("ServerLogger");
        try {
            logger.addHandler(new FileHandler("Server " + Thread.currentThread().getName() + ".log"));
            tipServerConn = new TIPServerConnection(
                    socket,
                    name,
                    logger);
            playGame();
        } catch (IOException | TIPException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {}

    /**
     * Gets a random player game for the server from a hardcoded list.
     * @return String random player name from a list.
     */
    String getPlayerName() {
        return names.get((new Random()).nextInt(names.size()));
    }

    /**
     * Gets a random secret number between 0 and 9.
     * @return Integer between 0 and 9.
     */
    int getSecret() {
        return (int)(Math.random() * 10);
    }

    /**
     * Gets a random insult from the learned ones.
     * @return String insult to send.
     */
    public String getInsult() {
        return insultsArr.get((new Random()).nextInt(insults.size()));
    }

    /**
     * Gets a random comeback from the learned ones.
     * @return String comeback to send.
     */
    public String getComeback() {
        return comebacksArr.get((new Random()).nextInt(insults.size()));
    }

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
     * Main method, plays the game. Until three duels are not won by the client or the server, the
     * game does not end. At the beginning, two random insult-comeback pairs are added to the
     * known list, and when a duel is started, another pair is added.
     */
    void playGame() {
        while (true) {
            insults.clear();
            insultsArr.clear();
            comebacksArr.clear();
            numWonDuel = 0;
            numLostDuel = 0;
            addUnknownInsultComeback(1);
            while (numWonDuel < 3 && numLostDuel < 3) {
                addUnknownInsultComeback(1);
                try {
                    duel.newDuel();
                } catch (TIPException e) {
                    System.out.println(e.getMessage());
                    if ("Equal IDs".equals(e.getMessage())) {
                        System.out.println((new TIPError(-1)).toString());
                    } else {
                        System.out.println((new TIPError()).toString());
                    }
                    return; //ends the thread normally
                }
            }
            tipServerConn.setServerName(getPlayerName());
            try {
                tipServerConn.hello();
            } catch (TIPException e) {
                System.out.println(e.getMessage());
                System.out.println((new TIPError()).toString());
                return;
            }
        }
    }
}
