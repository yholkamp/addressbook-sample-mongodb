package nl.enovation.addressbook.cqrs.command;

import nl.enovation.addressbook.cqrs.pojo.PhoneNumber;

import org.axonframework.domain.AggregateIdentifier;

public class RemovePhoneNumberCommand extends AbstractContactCommand {

    private PhoneNumber phoneNumber;

    public RemovePhoneNumberCommand(AggregateIdentifier identifier) {
        super(identifier);
    }

    public RemovePhoneNumberCommand(AggregateIdentifier identifier, PhoneNumber phoneNumber) {
        super(identifier);
        this.phoneNumber = phoneNumber;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
