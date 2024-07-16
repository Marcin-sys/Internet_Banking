package pl.mirocha.marcin.internet.banking.services.impl;

import jakarta.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mirocha.marcin.internet.banking.dao.IUserDAO;
import pl.mirocha.marcin.internet.banking.model.User;
import pl.mirocha.marcin.internet.banking.model.dto.RegisterUserDTO;
import pl.mirocha.marcin.internet.banking.services.IAuthenticationService;

@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    HttpSession httpSession;
    private final IUserDAO userDAO;

    public AuthenticationService(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void login(String login, String password) {
        this.userDAO.getByLogin(login).ifPresent(user -> {
            if(user.getPassword().equals(DigestUtils.md5Hex(password))) {
                user.setPassword(null);
                httpSession.setAttribute("user", user);
            }
        });
    }

    @Override
    public void logout() {
        httpSession.removeAttribute("user");
    }

    @Override
    public void register(RegisterUserDTO userDTO) {
        this.userDAO.persist(map(userDTO));
    }

    private User map(RegisterUserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setLogin(userDTO.getLogin());
        user.setPassword(DigestUtils.md5Hex(userDTO.getPassword()));
        user.setRole(User.Role.USER);

        return user;
    }
}