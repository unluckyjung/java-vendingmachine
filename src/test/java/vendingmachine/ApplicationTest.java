package vendingmachine;

import org.assertj.core.util.Strings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class ApplicationTest {
    private static final Duration SIMPLE_TEST_TIMEOUT = Duration.ofSeconds(1L);
    private static final Duration RANDOM_TEST_TIMEOUT = Duration.ofSeconds(10L);

    private static final String MAIN_EXIT_NUMBER = "3";

    private void subject(final String... args) {
        command(args);
        Application.main(new String[]{});
    }

    private void command(final String... args) {
        final byte[] buf = Strings.join(args).with("\n").getBytes();
        System.setIn(new ByteArrayInputStream(buf));
    }

    private void assertSimpleTest(final Executable executable) {
        assertTimeoutPreemptively(SIMPLE_TEST_TIMEOUT, executable);
    }


    @DisplayName("관리자 모드 실패 테스트")
    @Nested
    class OwnerFailTest {
        private PrintStream standardOut;
        private OutputStream captor;

        @BeforeEach
        void setUp() {
            standardOut = System.out;
            captor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(captor));
        }

        @DisplayName("메인메뉴 잘못 선택한 경우 테스트")
        @ParameterizedTest
        @ValueSource(strings = {"4"})
        void 잘못된_메인메뉴_선택(final String selectNumber) {
            assertSimpleTest(() -> {
                subject(
                        selectNumber,
                        MAIN_EXIT_NUMBER
                );
                assertThat(captor.toString().trim()).contains("없는 메뉴 번호입니다.");
            });
        }

        @DisplayName("상품가격이 음수이면, 예외가 발생한다.")
        @ParameterizedTest
        @CsvSource({"시드,-1", "포츈,-2"})
        void 잘못된_상품가격_테스트(final String name, final String price) {
            assertSimpleTest(() -> {
                assertThatThrownBy(() ->
                        subject("1", "1", name + " " + price)
                ).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("돈은 음수가 될 수 없습니다.");
            });
        }


        @DisplayName("상품가격이 숫자가 아니면, 예외가 발생한다.")
        @ParameterizedTest
        @CsvSource({"시드,aa"})
        void 문자_상품가격_테스트(final String name, final String price) {
            assertSimpleTest(() -> {
                assertThatThrownBy(() ->
                        subject("1", "1", name + " " + price)
                ).isInstanceOf(IllegalArgumentException.class);
            });
        }

        @AfterEach
        void tearDown() {
            System.setOut(standardOut);
            System.out.println(captor.toString());
        }
    }

    @DisplayName("관리자 모드 기능 테스트")
    @Nested
    class OwnerTest {
        private PrintStream standardOut;
        private OutputStream captor;

        @BeforeEach
        void setUp() {
            standardOut = System.out;
            captor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(captor));
        }

        @AfterEach
        public void tearDown() {
            System.setOut(standardOut);
        }

        @DisplayName("상품 등록 테스트")
        @ParameterizedTest
        @CsvSource({"시드,300"})
        void 상품_정상_등록_테스트(final String name, final String price) {
            assertSimpleTest(() -> {
                subject(
                        // 상품 등록
                        "1", "1", name + " " + price,
                        // 종료
                        MAIN_EXIT_NUMBER
                );
                assertThat(captor.toString().trim()).contains(name, price);
            });
        }

        @DisplayName("상품 삭제 테스트")
        @ParameterizedTest
        @CsvSource({"시드,300"})
        void 상품_정상_삭제_테스트(final String name, final String price) {
            assertSimpleTest(() -> {
                subject(
                        // 상품 등록
                        "1", "1", name + " " + price,
                        // 상품 삭제
                        "1", "2", name,
                        // 종료
                        MAIN_EXIT_NUMBER
                );
            });
        }
    }
}
