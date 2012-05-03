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

package org.axonframework.samples.trader.contacts.api;

import org.axonframework.domain.AggregateIdentifier;
import org.springframework.util.Assert;

/**
 * <p>Create a new contact with the provided name</p>
 *
 * @author Jettro Coenradie
 */
public class CreateContactCommand extends AbstractContactCommand {
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String street;
	private String city;
	private String zipCode;

    /**
     * Set the name for the new Contact. An exception is thrown when the provided name is empty
     *
     * @param firstName String containing the name for the new contact
     */
    public void setFirstName(String firstName) {
        Assert.hasText(firstName, "Name for new contact must contain text");
        this.firstName = firstName;
    }

    /**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
     * Returns the name for the new contact
     *
     * @return String containing the name for the new contact
     */
    public String getFirstName() {
        return firstName;
    }

}
