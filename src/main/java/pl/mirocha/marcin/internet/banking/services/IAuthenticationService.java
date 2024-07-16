package pl.mirocha.marcin.internet.banking.services;

import pl.mirocha.marcin.internet.banking.model.dto.RegisterUserDTO;

public interface IAuthenticationService {
    void login(String login, String password);
    void logout();
    void register(RegisterUserDTO userDTO);
}
