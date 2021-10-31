package homework.model;

import java.io.Serializable;
import java.util.List;

public class ObjectForMessage implements Serializable {
    private List<String> data;

    public ObjectForMessage() {
    };

    //иммутабельный конструктор
    public ObjectForMessage(ObjectForMessage newData) {
        this.data = newData.getData();
    };

    public List<String> getData() {
        return List.copyOf(data);
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
