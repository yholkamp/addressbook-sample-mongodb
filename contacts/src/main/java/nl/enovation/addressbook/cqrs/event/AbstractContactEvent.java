package nl.enovation.addressbook.cqrs.event;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.DomainEvent;

public abstract class AbstractContactEvent extends DomainEvent {
    protected final AggregateIdentifier contactId;

    public AbstractContactEvent(AggregateIdentifier contactId) {
        this.contactId = contactId;
    }

    public AggregateIdentifier getContactId() {
        return contactId;
    }
}
