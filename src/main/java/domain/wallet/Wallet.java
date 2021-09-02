package domain.wallet;

public class Wallet {
    private static final int MINIMUM_PRICE = 0;

    private int amount;

    public Wallet(final int amount) {
        validate(amount);
        this.amount = amount;
    }

    private void validate(final int amount) {
        if (amount < MINIMUM_PRICE) {
            throw new IllegalArgumentException(String.format("0원 이상 입력해야 합니다. amount:%d", amount));
        }
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Wallet{" +
            "amount=" + amount +
            '}';
    }
}
