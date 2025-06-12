package com.dpa.account.cmd;

import com.dpa.account.cmd.api.commands.CloseAccountCommand;
import com.dpa.account.cmd.api.commands.CommandHandler;
import com.dpa.account.cmd.api.commands.DepositFundsCommand;
import com.dpa.account.cmd.api.commands.OpenAccountCommand;
import com.dpa.account.cmd.api.commands.WithdrawFundsCommand;
import com.dpa.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CommandApplication {
	@Autowired
	private CommandDispatcher commandDispatcher;
	@Autowired
	private CommandHandler commandHandler;

	public static void main(String[] args) {
		SpringApplication.run(CommandApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers() {
		commandDispatcher.registerHandler(OpenAccountCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(DepositFundsCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(WithdrawFundsCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(CloseAccountCommand.class, commandHandler::handle);
	}
}
