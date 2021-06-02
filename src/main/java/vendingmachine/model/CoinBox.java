package vendingmachine.model;

import java.util.EnumMap;
import java.util.Map;

public class CoinBox {
    private final Map<Coin, Integer> coinCount;

    public CoinBox() {
        this(new EnumMap<>(Coin.class));
    }

    public CoinBox(Map<Coin, Integer> coinCount) {
        this.coinCount = new EnumMap<>(coinCount);
    }

    public int getCoinCount(Coin coin) {
        return coinCount.getOrDefault(coin, 0);
    }

    public void addOneTypeCoins(Coin coin, int count) {
        coinCount.merge(coin, count, Integer::sum);
    }

    public void reduceCoinCount(Coin coin, int count) {
        coinCount.computeIfPresent(coin, (savedCoin, oldCount) -> oldCount - count);
    }

    public void put(Coin coin, int count) {
        coinCount.put(coin, count);
    }
}
