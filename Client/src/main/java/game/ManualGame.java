package game;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ManualGame extends Game{

    public ManualGame(Socket socket) {
        super(socket);
    }

    /**
     * Gets a player name from the terminal.
     * @return String the name of the player, read from terminal.
     */
    @Override
    public String getPlayerName() {
        Scanner sc = new Scanner(System.in);
        System.out.println("¡Como te llamas, marinero?");
        String username = sc.nextLine();
        System.out.println("¡Pues claro, " + username + "! ¡Conocido en los siete mares!");
        return username;
    }

    /**
     * Gets a player's id.
     * @return int the player's id.
     */
    @Override
    public int getPlayerId() {
        return getInteger("identificador");
    }

    /**
     * Gets a secret number, from terminal.
     * @return Int the secret number read from terminal.
     */
    @Override
    public int getSecret() {
        return getInteger("número secreto");
    }

    /**
     * Gets an integer from the player, from terminal.
     * @return Int the number read from terminal.
     */
    public int getInteger(String integer) {
        Scanner sc = new Scanner(System.in);
        System.out.println("¡Introduce un número entero para tu " + integer + ", grumetilla!");
        String intParsed = sc.nextLine();
        while (true) {
            try {
                return Integer.parseInt(intParsed);
            } catch (NumberFormatException ignored) {
                System.out.println("¡Eso no vale! ¡Prueba de nuevo con un nuevo " + integer + "!");
                intParsed = sc.nextLine();
            }
        }
    }

    /**
     * Gets an insult from the learned ones. Displays a menu of the available choices, and the user
     * enters a number corresponding to an option.
     * @return String Insult chosen by the user.
     */
    @Override
    public String getInsult() {
        return getOption(" insulto", this.insultsArr);
    }

    /**
     * Gets a comeback from the learned ones. Displays a menu of the available choices, and the user
     * enters a number corresponding to an option.
     * @return String Comeback chosen by the user.
     */
    @Override
    public String getComeback() {
        return getOption("a réplica", this.comebacksArr);
    }

    /**
     * Gets an option from the given list. Displays a menu of the available choices, and the user
     * enters a number corresponding to an option.
     * @param option String name of the option (i.e., insult or comeback)
     * @param optionsList ArrayList<String> Options to select.
     * @return String Option chosen by the user.
     */
    public String getOption(String option, ArrayList<String> optionsList) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Escoge un" + option + " para jugar:");
        Collections.shuffle(optionsList);
        for (int i = 0; i < optionsList.size(); i++) {
            System.out.println((i + 1) + ". " + optionsList.get(i));
        }
        boolean done = false;
        int index = 0;
        while(!done) {
            try {
                index = Integer.parseInt(sc.nextLine());
                if (index < 1 || index > optionsList.size()) {
                    System.out.println("¡Número inválido, bucanero! ¡Prueba otra vez!");
                } else {
                    done = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("¡Esto no es un número, descerebrado! ¡Que no te enseñaron" +
                        " a contar, lobo de mar?");
            }

        }
        return optionsList.get(index-1);
    }

    /**
     * Asks the player if they want to play another game after finishing the previous.
     * @return Boolean: true if they want to play more, false if they do not.
     */
    @Override
    public boolean askToPlayMoreGames() {
        return askYNQuestion("¿Quieres volver a jugar, navegante?");
    }

    /**
     * Asks the player if they want to use the same name and id of the previous game.
     * @return Boolean: true if they want to use the same data, false if they do not.
     */
    @Override
    public boolean useSameData() {
        return askYNQuestion("¿Quieres seguir con el mismo nombre, tripulante?");
    }

    /**
     * Asks a yes/no question to the manual player.
     * @param question String containing the question.
     * @return Boolean true if the answer is yes, false if the answer is no.
     */
    private boolean askYNQuestion(String question) {
        Scanner sc = new Scanner(System.in);
        System.out.println(question + " [Ss/Nn]");
        String response = sc.nextLine();
        while (true) {
            if (response.equals("S") || response.equals("s") || response.equals("SI") ||
                    response.equals("si") || response.equals("Si") || response.equals("SÍ") ||
                    response.equals("sí") || response.equals("Sí")) {
                return true;
            } else if (response.equals("N") || response.equals("n") || response.equals("NO") ||
                    response.equals("no") || response.equals("No")) {
                return false;
            } else{
                System.out.println("¡¿Que no sabes hablar?!");
                response = sc.nextLine();
            }
        }
    }
}
