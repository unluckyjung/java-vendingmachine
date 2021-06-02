package vendingmachine.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChangesModule {

    private Money money;
    private final CoinBox coinBox;

    public ChangesModule() {
        this(0);
    }

    public ChangesModule(int money) {
        this(Money.from(money), new CoinBox());
    }

    public ChangesModule(int money, CoinBox coinBox) {
        this(Money.from(money), coinBox);
    }

    public ChangesModule(Money money, CoinBox coinBox) {
        this.money = money;
        this.coinBox = coinBox;
    }

    public void insertCoins(Coin... coins) {
        this.money = money.addAll(coins);
    }

    public int getCurrentMoney() {
        return money.getMoney();
    }

    public void subtractCurrentMoney(Money money) {
        this.money = this.money.subtract(money);
    }

    public List<Coin> returnChanges() {
        final List<Coin> coins = toCoins();
        subtractCurrentMoney(this.money);
        return coins;
    }

    private List<Coin> toCoins() {
        List<Coin> changes = new ArrayList<>();
        int amount = money.getMoney();
        for (Coin coin : Coin.descendingOrder()) {
            final List<Coin> coins = withdrawFromCoinBox(coin, amount);
            changes.addAll(coins);
            amount -= coin.getValue() * coins.size();
        }
        return changes;
    }

    private List<Coin> withdrawFromCoinBox(Coin coin, int amount) {
        final int needCoinCount = amount / coin.getValue();
        final int savedCoinCount = coinBox.getCoinCount(coin);

        if (needCoinCount >= savedCoinCount) {
            coinBox.put(coin, 0);
            return Collections.nCopies(savedCoinCount, coin);
        }

        coinBox.reduceCoinCount(coin, savedCoinCount - needCoinCount);
        return Collections.nCopies(needCoinCount, coin);
    }
}
