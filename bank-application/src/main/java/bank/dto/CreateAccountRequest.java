package bank.dto;

import bank.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAccountRequest {
    private Long id = UUID.randomUUID().getMostSignificantBits();
    private Long customerId;
    private Double balance;
    private Currency currency;
}
