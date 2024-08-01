package pl.mirocha.marcin.internet.banking.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.mirocha.marcin.internet.banking.services.IAccountService;

@Controller
public class CommonController {

    private final IAccountService accountService;

    public CommonController(IAccountService iAccountService) {
        this.accountService = iAccountService;
    }

    @RequestMapping(path = {"/main", "/", "/index"}, method = RequestMethod.GET)
    public String main(Model model, HttpSession httpSession) {

        if(httpSession.getAttribute("user") != null) {
            model.addAttribute("accounts",
                    this.accountService.getByCurrentUser());
        }
        return "index";
    }

    @RequestMapping(path = "/contact", method = RequestMethod.GET)
    public String contact() {
        return "contact";
    }
}

