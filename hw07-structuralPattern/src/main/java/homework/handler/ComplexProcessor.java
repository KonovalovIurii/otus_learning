package homework.handler;

import homework.model.Message;
import homework.listener.Listener;
import homework.processor.Processor;
import homework.processor.homework.DateTimeProvider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class ComplexProcessor implements Handler {

    private final List<Listener> listeners = new ArrayList<>();
    private final List<Processor> processors;
    private final Consumer<Exception> errorHandler;
    private final HashMap<Object, DateTimeProvider> logRunAttemps = new HashMap<>();

    public ComplexProcessor(List<Processor> processors, Consumer<Exception> errorHandler) {
        this.processors = processors;
        this.errorHandler = errorHandler;
    }

    @Override
    public Message handle(Message msg) {
        Message newMsg = msg;
        for (Processor pros : processors) {
            //логирование
            logRunAttemps.put(pros.getClass(), LocalDateTime::now);
            System.out.println("logRunAttemps" + logRunAttemps.get(pros.getClass()));
            try {
                newMsg = pros.process(newMsg);
            } catch (Exception ex) {
                errorHandler.accept(ex);
            }
        }
        notify(newMsg);
        return newMsg;
    }

    @Override
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    private void notify(Message msg) {
        listeners.forEach(listener -> {
            try {
                listener.onUpdated(msg);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public Consumer<Exception> getErrorHandler() {
        return this.errorHandler;
    }

    // получение времени запуска по классу процессора
    public DateTimeProvider getLog(Object prosClass) {
        return logRunAttemps.get(prosClass);
    }
}
