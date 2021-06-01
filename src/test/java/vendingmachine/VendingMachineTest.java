package vendingmachine;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VendingMachineTest {


    @DisplayName("물품 구입")
    @Test
    void buyTest() {

        // given
        final Product 콜라 = new Product("콜라", 1000);
        final Products products = new Products(
                콜라,
                new Product("사이다", 1500)
        );

        VendingMachine vendingMachine = new VendingMachine(products);
        vendingMachine.insertCoins(Coin.FIVE_HUNDRED, Coin.FIVE_HUNDRED, Coin.FIVE_HUNDRED);

        // when
        final Product boughtProduct = vendingMachine.buy("콜라");

        // then
        assertThat(콜라).isEqualTo(boughtProduct);
    }
}
