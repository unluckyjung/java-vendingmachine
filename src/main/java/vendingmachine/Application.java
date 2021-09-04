package vendingmachine;

import vendingmachine.domain.coin.CoinSet;
import vendingmachine.domain.product.Storage;
import vendingmachine.domain.wallet.Wallet;
import vendingmachine.view.Console;

public class Application {

    public static void main(String[] args) {
        final Console console = new Console();

        final CoinSet coinSet = CoinSet.from(console.askVendingMachineAmount());
        final Storage storage = Storage.from(console.askProducts());
        final Wallet wallet = new Wallet(console.askAmount());

        while (canBuy(storage, wallet)) {
            console.printBalance(wallet.getAmount());
            wallet.deduct(storage.pop(console.askProduct()).getPrice());
        }
        final CoinSet changes = coinSet.changes(wallet.getAmount());
        wallet.deduct(changes.sum());
        console.printBalance(wallet.getAmount());
        console.printChanges(changes.getCoins());
    }

    private static boolean canBuy(final Storage storage, final Wallet wallet) {
        return wallet.getAmount() >= storage.minimumPrice() && !storage.empty();
    }
}
