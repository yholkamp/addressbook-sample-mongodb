/*
 * Copyright (c) 2010. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.enovation.addressbook.cqrs.query;

import java.util.List;

import javax.validation.constraints.Size;

import nl.enovation.addressbook.cqrs.pojo.PhoneNumberEntry;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class representing a ContactEntry in our main view. Used for both generic listing and detailed viewing of a contact.
 * 
 * @author Allard Buijze, Yorick Holkamp
 */
@Document
public class ContactEntry {

    @Id
    private String identifier;

    @Size(min = 1, max = 255)
    private String firstName;

    @Size(min = 2, max = 255)
    private String lastName;

    private String street;

    private String city;

    private String zipCode;

    private String department;

    private List<PhoneNumberEntry> phoneNumbers;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ContactEntry) {
            final ContactEntry other = (ContactEntry) obj;
            return new EqualsBuilder().append(identifier, other.getIdentifier()).append(firstName, other.getFirstName()).append(lastName, other.getLastName())
                                      .append(street, other.getStreet()).append(city, other.getCity()).append(zipCode, other.getZipCode())
                                      .append(phoneNumbers, other.getPhoneNumbers()).append(department, other.getDepartment()).isEquals();
        } else {
            return false;
        }
    }

    public String getCity() {
        return city;
    }

    public String getDepartment() {
        return department;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getLastName() {
        return lastName;
    }

    /**
     * Retrieves a specific PhoneNumber entry using a phone number string if it can be found.
     * 
     * @param number
     *            String containing the phone number
     * @return the PhoneNumberEntry containing the provided phone number string
     */
    public PhoneNumberEntry getPhoneNumberEntry(String number) {
        for (PhoneNumberEntry phoneNumber : phoneNumbers) {
            if (number.equals(phoneNumber.getPhoneNumber())) {
                return phoneNumber;
            }
        }
        return null;
    }

    public List<PhoneNumberEntry> getPhoneNumbers() {
        return phoneNumbers;
    }

    public String getStreet() {
        return street;
    }

    public String getZipCode() {
        return zipCode;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(identifier).append(firstName).append(lastName).append(street).append(city).append(zipCode)
                                    .append(phoneNumbers.hashCode()).append(department).toHashCode();
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setFirstName(String name) {
        firstName = name;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumbers(List<PhoneNumberEntry> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
