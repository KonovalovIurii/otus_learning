package ru.ws.Homework.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import com.google.gson.Gson;
import ru.otus.messagesystem.client.MessageCallback;
import ru.ws.Homework.db.crm.model.Address;
import ru.ws.Homework.db.crm.model.Client;
import ru.ws.Homework.db.crm.model.Phone;
import ru.ws.Homework.db.crm.service.DBServiceClient;
import ru.ws.Homework.dto.UserData;
import ru.ws.Homework.front.FrontendService;
import javax.json.*;
import java.io.StringReader;
import java.util.Set;


@Controller
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    private final FrontendService frontendService;
    private final DBServiceClient dbServiceClient;

    @Autowired
    private SimpMessagingTemplate template;

    public MessageController(DBServiceClient dbServiceClient, FrontendService frontendService) {
        this.dbServiceClient = dbServiceClient;
        this.frontendService = frontendService;
    }

    @MessageMapping("/message")
    public void getMessage(String clientWeb) {
        //добавляем клиента в сообщение и отправляем его
        JsonReader jsonReader = Json.createReader(new StringReader(clientWeb));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();
        var client = new Client(object.getString("name"), new Address(object.getString("address")), Set.of(new Phone(object.getString("phone"))));
        MessageCallback<UserData> dataConsumer = data -> {
            logger.info("got data:{}", data);
            template.convertAndSend("/topic/response", new Gson().toJson(data.getData()));
        };

        frontendService.getClientData(new UserData(client), dataConsumer);
    }

}
