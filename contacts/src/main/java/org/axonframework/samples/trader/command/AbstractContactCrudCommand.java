package org.axonframework.samples.trader.command;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.samples.trader.query.ContactEntry;

public abstract class AbstractContactCrudCommand extends AbstractContactCommand {
    
    public AbstractContactCrudCommand(AggregateIdentifier identifier, ContactEntry contactEntry) {
        super(identifier);
        this.contactEntry = contactEntry;
    }
    
    public AbstractContactCrudCommand(AggregateIdentifier identifier) {
        super(identifier);
    }

    private ContactEntry contactEntry;

    public ContactEntry getContactEntry() {
        return contactEntry;
    }

    public void setContactEntry(ContactEntry contact) {
        this.contactEntry = contact;
    }
}
