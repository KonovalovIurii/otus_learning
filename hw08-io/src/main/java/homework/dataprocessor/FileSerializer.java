package homework.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.util.Map;
import java.io.File;
import java.io.IOException;

public class FileSerializer implements Serializer {
    private final String fileName;
    private final ObjectMapper mapperJson = new ObjectMapper();

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл
        try {
            var file = new File(fileName);
            Files.writeString(file.toPath(), mapperJson.writeValueAsString(data));
        } catch (IOException e) {
            e.printStackTrace();
            // вызываем пользовательскую ошибку
            throw new FileProcessException(e);
        }
    }
}
