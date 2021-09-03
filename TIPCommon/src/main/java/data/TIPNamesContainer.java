package data;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class TIPNamesContainer {

    private List<String> names;
    private static TIPNamesContainer container;

    private TIPNamesContainer() {
        load();
    }

    public static TIPNamesContainer getInstance() {
        if (container == null) {
            container = new TIPNamesContainer();
        }
        return container;
    }

    /**
     * Loads insults from json file.
     */
    private void load() {
        try {
            HashMap<String, List<String>> map = new ObjectMapper().readValue(
                    new File("TIPCommon/src/main/java/data/names.json"), HashMap.class);
            names = map.get("names");
        } catch (IOException e) {
            System.out.println("IOException: Error in parsing names.json file.");
            System.out.println("Working Directory: " + System.getProperty("user.dir"));
            System.out.println(e.getMessage());
        }
    }

    public List<String> getNames() {
        return this.names;
    }

}
