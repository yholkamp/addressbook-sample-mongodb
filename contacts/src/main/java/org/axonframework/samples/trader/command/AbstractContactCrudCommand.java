package org.axonframework.samples.trader.command;

import org.axonframework.samples.trader.query.ContactEntry;

public abstract class AbstractContactCrudCommand extends AbstractContactCommand {
    private ContactEntry contactEntry;

    public ContactEntry getContactEntry() {
        return contactEntry;
    }

    public void setContactEntry(ContactEntry contact) {
        this.contactEntry = contact;
    }
}
