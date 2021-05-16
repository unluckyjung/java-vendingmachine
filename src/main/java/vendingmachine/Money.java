package vendingmachine;

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

    public int subtract(Money money) {
        final int changes = this.money - money.money;
        if (changes < 0) {
            throw new IllegalArgumentException("차감하려는 금액이 입금 금액보다 큽니다.");
        }

        return changes;
    }
}
