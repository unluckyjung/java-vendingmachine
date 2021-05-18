package vendingmachineTest.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import vendingmachine.domain.Changes;
import vendingmachine.domain.CoinSet;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ChangesTest {
    @DisplayName("음수로 잔돈을 만드려고 하면 예외가 발생한다.")
    @Test
    void moneyTest() {
        assertThatThrownBy(() -> {
            Changes changes = new Changes(-1);
        }).isInstanceOf(IllegalArgumentException.class);
    }


    @DisplayName("잔돈을 만들면, 만든만큼 돈이 존재한다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 50, 77, 100})
    void getMoneyTest(final int amount) {
        Changes changes = new Changes(amount);
        assertThat(changes.getAmount()).isEqualTo(amount);
    }

    @DisplayName("잔돈을 동전으로 변환하면, 가장 적은 수의 동전으로, 가장 큰 동전순으로 반환한다.")
    @Test
    void getCoinsTest(){
        Changes changes = new Changes(659);
        List<CoinSet> WrongOrderExpectCoins = Arrays.asList(CoinSet._500, CoinSet._50, CoinSet._100);
        assertThat(changes.getCoins()).usingRecursiveComparison().isNotEqualTo(WrongOrderExpectCoins);

        changes = new Changes(659);
        List<CoinSet> expectCoins = Arrays.asList(CoinSet._500, CoinSet._100, CoinSet._50);
        assertThat(changes.getCoins()).usingRecursiveComparison().isEqualTo(expectCoins);
    }
}
