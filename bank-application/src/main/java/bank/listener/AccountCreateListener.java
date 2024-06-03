package bank.listener;

import bank.client.AccountClient;
import bank.dto.CreateAccountRequest;
import bank.model.Account;
import bank.model.Currency;
import bank.model.FilePaths;
import bank.service.AccountService;
import bank.service.UserReadOperation;
import bank.util.JsonReader;
import org.json.JSONException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AccountCreateListener implements ActionListener {
    private final AccountService accountService;


    public AccountCreateListener(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Long id = UUID.randomUUID().getMostSignificantBits();
        final String email = JOptionPane.showInputDialog("Email");
        Long customerId = UserReadOperation.findByUser(email).getId();
        Double balance = 0.0;
        Currency currency = Currency.TRY;

        try {
            accountService.createAccount(CreateAccountRequest.builder()
                    .balance(balance)
                    .currency(currency)
                    .customerId(customerId)
                    .id(id)
                    .build());

            final List<LinkedHashMap> accounts = JsonReader.read(FilePaths.ACCOUNT.getPath(), List.class);
            final List<Account> accountList = accounts.stream().map(account -> Account.builder()
                    .id((Long) account.get("id"))
                    .currency(Currency.valueOf((String) account.get("currency")))
                    .customerId((Long) account.get("customerId"))
                    .balance(Double.valueOf((Integer) account.get("balance")))
                    .build()).collect(Collectors.toList());

            JFrame jr = new AccountClient(accountList);
            jr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jr.setSize(1500, 1500);
            jr.setVisible(true);


        } catch (JSONException | IOException ex) {
            JOptionPane.showMessageDialog(null, "HESAP OLUŞTURULAMADI");
            throw new RuntimeException(ex);
        }


    }
}
