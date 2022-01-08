package ru.otus.protobuf.model;

public class CounterNums {
    private long startNum;
    private long finNum ;
    private long countNum;


    public CounterNums(long startNum, long finNum, long countNum) {
        this.startNum = startNum;
        this.finNum = finNum;
        this.countNum = countNum;
    }

    public long getCountNum() {
        return countNum;
    }
    public long getStartNum() {
        return startNum;
    }
    public long getFinNum() {
        return countNum;
    }

    public void setStartNum(long startNum) {
        this.startNum = startNum;
    }
    public void setFinNum(long finNum) {
        this.finNum = finNum;
    }
    public void setCountNum(long countNum) {
        this.countNum = countNum;
    }

}
