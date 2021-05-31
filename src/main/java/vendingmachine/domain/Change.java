package vendingmachine.domain;

import java.util.Objects;

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

    public Change reduce(final int deductAmount) {
        return new Change(amount - deductAmount);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Change change = (Change) o;
        return amount == change.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
