package ru.ws.Homework.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.MessageSystem;
import com.google.gson.Gson;
import ru.otus.messagesystem.message.MessageType;
import ru.ws.Homework.Message;
import ru.ws.Homework.db.crm.model.Address;
import ru.ws.Homework.db.crm.model.Client;
import ru.ws.Homework.db.crm.model.Phone;
import ru.ws.Homework.db.crm.service.DBServiceClient;
import ru.ws.Homework.db.handlers.GetUserDataRequestHandler;
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

    public MessageController(DBServiceClient dbServiceClient, FrontendService frontendService) {
        this.dbServiceClient = dbServiceClient;
        this.frontendService = frontendService;
    }

    @MessageMapping("/message")
    @SendTo("/topic/response")
    public Message getMessage(String clientWeb) {
        //добавляем клиента в сообщение и отправляем его

        JsonReader jsonReader = Json.createReader(new StringReader(clientWeb));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();
        var client = new Client(object.getString("name"), new Address(object.getString("address")), Set.of(new Phone(object.getString("phone"))));

        frontendService.getUserData(new UserData(client), data -> logger.info("got data:{}", data));
        // вернем на страницу всех клиентов
        String allClient = new Gson().toJson(dbServiceClient.findAll());
        return new Message(HtmlUtils.htmlEscape(allClient));
    }

}
