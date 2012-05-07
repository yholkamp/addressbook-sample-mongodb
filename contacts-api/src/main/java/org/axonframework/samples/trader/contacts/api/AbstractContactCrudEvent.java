package org.axonframework.samples.trader.contacts.api;

import org.axonframework.domain.AggregateIdentifier;

/**
 * Abstract class containing all Contact model fields for CRUD related events.
 *
 * @author Yorick Holkamp
 */
public abstract class AbstractContactCrudEvent extends AbstractContactEvent {
    protected final String firstName;
    protected final String lastName;
    protected final String phoneNumber;
    protected final String street;
    protected final String city;
    protected final String zipCode;
    protected final String department;

    public AbstractContactCrudEvent(AggregateIdentifier contactId, String firstName, String lastName, String phoneNumber, String street, String city, String zipCode, String department) {
        super(contactId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.department = department;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getDepartment() {
        return department;
    }
}
