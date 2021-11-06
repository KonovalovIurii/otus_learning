package homework;

public class runBankomat {
    public static void main(String[] args) throws Exception {
        cashPoint bankomat = new cashPoint();
        System.out.println("Доступный балланс: " + bankomat.getRest());
        //пополняем банкомат
        bankomat.putMoney(1000, 10);
        bankomat.putMoney(5000, 4);
        bankomat.putMoney(2000, 1);
        bankomat.putMoney(100, 10);
        System.out.println("Доступный балланс: " + bankomat.getRest());
        //пытаемся пополнить еще
        bankomat.putMoney(7000, 40);
        System.out.println("Доступный балланс: " + bankomat.getRest());
        //не кратно кол-ву
        bankomat.withdrawalMoney(10);
        bankomat.withdrawalMoney(50);
        //снимаем
        bankomat.withdrawalMoney(500);
        System.out.println("Доступный балланс: " + bankomat.getRest());
        //не кратно кол-ву
        bankomat.withdrawalMoney(20700);
        System.out.println("Доступный балланс: " + bankomat.getRest());
        //снимем все
        bankomat.withdrawalMoney(32500);
        System.out.println("Доступный балланс: " + bankomat.getRest());
        //снимем еще
        bankomat.withdrawalMoney(2500);
    }
}
