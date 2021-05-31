package vendingmachine.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class VendingMachineTest {
    @DisplayName("자판기에 돈을 넣으면, 잘들어간다.")
    @Test
    void insertCoinTest() {
        VendingMachine vendingMachine = new VendingMachine(new ChangeModule());
        vendingMachine.insertCoin(CoinSet._500);
        assertThat(vendingMachine.getAmount()).isEqualTo(500);
    }

    @DisplayName("반환 요청을 하면, 전체 금액을 가장 적은 수의 동전으로 돌려준다.")
    @Test
    void withDrawToCoins() {
        VendingMachine vendingMachine = new VendingMachine(new ChangeModule());
        vendingMachine.insertCoin(CoinSet._500);
        vendingMachine.insertCoin(CoinSet._500);

        List<CoinSet> expectedCoins = Arrays.asList(CoinSet._500, CoinSet._500);
        List<CoinSet> returnedCoins = vendingMachine.withDrawToCoins();

        assertThat(expectedCoins).usingRecursiveComparison().isEqualTo(returnedCoins);
    }
}
