package vendingmachine.domain;

import java.util.Arrays;

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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코인입니다."));
    }
}
