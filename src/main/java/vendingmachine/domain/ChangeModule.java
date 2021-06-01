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

    public List<CoinSet> withDrawToCoins(final Coins hasCoins) {
        List<CoinSet> coins = new ArrayList<>();
        for (CoinSet coin : hasCoins.toListDescendingOrder()) {
            getCoinsByHasCoins(coins, coin);
        }
        getCoinsBy10ValueCoin(coins);
        return coins;
    }

    private void getCoinsBy10ValueCoin(final List<CoinSet> coins) {
        for (int i = 0; i < getAmount() / CoinSet._10.getValue(); ++i) {
            coins.add(CoinSet._10);
        }
        change = new Change(change.getAmount() % CoinSet._10.getValue());
    }

    private void getCoinsByHasCoins(final List<CoinSet> coins, final CoinSet coin) {
        if (coin.getValue() > change.getAmount()) {
            return;
        }
        change = new Change(change.getAmount() - coin.getValue());
        coins.add(coin);
    }

    private void divideAmountByCoin(final List<CoinSet> coins, final CoinSet coin) {
        for (int i = 0; i < getAmount() / coin.getValue(); ++i) {
            coins.add(coin);
        }
        change = new Change(change.getAmount() % coin.getValue());
    }
}
