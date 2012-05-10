package nl.enovation.addressbook.cqrs.pojo;

import javax.validation.constraints.Size;

/**
 * Plain Old Java Object representing a phone number in our system.
 * 
 * @author Yorick Holkamp
 */
public class PhoneNumber {

    @Size(min = 6, max = 14)
    private String phoneNumber;

    private PhoneNumberType phoneNumberType;

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
        PhoneNumber other = (PhoneNumber) obj;
        if (phoneNumber == null) {
            if (other.phoneNumber != null) {
                return false;
            }
        } else if (!phoneNumber.equals(other.phoneNumber)) {
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhoneNumberType(PhoneNumberType phoneNumberType) {
        this.phoneNumberType = phoneNumberType;
    }

}
