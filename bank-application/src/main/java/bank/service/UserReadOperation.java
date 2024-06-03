package bank.service;

import bank.dto.SignUpRequest;
import bank.exception.UserNotFoundException;
import bank.model.FileFacade;

import static bank.service.SignInService.getAllUsers;


public class UserReadOperation {
    public static SignUpRequest findByUser(String email) {
       return getAllUsers(FileFacade.CUSTOMER.getFile())
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı"));
    }
}
