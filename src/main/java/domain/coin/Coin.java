package domain.coin;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum Coin {
    COIN_500(500),
    COIN_100(100),
    COIN_50(50),
    COIN_10(10);

    private final int amount;

    Coin(final int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public static List<Integer> amounts() {
        return Arrays.stream(values())
            .map(Coin::getAmount)
            .collect(Collectors.toList());
    }

    public static List<Coin> highestAmount() {
        return Arrays.stream(values())
            .sorted(Comparator.comparingInt(Coin::getAmount).reversed())
            .collect(Collectors.toList());
    }

    public static Coin from(final int amount) {
        return Arrays.stream(values())
            .filter(it -> it.amount == amount)
            .findAny()
            .orElseThrow(IllegalArgumentException::new);
    }
}
