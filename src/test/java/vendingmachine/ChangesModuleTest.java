package vendingmachine;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChangesModuleTest {

    @DisplayName("입금 금액 출력")
    @Test
    void printSumOfCoinsTest() {

        // given
        ChangesModule changesModule = new ChangesModule(new Money(600));

        // when
        final int money = changesModule.printCurrentMoney();

        // then
        assertThat(money).isEqualTo(600);
    }
}
