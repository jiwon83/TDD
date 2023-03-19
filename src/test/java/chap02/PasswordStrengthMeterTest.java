package chap02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordStrengthMeterTest {
    private final PasswordStrengthMeter meter = new PasswordStrengthMeter();

    private void assertStrength(String password, PasswordStrength expStr) {
        assertEquals(expStr, meter.meter(password));
    }

    @Test
    void meetsAllCriteria_Strong() {
        assertStrength("ab12!@AB", PasswordStrength.STRONG);
        assertStrength("00AA12345", PasswordStrength.STRONG);
    }

    @Test
    void meetsTwoCriteria_except_for_length_Regular() {
        assertStrength("ab12!@A", PasswordStrength.REGULAR);
        assertStrength("AB12!c", PasswordStrength.REGULAR);
    }

    @Test
    void meetsTwoCriteria_except_for_num_Regular() {
        assertStrength("aaaaBBBB", PasswordStrength.REGULAR);
        assertStrength("a!@#aaBBBB", PasswordStrength.REGULAR);
    }
    @Test
    void meetsTwoCriteria_except_for_upperCase_Regular() {
        assertStrength("aaaa1234", PasswordStrength.REGULAR);
    }
    @Test
    void meetsOneCriteria_for_length_then_Weak() {
        assertStrength("!c!!#$!!a", PasswordStrength.WEAK);
    }
    @Test
    void meetsOneCriteria_for_num_then_Weak() {
        assertStrength("!c!!1", PasswordStrength.WEAK);
        assertStrength("132", PasswordStrength.WEAK);
    }
    @Test
    void meetsOneCriteria_for_upperCase_then_Weak() {
        assertStrength("!DUU", PasswordStrength.WEAK);
    }

    @Test
    void meets0Criteria_then_Weak() {
        assertStrength("!@!ab", PasswordStrength.WEAK);
    }

    @Test
    void emptyInput_then_Invalid() {
        assertStrength("", PasswordStrength.INVALID);
        assertStrength(" ", PasswordStrength.INVALID);
    }

    @Test
    void nullInput_then_Invalid() {
        assertStrength(null, PasswordStrength.INVALID);
    }
}
