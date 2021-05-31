package vendingmachine.domain;

public class Change {
    public static final int AMOUNT_LOW_LIMIT = 0;
    private final int amount;

    public Change(final int amount) {
        validateAmount(amount);
        this.amount = amount;
    }

    private void validateAmount(final int amount) {
        if (amount < AMOUNT_LOW_LIMIT) {
            throw new IllegalArgumentException("잔돈은 음수가 될 수 없습니다.");
        }
    }

    public int getAmount() {
        return amount;
    }
}
