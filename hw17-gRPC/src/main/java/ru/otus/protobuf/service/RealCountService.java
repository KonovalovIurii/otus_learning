package ru.otus.protobuf.service;

import ru.otus.protobuf.model.CounterNums;

import java.util.List;

public interface RealCountService {
  List<CounterNums> startCount(long startNum, long finNum, long countNum);
}
