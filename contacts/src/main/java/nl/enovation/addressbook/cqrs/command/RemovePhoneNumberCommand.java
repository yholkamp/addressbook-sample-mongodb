package nl.enovation.addressbook.cqrs.command;

import org.axonframework.domain.AggregateIdentifier;

/**
 * <p>
 * Remove a PhoneNumber from the given contact.
 * </p>
 *
 * @author Yorick Holkamp
 */
public class RemovePhoneNumberCommand extends AbstractContactCommand {

    private String phoneNumberId;

    public RemovePhoneNumberCommand(AggregateIdentifier contactIdentifier) {
        super(contactIdentifier);
    }

    public RemovePhoneNumberCommand(AggregateIdentifier contactIdentifier, String identifier) {
        super(contactIdentifier);
        phoneNumberId = identifier;
    }

    public String getPhoneNumberId() {
        return phoneNumberId;
    }
}
