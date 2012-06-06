package nl.enovation.addressbook.cqrs.command;

import nl.enovation.addressbook.cqrs.query.ContactEntry;

import org.axonframework.domain.AggregateIdentifier;

/**
 * <p>
 * Parent class for all contact related commands that require a full ContactEntry for their completion.
 * </p>
 * 
 * @author Yorick Holkamp
 */
public abstract class AbstractContactCrudCommand extends AbstractContactCommand {

    private ContactEntry contactEntry;

    public AbstractContactCrudCommand(AggregateIdentifier identifier) {
        super(identifier);
    }

    public AbstractContactCrudCommand(AggregateIdentifier identifier, ContactEntry contactEntry) {
        super(identifier);
        this.contactEntry = contactEntry;
    }

    public ContactEntry getContactEntry() {
        return contactEntry;
    }

    public void setContactEntry(ContactEntry contact) {
        contactEntry = contact;
    }
}
