package vendingmachine;

import java.util.ArrayList;
import java.util.List;

public class Change {
    private int amount;

    public Change(final int amount) {
        this.amount = amount;
    }

    public void addAmount(final int amount) {
        this.amount += amount;
    }

    public void reduce(final int price) {
        this.amount -= price;
    }

    public int getAmount() {
        return amount;
    }

    public List<Coin> getCoins() {
        List<Coin> coins = new ArrayList<>();

        for (int coinValue : CoinSet.coins) {
            int coinCount = amount / coinValue;
            for (int i = 0; i < coinCount; ++i) {
                coins.add(new Coin(coinValue));
            }
            amount %= coinValue;
        }
        return coins;
    }
}
