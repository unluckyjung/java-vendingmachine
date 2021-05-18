package vendingmachine.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum CoinSet {

    _10(10), _50(50), _100(100), _500(500);

    private final int value;

    CoinSet(final int value) {
        this.value = value;
    }

    public static CoinSet getCoin(final int amount) {
        return Arrays.stream(values())
                .filter(coin -> coin.value == amount)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static List<CoinSet> descendingOrder() {
        return Arrays.stream(values()).sorted((o1, o2) -> Integer.compare(o2.value, o1.value)).collect(Collectors.toList());
    }

    public int getValue(){
        return value;
    }
}
