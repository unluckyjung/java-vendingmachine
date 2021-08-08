package vendingmachine.domain;

public class Money {

    private static final int AMOUNT_LOW_LIMIT = 0;
    private final int amount;

    public Money(final int amount) {
        validateAmount(amount);
        this.amount = amount;
    }

    private void validateAmount(final int amount) {
        if (amount < AMOUNT_LOW_LIMIT) {
            throw new IllegalArgumentException("돈은 음수가 될 수 없습니다");
        }
    }

    public Money reduce(final int deductAmount) {
        return new Money(amount - deductAmount);
    }

    public Money increase(final int increaseAmount) {
        return new Money(amount + increaseAmount);
    }

    public boolean isCanReduce(final int amount) {
        return this.amount >= amount;
    }

    public int getAmount() {
        return amount;
    }

    public boolean canMakeCoin() {
        return amount >= CoinSet._10.getValue();
    }
}
