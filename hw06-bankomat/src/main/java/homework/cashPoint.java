package homework;

import java.util.*;

public class cashPoint {
    // массив кассет банкомата, от большего к меньшему
    private NavigableMap<Integer, Integer> cartridges = new TreeMap<>(Comparator.comparingInt(o -> o * -1));

    // массив доступных номиналов
    private final Integer[] nominalBanknotes = new Integer[]{5000, 2000, 1000, 500, 200, 100, 50, 10};

    //метод добавления купюр определенного номинала
    public void putMoney(Integer nominal, Integer count) {
        //проверка: бывают ли такие купюры
        if (Arrays.stream(nominalBanknotes).anyMatch(n -> n == nominal.intValue())) {
            //найдем нужный номинал (если он есть) и обновим кол-во
            Integer nomCount = cartridges.get(nominal);
            if (nomCount != null) {
                nomCount += count;
            } else {
                cartridges.put(nominal, count);
            }
        } else {
            System.out.println("Неверный номинал, проверьте внесенные купюры");
        }
    }


    //метод снятия запрошенной суммы минимальным кол-вом купюр
    public void withdrawalMoney(Integer amountMoney) {
        //проверить баланс
        if (!(getRest() != 0 && getRest() >= amountMoney)) {
            System.out.println("Запрошенная сумма превышает доступный остаток средств");
            return;
        }
        // проверить кратность запрашиваемой суммы (кратна доступной минимальной купюре)
        if (!(getMinNom() != 0 && (amountMoney % getMinNom()) == 0)) {
            System.out.println("Запрошенная сумма не кратна доступному номиналу");
            return;
        }

        // массив денег к выдаче
        NavigableMap<Integer, Integer> nominalWithdrawal = new TreeMap<>(Comparator.comparingInt(o -> o * -1));
        Integer nominal;
        Integer count;
        Integer withdrawnCount;
        // пробуем выдать запрошенную сумму минимальным кол-вом купюр
        for (Map.Entry<Integer, Integer> cartridge : cartridges.entrySet()) {
            nominal = cartridge.getKey();
            count = cartridge.getValue();

            // количество купюр номинала к снятию
            withdrawnCount = Math.min(((int) Math.floor(amountMoney / nominal)), count);
            // уменьшим запрошенную сумму на сумма к снятию в данном номинале
            amountMoney = amountMoney - (withdrawnCount * nominal);
            // заполним массив денег к снятию
            nominalWithdrawal.put(nominal, withdrawnCount);
        }
        // если не смогли снять запрошенную сумму полностью - выведем ошибку
        if (amountMoney != 0) {
            System.out.println("Запрошенная сумма не кратна доступному номиналу, попробуйте изменить сумму");
            return;
        }
        // если до этого шага дошли - значит запрошенная сумма может быть корректно снята - снимем деньги
        cashing(nominalWithdrawal);
    }

    private void cashing(NavigableMap<Integer, Integer> nominalWithdrawal) {
        Integer denomination;
        Integer count;
        Integer quantityCartrige;
        //согласно расчету уменьшим кол-во в кассетах согласно набору банкнот для снятия
        for (Map.Entry<Integer, Integer> Cell : nominalWithdrawal.entrySet()) {
            denomination = Cell.getKey();
            count = Cell.getValue();
            quantityCartrige = cartridges.get(denomination);
            // если количество списалось полностью - удалим запись
            if (quantityCartrige <= count) {
                cartridges.remove(denomination);
            } else {
                quantityCartrige = quantityCartrige - count;
                cartridges.put(denomination, quantityCartrige);
            }
            // "Выдача банкнот"
            System.out.println("Кол-во купюр: " + count + " номиналом: " + denomination + " ;");
        }
    }

    // метод возвращает минимальный доступный к выдаче номинал
    private Integer getMinNom() {
        Integer res = 0;
        if (!cartridges.isEmpty()) {
            res = cartridges.lastKey();
        }
        return res;
    }


    //метод возвращает доступный остаток
    Integer getRest() {
        return cartridges.entrySet()
                .stream()
                .map(n -> n.getKey() * n.getValue())
                .reduce(0, Integer::sum);
    }
}
