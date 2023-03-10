package chap03;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpiryDateCalculatorTest {
    final ExpiryDateCalculator calculator = new ExpiryDateCalculator();

    public void assertExpiryDate(PayData payData, LocalDate expExpiryDate) {
        LocalDate realExpiryDate = calculator.calculatorExpiryDate(payData);
        assertEquals(expExpiryDate, realExpiryDate);
    }

    @Test
    public void pay1만원() {

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2023, 03, 10))
                        .payAmount(10000)
                        .build()
                ,
                LocalDate.of(2023, 4, 10)
        );

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(1999, 8, 03))
                        .payAmount(10000)
                        .build()
                ,
                LocalDate.of(1999, 9, 3)
        );
    }


    @Test
    public void pay1만원_notSameDay() {

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 1, 31))
                        .payAmount(10000)
                        .build()
                ,
                LocalDate.of(2019, 2, 28));

        assertExpiryDate(
                PayData.builder()
                                .billingDate(LocalDate.of(2019, 5, 31))
                        .payAmount(10000)
                        .build()
                ,
                LocalDate.of(2019, 6, 30));

        assertExpiryDate(
                PayData.builder()
                                .billingDate(LocalDate.of(2019, 5, 29))
                        .payAmount(10000)
                        .build()
                ,
                LocalDate.of(2019, 6, 29));

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2020, 1, 31))
                        .payAmount(10000)
                        .build()
                ,
                LocalDate.of(2020, 2, 29));

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 1, 30))
                        .payAmount(10000)
                        .build()
                ,
                LocalDate.of(2019, 2, 28));//not 27 true 28
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 2, 28))
                        .payAmount(10000)
                        .build()
                ,
                LocalDate.of(2019, 3, 28));//not 31 true 28

    }

    @Test
    public void pay_10000_diff_firstBillingDate_with_afterBillingDate() {
        PayData payData = PayData.builder()
                .firstBillingDate(LocalDate.of(2019,1,31))
                .billingDate(LocalDate.of(2019,2,28))
                .payAmount(10000)
                .build();

        assertExpiryDate( payData, LocalDate.of(2019,3,31));

        PayData payData3 = PayData.builder()
                .firstBillingDate(LocalDate.of(2019,1,30))
                .billingDate(LocalDate.of(2019,2,28))
                .payAmount(10000)
                .build();

        assertExpiryDate( payData3, LocalDate.of(2019,3,30));

        PayData payData4 = PayData.builder()
                .firstBillingDate(LocalDate.of(2019,5,31))
                .billingDate(LocalDate.of(2019,6,30))
                .payAmount(10000)
                .build();

        assertExpiryDate( payData4, LocalDate.of(2019,7,31));

    }
    @Test
    public void pay_2만원_and_more(){
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019,3,1))
                        .payAmount(20000)
                        .build()
                , LocalDate.of(2019,5,1)
        );
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019,3,1))
                        .payAmount(30000)
                        .build()
                , LocalDate.of(2019,6,1)
        );

    }

    @Test
    public void pay_2만원_ande_more_diff_firstBillingDate_with_afterBillingDate(){
        assertExpiryDate(
                PayData.builder()
                        .firstBillingDate(LocalDate.of(2019,1,31))
                        .billingDate(LocalDate.of(2019,2,28))
                        .payAmount(20000)
                        .build(),
                LocalDate.of(2019,4,30)
        );
        assertExpiryDate(
                PayData.builder()
                        .firstBillingDate(LocalDate.of(2019,1,31))
                        .billingDate(LocalDate.of(2019,2,28))
                        .payAmount(40000)
                        .build(),
                LocalDate.of(2019,6,30)
        );
        assertExpiryDate(
                PayData.builder()
                        .firstBillingDate(LocalDate.of(2019,3,31))
                        .billingDate(LocalDate.of(2019,4,30))
                        .payAmount(30000)
                        .build(),
                LocalDate.of(2019,7,31)
        );
    }

    @Test
    public void pay10_and_more_get_1year(){
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019,1,28))
                        .payAmount(100000)
                        .build(),
                LocalDate.of(2020,1,28)
        );
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019,1,28))
                        .payAmount(130000)
                        .build(),
                LocalDate.of(2020,4,28)
        );
    }


}
