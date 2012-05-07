package org.axonframework.samples.trader.contacts.api;

import org.axonframework.samples.trader.contacts.query.ContactEntry;

public abstract class AbstractContactCrudCommand extends AbstractContactCommand {
    private ContactEntry contactEntry;

    public ContactEntry getContactEntry() {
        return contactEntry;
    }

    public void setContactEntry(ContactEntry contact) {
        this.contactEntry = contact;
    }
}
