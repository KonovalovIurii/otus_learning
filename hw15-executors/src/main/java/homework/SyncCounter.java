package homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyncCounter {
    private static final Logger logger = LoggerFactory.getLogger(SyncCounter.class);
    //номер потока по порядку кто получает управление
    private int currentTread = 1;
    private static final int THREAD_COUNT = 2;
    private static final int MAX_COUNTING_NUMBER = 10;
    private static final int MIN_COUNTING_NUMBER = 1;

    private synchronized void counter(int order) {
        int isIncrease = 1;
        int currNum = MIN_COUNTING_NUMBER;

        while (!Thread.currentThread().isInterrupted()) {
            try {
                //spurious wakeup https://en.wikipedia.org/wiki/Spurious_wakeup
                //поэтому не if
                while (currentTread != order) {
                    this.wait();
                }

                logger.info(String.valueOf(currNum));

                    //идем на увеличение и не достигли максимума то увеличим тек. счетчик
                    if (isIncrease == 1 && currNum < MAX_COUNTING_NUMBER) {

                        currNum++;

                        //идем на уменьшение и не достигли 1 то уменьшим тек. счетчик
                    } else if (isIncrease == 0 && currNum > MIN_COUNTING_NUMBER) {
                        currNum--;
                        //идем на увеличение и достигли макс. значения
                    } else if (isIncrease == 1 && currNum == MAX_COUNTING_NUMBER) {
                        isIncrease = 0;
                        currNum--;
                        //идем на уменьшение и достигли мин. значения
                    } else if (isIncrease == 0 && currNum == MIN_COUNTING_NUMBER) {
                        Thread.currentThread().interrupt();
                    }
                // если запустился первый в приоритете поток - сбрасываем количество текущих потоков
                currentTread = order == THREAD_COUNT ? 1 : currentTread+1;
                notifyAll();
              //  logger.info("after notify");
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        SyncCounter syncCounter = new SyncCounter();
        new Thread(() -> syncCounter.counter(1)).start();
        new Thread(() -> syncCounter.counter(2)).start();
    }
}