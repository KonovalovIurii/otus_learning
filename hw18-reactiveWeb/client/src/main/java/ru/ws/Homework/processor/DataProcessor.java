package ru.ws.Homework.processor;

public interface DataProcessor<T> {

    T process(T data);
}
