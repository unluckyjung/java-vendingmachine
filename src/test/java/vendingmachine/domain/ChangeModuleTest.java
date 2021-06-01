package vendingmachine.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @DisplayName("1원단위가 남아 있다면 10원 동전 2개를 돌려준다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9})
    void withDraw1(final int value) {
        Change change = new Change(value);
        ChangeModule changeModule = new ChangeModule(change);
        List<CoinSet> expectedCoins = new ArrayList<>();

        List<CoinSet> returnedCoins = changeModule.withDrawToCoins();
        assertThat(expectedCoins).usingRecursiveComparison().isEqualTo(returnedCoins);
    }

    @DisplayName("10원이 남아 있다면 10원 동전 1개를 돌려준다.")
    @Test
    void withDraw10() {
        Change change = new Change(10);
        ChangeModule changeModule = new ChangeModule(change);
        List<CoinSet> expectedCoins = Arrays.asList(CoinSet._10);

        List<CoinSet> returnedCoins = changeModule.withDrawToCoins();
        assertThat(expectedCoins).usingRecursiveComparison().isEqualTo(returnedCoins);
    }

    @DisplayName("50원이 남아 있다면 50원 동전 1개를 돌려준다.")
    @Test
    void withDraw50() {
        Change change = new Change(50);
        ChangeModule changeModule = new ChangeModule(change);
        List<CoinSet> expectedCoins = Arrays.asList(CoinSet._50);

        List<CoinSet> returnedCoins = changeModule.withDrawToCoins();
        assertThat(expectedCoins).usingRecursiveComparison().isEqualTo(returnedCoins);
    }

    @DisplayName("500원이 남아 있다면 500원 동전 2개를 돌려준다.")
    @Test
    void withDraw1000() {
        Change change = new Change(1000);
        ChangeModule changeModule = new ChangeModule(change);
        List<CoinSet> expectedCoins = Arrays.asList(CoinSet._500, CoinSet._500);

        List<CoinSet> returnedCoins = changeModule.withDrawToCoins();
        assertThat(expectedCoins).usingRecursiveComparison().isEqualTo(returnedCoins);
    }

    @DisplayName("20원이 남아 있다면 10원 동전 2개를 돌려준다.")
    @Test
    void withDraw20() {
        Change change = new Change(20);
        ChangeModule changeModule = new ChangeModule(change);
        List<CoinSet> expectedCoins = Arrays.asList(CoinSet._10, CoinSet._10);

        List<CoinSet> returnedCoins = changeModule.withDrawToCoins();
        assertThat(expectedCoins).usingRecursiveComparison().isEqualTo(returnedCoins);
    }

    @DisplayName("600원이 남아 있다면 500원 동전 1개, 100원 1개를 돌려준다.")
    @Test
    void withDraw600() {
        Change change = new Change(600);
        ChangeModule changeModule = new ChangeModule(change);
        List<CoinSet> expectedCoins = Arrays.asList(CoinSet._500, CoinSet._100);

        List<CoinSet> returnedCoins = changeModule.withDrawToCoins();
        assertThat(expectedCoins).usingRecursiveComparison().isEqualTo(returnedCoins);
    }

    @DisplayName("650원이 남아 있다면 500원 동전 1개, 100원 1개, 50원 1개를 돌려준다.")
    @Test
    void withDraw650() {
        Change change = new Change(650);
        ChangeModule changeModule = new ChangeModule(change);
        List<CoinSet> expectedCoins = Arrays.asList(CoinSet._500, CoinSet._100, CoinSet._50);

        List<CoinSet> returnedCoins = changeModule.withDrawToCoins();
        assertThat(expectedCoins).usingRecursiveComparison().isEqualTo(returnedCoins);
    }

    @DisplayName("659원이 남아 있다면 500원 동전 1개, 100원 1개, 50원 1개를 돌려준다.")
    @Test
    void withDraw659() {
        Change change = new Change(659);
        ChangeModule changeModule = new ChangeModule(change);
        List<CoinSet> expectedCoins = Arrays.asList(CoinSet._500, CoinSet._100, CoinSet._50);

        List<CoinSet> returnedCoins = changeModule.withDrawToCoins();
        assertThat(expectedCoins).usingRecursiveComparison().isEqualTo(returnedCoins);
    }

    @DisplayName("자판기가 보유한 동전의 개수에 맞춰서, 동전을 반환한다.")
    @Test
    void withDraw1770() {
        Change change = new Change(1770);
        ChangeModule changeModule = new ChangeModule(change);

        // 1750원
        Coins coins = new Coins(Arrays.asList(
                CoinSet._500, CoinSet._500,
                CoinSet._100, CoinSet._100, CoinSet._100, CoinSet._100, CoinSet._100, CoinSet._100, CoinSet._100,
                CoinSet._50
        ));

        List<CoinSet> expectedCoins = Arrays.asList(
                CoinSet._500, CoinSet._500,
                CoinSet._100, CoinSet._100, CoinSet._100, CoinSet._100, CoinSet._100, CoinSet._100, CoinSet._100,
                CoinSet._50,
                CoinSet._10, CoinSet._10
        );

        List<CoinSet> returnedCoins = changeModule.withDrawToCoins(coins);
        assertThat(expectedCoins).usingRecursiveComparison().isEqualTo(returnedCoins);
    }
}
