package vendingmachine.domain;

public class ChangeModule {
    private static final int AMOUNT_PIVOT = 0;

    private Changes changes = new Changes(AMOUNT_PIVOT);

    public void inputCoin(final CoinSet coin) {
        changes = new Changes(changes.getAmount() + coin.getValue());
    }

    public int getAmount() {
        return changes.getAmount();
    }

    public void withDraw(final int amount) {
        changes = new Changes(changes.getAmount() - amount);
    }
}
