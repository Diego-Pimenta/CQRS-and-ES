package com.dpa.account.cmd.api.commands;

import com.dpa.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class WithdrawFundsCommand extends BaseCommand {

    private double amount;
}
