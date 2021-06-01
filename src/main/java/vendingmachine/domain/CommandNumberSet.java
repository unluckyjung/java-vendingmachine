package vendingmachine.domain;

public enum CommandNumberSet {
    DEFAULT_NUMBER(0),
    ADMIN_MODE_NUMBER(1),
    CUSTOM_MODE_NUMBER(2),
    PRODUCT_BUY_NUMBER(1),
    WITH_DRAW_NUMBER(2),
    INSERT_COIN_NUMBER(3),
    PRODUCT_ADD_NUMBER(1),
    PRODUCT_DELETE_NUMBER(2);

    private final int commandNumber;

    CommandNumberSet(final int commandNumber) {
        this.commandNumber = commandNumber;
    }

    public int getValue() {
        return commandNumber;
    }
}
