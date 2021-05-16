package vendingmachine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Money {
    private final int money;

    private Money(int money) {
        this.money = money;
    }

    public static Money from(int money) {
        if (money < 0) {
            throw new IllegalArgumentException("돈은 음수가 될 수 없습니다.");
        }
        return new Money(money);
    }

    public int getMoney() {
        return money;
    }

    public Money subtract(Money money) {
        final int changes = this.money - money.money;
        if (changes < 0) {
            throw new IllegalArgumentException("차감하려는 금액이 입금 금액보다 큽니다.");
        }

        return new Money(changes);
    }

    public List<Coin> toCoins() {
        List<Coin> coins = new ArrayList<>();
        int amount = money;
        for (Coin coin : Coin.descendingOrder()) {
            final int count = amount / coin.getValue();
            coins.addAll(Collections.nCopies(count, coin));
            amount -= coin.getValue() * count;
        }
        return coins;
    }
}
