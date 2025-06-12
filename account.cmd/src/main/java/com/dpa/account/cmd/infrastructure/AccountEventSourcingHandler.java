package com.dpa.account.cmd.infrastructure;

import com.dpa.account.cmd.domain.AccountAggregate;
import com.dpa.cqrs.core.domain.AggregateRoot;
import com.dpa.cqrs.core.handlers.EventSourcingHandler;
import com.dpa.cqrs.core.infrastructure.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {
    @Autowired
    private EventStore eventStore;

    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommitedChanges(), aggregate.getVersion());
        aggregate.mockChangesAsCommited();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvents(id);
        if (events != null && !events.isEmpty()) {
            aggregate.replayEvents(events);
            var latestVersion = events.stream().map(e -> e.getVersion()).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }
        return aggregate;
    }
}
