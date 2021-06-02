package vendingmachine.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.assertj.core.api.ThrowableAssert;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class MoneyTest {

    @DisplayName("입금")
    @Test
    void addAllTest() {

        // given
        Coin[] coins = new Coin[]{Coin.TEN, Coin.FIFTY, Coin.FIVE_HUNDRED};
        Money money = Money.from(0);

        // when
        final Money addedMoney = money.addAll(coins);

        // then
        assertThat(addedMoney.getMoney()).isEqualTo(560);
    }


    @DisplayName("입금 금액 반환")
    @Test
    void printSumOfCoinsTest() {

        // given
        Money money = Money.from(1000);

        // when
        final int currentMoney = money.getMoney();

        // then
        assertThat(currentMoney).isEqualTo(1000);
    }

    @DisplayName("차감 성공")
    @Test
    void withdrawTest() {

        // given
        final Money input = Money.from(1000);
        final Money output = Money.from(500);

        // when
        final Money subtractedMoney = input.subtract(output);

        // then
        final int changes = subtractedMoney.getMoney();
        assertThat(changes).isEqualTo(500);
    }

    @DisplayName("차감 실패 - 입금 금액보다 큰 차감액")
    @Test
    void withdrawOverMoneyTest() {

        // given
        final Money input = Money.from(1000);
        final Money output = Money.from(1500);

        // when
        final ThrowableAssert.ThrowingCallable callable = () -> input.subtract(output);

        // then
        assertThatIllegalArgumentException().isThrownBy(callable)
                                            .withMessage("차감하려는 금액이 현재 입금 금액보다 큽니다.");
    }
}
