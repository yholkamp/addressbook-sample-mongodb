package nl.enovation.addressbook.cqrs.pojo;

import javax.validation.constraints.Size;

import nl.enovation.addressbook.cqrs.query.ContactEntry;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Plain Old Java Object representing a phone number in our system.
 * 
 * @author Yorick Holkamp
 */
@Document
public class PhoneNumberEntry {

    @Id
    @Size(min = 6, max = 14)
    private String phoneNumber;

    private PhoneNumberType phoneNumberType;


    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(phoneNumber).append(phoneNumberType).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PhoneNumberEntry) {
            final PhoneNumberEntry other = (PhoneNumberEntry) obj;
            return new EqualsBuilder().append(phoneNumber, other.getPhoneNumber()).append(phoneNumberType, other.getPhoneNumberType()).isEquals();
        } else {
            return false;
        }
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public PhoneNumberType getPhoneNumberType() {
        return phoneNumberType;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhoneNumberType(PhoneNumberType phoneNumberType) {
        this.phoneNumberType = phoneNumberType;
    }

}
