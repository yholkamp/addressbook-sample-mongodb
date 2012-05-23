package nl.enovation.addressbook.cqrs.command;

import nl.enovation.addressbook.cqrs.pojo.PhoneNumberEntry;

import org.axonframework.domain.AggregateIdentifier;

/**
 * Command that will add a phone number to an existing contact
 * 
 * @author Yorick Holkamp
 */
public class CreatePhoneNumberCommand extends AbstractContactCommand {

    private PhoneNumberEntry phoneNumber;

    public CreatePhoneNumberCommand(AggregateIdentifier contactIdentifier) {
        super(contactIdentifier);
    }

    public CreatePhoneNumberCommand(AggregateIdentifier contactIdentifier, PhoneNumberEntry phoneNumber) {
        super(contactIdentifier);
        this.phoneNumber = phoneNumber;
    }

    public PhoneNumberEntry getPhoneNumber() {
        return phoneNumber;
    }

}
