package homework;


import java.util.*;
import java.util.TreeMap;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    //новая map
    private final NavigableMap<Customer, String> myMap = new TreeMap<>(Comparator.comparingLong(o -> o.getScores()));

    final class MyEntry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;

        public MyEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }

    }

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        if (myMap.firstEntry() != null) {
            Customer newEntry = myMap.firstEntry().getKey();
            final Map.Entry<Customer, String> entry = new MyEntry<Customer, String>(
                    new Customer(newEntry.getId(),
                            newEntry.getName(),
                            newEntry.getScores())
                    , myMap.firstEntry().getValue());
            return entry;
        }
        return null;

    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        if (myMap.higherEntry(customer) != null) {
            Customer newEntry = myMap.higherEntry(customer).getKey();
            final Map.Entry<Customer, String> entry = new MyEntry<Customer, String>(
                    new Customer(newEntry.getId(),
                            newEntry.getName(),
                            newEntry.getScores())
                    , myMap.higherEntry(customer).getValue());
            return entry;
        }
        return null;
    }

    public void add(Customer customer, String data) {
        this.myMap.put(customer, data);
    }
}
