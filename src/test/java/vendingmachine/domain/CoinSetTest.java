package vendingmachine.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CoinSetTest {
    private static Stream<Arguments> getCoinValueTest() {
        return Stream.of(
                Arguments.of(10, CoinSet._10),
                Arguments.of(50, CoinSet._50),
                Arguments.of(100, CoinSet._100),
                Arguments.of(500, CoinSet._500)
        );
    }

    @DisplayName("CoinSet 으로부터 존재하는 코인이 나오는지 확인한다.")
    @ParameterizedTest
    @MethodSource
    void getCoinValueTest(final int coinValue, final CoinSet coin) {
        assertThat(CoinSet.getCoin(coinValue)).isEqualTo(coin);
    }

    @DisplayName("잘못된 값으로 코인을 받아오려 하면, 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {5, 7, 13})
    void inValidCoinTest(int coinValue) {
        assertThatThrownBy(() -> {
            CoinSet.getCoin(coinValue);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("존재하지 않는 코인입니다.");
    }
}
