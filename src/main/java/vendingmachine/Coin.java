package vendingmachine;

public class Coin {
    private final int amount;

    public int getAmount() {
        return amount;
    }

    public Coin(final int amount) {
        isValidateAmount(amount);
        this.amount = amount;
    }

    private void isValidateAmount(final int amount) {
        if (!CoinSet.containsValue(amount)) {
            throw new IllegalArgumentException("만들 수 없는 코인 금액입니다.");
        }
    }
}
