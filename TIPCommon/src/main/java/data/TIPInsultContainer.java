package data;

import utils.TIPException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class TIPInsultContainer {

    private HashMap<String, String> insults;
    private HashMap<String, String> comebacks;
    private static TIPInsultContainer container;

    private TIPInsultContainer() {
        load();
    }

    public static TIPInsultContainer getInstance() {
        if (container == null) {
            container = new TIPInsultContainer();
        }
        return container;
    }

    /**
     * Loads insults from json file.
     */
    private void load() {
        try {
            if (System.getProperty("user.dir").endsWith("practica-1-f9")) {
                insults = new ObjectMapper().readValue(
                        new File("TIPCommon/src/main/java/data/insults.json"), HashMap.class);
            } else if (System.getProperty("user.dir").endsWith("Client")) {
                insults = new ObjectMapper().readValue(
                        new File("../TIPCommon/src/main/java/data/insults.json"), HashMap.class);
            } else if (System.getProperty("user.dir").endsWith("Server")) {
                insults = new ObjectMapper().readValue(
                        new File("../TIPCommon/src/main/java/data/insults.json"), HashMap.class);
            } else {
                insults = new ObjectMapper().readValue(
                        new File("src/main/java/data/insults.json"), HashMap.class);
            }
            comebacks = new HashMap<>();
            for (String key: insults.keySet()){
                comebacks.put(insults.get(key), key);
            }
        } catch (IOException e) {
            System.out.println("IOException: Error in parsing insults.json file.");
            System.out.println("Working Directory: " + System.getProperty("user.dir"));
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns the correct comeback for a given insult.
     * @param insult String insult given.
     * @return String correct comeback matching given insult.
     * @throws TIPException If the input insult is not a valid insult.
     */
    public String getComeback(String insult) throws TIPException {
        if (!insults.containsKey(insult)) {
            throw new TIPException("Not a valid insult");
        }
        return insults.get(insult);
    }

    public boolean isInsult(String mess) {
        return insults.containsKey(mess);
    }

    public boolean isComeback(String mess) {
        return comebacks.containsKey(mess);
    }

    public HashMap<String, String> getInsults() {
        return this.insults;
    }

}
