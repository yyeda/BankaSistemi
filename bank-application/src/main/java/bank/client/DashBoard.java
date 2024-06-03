package bank.client;

import bank.listener.*;
import bank.model.Account;
import bank.service.AccountService;
import bank.service.CreditService;

import javax.swing.*;
import java.awt.*;


public class DashBoard extends JFrame {
    private final AccountService accountService = new AccountService();
    private final CreditService creditService = new CreditService(accountService);

    private final JButton ACCOUNTS = new JButton("Hesaplarım");
    private final JButton CREATE_ACCOUNT = new JButton("Hesap Aç");
    private final JButton DELETE_ACCOUNT = new JButton("Hesap Sil");
    private final JButton DEPOSIT = new JButton("Para Yatır");
    private final JButton WITHDRAW = new JButton("Para Çek");
    private final JButton TRANSFER = new JButton("Havale Yap");
    private final JButton LOGOUT = new JButton("Çıkış Yap");
    private final JButton CREDIT = new JButton("Kredi Al");

    public DashBoard(String title) {
        super(title);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 4));

        //panel.setLayout(new GridLayout(3, 8, 10, 10));
        panel.setBorder(BorderFactory.createLineBorder(new Color(144, 238, 144), 10));

        panel.add(ACCOUNTS);
        ACCOUNTS.setForeground(new Color(0, 139, 139));
        ACCOUNTS.setFont(new Font("Arial", Font.BOLD, 20));

        panel.add(CREATE_ACCOUNT);
        CREATE_ACCOUNT.setForeground(new Color(0, 139, 139));
        CREATE_ACCOUNT.setFont(new Font("Arial", Font.BOLD, 20));

        panel.add(DELETE_ACCOUNT);
        DELETE_ACCOUNT.setForeground(new Color(0, 139, 139));
        DELETE_ACCOUNT.setFont(new Font("Arial", Font.BOLD, 20));

        panel.add(DEPOSIT);
        DEPOSIT.setForeground(new Color(0, 139, 139));
        DEPOSIT.setFont(new Font("Arial", Font.BOLD, 20));

        panel.add(WITHDRAW);
        WITHDRAW.setForeground(new Color(0, 139, 139));
        WITHDRAW.setFont(new Font("Arial", Font.BOLD, 20));

        panel.add(TRANSFER);
        TRANSFER.setForeground(new Color(0, 139, 139));
        TRANSFER.setFont(new Font("Arial", Font.BOLD, 20));

        panel.add(LOGOUT);
        LOGOUT.setForeground(new Color(139, 0, 25));
        LOGOUT.setFont(new Font("Arial", Font.BOLD, 30));

        panel.add(CREDIT);
        CREDIT.setForeground(new Color(0, 139, 139));
        CREDIT.setFont(new Font("Arial", Font.BOLD, 20));

        add(panel, BorderLayout.CENTER);

        ACCOUNTS.addActionListener(new AccountListListener());
        CREATE_ACCOUNT.addActionListener(new AccountCreateListener(accountService));
        DELETE_ACCOUNT.addActionListener(new AccountDeleteListener());
        CREDIT.addActionListener(new CreditListener(creditService));
        DEPOSIT.addActionListener(new DepositMoneyListener(accountService));
        TRANSFER.addActionListener(new TransferMoneyListener(accountService));
        LOGOUT.addActionListener(new LogoutListener());
        WITHDRAW.addActionListener(new WithdrawMoneyListener(accountService));
    }

    public void redirect() {
        JFrame frame = this;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 1500);
        frame.setVisible(true);
    }

}
