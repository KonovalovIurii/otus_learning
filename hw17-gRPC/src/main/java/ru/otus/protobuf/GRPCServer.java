package ru.otus.protobuf;


import io.grpc.Server;
import io.grpc.ServerBuilder;
import ru.otus.protobuf.service.RealCountService;
import ru.otus.protobuf.service.RealCountServiceImpl;
import ru.otus.protobuf.service.RemoteCountServiceImpl;


import java.io.IOException;

public class GRPCServer {

    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {

        RealCountService countService = new RealCountServiceImpl();
        RemoteCountServiceImpl remoteCountService = new RemoteCountServiceImpl(countService);

        Server server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(remoteCountService).build();
        server.start();
        System.out.println("server waiting for client connections...");
        server.awaitTermination();
    }
}
