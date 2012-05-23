package nl.enovation.addressbook.cqrs.command;

import org.axonframework.domain.AggregateIdentifier;

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

    public void setPhoneNumber(String identifier) {
        phoneNumberId = identifier;
    }

}
