package bank.client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BackButton extends JButton implements ActionListener {

    JMenuBar menuBar = new JMenuBar();


    public void initializeMenubar() {

        JMenu menu = new JMenu("MENU");

        menuBar.add(menu);

        JMenuItem homePage = new JMenuItem("ANA SAYFA");

        menu.add(homePage);


        homePage.addActionListener(e -> new BackButton().actionPerformed(e));
    }

    public BackButton() {
        super("ANASAYFAYA DÖN");
        setSize(100, 100);
        setBorder(BorderFactory.createEmptyBorder());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame frame = new DashBoard("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 1500);
        frame.setVisible(true);
    }
}
