package vendingmachine.domain;

import java.util.ArrayList;
import java.util.List;

public class ChangeModule {

    private Money change;
    private Coins coins;

    public ChangeModule() {
        this(new Money(0));
    }

    public ChangeModule(final Money money) {
        this.change = money;
    }

    public void increase(final Money money) {
        change = change.increase(money.getAmount());
        coins = new Coins(new Money(change.getAmount()));
    }

    public void reduce(final int amount) {
        change = change.reduce(amount);
    }

    public List<CoinSet> withDrawToCoin() {
        List<CoinSet> coinList = new ArrayList<>();
        for (CoinSet coin : coins.getCoins()) {
            if (change.isCanReduce(coin.getValue())) {
                change = change.reduce(coin.getValue());
                coinList.add(coin);
            }
        }
        return coinList;
    }

    public int amount() {
        return change.getAmount();
    }
}
