package bank.model;


public final class Customer extends User {
    private Long accountId;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long accountId;
        private Long id;
        private String name;
        private String surname;
        private String email;
        private String password;
        private String phoneNumber;

        private Builder() {

        }

        public Builder accountId(Long accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Customer build() {
            Customer customer = new Customer();
            customer.setAccountId(accountId);
            customer.setId(id);
            customer.setName(name);
            customer.setSurname(surname);
            customer.setEmail(email);
            customer.setPassword(password);
            customer.setPhoneNumber(phoneNumber);
            return customer;
        }
    }
}
