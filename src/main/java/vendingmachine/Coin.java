package vendingmachine;

import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Coin coin = (Coin) o;
        return amount == coin.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
