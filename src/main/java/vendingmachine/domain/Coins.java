package vendingmachine.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Coins {
    private final List<CoinSet> coins;

    public Coins(final List<CoinSet> coins) {
        this.coins = new ArrayList<>(coins);
    }

    public List<CoinSet> toListDescendingOrder() {
        return coins.stream()
                .sorted((o1, o2) -> Integer.compare(o2.getValue(), o1.getValue()))
                .collect(Collectors.toList());
    }

    public void add(final CoinSet coin){
        coins.add(coin);
    }
}
