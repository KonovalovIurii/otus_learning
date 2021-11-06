package homework;

import homework.handler.ComplexProcessor;
import homework.listener.homework.HistoryListener;
import homework.model.Message;
import homework.processor.Processor;
import homework.processor.homework.ProcessorCheckEvenSec;
import homework.processor.homework.ProcessorSwitch;
import homework.listener.ListenerPrinterConsole;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
             Секунда должна определяьться во время выполнения.
             Тест - важная часть задания
             Обязательно посмотрите пример к паттерну Мементо!
       4. Сделать Listener для ведения истории (подумайте, как сделать, чтобы сообщения не портились)
          Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
          Для него уже есть тест, убедитесь, что тест проходит
     */

    public static void main(String[] args) {
        /*
           по аналогии с Demo.class
           из элеменов "to do" создать new ComplexProcessor и обработать сообщение
         */
        List<Processor> processors = List.of(new ProcessorSwitch(), new ProcessorCheckEvenSec(() -> LocalDateTime.now()));

        // лист ошибок четных секунд
        final ArrayList<String> evenErrList = new ArrayList<>();
        var complexProcessor = new ComplexProcessor(processors,
                // перехват ошибки
                ex -> {
                    System.out.println(ex);
                    // получаем сообщение об ошибке
                    var mes = ex.getMessage();
                    // если ошибка чётной секунды
                    if (mes.equals(ProcessorCheckEvenSec.EVEN_SEC_EXCEPTION)) {
                        // сохраняем ошибку в список
                        evenErrList.add(mes);
                    }
                });
        var listenerPrinter = new ListenerPrinterConsole();
        complexProcessor.addListener(listenerPrinter);

        var historyListener = new HistoryListener();
        complexProcessor.addListener(historyListener);
        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        var messageFromHistory = historyListener.findMessageById(1L);
        System.out.println(messageFromHistory);

        complexProcessor.removeListener(listenerPrinter);
    }
}
