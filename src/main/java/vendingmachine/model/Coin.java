package vendingmachine.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Coin {
    FIVE_HUNDRED(500), ONE_HUNDRED(100), FIFTY(50), TEN(10);

    public static final String BLANK = " ";

    private final int value;

    Coin(int value) {
        this.value = value;
    }

    public static List<Coin> descendingOrder() {
        return Arrays.stream(Coin.values())
                     .sorted((c1, c2) -> c2.value - c1.value)
                     .collect(Collectors.toList());
    }

    public static List<Coin> toCoins(String money) {
        return Arrays.stream(money.split(BLANK))
                     .map(Coin::findByValue)
                     .collect(Collectors.toList());
    }

    private static Coin findByValue(String money) {
        return Arrays.stream(Coin.values())
                     .filter(coin -> coin.value == Integer.parseInt(money))
                     .findAny()
                     .orElseThrow(() -> new IllegalArgumentException("없는 동전입니다."));
    }

    public int getValue() {
        return value;
    }
}
