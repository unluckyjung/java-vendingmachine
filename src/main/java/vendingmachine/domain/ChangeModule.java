package vendingmachine.domain;

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
}
