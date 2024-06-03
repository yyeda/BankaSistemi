package bank;

import bank.client.HomePageClient;

import javax.swing.*;
import java.awt.*;


public final class Client {

    public static void main(String[] args) {
        JFrame frame = new HomePageClient("Banka Sayfasına Hoşgeldiniz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setFont(new Font("Arial", Font.ITALIC, 20));
        frame.setSize(1500, 1500);
        frame.setVisible(true);
    }
}
