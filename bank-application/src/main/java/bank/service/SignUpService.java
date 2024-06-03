package bank.service;

import bank.dto.SignUpRequest;
import bank.model.FileFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;

import static bank.util.JsonReader.formatWithJson;


public class SignUpService {


    public void signUp(SignUpRequest request) throws IOException, JSONException {
        execute(request);
    }

    private File execute(SignUpRequest request) throws IOException, JSONException {
        File file = FileFacade.CUSTOMER.getFile();

        if (!file.exists()) {
            file.createNewFile();
        }

        checkEmailUsed(request, file);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", request.getId());
        jsonObject.put("name", request.getName());
        jsonObject.put("surname", request.getSurname());
        jsonObject.put("email", request.getEmail());
        jsonObject.put("password", request.getPassword());

        formatWithJson(file, jsonObject);
        return file;
    }

    private void checkEmailUsed(SignUpRequest request, File file) {
        if (file.length() == 0) {
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<LinkedHashMap> list = objectMapper.readValue(file, List.class);
            for (LinkedHashMap map : list) {
                if (map.get("email").equals(request.getEmail())) {
                    throw new IllegalArgumentException("This email is already used");
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("error");

        }
    }
}
