package com.dpa.account.cmd.infrastructure;

import com.dpa.account.cmd.domain.AccountAggregate;
import com.dpa.account.cmd.domain.EventStoreRepository;
import com.dpa.cqrs.core.events.BaseEvent;
import com.dpa.cqrs.core.events.EventModel;
import com.dpa.cqrs.core.exceptions.AggregateNotFoundException;
import com.dpa.cqrs.core.exceptions.ConcurrencyException;
import com.dpa.cqrs.core.infrastructure.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountEventStore implements EventStore {
    @Autowired
    private EventStoreRepository eventStoreRepository;

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1 && expectedVersion != eventStream.get(eventStream.size() - 1).getVersion()) {
            throw new ConcurrencyException();
        }

        var version = expectedVersion;
        for (var event : events) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .timeStamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();
            var persistedEvent = eventStoreRepository.save(eventModel);
            if (persistedEvent != null) {
                // TODO: produce event to Kafka
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException("Incorrect account ID provided!");
        }
        return eventStream.stream().map(e -> e.getEventData()).collect(Collectors.toList());
    }
}
