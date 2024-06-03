package bank.listener;

import bank.client.AccountClient;
import bank.model.Account;
import bank.model.Currency;
import bank.model.FilePaths;
import bank.util.JsonReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;


public class AccountListListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

        final List<LinkedHashMap>  accounts = JsonReader.read(FilePaths.ACCOUNT.getPath(), List.class);

        JFrame frame = new AccountClient(accounts
                .stream()
                .map(json -> Account.builder()
                        .id((Long) json.get("id"))
                        .currency(Currency.valueOf((String) json.get("currency")))
                        .customerId((Long) json.get("customerId"))
                        .balance(Double.valueOf((Integer) json.get("balance")))
                        .build())
                .collect(Collectors.toList())
        );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 1500);
        frame.setVisible(true);

    }


}
