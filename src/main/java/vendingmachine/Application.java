package vendingmachine;

import vendingmachine.domain.coin.CoinSet;
import vendingmachine.domain.product.Storage;
import vendingmachine.domain.wallet.Wallet;
import vendingmachine.view.View;

public class Application {

    public static void main(String[] args) {
        final View view = new View();

        final CoinSet coinSet = CoinSet.from(view.askVendingMachineAmount());
        final Storage storage = Storage.from(view.askProducts());
        final Wallet wallet = new Wallet(view.askAmount());

        while (canBuy(storage, wallet)) {
            view.printBalance(wallet.getAmount());
            wallet.deduct(storage.pop(view.askProduct()).getPrice());
        }
        final CoinSet changes = coinSet.changes(wallet.getAmount());
        wallet.deduct(changes.sum());
        view.printBalance(wallet.getAmount());
        view.printChanges(changes.getCoins());
    }

    private static boolean canBuy(final Storage storage, final Wallet wallet) {
        return wallet.getAmount() >= storage.minimumPrice() && !storage.empty();
    }
}
