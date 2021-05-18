package vendingmachineTest.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import vendingmachine.domain.ChangeModule;
import vendingmachine.domain.CoinSet;

import static org.assertj.core.api.Assertions.assertThat;

public class ChangeModuleTest {

    private ChangeModule changeModule = new ChangeModule();
    private final int defaultAmount = 500;

    @BeforeEach
    void setUp() {
        changeModule.inputCoin(CoinSet.getCoin(500));
    }

    @DisplayName("잔돈 모듈에 동전을 넣으면, 정상적으로 들어간다.")
    @ParameterizedTest
    @EnumSource(CoinSet.class)
    void inputCoinTest(CoinSet coinSet) {
        changeModule.inputCoin(coinSet);
    }

    @DisplayName("잔돈모듈의 남은 돈을 확인하면, 올바른 금액이 나온다. ")
    @Test
    void getAmountTest() {
        assertThat(changeModule.getAmount()).isEqualTo(500);
        changeModule.inputCoin(CoinSet._10);
    }

    @DisplayName("돈을 차감하면, 기계안의 돈이 줄어든다.")
    @ParameterizedTest
    @ValueSource(ints = {100, 77})
    void withDrawTest(final int amount) {
        changeModule.withDraw(amount);
        assertThat(changeModule.getAmount()).isEqualTo(defaultAmount - amount);
    }
}
