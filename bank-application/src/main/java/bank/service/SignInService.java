package bank.service;

import bank.dto.SignUpRequest;
import bank.exception.UserNotFoundException;
import bank.model.FileFacade;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public final class SignInService {

    public void signIn(String email, String password) {
        getAllUsers(FileFacade.CUSTOMER.getFile())
                .stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı adı veya şifre hatalı"));
    }

    public static List<SignUpRequest> getAllUsers(File file) {
        if (file.length() == 0) {
            return List.of();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<LinkedHashMap> list;
        try {
            list = objectMapper.readValue(file, List.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("error");
        }
        return list.stream()
                .map(map -> SignUpRequest.builder()
                        .name((String) map.get("name"))
                        .surname((String) map.get("surname"))
                        .email((String) map.get("email"))
                        .password((String) map.get("password"))
                        .id((Long) map.get("id"))
                        .build())
                .collect(Collectors.toList());
    }
}
