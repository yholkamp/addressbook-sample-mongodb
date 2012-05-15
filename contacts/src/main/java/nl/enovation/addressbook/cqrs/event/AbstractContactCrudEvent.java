package nl.enovation.addressbook.cqrs.event;

import org.axonframework.domain.AggregateIdentifier;

import nl.enovation.addressbook.cqrs.query.ContactEntry;

/**
 * Abstract class containing all Contact model fields for CRUD related events.
 * 
 * @author Yorick Holkamp
 */
public abstract class AbstractContactCrudEvent extends AbstractContactEvent {
    /**
     * The ContactEntry relevant for the event
     */
    protected final ContactEntry contactEntry;

    public AbstractContactCrudEvent(AggregateIdentifier contactId, ContactEntry contact) {
        super(contactId);
        contactEntry = contact;
    }

    /**
     * @return ContactEntry the ContactEntry
     */
    public ContactEntry getContactEntry() {
        return contactEntry;
    }
}
