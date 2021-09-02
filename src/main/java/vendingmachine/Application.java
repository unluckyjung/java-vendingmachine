package vendingmachine;

import vendingmachine.domain.coin.CoinSet;
import vendingmachine.domain.product.Storage;
import vendingmachine.domain.wallet.Wallet;
import vendingmachine.view.Console;

public class Application {
    public static void main(String[] args) {
        final CoinSet coinSet = CoinSet.from(Console.askVendingMachineAmount());
        final Storage storage = Storage.from(Console.askProducts());
        final Wallet wallet = new Wallet(Console.askAmount());

        while (canBuy(storage, wallet)) {
            Console.printBalance(wallet.getAmount());
            wallet.deduct(storage.pop(Console.askProduct()).getPrice());
        }
        final CoinSet changes = coinSet.changes(wallet.getAmount());
        wallet.deduct(coinSet.sum());
        Console.printBalance(wallet.getAmount());
        Console.printChanges(changes.getCoins());
    }

    private static boolean canBuy(final Storage storage, final Wallet wallet) {
        return wallet.getAmount() >= storage.minimumPrice() && !storage.empty();
    }
}
