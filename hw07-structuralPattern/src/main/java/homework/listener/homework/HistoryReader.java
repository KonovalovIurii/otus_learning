package homework.listener.homework;

import homework.model.Message;
import java.util.Optional;

public interface HistoryReader {

    Optional<Message> findMessageById(long id);
}
