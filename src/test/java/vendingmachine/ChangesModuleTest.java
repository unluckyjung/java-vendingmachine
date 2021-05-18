package vendingmachine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChangesModuleTest {
    private final ChangesModule changesModule = new ChangesModule();

    @BeforeEach
    void setUp() {
        changesModule.insertCoin(new Coin(500));
        changesModule.insertCoin(new Coin(500));
    }

    @ParameterizedTest
    @ValueSource(ints = {100})
    void insertCoinTest(int amount) {
        changesModule.insertCoin(new Coin(amount));
        assertThat(changesModule.getAmount()).isEqualTo(1100);
    }

    @ParameterizedTest
    @ValueSource(ints = {30})
    void insertFailCoinTest(int amount) {
        assertThatThrownBy(() -> {
            new Coin(amount);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {100})
    void withDrawTest(int amount) {
        changesModule.withDraw(amount);
        assertThat(changesModule.getAmount()).isEqualTo(900);
    }

    @Test
    void getCoinsTest() {
        changesModule.withDraw(400);
        List<Coin> expectedCoins = Arrays.asList(new Coin(500), new Coin(100));
        //assertThat(changesModule.getCoins()).containsExactlyInAnyOrder(new Coin(500),  new Coin(100));

        assertThat(changesModule.getCoins()).hasSameSizeAs(expectedCoins).hasSameElementsAs(expectedCoins);
    }
}