package ru.ws.Homework.sessionmanager;

import java.util.function.Supplier;

public interface TransactionAction<T> extends Supplier<T> {
}
