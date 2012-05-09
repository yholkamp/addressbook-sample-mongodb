package org.axonframework.samples.trader.command;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.samples.trader.query.ContactEntry;

/**
 * <p>Parent class for all contact related commands that require a ContactEntry to be processed properly.</p>
 * 
 * @author Yorick Holkamp
 */
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
