package vendingmachine.domain.coin;

import com.woowahan.techcourse.utils.Randoms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CoinSet {
    private static final int MINIMUM_AMOUNT = 10;

    private final Map<Coin, Integer> coins;

    public CoinSet(final List<Coin> coins) {
        this(
            coins.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.reducing(0, e -> 1, Integer::sum)))
        );
    }

    public CoinSet(final Map<Coin, Integer> coins) {
        this.coins = new HashMap<>(coins);
    }

    public static CoinSet from(int total) {
        if (total % MINIMUM_AMOUNT != 0) {
            throw new IllegalArgumentException();
        }
        final List<Coin> coins = new ArrayList<>();
        final List<Integer> amounts = Coin.amounts();
        while (total > 0) {
            final int amount = Randoms.pickNumberInList(amounts);
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

    public CoinSet changes(int total) {
        final Map<Coin, Integer> changes = new HashMap<>();
        for (final Coin coin : Coin.highestAmount()) {
            if (!coins.containsKey(coin)) {
                continue;
            }
            final int count = allowedCount(coin, total);
            total -= count * coin.getAmount();
            coins.compute(coin, (key, value) -> value - count);
            changes.put(coin, count);
        }
        return new CoinSet(changes);
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
        return coins.entrySet().stream()
            .mapToInt(it -> it.getKey().getAmount() * it.getValue())
            .sum();
    }

    public Map<Coin, Integer> getCoins() {
        return new HashMap<>(coins);
    }

    @Override
    public String toString() {
        return "CoinSet{" +
            "coins=" + coins +
            '}';
    }
}
