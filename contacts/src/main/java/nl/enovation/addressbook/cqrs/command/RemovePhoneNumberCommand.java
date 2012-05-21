package nl.enovation.addressbook.cqrs.command;

import nl.enovation.addressbook.cqrs.pojo.PhoneNumberEntry;

import org.axonframework.domain.AggregateIdentifier;

public class RemovePhoneNumberCommand extends AbstractContactCommand {

    private String phoneNumberId;

    public RemovePhoneNumberCommand(AggregateIdentifier contactIdentifier) {
        super(contactIdentifier);
    }

    public RemovePhoneNumberCommand(AggregateIdentifier contactIdentifier, String identifier) {
        super(contactIdentifier);
        this.phoneNumberId = identifier;
    }

    public String getPhoneNumberId() {
        return phoneNumberId;
    }

    public void setPhoneNumber(String identifier) {
        this.phoneNumberId = identifier;
    }

}
