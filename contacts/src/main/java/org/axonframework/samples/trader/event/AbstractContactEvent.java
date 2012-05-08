package org.axonframework.samples.trader.event;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.DomainEvent;

public abstract class AbstractContactEvent extends DomainEvent {
	private static final long serialVersionUID = 3691736537072655329L;
	protected final AggregateIdentifier contactId;

    public AbstractContactEvent(AggregateIdentifier contactId) {
        this.contactId = contactId;
    }

    public AggregateIdentifier getContactId() {
        return contactId;
    }
}
