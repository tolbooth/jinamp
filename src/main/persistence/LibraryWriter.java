package persistence;

import model.Library;
import model.Player;
import org.json.JSONObject;

public class LibraryWriter extends JsonWriter {

    public LibraryWriter(String path) {
        super(path);
    }

    @Override
    public void write(Writeable library) {
        JSONObject json = ((Library)library).toJson();
        saveToFile(json.toString());
    }
}
