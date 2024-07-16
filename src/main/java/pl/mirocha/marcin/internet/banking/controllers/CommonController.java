package pl.mirocha.marcin.internet.banking.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommonController {


    @RequestMapping(path = {"/main", "/", "/index"}, method = RequestMethod.GET)
    public String main() {
        return "index";
    }

    /*     return "redirect:/main";   */
    @RequestMapping(path = "/contact", method = RequestMethod.GET)
    public String contact() {
        return "contact";
    }
}
