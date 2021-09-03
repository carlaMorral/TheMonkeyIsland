import game.ServerFactory;

public class Server {

    public static void main(String[] args) {
        ServerFactory factory = new ServerFactory(args);
        factory.createServer();
    }
}
