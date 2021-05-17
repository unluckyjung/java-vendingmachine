package vendingmachine;

public class ChangesModule {
    private Change change = new Change();

    public void insertCoin(final Coin coin) {
        change.addAmount(coin.getAmount());
    }

    public void withDraw(final int amount) {
        isValidatePrice(amount);
        change.reduce(amount);
    }

    public int getAmount(){
        return change.getAmount();
    }

    private void isValidatePrice(final int amount) {
        if(amount >= change.getAmount()){
            throw new IllegalArgumentException("현재 가지고 있는 돈보다 더 많이 인출 할 수 없습니다.");
        }
    }
}
