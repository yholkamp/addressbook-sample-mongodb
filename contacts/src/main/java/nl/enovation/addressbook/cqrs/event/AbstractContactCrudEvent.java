package nl.enovation.addressbook.cqrs.event;

import nl.enovation.addressbook.cqrs.query.ContactEntry;

import org.axonframework.domain.AggregateIdentifier;

/**
 * Abstract class containing all Contact model fields for CRUD related events.
 * 
 * @author Yorick Holkamp
 */
public abstract class AbstractContactCrudEvent extends AbstractContactEvent {
    /**
     * The ContactEntry relevant for the event
     */
    private final ContactEntry contactEntry;

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
