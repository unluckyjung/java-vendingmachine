package vendingmachine.domain.coin;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class CoinSetTest {
    @Test
    void from() {
        final CoinSet coinSet = CoinSet.from(1000);
        final int sum = coinSet.sum();
        assertThat(sum).isEqualTo(1000);
    }

    @Test
    void changes_minimum_number() {
        final CoinSet coinSet = new CoinSet(Arrays.asList(Coin.COIN_500, Coin.COIN_100));
        final CoinSet changes = coinSet.changes(500);
        assertThat(changes.getCoins()).containsEntry(Coin.COIN_500, 1);
        final int sum = changes.sum();
        assertThat(sum).isEqualTo(500);
    }

    @Test
    void changes() {
        final CoinSet coinSet = new CoinSet(Arrays.asList(Coin.COIN_500, Coin.COIN_100, Coin.COIN_50, Coin.COIN_10));
        final CoinSet changes = coinSet.changes(450);
        assertThat(changes.getCoins()).containsAllEntriesOf(new HashMap<Coin, Integer>() {{
            put(Coin.COIN_100, 1);
            put(Coin.COIN_50, 1);
            put(Coin.COIN_10, 1);
        }});
        final int sum = changes.sum();
        assertThat(sum).isEqualTo(160);
    }

    @Test
    void sum() {
        final CoinSet coinSet = new CoinSet(Arrays.asList(Coin.COIN_500, Coin.COIN_100, Coin.COIN_50, Coin.COIN_10));
        final int sum = coinSet.sum();
        assertThat(sum).isEqualTo(660);
    }
}
