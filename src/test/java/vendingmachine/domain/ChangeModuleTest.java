package vendingmachine.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

public class ChangeModuleTest {
    private ChangeModule changeModule;

    @BeforeEach
    void setUp() {
        changeModule = new ChangeModule();
    }

    @DisplayName("잔돈의 금액을 증액하면, 금액이 증가한다.")
    @ParameterizedTest
    @EnumSource(CoinSet.class)
    void increaseChange(final CoinSet coin) {
        changeModule.inputCoin(coin);
        assertThat(changeModule.getAmount()).isEqualTo(coin.getValue());
    }
}
