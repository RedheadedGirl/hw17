package implementations;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.sbrf.implementations.CalculatorImpl;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorImplTest {

    private static Stream<Arguments> provideArgs() {
        return Stream.of(
                Arguments.of(5, List.of(0, 1, 1, 2, 3)),
                Arguments.of(6, List.of(0, 1, 1, 2, 3, 5)),
                Arguments.of(10, List.of(0, 1, 1, 2, 3, 5, 8, 13, 21, 34))
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgs")
    void givenNumber_whenFibonachi_thenCorrectList(int arg, List<Integer> result) {
        CalculatorImpl calculator = new CalculatorImpl();
        List<Integer> fibonachi = calculator.fibonachi(arg);
        assertEquals(result.size(), fibonachi.size());
        assertEquals(result, fibonachi);
    }

}
