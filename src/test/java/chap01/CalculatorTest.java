package chap01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
    @Test
    void add(){
        int result = Calculator.add(3,5);
        assertEquals(8, result);
        assertEquals(-1,Calculator.add(-3,2));
    }
}
