package bank.listener;

import bank.model.FileFacade;
import bank.model.FilePaths;
import bank.util.JsonReader;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;


public class AccountDeleteListener implements ActionListener {
    @Override
    @SuppressWarnings("unchecked")
    public void actionPerformed(ActionEvent e) {
        final Long IBAN = Long.valueOf(JOptionPane.showInputDialog("Silmek istediğiniz hesap numarasını giriniz"));

        final List<LinkedHashMap> accounts = JsonReader.read(FilePaths.ACCOUNT.getPath(), List.class);
        final LinkedHashMap account = accounts.stream()
                .filter(a -> a.get("id").equals(IBAN))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Hesap bulunamadı"));

        accounts.remove(account);

        FileFacade.deleteFile(FilePaths.ACCOUNT.getPath());
        reWriteData(accounts);


    }

    public static void reWriteData(List<LinkedHashMap> accounts) {
        accounts.forEach(a -> {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", a.get("id"));
                jsonObject.put("balance", a.get("balance"));
                jsonObject.put("currency", a.get("currency"));
                jsonObject.put("customerId", a.get("customerId"));

                JsonReader.formatWithJson(FileFacade.ACCOUNT.getFile(), jsonObject);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
