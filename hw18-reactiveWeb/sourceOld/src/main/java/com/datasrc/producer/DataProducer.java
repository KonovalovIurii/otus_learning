package com.datasrc.producer;

import com.datasrc.crm.service.DBServiceClient;

public interface DataProducer<T> {

    T produce(long seed);

    T saveClient(String name, String address, String phone, DBServiceClient dbServiceClient);

    T findAll(DBServiceClient dbServiceClient);
}
