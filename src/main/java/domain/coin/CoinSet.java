package domain.coin;

import com.woowahan.techcourse.utils.Randoms;

import java.util.ArrayList;
import java.util.List;

public class CoinSet {
    private static final int MINIMUM_AMOUNT = 10;

    private final List<Coin> coins;

    public CoinSet(final List<Coin> coins) {
        this.coins = new ArrayList<>(coins);
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

    public List<Coin> getCoins() {
        return new ArrayList<>(coins);
    }

    @Override
    public String toString() {
        return "CoinSet{" +
            "coins=" + coins +
            '}';
    }
}
