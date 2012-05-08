package org.axonframework.samples.trader.event;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.samples.trader.query.ContactEntry;

/**
 * Abstract class containing all Contact model fields for CRUD related events.
 *
 * @author Yorick Holkamp
 */
public abstract class AbstractContactCrudEvent extends AbstractContactEvent {
    protected final ContactEntry contactEntry;

    public AbstractContactCrudEvent(AggregateIdentifier contactId, ContactEntry contact) {
        super(contactId);
        this.contactEntry = contact;
    }

    public ContactEntry getContactEntry() {
        return contactEntry;
    }
}
