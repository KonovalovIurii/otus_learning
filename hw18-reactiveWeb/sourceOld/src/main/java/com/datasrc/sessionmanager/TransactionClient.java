package com.datasrc.sessionmanager;

public interface TransactionClient {

    <T> T doInTransaction(TransactionAction<T> action);
}