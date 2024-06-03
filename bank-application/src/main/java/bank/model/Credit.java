package bank.model;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class Credit {
    private Long customerId;
    private double totalAmount;
    private int months;
    private double monthlyPayment;
    private Long iban;
    private double remainingPayment;

    public Credit(Builder builder) {
        this.customerId = builder.customerId;
        this.totalAmount = builder.totalAmount;
        this.months = builder.months;
        this.monthlyPayment = builder.monthlyPayment;
        this.iban = builder.iban;
        this.remainingPayment = builder.remainingPayment;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long customerId;
        private double totalAmount;
        private int months;
        private double monthlyPayment;
        private Long iban;
        private double remainingPayment;

        private Builder() {
        }


        public Builder customerId(Long customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder totalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder months(int months) {
            this.months = months;
            return this;
        }

        public Builder monthlyPayment(double monthlyPayment) {
            this.monthlyPayment = monthlyPayment;
            return this;
        }

        public Builder iban(Long iban) {
            this.iban = iban;
            return this;
        }

        public Builder remainingPayment(double remainingPayment) {
            this.remainingPayment = remainingPayment;
            return this;
        }

        public Credit build() {
            return new Credit(this);
        }
    }
}
