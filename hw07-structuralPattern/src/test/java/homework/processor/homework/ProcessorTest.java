package homework.processor.homework;

import org.junit.jupiter.api.Test;
import homework.handler.ComplexProcessor;
import homework.model.Message;
import homework.model.ObjectForMessage;
import homework.processor.Processor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProcessorTest {

    @Test
    void processorTest() {

        var id = 100L;
        var data = "23";
        var field13 = new ObjectForMessage();
        var field13Data = new ArrayList<String>();
        field13Data.add(data);
        field13.setData(field13Data);

        // выставляем ЧЁТНУЮ секунду
        var processorEvenSecError = new ProcessorCheckEvenSec(() -> LocalDateTime.of(LocalDate.now(), LocalTime.of(10,10,10)));

        List<Processor> processors = List.of(processorEvenSecError);

        var complexProcessor = new ComplexProcessor(processors,
                // вызовем ошибку, если она случилась
                ex -> {
                    throw new RuntimeException(ex.getMessage());
                });

        var message = new Message.Builder(id)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .field13(field13)
                .build();

        // проверяем что была ошибка
        Throwable thrown = assertThrows(RuntimeException.class, () -> {
            complexProcessor.handle(message);
        });
        assertThat(thrown.getMessage()).isEqualTo(ProcessorCheckEvenSec.EVEN_SEC_EXCEPTION);

        // выставлыяем НЕ чётную секунду
        var processorNotEvenSecError = new ProcessorCheckEvenSec(() -> LocalDateTime.of(LocalDate.now(), LocalTime.of(10,10,11)));

        processors = List.of(processorNotEvenSecError);
        var complexProcessorNoErr = new ComplexProcessor(processors,
                ex -> {
                    throw new RuntimeException(ex.getMessage());
                });
        // проверяем что ошибки НЕ было
        assertDoesNotThrow(() -> {
            complexProcessorNoErr.handle(message);
        });

    }
}