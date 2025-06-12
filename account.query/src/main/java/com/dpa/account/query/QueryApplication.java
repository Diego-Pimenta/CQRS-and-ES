package com.dpa.account.query;

import com.dpa.account.query.api.queries.FindAccountByHolderQuery;
import com.dpa.account.query.api.queries.FindAccountByIdQuery;
import com.dpa.account.query.api.queries.FindAccountWithBalanceQuery;
import com.dpa.account.query.api.queries.FindAllAccountsQuery;
import com.dpa.account.query.api.queries.QueryHandler;
import com.dpa.cqrs.core.infrastructure.QueryDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class QueryApplication {
    @Autowired
    private QueryDispatcher queryDispatcher;
    @Autowired
    private QueryHandler queryHandler;

    public static void main(String[] args) {
        SpringApplication.run(QueryApplication.class, args);
    }

    @PostConstruct
    public void registerHandlers() {
        queryDispatcher.registerHandler(FindAllAccountsQuery.class, queryHandler::handler);
        queryDispatcher.registerHandler(FindAccountByIdQuery.class, queryHandler::handler);
        queryDispatcher.registerHandler(FindAccountByHolderQuery.class, queryHandler::handler);
        queryDispatcher.registerHandler(FindAccountWithBalanceQuery.class, queryHandler::handler);
    }
}
