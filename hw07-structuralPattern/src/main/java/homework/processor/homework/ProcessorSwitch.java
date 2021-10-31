package homework.processor.homework;

import homework.model.Message;
import homework.processor.Processor;

public class ProcessorSwitch implements Processor {
    // todo  2. Сделать процессор, который поменяет местами значения field11 и field12
    @Override
    public Message process(Message message) {
        var field11 = message.getField11();
        var field12 = message.getField12();
        return message.toBuilder().field11(field12).field12(field11).build();
    }
}
