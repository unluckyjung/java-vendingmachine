package vendingmachine.domain;

public class ChangeModule {

    private Money change;
    private Coins coins;

    public ChangeModule(final Money money) {
        this.change = money;
    }

    public void increase(final Money money) {
        change = change.increase(money.getAmount());
        coins = new Coins(change);
    }

    public void reduce(final int amount) {
        change = change.reduce(amount);
    }

}
