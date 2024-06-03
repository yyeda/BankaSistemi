package bank.exception;

import javax.swing.*;


public final class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
        JPanel panel = new JPanel();
        JOptionPane.showMessageDialog(panel, message, "Hata", JOptionPane.ERROR_MESSAGE);
    }
}
