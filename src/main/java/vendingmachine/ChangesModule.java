package vendingmachine;

public class ChangesModule {
    private Change change = new Change();

    public void insertCoin(final Coin coin) {
        change.addAmount(coin.getAmount());
    }
}
