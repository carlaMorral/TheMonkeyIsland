package game;

import data.TIPNamesContainer;
import java.net.Socket;
import java.util.List;
import java.util.Random;

public class AutomaticGame extends Game{

    static final List<String> names = TIPNamesContainer.getInstance().getNames();

    public AutomaticGame(Socket socket) {
        super(socket);
    }

    /**
     * Gets a random player game for the server from a hardcoded list.
     * @return String random player name from a list.
     */
    @Override
    public String getPlayerName() {
        return names.get((new Random()).nextInt(names.size()));
    }

    /**
     * Gets a player's id.
     * @return int the player's id.
     */
    @Override
    public int getPlayerId() {
        return (int)(Math.random() * Math.pow(2,31));
    }

    /**
     * Gets a random secret number between 0 and 9.
     * @return Integer between 0 and 9.
     */
    @Override
    public int getSecret() {
        return (int)(Math.random() * 10);
    }

    /**
     * Gets a random insult from the learned ones.
     * @return String containing a learned insult.
     */
    @Override
    public String getInsult() {
        return super.insultsArr.get((new Random()).nextInt(super.insults.size()));
    }

    /**
     * Gets a random comeback from the learned ones.
     * @return String containing a learned comeback.
     */
    @Override
    public String getComeback() {
        return super.comebacksArr.get((new Random()).nextInt(super.insults.size()));
    }

    /**
     * Asks the player if they want to play another game after finishing the previous. There is a
     * 75% chance that the automatic player will play again.
     * @return Boolean: true if they want to play more, false if they do not.
     */
    @Override
    public boolean askToPlayMoreGames() {
        boolean out = (new Random()).nextBoolean() || (new Random()).nextBoolean();
        if (out) {
            System.out.println("\n¡Quiero la revancha, marinero de agua dulce!");
        } else {
            System.out.println("\n¡Ya he tenido bastante de tus tonterias, cucaracha!");
        }
        return out;
    }

    /**
     * Asks the player if they want to use the same name and id of the previous game. There is a
     * 75% chance that the automatic player will use the same name and id.
     * @return Boolean: true if they want to use the same data, false if they do not.
     */
    @Override
    public boolean useSameData() {
        boolean out = (new Random()).nextBoolean() || (new Random()).nextBoolean();
        if (out) {
            System.out.println("¡Pues claro que sigo siendo yo!\n");
        } else {
            System.out.println("¡Oh, ahora verás! ¡Tengo muchos apodos!\n");
        }
        return out;
    }
}
