package ru.otus.protobuf.service;

import ru.otus.protobuf.model.CounterNums;
import java.util.ArrayList;
import java.util.List;

public class RealCountServiceImpl implements RealCountService {
    private final List<CounterNums> counters;

    public RealCountServiceImpl() {
        counters = new ArrayList<>();

    }

    @Override
    public List<CounterNums> startCount(long startNum, long finNum, long countNum) {
        for (int i = (int) startNum; i<= finNum; i++) {
            counters.add(new CounterNums(startNum, finNum, i));
        }
        return  counters;
    }


}
