package com.dpa.account.query.infrastructure.handlers;

import com.dpa.account.common.events.AccountClosedEvent;
import com.dpa.account.common.events.AccountOpenedEvent;
import com.dpa.account.common.events.FundsDepositedEvent;
import com.dpa.account.common.events.FundsWithdrawnEvent;

public interface EventHandler {

    void on(AccountOpenedEvent event);

    void on(FundsDepositedEvent event);

    void on(FundsWithdrawnEvent event);

    void on(AccountClosedEvent event);
}
