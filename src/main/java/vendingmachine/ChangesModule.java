package vendingmachine;

import java.util.List;

public class ChangesModule {

    private Money money;

    public ChangesModule() {
        this(0);
    }

    public ChangesModule(int money) {
        this(Money.from(money));
    }

    public ChangesModule(Money money) {
        this.money = money;
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
        final List<Coin> coins = money.toCoins();
        subtractCurrentMoney(this.money);
        return coins;
    }
}
