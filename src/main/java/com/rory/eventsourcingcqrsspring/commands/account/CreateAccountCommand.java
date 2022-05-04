package com.rory.eventsourcingcqrsspring.commands.account;

import com.rory.eventsourcingcqrsspring.commands.BaseCommand;

public class CreateAccountCommand extends BaseCommand<String> {
    public final double accountBalance;

    public final String currency;
    public CreateAccountCommand(String id, double accountBalance, String currency) {
        super(id);
        this.accountBalance = accountBalance;
        this.currency = currency;
    }
}
