package bank.listener;

import bank.service.CreditService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CreditListener implements ActionListener {

    private final CreditService creditService;

    public CreditListener(CreditService creditService) {
        this.creditService = creditService;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double amount = Double.parseDouble(JOptionPane.showInputDialog("ÇEKMEK İSTEDİĞİNİZ MİKTAR: "));
        int months = Integer.parseInt(JOptionPane.showInputDialog("KAÇ AYLIK KREDİ: "));
        Long iban = Long.parseLong(JOptionPane.showInputDialog("HESAP NUMARANIZ: "));

        try {
            creditService.scheduleCredit(amount, months, iban);
            JOptionPane.showMessageDialog(null, "KREDİ BAŞARIYLA ALINDI.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
