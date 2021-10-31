package homework.listener.homework;

import homework.listener.Listener;
import homework.model.Message;

import java.util.*;

public class HistoryListener implements Listener, HistoryReader {
    private HashMap<Long, Message> loglist = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {

        // Используем метод copy
        Message tmpRec = msg.copy();
        // добавим запись
        loglist.put(msg.getId(), tmpRec);
    }

    @Override
    public Optional<Message> findMessageById(long id) {

        return Optional.ofNullable(loglist.get(id));
    }
}