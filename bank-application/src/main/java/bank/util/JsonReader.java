package bank.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.util.function.Function;


public class JsonReader {
    public static <T> T read(String path, Class<T> type) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(path), type);
        } catch (IOException e) {
            JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "VERİ BULUNAMADI", "Hata", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("error");
        }
    }

    public static void formatWithJson(File file, JSONObject jsonObject) throws IOException {
        FileWriter fileWriter = new FileWriter(file, true);

        String open = "[";
        String close = "]";

        Function<String, String> addBrackets = (String s) -> open + s + close;

        FileReader fileReader = new FileReader(file);
        String currentFileJson = IOUtils.toString(fileReader);


        if (currentFileJson.length() == 0) {
            fileWriter.write(addBrackets.apply(jsonObject.toString()));
        } else {
            PrintWriter printWriter = new PrintWriter(file);
            String newJson = currentFileJson + "," + jsonObject;
            newJson = newJson.replace("[", "")
                    .replace("]", "");
            printWriter.write(addBrackets.apply(newJson));
            printWriter.close();
        }

        fileWriter.close();
    }
}
