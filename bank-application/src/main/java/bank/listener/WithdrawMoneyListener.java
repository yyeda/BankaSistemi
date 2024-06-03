package bank.listener;

import bank.model.Account;
import bank.model.FileFacade;
import bank.model.FilePaths;
import bank.service.AccountService;
import bank.util.JsonReader;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.List;

import static bank.listener.AccountDeleteListener.reWriteData;


public class WithdrawMoneyListener implements ActionListener {
    private final AccountService accountService;

    public WithdrawMoneyListener(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        final Long IBAN = Long.valueOf(JOptionPane.showInputDialog("Para çekmek istediğiniz hesap numarasını giriniz"));
        final String AMOUNT = JOptionPane.showInputDialog("çekmek istediğiniz tutarı giriniz");

        try {
            final List<LinkedHashMap> accounts = JsonReader.read(FilePaths.ACCOUNT.getPath(), List.class);
            final Account account = accountService.findByIban(IBAN, accounts);

            FileFacade.deleteFile(FilePaths.ACCOUNT.getPath());

            account.setBalance(account.getBalance() - Double.parseDouble(AMOUNT));
            buildAccount(accounts, account);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Hesap bulunamadı");
            throw new RuntimeException(ex);
        }

    }

    public static void buildAccount(List<LinkedHashMap> accounts, Account account) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("id", account.getId());
        map.put("balance", account.getBalance());
        map.put("currency", account.getCurrency());
        map.put("customerId", account.getCustomerId());
        accounts.add(map);
        reWriteData(accounts);
    }
}
