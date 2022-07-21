package com.datasrc;

import com.datasrc.crm.service.DBServiceClient;
import com.datasrc.producer.DataProducer;
import com.datasrc.producer.StringValue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class SourceDataController {
    private static final Logger log = LoggerFactory.getLogger(SourceDataController.class);

    private final DataProducer<StringValue> dataProducerStringBlocked;
    private final DBServiceClient dbServiceClient;
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public SourceDataController(@Qualifier("dataProducerStringBlocked") DataProducer<StringValue> dataProducerStringBlocked,
                                DBServiceClient dbServiceClient
    ) {
        this.dataProducerStringBlocked = dataProducerStringBlocked;
        this.dbServiceClient = dbServiceClient;
    }


    @GetMapping(value = "/data-mono/clients/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<StringValue> dataMonoClients() {
        log.info("Method request for string data done");

        var future = CompletableFuture
                .supplyAsync(() -> dataProducerStringBlocked.findAll(dbServiceClient), executor);
        return Mono.fromFuture(future);
    }

    @PostMapping(value = "/data-mono/clients/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<StringValue> dataMono(@RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "address", required = false) String address,
                                      @RequestParam(value = "phone", required = false) String phone
                                      ) {
        log.info("request for string data-mono, name:{}, address:{} , phone{}", name,address,phone);
        log.info("Method request for string data done");

        var future = CompletableFuture
                .supplyAsync(() -> dataProducerStringBlocked.saveClient(name,address,phone,dbServiceClient), executor);
        return Mono.fromFuture(future);
    }
}
