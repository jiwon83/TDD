package chap03;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;

public class ExpiryDateCalculator {
    private final int payPerMonth = 10000;
    public LocalDate calculatorExpiryDate(PayData payData){
        int addedMonths= payData.getPayAmount() / payPerMonth;
        if(addedMonths >= 10) addedMonths = addedMonths/10 * 12 + addedMonths % 10;

        if (payData.getFirstBillingDate() != null){
            return expiryDateUsingFirstBillingDate(payData,addedMonths);
        }else {
            return payData.getBillingDate().plusMonths(addedMonths);
        }

    }
    private LocalDate expiryDateUsingFirstBillingDate(PayData payData, int addedMonths){
        LocalDate candidateExp = payData.getBillingDate().plusMonths(addedMonths);
        if (isSameDayOfMonth(payData.getFirstBillingDate(),candidateExp)){
            final int dayLenOfCandiMon = YearMonth.from(candidateExp).lengthOfMonth();
            final int dayLenOfFirstBilling = payData.getFirstBillingDate().getDayOfMonth();
            if (dayLenOfCandiMon < dayLenOfFirstBilling){
                //예상달의 날짜의 길이가 더 작다면, 예상달의 마지막 날자로
                return candidateExp.withDayOfMonth(dayLenOfCandiMon);
            }
            return candidateExp.withDayOfMonth(dayLenOfFirstBilling);
        }else{ //
            return candidateExp;
        }
    }
    private boolean isSameDayOfMonth(LocalDate date1, LocalDate date2){
        return date1.getDayOfMonth() != date2.getDayOfMonth();
    }
}
