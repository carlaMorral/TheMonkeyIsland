package game;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

public class GameFactory {

    private static final String usageMessage = "Us: java -jar client -s <maquina_servidora> -p <port>  [-i 0|1]";
    private final String[] args;
    private final HashMap<String, String> options = new HashMap<>();
    private Socket socket;

    public GameFactory(String[] args) {
        this.args = args;
    }

    /**
     * Shows the game usage message.
     */
    void showUsageMess() {
        System.out.println(usageMessage);
    }

    /**
     * Checks the input arguments.
     * @return a boolean indicating if the passed parameters are legal or not.
     */
    private boolean helpAndEdgeCases() {
        if (args.length == 1 && args[0].equals("-h")) {
            showUsageMess();
            return false;
        } else if (args.length == 0 || args.length % 2 == 1) {
            showUsageMess();
            return false;
        }
        setOptions(args);
        if (options.get("-s") == null) {
            showUsageMess();
            return false;
        }
        return true;
    }

    /**
     * Creates a ManualGame.
     */
    void createManualGame() {
        new ManualGame(socket);
    }

    /**
     * Creates an AutomaticGame.
     */
    void createAutoGame() {
        new AutomaticGame(socket);
    }

    /**
     * Sets the options member.
     */
    void setOptions(String[] args) {
        for (int i = 0; i < args.length; i += 2) {
            options.put(args[i], args[i + 1]);
        }
    }

    /**
     * Creates the client socket.
     * @throws IOException if the socket cannot be created.
     * @throws NumberFormatException if the port argument is not an Integer.
     */
    private void createSocket() throws IOException, NumberFormatException {
        int port = Integer.parseInt(options.get("-p"));
        socket = new Socket(InetAddress.getByName(options.get("-s")), port);
        socket.setSoTimeout(30000);
    }

    /**
     * Instantiates a new Game according to the parameters passed.
     */
    void instantiateGame() {
        String optionI = options.getOrDefault("-i", null);
        if (optionI == null) {
            createManualGame();
        } else if (optionI.equals("0")) {
            createManualGame();
        } else if (optionI.equals("1")) {
            createAutoGame();
        } else {
            showUsageMess();
        }
    }

    /**
     * Creates the client socket and instantiates the Game.
     */
    private void startGame() {
        try {
            createSocket();
            instantiateGame();

        } catch (IOException | NumberFormatException e) {
            if (e instanceof IOException) {
                System.out.println("IOException: " + e.getMessage());
            } else {
                System.out.println("NumberFormatException: " + e.getMessage());
            }
            showUsageMess();
        }
    }

    /**
     * Creates anew Game.
     */
    public void createGame() {
        boolean helpEdge = helpAndEdgeCases();
        if (!helpEdge) {
            return;
        }
        startGame();
    }
}
