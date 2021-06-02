package vendingmachine.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

public class ChangesModuleTest {

    private static Stream<Arguments> returnChangesSource() {
        return Stream.of(
                Arguments.of(10, Arrays.asList(Coin.TEN)),
                Arguments.of(50, Arrays.asList(Coin.FIFTY)),
                Arguments.of(100, Arrays.asList(Coin.ONE_HUNDRED)),
                Arguments.of(500, Arrays.asList(Coin.FIVE_HUNDRED)),
                Arguments.of(510, Arrays.asList(Coin.FIVE_HUNDRED, Coin.TEN)),
                Arguments.of(600, Arrays.asList(Coin.FIVE_HUNDRED, Coin.ONE_HUNDRED)),
                Arguments.of(1, Arrays.asList()),
                Arguments.of(9, Arrays.asList()),
                Arguments.of(11, Arrays.asList(Coin.TEN)),
                Arguments.of(511, Arrays.asList(Coin.FIVE_HUNDRED, Coin.TEN))
        );
    }

    @ParameterizedTest(name = "잔돈 반환 성공 및 남아있는 돈 확인 - 수량 무제한")
    @MethodSource(value = "returnChangesSource")
    void returnChangesTest(int money, List<Coin> changes) {

        // given
        final CoinBox coinBox = new CoinBox();
        for (Coin coin : Coin.values()) {
            coinBox.put(coin, Integer.MAX_VALUE);
        }

        final ChangesModule changesModule = new ChangesModule(money, coinBox);

        // when
        final List<Coin> coins = changesModule.returnChanges();

        // then
        final int balance = changesModule.getCurrentMoney();
        assertThat(balance).isEqualTo(0);
        assertThat(coins).usingRecursiveComparison().isEqualTo(changes);
    }

    private static Stream<Arguments> returnLimitedChangesSource() {
        return Stream.of(
                Arguments.of(200, Arrays.asList(Coin.ONE_HUNDRED, Coin.FIFTY, Coin.TEN, Coin.TEN, Coin.TEN, Coin.TEN, Coin.TEN)),
                Arguments.of(570, Arrays.asList(Coin.FIVE_HUNDRED, Coin.FIFTY, Coin.TEN, Coin.TEN)),
                Arguments.of(700, Arrays.asList(Coin.FIVE_HUNDRED, Coin.ONE_HUNDRED, Coin.FIFTY, Coin.TEN, Coin.TEN, Coin.TEN, Coin.TEN, Coin.TEN))
        );
    }

    @ParameterizedTest(name = "잔돈 반환 성공 및 남아있는 돈 확인 - 수량 제한")
    @MethodSource(value = "returnLimitedChangesSource")
    void returnLimitedChangesTest(int money, List<Coin> changes) {

        // given
        final CoinBox coinBox = new CoinBox();
        coinBox.put(Coin.TEN, Integer.MAX_VALUE);
        coinBox.put(Coin.FIFTY, 1);
        coinBox.put(Coin.ONE_HUNDRED, 1);
        coinBox.put(Coin.FIVE_HUNDRED, 1);


        final ChangesModule changesModule = new ChangesModule(money, coinBox);

        // when
        final List<Coin> coins = changesModule.returnChanges();

        // then
        final int balance = changesModule.getCurrentMoney();
        assertThat(balance).isEqualTo(0);
        assertThat(coins).usingRecursiveComparison().isEqualTo(changes);
    }
}
