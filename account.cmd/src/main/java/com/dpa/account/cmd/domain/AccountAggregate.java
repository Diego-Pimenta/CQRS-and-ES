package com.dpa.account.cmd.domain;

import com.dpa.account.cmd.api.commands.OpenAccountCommand;
import com.dpa.account.common.events.AccountClosedEvent;
import com.dpa.account.common.events.AccountOpenedEvent;
import com.dpa.account.common.events.FundsDepositedEvent;
import com.dpa.account.common.events.FundsWithdrawnEvent;
import com.dpa.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {

    private Boolean active;
    private double balance;

    public AccountAggregate(OpenAccountCommand command) {
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .createdDate(new Date())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build());
    }

    public double getBalance() {
        return balance;
    }

    public void apply(AccountOpenedEvent event) {
        id = event.getId();
        active = true;
        balance = event.getOpeningBalance();
    }

    public void depositFunds(double amount) {
        if (!active) {
            throw new IllegalStateException("Funds cannot be deposited into a closed account!");
        }
        if (amount <= 0) {
            throw new IllegalStateException("The deposit amount must be greater than 0!");
        }

        raiseEvent(FundsDepositedEvent.builder()
                .id(id)
                .amount(amount)
                .build());
    }

    public void apply(FundsDepositedEvent event) {
        id = event.getId();
        balance += event.getAmount();
    }

    public void withdrawFunds(double amount) {
        if (!active) {
            throw new IllegalStateException("Funds cannot be withdrawn from a closed account!");
        }

        raiseEvent(FundsWithdrawnEvent.builder()
                .id(id)
                .amount(amount)
                .build());
    }

    public void apply(FundsWithdrawnEvent event) {
        id = event.getId();
        balance -= event.getAmount();
    }

    public void closeAccount() {
        if (!active) {
            throw new IllegalStateException("The bank account has already been closed!");
        }

        raiseEvent(AccountClosedEvent.builder()
                .id(id)
                .build());
    }

    public void apply(AccountClosedEvent event) {
        id = event.getId();
        active = false;
    }
}
