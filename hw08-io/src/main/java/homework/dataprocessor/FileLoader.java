package homework.dataprocessor;

import homework.model.Measurement;

import javax.json.Json;
import javax.json.JsonString;
import javax.json.JsonStructure;
import java.util.ArrayList;
import java.util.List;

public class FileLoader implements Loader {

    private final String fileName;

    public FileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        try (var jsonReader = Json.createReader(FileLoader.class.getClassLoader().getResourceAsStream(fileName))) {
            JsonStructure jsonFromTheFile = jsonReader.read();
            System.out.println("\n json from the file:");
            System.out.println(jsonFromTheFile);
            final List<Measurement> result = new ArrayList<>();
            // т.к. структура известна то можем пройти по файлу
            jsonFromTheFile.asJsonArray().stream().forEach(str ->
                    {
                        JsonStructure strJsonFromTheFile = str.asJsonObject();
                        JsonString js = (JsonString) strJsonFromTheFile.getValue("/name");
                        String name = js.getString();
                        double value = Double.parseDouble(strJsonFromTheFile.getValue("/value").toString());
                        result.add(new Measurement(
                                        name,
                                        value
                                )
                        );
                    }
            );
            return result;
        }
    }
}
