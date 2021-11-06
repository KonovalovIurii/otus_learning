package homework.processor.homework;
import homework.model.Message;
import homework.processor.Processor;

public class ProcessorCheckEvenSec implements Processor {
    public static final String EVEN_SEC_EXCEPTION = "Error cous of even second";
    private final DateTimeProvider dateTimeProvider;


    public ProcessorCheckEvenSec(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    // должны генерировать ошибку каждую четную секунду
    @Override
    public Message process(Message message) {
        int currentSec = dateTimeProvider.getDate().getSecond();

        if (currentSec%2==0) {
            System.out.println(EVEN_SEC_EXCEPTION);
            throw new RuntimeException(EVEN_SEC_EXCEPTION);
        }
        return message;

    }
}
