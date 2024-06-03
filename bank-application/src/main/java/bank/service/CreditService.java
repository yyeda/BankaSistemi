package bank.service;

import bank.model.Account;
import bank.model.Credit;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static bank.listener.WithdrawMoneyListener.buildAccount;
import static bank.util.JsonReader.formatWithJson;


public class CreditService {

    private final AccountService accountService;
    private final ScheduledExecutorService scheduledExecutorService;

    public CreditService(AccountService accountService) {
        this.accountService = accountService;
        this.scheduledExecutorService = Executors.unconfigurableScheduledExecutorService(Executors.newScheduledThreadPool(1));
    }


    public void scheduleCredit(double amount, int months, Long iban) throws Exception {

        final List<LinkedHashMap> accounts = JsonReader.read(FilePaths.ACCOUNT.getPath(), List.class);
        final Account account = accountService.findByIban(iban, accounts);

        FileFacade.deleteFile(FilePaths.ACCOUNT.getPath());

        account.setBalance(account.getBalance() + amount);

        buildAccount(accounts, account);

        double monthlyPayment = amount / months;

        saveCredit(
                Credit.builder()
                        .customerId(account.getCustomerId())
                        .totalAmount(amount)
                        .monthlyPayment(monthlyPayment)
                        .remainingPayment(amount)
                        .iban(iban)
                        .months(months)
                        .build()
        );

        startCreditSchedule(iban, monthlyPayment);
    }

    private void startCreditSchedule(Long iban, double monthlyPayment) {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                final List<LinkedHashMap> accounts1 = JsonReader.read(FilePaths.ACCOUNT.getPath(), List.class);
                final List<LinkedHashMap> credits = JsonReader.read(FilePaths.CREDIT.getPath(), List.class);
                final Account account1 = accountService.findByIban(iban, accounts1);

                Credit credit = findCreditByIban(iban);

                if (credit.getRemainingPayment() <= 0) {
                    scheduledExecutorService.shutdown();
                    return;
                }

                credit.setRemainingPayment(credit.getRemainingPayment() - credit.getMonthlyPayment());

                FileFacade.deleteFile(FilePaths.CREDIT.getPath());
                buildCredits(credits, credit);


                FileFacade.deleteFile(FilePaths.ACCOUNT.getPath());

                account1.setBalance(account1.getBalance() - monthlyPayment);

                buildAccount(accounts1, account1);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 30, 30, TimeUnit.DAYS);
    }

    public void saveCredit(Credit credit) throws Exception {
        final File file = FileFacade.CREDIT.getFile();
        if (!file.exists()) {
            file.createNewFile();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("customerId", credit.getCustomerId());
        jsonObject.put("totalAmount", credit.getTotalAmount());
        jsonObject.put("months", credit.getMonths());
        jsonObject.put("monthlyPayment", credit.getMonthlyPayment());
        jsonObject.put("iban", credit.getIban());
        jsonObject.put("remainingPayment", credit.getRemainingPayment());

        formatWithJson(file, jsonObject);
    }

    public Credit findCreditByIban(Long iban) throws Exception {
        final List<LinkedHashMap> credits = JsonReader.read(FilePaths.CREDIT.getPath(), List.class);
        final LinkedHashMap linkedHashMap = credits.stream()
                .filter(a -> a.get("iban").equals(iban))
                .findFirst()
                .orElseThrow(() -> new Exception("Kredi bulunamadı"));
        final Credit db = Credit.builder()
                .customerId((Long) linkedHashMap.get("customerId"))
                .totalAmount((Double) linkedHashMap.get("totalAmount"))
                .months((Integer) linkedHashMap.get("months"))
                .monthlyPayment((Double) linkedHashMap.get("monthlyPayment"))
                .iban((Long) linkedHashMap.get("iban"))
                .remainingPayment((Double) linkedHashMap.get("remainingPayment"))
                .build();

        credits.remove(linkedHashMap);
        return db;
    }

    public void buildCredits(List<LinkedHashMap> credits, Credit credit) throws Exception {
        LinkedHashMap<String, Object> jsonObject = new LinkedHashMap<>();
        jsonObject.put("customerId", credit.getCustomerId());
        jsonObject.put("totalAmount", credit.getTotalAmount());
        jsonObject.put("months", credit.getMonths());
        jsonObject.put("monthlyPayment", credit.getMonthlyPayment());
        jsonObject.put("iban", credit.getIban());
        jsonObject.put("remainingPayment", credit.getRemainingPayment());
        credits.add(jsonObject);
        reWriteData(credits);
    }

    public void reWriteData(List<LinkedHashMap> credits) throws Exception {
        credits.forEach(a -> {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("customerId", a.get("customerId"));
                jsonObject.put("totalAmount", a.get("totalAmount"));
                jsonObject.put("months", a.get("months"));
                jsonObject.put("monthlyPayment", a.get("monthlyPayment"));
                jsonObject.put("iban", a.get("iban"));
                jsonObject.put("remainingPayment", a.get("remainingPayment"));

                JsonReader.formatWithJson(FileFacade.CREDIT.getFile(), jsonObject);
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, "Kredi dosyası bulunamadı");
                throw new RuntimeException(ioException);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
