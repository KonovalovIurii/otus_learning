package ru.ws.Homework.db.sessionmanager;

public interface TransactionClient {

    <T> T doInTransaction(TransactionAction<T> action);
}