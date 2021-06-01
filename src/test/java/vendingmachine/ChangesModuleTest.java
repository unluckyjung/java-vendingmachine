package vendingmachine;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChangesModuleTest {

    @DisplayName("잔돈 반환 성공 및 남아있는 돈 확인")
    @Test
    void getChangesReturnFiftyTest() {

        // given
        final ChangesModule changesModule = new ChangesModule(50);

        // when
        final List<Coin> coins = changesModule.returnChanges();

        // then
        final int changes = changesModule.getCurrentMoney();
        assertThat(changes).isEqualTo(0);
        assertThat(coins).containsExactly(Coin.FIFTY);
    }
}
