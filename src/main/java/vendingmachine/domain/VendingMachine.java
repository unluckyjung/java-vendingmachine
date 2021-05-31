package vendingmachine.domain;

import java.util.List;

public class VendingMachine {
    private final ChangeModule changeModule;

    public VendingMachine(final ChangeModule changeModule) {
        this.changeModule = changeModule;
    }

    public void insertCoin(final CoinSet coin) {
        changeModule.inputCoin(coin);
    }

    public int getAmount() {
        return changeModule.getAmount();
    }

    public List<CoinSet> withDrawToCoins() {
        return changeModule.withDrawToCoins();
    }
}
