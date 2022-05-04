package com.rory.eventsourcingcqrsspring.controllers;

import com.rory.eventsourcingcqrsspring.dto.AccountCreateDTO;
import com.rory.eventsourcingcqrsspring.dto.MoneyCreditDTO;
import com.rory.eventsourcingcqrsspring.dto.MoneyDebitDTO;
import com.rory.eventsourcingcqrsspring.services.AccountCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/bank-accounts")
public class AccountCommandController {
    @Autowired
    private AccountCommandService accountCommandService;

    @PostMapping
    public CompletableFuture<Object> createAccount(@RequestBody AccountCreateDTO accountCreateDTO){
        return accountCommandService.createAccount(accountCreateDTO);
    }

    @PutMapping(value = "/credits/{accountNumber}")
    public CompletableFuture<String> creditMoneyToAccount(@PathVariable(value = "accountNumber") String accountNumber,
                                                          @RequestBody MoneyCreditDTO moneyCreditDTO){
        return accountCommandService.creditMoneyToAccount(accountNumber, moneyCreditDTO);
    }

    @PutMapping(value = "/debits/{accountNumber}")
    public CompletableFuture<String> debitMoneyFromAccount(@PathVariable(value = "accountNumber") String accountNumber,
                                                           @RequestBody MoneyDebitDTO moneyDebitDTO){
        return accountCommandService.debitMoneyFromAccount(accountNumber, moneyDebitDTO);
    }
}
