package vendingmachine.domain.coin;

import com.woowahan.techcourse.utils.Randoms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CoinSet {
    private static final int MINIMUM_AMOUNT = 10;

    private final Map<Coin, Integer> coins;

    public CoinSet(final List<Coin> coins) {
        this.coins = coins.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.reducing(0, e -> 1, Integer::sum)));
    }

    public static CoinSet from(int total) {
        if (total % MINIMUM_AMOUNT != 0) {
            throw new IllegalArgumentException();
        }
        final List<Coin> coins = new ArrayList<>();
        final List<Integer> amounts = Coin.amounts();
        while (total > 0) {
            final int amount = Randoms.pick(amounts);
            if (total - amount >= 0) {
                total -= amount;
                coins.add(Coin.from(amount));
            }
            if (total < MINIMUM_AMOUNT) {
                break;
            }
        }
        return new CoinSet(coins);
    }

    public List<Coin> changes(int total) {
        final List<Coin> changes = new ArrayList<>();
        for (final Coin coin : Coin.highestAmount()) {
            if (!coins.containsKey(coin)) {
                continue;
            }
            final int count = allowedCount(coin, total);
            total -= count * coin.getAmount();
            coins.compute(coin, (key, value) -> value - count);
            changes.addAll(Collections.nCopies(count, coin));
        }
        return changes;
    }

    private int allowedCount(final Coin coin, final int total) {
        final int need = total / coin.getAmount();
        final int quantity = coins.get(coin);
        if (need > quantity) {
            return quantity;
        }
        return need;
    }

    public int sum() {
        return getCoins().stream()
            .mapToInt(Coin::getAmount)
            .sum();
    }

    public List<Coin> getCoins() {
        return coins.entrySet().stream()
            .flatMap(it -> Collections.nCopies(it.getValue(), it.getKey()).stream())
            .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "CoinSet{" +
            "coins=" + coins +
            '}';
    }
}
