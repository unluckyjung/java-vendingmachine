package vendingmachine;

public class ChangesModule {

    private final Money money;

    public ChangesModule(Money money) {
        this.money = money;
    }

    public int printCurrentMoney() {
        return money.getMoney();
    }
}
