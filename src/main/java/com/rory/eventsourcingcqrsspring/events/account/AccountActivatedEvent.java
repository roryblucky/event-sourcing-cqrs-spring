package com.rory.eventsourcingcqrsspring.events.account;

import com.rory.eventsourcingcqrsspring.aggregates.Status;
import com.rory.eventsourcingcqrsspring.events.BaseEvent;

public class AccountActivatedEvent extends BaseEvent<String> {
    public final Status status;

    public AccountActivatedEvent(String id, Status status) {
        super(id);
        this.status = status;
    }
}
