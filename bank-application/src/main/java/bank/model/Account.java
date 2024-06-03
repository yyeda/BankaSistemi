package bank.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class Account {
    private Long id;
    private Long customerId;
    private Double balance;
    private Currency currency;
}
