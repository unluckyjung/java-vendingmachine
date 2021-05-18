package vendingmachine.domain;

import java.util.ArrayList;
import java.util.List;

public class Changes {
    private static final int AMOUNT_PIVOT = 0;

    private int amount;

    public Changes(final int amount) {
        validateAmount(amount);
        this.amount = amount;
    }

    private void validateAmount(final int amount) {
        if (amount < AMOUNT_PIVOT) {
            throw new IllegalArgumentException();
        }
    }

    public int getAmount() {
        return amount;
    }

    public List<CoinSet> getCoins() {
        List<CoinSet> coins = new ArrayList<>();
        for (CoinSet coin : CoinSet.descendingOrder()) {
            divideCoin(coins, coin);
        }
        return coins;
    }

    private void divideCoin(final List<CoinSet> coins, final CoinSet coin) {
        for (int i = 0; i < amount / coin.getValue(); ++i) {
            coins.add(coin);
        }
        amount %= coin.getValue();
    }
}
