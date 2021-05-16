package vendingmachine;

import java.util.List;

public class ChangesModule {

    private Money money;

    public ChangesModule(int money) {
        this(Money.from(money));
    }

    public ChangesModule(Money money) {
        this.money = money;
    }

    public int printCurrentMoney() {
        return money.getMoney();
    }

    public void withdraw(Money money) {
        this.money = this.money.subtract(money);
    }

    public List<Coin> getChanges() {
        final List<Coin> coins = money.toCoins();
        withdraw(this.money);
        return coins;
    }
}
