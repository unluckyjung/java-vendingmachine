package vendingmachine;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Coin {
    FIVE_HUNDRED(500), ONE_HUNDRED(100), FIFTY(50), TEN(10);

    private final int value;

    Coin(int value) {
        this.value = value;
    }

    public static List<Coin> descendingOrder() {
        return Arrays.stream(Coin.values())
                     .sorted((c1, c2) -> c2.value - c1.value)
                     .collect(Collectors.toList());
    }

    public int getValue() {
        return value;
    }
}
