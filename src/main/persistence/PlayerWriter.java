package persistence;

import model.Player;
import org.json.JSONObject;

// Represents a player writer. Inherits most functionality from JsonWriter
public class PlayerWriter extends JsonWriter {

    public PlayerWriter(String path) {
        super(path);
    }

    @Override
    public void write(Writeable player) {
        JSONObject json = ((Player)player).toJson();
        saveToFile(json.toString());
    }
}
