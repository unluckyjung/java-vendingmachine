package vendingmachine;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VendingMachineTest {

    @DisplayName("물품 구입 후, 남은 금액이 최소 구입 금액 미만인 경우 잔돈 반환")
    @Test
    void BuyAndWithdrawChangesTest() {

        // given
        Products vendingMachineProducts = new Products(Product.values());
        VendingMachine vendingMachine = new VendingMachine(vendingMachineProducts);
        vendingMachine.insertCoins(Coin.ONE_HUNDRED, Coin.FIFTY);

        // when
        final BuyResult buyResult = vendingMachine.buy(Product.CIDER.name());

        // then
        assertThat(buyResult.getProducts().getProducts()).containsExactly(Product.CIDER);
        assertThat(buyResult.getCoins()).containsExactly(Coin.TEN, Coin.TEN);
    }

    @DisplayName("물품 구입 후, 남은 금액이 최소 구입 금액 이상일 경우 잔돈 미반환")
    @Test
    void BuyAndDoNotWithdrawChangesTest() {

        // given
        Products vendingMachineProducts = new Products(Product.values());
        VendingMachine vendingMachine = new VendingMachine(vendingMachineProducts);
        vendingMachine.insertCoins(Coin.FIVE_HUNDRED);

        // when
        final BuyResult buyResult = vendingMachine.buy(Product.CIDER.name());

        // then
        assertThat(buyResult.getProducts().getProducts()).containsExactly(Product.CIDER);
        assertThat(buyResult.getCoins()).isEmpty();
        assertThat(vendingMachine.getCurrentMoney()).isEqualTo(370);
    }
}
