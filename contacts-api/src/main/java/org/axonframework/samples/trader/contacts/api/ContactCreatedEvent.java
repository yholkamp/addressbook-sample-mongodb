/*
 * Copyright (c) 2010-2011. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.axonframework.samples.trader.contacts.api;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.DomainEvent;

/**
 * @author Allard Buijze
 */
public class ContactCreatedEvent extends DomainEvent {

	private final AggregateIdentifier contactId;
	private final String firstName;
	private final String lastName;
	private final String phoneNumber;
	private final String street;
	private final String city;
	private final String zipCode;

	public ContactCreatedEvent(AggregateIdentifier contactId, String firstName,
			String lastName, String phoneNumber, String street, String city,
			String zipCode) {
		this.contactId = contactId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
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

	public AggregateIdentifier getContactId() {
		return contactId;
	}
}
