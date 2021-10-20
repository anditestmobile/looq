package sg.carpark.looq.data.model;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class BooleanAdapter extends TypeAdapter<String[]> {

    private final Gson gson;

    public BooleanAdapter(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void write(JsonWriter jsonWriter, String[] guid) throws IOException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public String[] read(JsonReader jsonReader) throws IOException {
        switch (jsonReader.peek()) {
            case BOOLEAN:
                // only a String, create the object
                return new String[]{String.valueOf(jsonReader.nextBoolean())};

            case BEGIN_ARRAY:
                // full object, forward to Gson
                return gson.fromJson(jsonReader, String[].class);

            default:
                throw new RuntimeException("Expected object or string, not " + jsonReader.peek());
        }
    }
}
