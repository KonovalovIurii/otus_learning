package homework;


import java.util.*;

public class CustomerReverseOrder {

    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    Deque<Customer> myDeque = new ArrayDeque<Customer>();
    public void add(Customer customer) {
        myDeque.addFirst(customer);
    }

    public Customer take() {
        return  myDeque.pollFirst();
    }
}
