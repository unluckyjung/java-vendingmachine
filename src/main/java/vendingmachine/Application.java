package vendingmachine;

import vendingmachine.domain.coin.Coin;
import vendingmachine.domain.coin.CoinSet;
import vendingmachine.domain.product.Storage;
import vendingmachine.domain.wallet.Wallet;
import vendingmachine.view.Console;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) {
        final CoinSet coinSet = CoinSet.from(Console.askVendingMachineAmount());
        final Storage storage = Storage.from(Console.askProducts());
        final Wallet wallet = new Wallet(Console.askAmount());

        while (canBuy(storage, wallet)) {
            Console.printBalance(wallet.getAmount());
            wallet.deduct(storage.pop(Console.askProduct()).getPrice());
        }
        final List<Coin> changes = coinSet.changes(wallet.getAmount());
        final int sum = changes.stream()
            .mapToInt(it -> it.getAmount())
            .sum();
        wallet.deduct(sum);
        Console.printBalance(wallet.getAmount());
        Console.printChanges(changes.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.reducing(0, e -> 1, Integer::sum))));
    }

    private static boolean canBuy(final Storage storage, final Wallet wallet) {
        return wallet.getAmount() >= storage.minimumPrice() && !storage.empty();
    }
}
