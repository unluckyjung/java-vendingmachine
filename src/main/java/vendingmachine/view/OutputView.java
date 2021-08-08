package vendingmachine.view;

import java.util.Map;

public class OutputView {

    public static void printChangeAmount(final int changeAmount) {
        System.out.println("\n투입된 금액: " + changeAmount + "원");
    }

    public static void printChangeCoins(final Map<Integer, Integer> coinsMap) {
        System.out.println("\n잔돈");
        coinsMap.forEach((coinValue, coinCount) -> {
            System.out.printf("%d원 - %d개%n", coinValue, coinCount);
        });
    }
}
