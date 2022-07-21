package com.datasrc.producer;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.datasrc.crm.model.Address;
import com.datasrc.crm.model.Client;
import com.datasrc.crm.model.Phone;
import com.datasrc.crm.service.DBServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
@Service("dataProducerStringBlocked")
public class DataProducerStringBlocked implements DataProducer<StringValue> {
    private static final Logger log = LoggerFactory.getLogger(DataProducerStringBlocked.class);

    @Override
    public StringValue produce(long seed) {
        log.info("produce using seed:{}", seed);
        return new StringValue(String.format("someDataStr:%s", seed));
    }

    public StringValue saveClient (String name, String address, String phone, DBServiceClient dbServiceClient){
        Client client = new Client(name,  new Address(address), Set.of(new Phone(phone)));
        dbServiceClient.saveClient(client);
        return new StringValue(String.format("someDataStr:%s", true));
    }

    public StringValue findAll(DBServiceClient dbServiceClient){
        List<Client> clients = dbServiceClient.findAll();
        var gson = new Gson();
        String json = gson.toJson(clients);
        return new StringValue(json);
    }
    private void sleep() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(10));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
