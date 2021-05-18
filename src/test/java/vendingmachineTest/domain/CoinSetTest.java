package vendingmachineTest.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import vendingmachine.domain.CoinSet;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CoinSetTest {
    @Test
    @DisplayName("enum으로부터 존재하는 코인이 나오는지 확인해본다.")
    void getCoinValueTest() {
        assertThat(CoinSet.getCoin(500)).isEqualTo(CoinSet._500);
        assertThatThrownBy(()->{
            CoinSet.getCoin(70);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("가지고 있는 코인 정보를 요청했을때, 내림차순으로 코인을 제공한다.")
    void descendingTest() {
        List<CoinSet> wrongOrderCoinSets = Arrays.asList(CoinSet._10, CoinSet._50, CoinSet._100, CoinSet._500);
        assertThat(CoinSet.descendingOrder()).isNotEqualTo(wrongOrderCoinSets);

        List<CoinSet> expectCoinSets = Arrays.asList(CoinSet._500, CoinSet._100, CoinSet._50, CoinSet._10);
        assertThat(CoinSet.descendingOrder()).isEqualTo(expectCoinSets);
        assertThat(CoinSet.descendingOrder()).hasSameSizeAs(expectCoinSets).hasSameElementsAs(expectCoinSets);
    }
}
