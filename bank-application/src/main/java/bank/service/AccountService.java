package bank.service;

import bank.dto.CreateAccountRequest;
import bank.model.Account;
import bank.model.Currency;
import bank.model.FileFacade;
import bank.model.FilePaths;
import bank.util.JsonReader;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import static bank.listener.WithdrawMoneyListener.buildAccount;
import static bank.util.JsonReader.formatWithJson;


public class AccountService {
    public void createAccount(CreateAccountRequest request) throws IOException, JSONException {
        File file = FileFacade.ACCOUNT.getFile();

        if (!file.exists()) {
            file.createNewFile();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", request.getId());
        jsonObject.put("balance", request.getBalance());
        jsonObject.put("currency", request.getCurrency());
        jsonObject.put("customerId", request.getCustomerId());

        formatWithJson(file, jsonObject);
    }

    public Account findByIban(Long iban, List<LinkedHashMap> accounts) throws Exception {
        final LinkedHashMap linkedHashMap = accounts.stream()
                .filter(a -> a.get("id").equals(iban))
                .findFirst()
                .orElseThrow(() -> new Exception("Hesap bulunamadı"));
        final Account db = Account.builder()
                .id((Long) linkedHashMap.get("id"))
                .balance(Double.valueOf((Integer) linkedHashMap.get("balance")))
                .currency(Currency.valueOf((String) linkedHashMap.get("currency")))
                .customerId((Long) linkedHashMap.get("customerId"))
                .build();

        accounts.remove(linkedHashMap);
        return db;

    }

    public void transferMoney(Long target, Long current, double amount) {
        final List<LinkedHashMap> accounts = JsonReader.read(FilePaths.ACCOUNT.getPath(), List.class);
        try {
            final Account targetAccount = findByIban(target, accounts);
            final Account currentAccount = findByIban(current, accounts);

            if (currentAccount.getBalance() < amount) {
                JOptionPane.showInputDialog("Yetersiz bakiye");
                return;
            }

            targetAccount.setBalance(targetAccount.getBalance() + amount);
            currentAccount.setBalance(currentAccount.getBalance() - amount);

            FileFacade.deleteFile(FilePaths.ACCOUNT.getPath());

            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("id", targetAccount.getId());
            map.put("balance", targetAccount.getBalance());
            map.put("currency", targetAccount.getCurrency());
            map.put("customerId", targetAccount.getCustomerId());
            accounts.add(map);
            buildAccount(accounts, currentAccount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
