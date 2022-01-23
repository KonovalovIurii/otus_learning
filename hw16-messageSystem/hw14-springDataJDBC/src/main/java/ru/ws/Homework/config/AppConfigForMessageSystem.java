package ru.ws.Homework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;
import ru.ws.Homework.db.crm.service.DBServiceClient;
import ru.ws.Homework.db.handlers.GetClientDataRequestHandler;
import ru.ws.Homework.front.FrontendService;
import ru.ws.Homework.front.FrontendServiceImpl;
import ru.ws.Homework.front.handlers.GetClientDataResponseHandler;

@Configuration
public class AppConfigForMessageSystem {

    @Bean(destroyMethod = "dispose")
    public MessageSystem messageSystem() {
        return new MessageSystemImpl();
    }

    @Bean
    public HandlersStore requestHandlerFrontendStore() {
        return new HandlersStoreImpl();
    }

    @Bean
    @Primary
    public MsClient frontendMsClient(MessageSystem messageSystem, HandlersStore requestHandlerFrontendStore,
                                     @Value("${frontService.name}") String frontendServiceName) {
        requestHandlerFrontendStore.addHandler(MessageType.USER_DATA, new GetClientDataResponseHandler());
        MsClientImpl frontendMsClient = new MsClientImpl(frontendServiceName, messageSystem, requestHandlerFrontendStore);
        messageSystem.addClient(frontendMsClient);
        return frontendMsClient;
    }

    @Bean
    public HandlersStore requestHandlerDatabaseStore() {
        return new HandlersStoreImpl();
    }

    @Bean
    public MsClient databaseMsClient(HandlersStore requestHandlerDatabaseStore,
                                     DBServiceClient dbServiceClient, MessageSystem messageSystem,
                                     @Value("${dbService.name}") String databaseServiceName) {
        requestHandlerDatabaseStore.addHandler(MessageType.USER_DATA, new GetClientDataRequestHandler(dbServiceClient));
        MsClientImpl databaseMsClient = new MsClientImpl(databaseServiceName, messageSystem, requestHandlerDatabaseStore);
        messageSystem.addClient(databaseMsClient);
        return databaseMsClient;
    }

    @Bean
    public FrontendService frontendService(MsClient frontendMsClient, @Value("${dbService.name}") String databaseServiceClientName) {
        return new FrontendServiceImpl(frontendMsClient, databaseServiceClientName);
    }


}
