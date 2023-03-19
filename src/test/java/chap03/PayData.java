package chap03;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

public class PayData {
    private LocalDate billingDate;
    private int payAmount;

    private LocalDate firstBillingDate;
    public PayData() {}

    public PayData(LocalDate billingDate, int payAmount) {
        this.billingDate = billingDate;
        this.payAmount = payAmount;
    }

    public PayData(LocalDate billingDate, int payAmount, LocalDate firstBillingDate) {
        this.billingDate = billingDate;
        this.payAmount = payAmount;
        this.firstBillingDate = firstBillingDate;
    }

    public LocalDate getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(LocalDate billingDate) {
        this.billingDate = billingDate;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(int payAmount) {
        this.payAmount = payAmount;
    }

    public LocalDate getFirstBillingDate() {
        return firstBillingDate;
    }

    public void setFirstBillingDate(LocalDate firstBillingDate) {
        this.firstBillingDate = firstBillingDate;
    }

    public static PaymentBuilder builder(){
        return new PaymentBuilder();
    }
    //Builder Pattern

    public static class PaymentBuilder{
        private final PayData data = new PayData();

        public PaymentBuilder billingDate(LocalDate billingDate){
            data.billingDate = billingDate;
            return this;
        }
        public PaymentBuilder payAmount(int payAmount){
            data.payAmount = payAmount;
            return this;
        }

        public PaymentBuilder firstBillingDate(LocalDate firstBillingDate){
            data.firstBillingDate = firstBillingDate;
            return this;
        }
        public PayData build(){
            return data;
        }

    }
}
