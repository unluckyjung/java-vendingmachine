package domain.coin;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CoinSetTest {
    @Test
    void from() {
        final CoinSet coinSet = CoinSet.from(1000);
        final int sum = coinSet.getCoins().stream()
            .mapToInt(it -> it.getAmount())
            .sum();
        assertThat(sum).isEqualTo(1000);
    }
}
