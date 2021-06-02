package vendingmachine.model;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CoinBox {
    private final Map<Coin, Integer> coinCount;

    public CoinBox() {
        this(new EnumMap<>(Coin.class));
    }

    public CoinBox(Map<Coin, Integer> coinCount) {
        this.coinCount = new EnumMap<>(coinCount);
    }

    public Map<Coin, Integer> getCoinCount() {
        return coinCount;
    }

    public int getCoinCount(Coin coin) {
        return coinCount.getOrDefault(coin, 0);
    }

    public void addCoins(Coin[] coins) {
        final Map<Coin, Long> addedCoinCountMap = Arrays.stream(coins)
                                                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        for (Map.Entry<Coin, Long> entry : addedCoinCountMap.entrySet()) {
            final Coin coin = entry.getKey();
            if (coin == Coin.TEN) {
                continue;
            }
            final int count = Math.toIntExact(entry.getValue());
            coinCount.merge(coin, count, Integer::sum);
        }
    }

    public void reduceCoinCount(Coin coin, int count) {
        coinCount.computeIfPresent(coin, (savedCoin, oldCount) -> oldCount - count);
    }

    public void put(Coin coin, int count) {
        coinCount.put(coin, count);
    }
}
