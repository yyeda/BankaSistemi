package bank.listener;

import bank.service.AccountService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TransferMoneyListener implements ActionListener {
    private final AccountService accountService;

    public TransferMoneyListener(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Long target = Long.parseLong(JOptionPane.showInputDialog("GÖNDERİLECEK HESAP NUMARASI:"));
            Long current = Long.parseLong(JOptionPane.showInputDialog("ALINACAK HESAP NUMARASI:"));
            double amount = Double.parseDouble(JOptionPane.showInputDialog("MİKTAR:"));

            if (target.equals(current)) {
                JOptionPane.showMessageDialog(null, "Hesap numaraları aynı olamaz");
                return;
            }
            accountService.transferMoney(target, current, amount);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Hesap bulunamadı");
            throw new RuntimeException(exception);
        }
    }
}
