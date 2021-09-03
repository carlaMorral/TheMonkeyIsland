import game.GameFactory;

public class Client {

    public static void main(String[] args) {
        GameFactory factory = new GameFactory(args);
        factory.createGame();
    }
}
