package pl.mirocha.marcin.internet.banking.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.mirocha.marcin.internet.banking.exceptions.AccountValidationException;
import pl.mirocha.marcin.internet.banking.model.Account;
import pl.mirocha.marcin.internet.banking.services.IAccountService;
import pl.mirocha.marcin.internet.banking.validators.AccountValidator;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Optional;

@Controller
@RequestMapping(path = "/account")
public class AccountController {

    private final IAccountService accountService;

    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(path = "/add",method = RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("accountModel", new Account());
        return "account-form";
    }

    @RequestMapping(path = "/add",method = RequestMethod.POST)
    public String add(@ModelAttribute Account account) {
        try {
            AccountValidator.validateAccount(account);
        }catch (AccountValidationException e){
            //TODO ADD POPUP frontend
            return "redirect:/account/add";
        }
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(26);

        long timestamp = Instant.now().toEpochMilli();
        sb.append(String.format("%013d", timestamp));

        for (int i = 0; i < 13; i++) {
            sb.append(random.nextInt(10));
        }
        String accountNumber = sb.toString();
        account.setAccountNumber(accountNumber);
        System.out.println("Account number has been made its " + accountNumber);

        accountService.persist(account);
        return "redirect:/main";
    }


    @RequestMapping(path = "/donate/{id}",method = RequestMethod.GET)
    public String donate(@PathVariable int id, Model model){
        Optional<Account> accountBox = this.accountService.getById(id);
        if (accountBox.isEmpty()){
            return "redirect:/main";
        }
        model.addAttribute("accountModel", accountBox.get());
        return "donate-form";
    }

    @RequestMapping(path = "/donate/{id}",method = RequestMethod.POST)
    public String donate(@PathVariable int id, @ModelAttribute Account account) {
        this.accountService.donateBalance(id,account);

        return "redirect:/main";
    }

    @RequestMapping(path = "/transfer/{id}",method = RequestMethod.GET)
    public String transfer(@PathVariable int id,
                           Model model ){  //TODO Do i need accountNumberForTransfer ?  param?
        Optional<Account> accountBox = this.accountService.getById(id);
        if (accountBox.isEmpty()){
            return "redirect:/main";
        }
        model.addAttribute("accountModel", accountBox.get());
        return "account-transfer-form";
    }

    @RequestMapping(path = "/transfer/{id}",method = RequestMethod.POST)
    public String transfer(@PathVariable int id,
                           @ModelAttribute Account account,
                           @RequestParam String accountNumberForTransfer) {
        //TODO here code for method


        return "redirect:/main";
    }
}
