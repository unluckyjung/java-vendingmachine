package vendingmachine.domain;

import java.util.ArrayList;
import java.util.List;

public class ChangeModule {
    private static final int DEFAULT_AMOUNT = 0;
    private Change change;

    public ChangeModule(final Change change) {
        this.change = new Change(change.getAmount());
    }

    public ChangeModule() {
        this(new Change(DEFAULT_AMOUNT));
    }

    public int getAmount() {
        return change.getAmount();
    }

    public void inputCoin(final CoinSet coin) {
        change = new Change(change.getAmount() + coin.getValue());
    }

    public void reduce(final int amount) {
        change = new Change(change.getAmount() - amount);
    }

    public List<CoinSet> withDrawToCoins() {
        List<CoinSet> coins = new ArrayList<>();
        for (CoinSet coin : CoinSet.descendingOrder()) {
            divideAmountByCoin(coins, coin);
        }
        return coins;
    }

    private void divideAmountByCoin(final List<CoinSet> coins, final CoinSet coin) {
        for (int i = 0; i < getAmount() / coin.getValue(); ++i) {
            coins.add(coin);
        }
        change = new Change(change.getAmount() % coin.getValue());
    }
}
