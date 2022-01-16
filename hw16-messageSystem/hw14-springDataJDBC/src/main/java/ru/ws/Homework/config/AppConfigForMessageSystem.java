package ru.ws.Homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;
import ru.ws.Homework.db.crm.repository.ClientRepository;
import ru.ws.Homework.db.crm.service.DBServiceClient;
import ru.ws.Homework.db.crm.service.DbServiceClientImpl;
import ru.ws.Homework.db.handlers.GetUserDataRequestHandler;
import ru.ws.Homework.db.sessionmanager.TransactionClient;
import ru.ws.Homework.front.FrontendService;
import ru.ws.Homework.front.FrontendServiceImpl;
import ru.ws.Homework.front.handlers.GetUserDataResponseHandler;

@Configuration
public class AppConfigForMessageSystem {
    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    @Bean
    public MessageSystem messageSystem() {
        return new MessageSystemImpl();
    }

    @Bean
    public DBServiceClient dbServiceClient(TransactionClient transactionClient, ClientRepository clientRepository) {
        return new DbServiceClientImpl(transactionClient, clientRepository);
    }

    @Bean
    public MsClient frontendMsClient(MessageSystem messageSystem) {
        HandlersStore requestHandlerFrontendStore = new HandlersStoreImpl();
        requestHandlerFrontendStore.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler());
        MsClientImpl frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem, requestHandlerFrontendStore);
        messageSystem.addClient(frontendMsClient);
        return frontendMsClient;
    }

    @Bean
    public String dbServiceName() {
        return DATABASE_SERVICE_CLIENT_NAME;
    }

    @Bean
    public FrontendService frontendService(MsClient frontendMsClient, DBServiceClient dbServiceClient, MessageSystem messageSystem,
                                           String dbServiceName) {

        HandlersStore requestHandlerDatabaseStore = new HandlersStoreImpl();
        requestHandlerDatabaseStore.addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(dbServiceClient));
        MsClientImpl databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem, requestHandlerDatabaseStore);
        messageSystem.addClient(databaseMsClient);

        return new FrontendServiceImpl(frontendMsClient, dbServiceName);
    }


}
