package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.RemoteCountServiceGrpc;
import ru.otus.protobuf.generated.UserMessage;
import ru.otus.protobuf.model.CounterNums;

import java.util.List;

public class RemoteCountServiceImpl extends RemoteCountServiceGrpc.RemoteCountServiceImplBase {

    private final RealCountService realCountService;

    public RemoteCountServiceImpl(RealCountService realCountService) {
        this.realCountService = realCountService;
    }

    @Override
    public void startCount(UserMessage request, StreamObserver<UserMessage> responseObserver) {

        List<CounterNums> counterNums = realCountService.startCount(request.getStartNum(), request.getFinNum(), request.getCountNum());
        counterNums.forEach(c -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Сервер отправил число " +  c.getCountNum());
            responseObserver.onNext(user2UserMessage(c));
        });

        responseObserver.onCompleted();
    }

    private UserMessage user2UserMessage(CounterNums counterNum) {
        return UserMessage.newBuilder()
                .setStartNum(counterNum.getStartNum())
                .setFinNum(counterNum.getFinNum())
                .setCountNum(counterNum.getCountNum())
                .build();
    }
}
