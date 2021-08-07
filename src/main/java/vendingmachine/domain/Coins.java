package vendingmachine.domain;

import com.woowahan.techcourse.utils.Randoms;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Coins {

    private final List<CoinSet> coins;

    public Coins(final Money money) {
        coins = getRandomCoins(money);
    }

    private List<CoinSet> getRandomCoins(Money money) {
        List<CoinSet> coins = new ArrayList<>();
        while (money.isZero()) {
            int coinValue = Randoms.pick(CoinSet.getCoinValues());
            if (money.isCanReduce(coinValue)) {
                coins.add(CoinSet.getCoin(coinValue));
                money = money.reduce(coinValue);
            }
        }
        return coins.stream()
            .sorted((o1, o2) -> Integer.compare(o2.getValue(), o1.getValue()))
            .collect(Collectors.toList());
    }

    public List<CoinSet> getCoins() {
        return coins;
    }
}
