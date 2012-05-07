/*
 * Copyright (c) 2010-2012. Axon Framework
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

package org.axonframework.samples.trader.query.contacts;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.samples.trader.contacts.api.AbstractContactCrudEvent;
import org.axonframework.samples.trader.contacts.api.ContactUpdatedEvent;
import org.axonframework.samples.trader.query.contacts.repositories.ContactQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.axonframework.samples.trader.contacts.api.ContactCreatedEvent;
import org.axonframework.samples.trader.contacts.api.ContactDeletedEvent;

/**
 * @author Jettro Coenradie
 */
@Component
public class ContactListener {
	private final static Logger logger = LoggerFactory.getLogger(ContactListener.class);

	private ContactQueryRepository contactRepository;

	@EventHandler
	public void handle(ContactCreatedEvent event) {
		logger.debug("Received a contactCreatedEvent for a contact with name : {}", event.getFirstName());
		
		ContactEntry contactEntry = new ContactEntry();
		contactEntry.setIdentifier(event.getContactId().asString());
        populateContactEntryFromEvent(contactEntry, event);

		contactRepository.save(contactEntry);
	}
	
	@EventHandler
	public void handle(ContactUpdatedEvent event) {
		logger.debug("Received a contactNameChangedEvent for a contact id : {}", event.getContactId());
		
		ContactEntry contactEntry = contactRepository.findOne(event.getContactId().asString());
        populateContactEntryFromEvent(contactEntry, event);

		contactRepository.save(contactEntry);
	}

    @EventHandler
    public void handle(ContactDeletedEvent event) {
        logger.debug("Received a ContactRemovedEvent for a contact with id : {}", event.getContactId());

        ContactEntry contactEntry = contactRepository.findOne(event.getContactId().asString());

        contactRepository.delete(contactEntry);
    }

	@Autowired
	public void setContactRepository(ContactQueryRepository contactRepository) {
		this.contactRepository = contactRepository;
	}

    /**
     * Populates a given ContactEntry with fields available in the ContactCrudEvent.
     * @param contactEntry  ContactEntry to populate with data
     * @param event         Source of information
     */
    private void populateContactEntryFromEvent(ContactEntry contactEntry, AbstractContactCrudEvent event) {
        contactEntry.setFirstName(event.getFirstName());
        contactEntry.setLastName(event.getLastName());
        contactEntry.setPhoneNumber(event.getPhoneNumber());
        contactEntry.setStreet(event.getStreet());
        contactEntry.setCity(event.getCity());
        contactEntry.setZipCode(event.getZipCode());
        contactEntry.setDepartment(event.getDepartment());
    }
}
