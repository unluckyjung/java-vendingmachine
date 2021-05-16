package vendingmachine;

public class ChangesModule {

    private final Money money;

    public ChangesModule(Money money) {
        this.money = money;
    }

    public int printCurrentMoney() {
        return money.getMoney();
    }

    public ChangesModule withdraw(Money money) {
        final int changes = this.money.subtract(money);
        return new ChangesModule(Money.from(changes));
    }
}
