package nl.enovation.addressbook.cqrs.pojo;

import javax.validation.constraints.Size;

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

    /**
     * Eclipse-generated equals method.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PhoneNumberEntry other = (PhoneNumberEntry) obj;
        if (phoneNumber == null) {
            if (other.phoneNumber != null) {
                return false;
            }
        } else if (!phoneNumber.equals(other.phoneNumber)) {
            return false;
        }
        if (phoneNumberType != other.phoneNumberType) {
            return false;
        }
        return true;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public PhoneNumberType getPhoneNumberType() {
        return phoneNumberType;
    }

    /**
     * Eclipse-generated hashCode method.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
        result = prime * result + ((phoneNumberType == null) ? 0 : phoneNumberType.hashCode());
        return result;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhoneNumberType(PhoneNumberType phoneNumberType) {
        this.phoneNumberType = phoneNumberType;
    }

}
