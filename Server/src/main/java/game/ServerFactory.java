package game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerFactory {

    private static final String usageMessage = "Us: java -jar server.jar -p <port> -m <1|2>";
    private final String[] args;
    private final HashMap<String, String> options = new HashMap<>();
    private ServerSocket serverSocket;

    public ServerFactory(String[] args) {
        this.args = args;
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
        if (options.get("-p") == null || options.get("-m") == null) {
            showUsageMess();
            return false;
        }
        return true;
    }

    /**
     * Shows the server usage message.
     */
    void showUsageMess() {
        System.out.println(usageMessage);
    }

    /**
     * Sets the options member.
     */
    public void setOptions(String[] args) {
        for (int i = 0; i < args.length; i = i+2) {
            options.put(args[i], args[i+1]);
        }
    }

    /**
     * Accepts multiple connections of one player and starts the corresponding threads.
     * Instantiates the Game class.
     * @throws IOException if a socket cannot accept a connection.
     */
    void acceptOneConnection() throws IOException {
        while(true) {
            Socket s = serverSocket.accept();
            s.setSoTimeout(30000);
            Thread server = new Thread(new Game(s));
            server.start();
        }
    }

    /**
     * Accepts multiple connections of two players and starts the corresponding threads.
     * Instantiates the ProxyGame class.
     * @throws IOException if a socket cannot accept a connection.
     */
    void acceptTwoConnections() throws IOException {
        while(true) {
            Socket s1 = serverSocket.accept();
            Socket s2 = serverSocket.accept();
            s1.setSoTimeout(30000);
            s2.setSoTimeout(30000);
            Thread server = new Thread(new ProxyGame(s1, s2));
            server.start();
        }
    }

    /**
     * Creates a ServerSocket in order to be able to accept connections.
     * @throws IOException if the socket cannot be created.
     */
    void createServerSocket() throws IOException {
        int port = Integer.parseInt(options.get("-p"));
        serverSocket = new ServerSocket(port);
    }

    /**
     * Creates a ServerSocket in order to be able to accept connections.
     * @throws IOException if the socket cannot be created.
     */
    void startAcceptingConnections() throws IOException {
        int mode = Integer.parseInt(options.get("-m"));
        if (mode == 1) {
            acceptOneConnection();
        } else if (mode == 2) {
            acceptTwoConnections();
        } else {
            System.out.println(usageMessage);
        }
    }

    /**
     * Creates the Server Socket and starts accepting connections.
     */
    void startServer() {
        try {
            createServerSocket();
            startAcceptingConnections();
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
     * Starts the server-side operations, first checking the input arguments.
     */
    public void createServer() {
        boolean helpEdge = helpAndEdgeCases();
        if (!helpEdge) {
            return;
        }
        startServer();
    }
}
