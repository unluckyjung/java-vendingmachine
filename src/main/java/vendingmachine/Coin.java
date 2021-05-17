package vendingmachine;

import java.util.Arrays;
import java.util.List;

public class Coin {
    private static List<Integer> coinSet = Arrays.asList(10, 50, 100, 500);
    private final int amount;

    public int getAmount() {
        return amount;
    }

    public Coin(final int amount) {
        isValidateAmount(amount);
        this.amount = amount;
    }

    private void isValidateAmount(final int amount) {
        if (!coinSet.contains(amount)) {
            throw new IllegalArgumentException("만들 수 없는 코인 금액입니다.");
        }
    }
}
