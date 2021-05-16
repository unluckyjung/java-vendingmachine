package vendingmachine;

public enum Coin {
    FIVE_HUNDRED(500), ONE_HUNDRED(100), FIFTY(50), TEN(10);

    private final int value;

    Coin(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
