package com.rory.eventsourcingcqrsspring.aggregates;

import com.rory.eventsourcingcqrsspring.commands.account.CreateAccountCommand;
import com.rory.eventsourcingcqrsspring.commands.money.CreditMoneyCommand;
import com.rory.eventsourcingcqrsspring.commands.money.DebitMoneyCommand;
import com.rory.eventsourcingcqrsspring.events.account.AccountActivatedEvent;
import com.rory.eventsourcingcqrsspring.events.account.AccountCreatedEvent;
import com.rory.eventsourcingcqrsspring.events.account.AccountHeldEvent;
import com.rory.eventsourcingcqrsspring.events.money.MoneyCreditedEvent;
import com.rory.eventsourcingcqrsspring.events.money.MoneyDebitedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {

    @AggregateIdentifier
    private String id;

    private double accountBalance;

    private String currency;

    private String status;

    public AccountAggregate() {}

    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        AggregateLifecycle.apply(new AccountCreatedEvent(createAccountCommand.id, createAccountCommand.accountBalance, createAccountCommand.currency))
                .andThenApply(() -> new AccountActivatedEvent("1", Status.ACTIVATED));
    }

    @EventSourcingHandler
    protected void on(AccountCreatedEvent accountCreatedEvent) {
        this.id = accountCreatedEvent.id;
        this.accountBalance = accountCreatedEvent.accountBalance;
        this.currency = accountCreatedEvent.currency;
        this.status = String.valueOf(Status.CREATED);

        AggregateLifecycle.apply(new AccountActivatedEvent(this.id, Status.ACTIVATED));
    }

    @EventSourcingHandler
    protected void on(AccountActivatedEvent accountActivatedEvent) {
        this.status = String.valueOf(accountActivatedEvent.status);
    }

    @CommandHandler
    protected void creditMoney(CreditMoneyCommand command) {
        AggregateLifecycle.apply(new MoneyCreditedEvent(command.id, command.creditAmount, command.currency));
    }

    @EventSourcingHandler
    protected void on(MoneyCreditedEvent moneyCreditedEvent) {
        if (this.accountBalance < 0 & (this.accountBalance + moneyCreditedEvent.creditAmount) >= 0){
            AggregateLifecycle.apply(new AccountActivatedEvent(this.id, Status.ACTIVATED));
        }

        this.accountBalance += moneyCreditedEvent.creditAmount;
    }

    @CommandHandler
    protected void on(DebitMoneyCommand debitMoneyCommand){
        AggregateLifecycle.apply(new MoneyDebitedEvent(debitMoneyCommand.id, debitMoneyCommand.debitAmount, debitMoneyCommand.currency));
    }

    @EventSourcingHandler
    protected void on(MoneyDebitedEvent moneyDebitedEvent){

        if (this.accountBalance >= 0 & (this.accountBalance - moneyDebitedEvent.debitAmount) < 0){
            AggregateLifecycle.apply(new AccountHeldEvent(this.id, Status.HOLD));
        }

        this.accountBalance -= moneyDebitedEvent.debitAmount;

    }

    @EventSourcingHandler
    protected void on(AccountHeldEvent accountHeldEvent){
        this.status = String.valueOf(accountHeldEvent.status);
    }
}
