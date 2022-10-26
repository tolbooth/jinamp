package persistence;

import org.json.*;

/*
Interface that models an object writable to json
 */
public interface Writeable {

    // EFFECTS: Writes object to JSON
    JSONObject toJson();
}
