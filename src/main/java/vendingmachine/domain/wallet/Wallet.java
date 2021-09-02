package vendingmachine.domain.wallet;

public class Wallet {
    private static final int MINIMUM_PRICE = 0;

    private int amount;

    public Wallet(final int amount) {
        validate(amount);
        this.amount = amount;
    }

    private void validate(final int amount) {
        if (amount < MINIMUM_PRICE) {
            throw new IllegalArgumentException(String.format("금액은 0원 미만일 수 없습니다. amount:%d", amount));
        }
    }

    public void deduct(final int amount) {
        if (this.amount - amount < MINIMUM_PRICE) {
            throw new IllegalArgumentException(String.format("금액은 0원 미만일 수 없습니다. amount:%d", amount));
        }
        this.amount -= amount;
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
