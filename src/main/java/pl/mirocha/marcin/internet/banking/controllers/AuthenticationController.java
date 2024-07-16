package pl.mirocha.marcin.internet.banking.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mirocha.marcin.internet.banking.exceptions.UserAlreadyExistException;
import pl.mirocha.marcin.internet.banking.exceptions.UserValidationException;
import pl.mirocha.marcin.internet.banking.model.dto.RegisterUserDTO;
import pl.mirocha.marcin.internet.banking.services.IAuthenticationService;
import pl.mirocha.marcin.internet.banking.validators.UserValidator;
@Controller
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    public AuthenticationController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String login, @RequestParam String password,
                        HttpSession httpSession) {
        try {
            UserValidator.validateLogin(login);
            UserValidator.validatePassword(password);
        } catch (UserValidationException e) {
            return "redirect:/login";
        }
        this.authenticationService.login(login, password);
        if(httpSession.getAttribute("user") != null) {
            return "redirect:/main";
        }
        return "redirect:/login";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession httpSession) {
        this.authenticationService.logout();
        httpSession.setAttribute("user", null);
        return "redirect:/main";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("userModel", new RegisterUserDTO());
        return "register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute RegisterUserDTO userDTO) {
        try {
            UserValidator.validateUserDTO(userDTO);
            this.authenticationService.register(userDTO);
        } catch (UserAlreadyExistException | UserValidationException e) {
            return "redirect:/register";
        }
        return "redirect:/main";
    }



}
