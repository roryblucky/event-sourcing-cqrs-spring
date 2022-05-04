package com.rory.eventsourcingcqrsspring.services;

import com.rory.eventsourcingcqrsspring.commands.account.CreateAccountCommand;
import com.rory.eventsourcingcqrsspring.commands.money.CreditMoneyCommand;
import com.rory.eventsourcingcqrsspring.commands.money.DebitMoneyCommand;
import com.rory.eventsourcingcqrsspring.dto.AccountCreateDTO;
import com.rory.eventsourcingcqrsspring.dto.MoneyCreditDTO;
import com.rory.eventsourcingcqrsspring.dto.MoneyDebitDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class AccountCommandService {
    @Autowired
    private CommandGateway commandGateway;

    public CompletableFuture<Object> createAccount(AccountCreateDTO accountCreateDTO) {
        CompletableFuture<Object> send = commandGateway.send(new CreateAccountCommand(UUID.randomUUID().toString(), accountCreateDTO.getStartingBalance(), accountCreateDTO.getCurrency()));
        return send;
    }

    public CompletableFuture<String> creditMoneyToAccount(String accountNumber, MoneyCreditDTO moneyCreditDTO) {
        return commandGateway.send(new CreditMoneyCommand(accountNumber, moneyCreditDTO.getCreditAmount(), moneyCreditDTO.getCurrency()));
    }

    public CompletableFuture<String> debitMoneyFromAccount(String accountNumber, MoneyDebitDTO moneyDebitDTO) {
        return commandGateway.send(new DebitMoneyCommand(accountNumber, moneyDebitDTO.getDebitAmount(), moneyDebitDTO.getCurrency()));
    }
}
