package vendingmachine;

import org.assertj.core.util.Strings;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.mockito.MockedStatic;
import vendingmachine.utils.RandomUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mockStatic;

public class AppTest {
    private static final Duration SIMPLE_TEST_TIMEOUT = Duration.ofSeconds(1L);
    private static final String ERROR_MESSAGE = "ERROR";

    private void subject(final String... args) {
        command(args);
        App.main(new String[]{});
    }

    private void command(final String... args) {
        final byte[] buf = Strings.join(args).with("\n").getBytes();
        System.setIn(new ByteArrayInputStream(buf));
    }

    private void assertSimpleTest(final Executable executable) {
        assertTimeoutPreemptively(SIMPLE_TEST_TIMEOUT, executable);
    }

    @DisplayName("기능 테스트")
    @Nested
    class FunctionTest {
        private PrintStream standardOut;
        private OutputStream captor;

        @BeforeEach
        void setUp() {
            standardOut = System.out;
            captor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(captor));
        }

        @Test
        void 전체기능_테스트() {
            assertSimpleTest(() -> {
                subject(
                        // 상품 추가
                        "1", "1", "콜라", "100",
                        // 동전 추가
                        "2", "3", "500",
                        // 상품 구매
                        "2", "1", "콜라",
                        // 동전 반환
                        "2", "2"
                );
                List<String> outputLines = getOutputLines();
                assertThat(outputLines.get(outputLines.size() - 1)).isEqualTo("인출된 동전 : 100 100 100 100");
            });
        }


        private List<String> getOutputLines() {
            return Arrays.asList(captor.toString().trim().split("\n"));
        }

        @AfterEach
        void tearDown() {
            System.setOut(standardOut);
            System.out.println(captor.toString());
        }
    }
}