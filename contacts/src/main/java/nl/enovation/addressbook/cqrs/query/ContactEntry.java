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

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
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

    public String getName() {
        return firstName;
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
    
    public PhoneNumberEntry getPhoneNumberEntry(String number) {
        for(PhoneNumberEntry phoneNumber : phoneNumbers) {
            if(number.equals(phoneNumber.getPhoneNumber())) {
                return phoneNumber;
            }
        }
        return null;
    }

    /**
     * Eclipse-generated hashCode method.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((department == null) ? 0 : department.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((phoneNumbers == null) ? 0 : phoneNumbers.hashCode());
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
        return result;
    }

    /**
     * Eclipse-generated equals method.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ContactEntry other = (ContactEntry) obj;
        if (city == null) {
            if (other.city != null) return false;
        } else if (!city.equals(other.city)) return false;
        if (department == null) {
            if (other.department != null) return false;
        } else if (!department.equals(other.department)) return false;
        if (firstName == null) {
            if (other.firstName != null) return false;
        } else if (!firstName.equals(other.firstName)) return false;
        if (identifier == null) {
            if (other.identifier != null) return false;
        } else if (!identifier.equals(other.identifier)) return false;
        if (lastName == null) {
            if (other.lastName != null) return false;
        } else if (!lastName.equals(other.lastName)) return false;
        if (phoneNumbers == null) {
            if (other.phoneNumbers != null) return false;
        } else if (!phoneNumbers.equals(other.phoneNumbers)) return false;
        if (street == null) {
            if (other.street != null) return false;
        } else if (!street.equals(other.street)) return false;
        if (zipCode == null) {
            if (other.zipCode != null) return false;
        } else if (!zipCode.equals(other.zipCode)) return false;
        return true;
    }
}
