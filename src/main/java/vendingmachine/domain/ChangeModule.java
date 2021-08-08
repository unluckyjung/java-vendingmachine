package vendingmachine.domain;

import java.util.ArrayList;
import java.util.List;

public class ChangeModule {

    private Money amount;
    private Money change;
    private Coins coins;

    public ChangeModule() {
        this(new Money(0));
    }

    public ChangeModule(final Money money) {
        this.amount = money;
        this.change = new Money(0);
    }

    public void increase(final Money money) {
        change = change.increase(money.getAmount());
        this.amount = money.increase(money.getAmount());
        coins = new Coins(new Money(this.amount.getAmount() + change.getAmount()));
    }

    public void reduce(final int amount) {
        this.amount = this.amount.reduce(amount);
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

    public int changeAmount() {
        return change.getAmount();
    }
}
