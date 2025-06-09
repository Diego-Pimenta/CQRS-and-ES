package com.dpa.account.cmd.api.commands;

import com.dpa.account.common.dto.AccountType;
import com.dpa.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class OpenAccountCommand extends BaseCommand {

    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;
}
