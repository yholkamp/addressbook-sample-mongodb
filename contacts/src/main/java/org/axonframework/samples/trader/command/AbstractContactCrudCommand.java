package org.axonframework.samples.trader.command;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.samples.trader.query.ContactEntry;

public abstract class AbstractContactCrudCommand extends AbstractContactCommand {
    private ContactEntry contactEntry;
    
    public AbstractContactCrudCommand(AggregateIdentifier contactId, ContactEntry contactEntry) {
    	super(contactId);
    	this.contactEntry = contactEntry; 
    }

    public ContactEntry getContactEntry() {
        return contactEntry;
    }

    public void setContactEntry(ContactEntry contact) {
        this.contactEntry = contact;
    }
}
