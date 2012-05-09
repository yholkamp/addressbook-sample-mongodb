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

package nl.enovation.addressbook.cqrs.query;

import org.axonframework.eventhandling.annotation.EventHandler;
import nl.enovation.addressbook.cqrs.event.ContactCreatedEvent;
import nl.enovation.addressbook.cqrs.event.ContactDeletedEvent;
import nl.enovation.addressbook.cqrs.event.ContactUpdatedEvent;
import nl.enovation.addressbook.cqrs.query.repositories.ContactQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Jettro Coenradie
 */
@Component
public class ContactListener {
    private final static Logger logger = LoggerFactory.getLogger(ContactListener.class);

    private ContactQueryRepository contactRepository;

    @EventHandler
    public void handle(ContactCreatedEvent event) {
        logger.debug("Received a contactCreatedEvent for a contact with id : {}", event.getContactId());

        ContactEntry contactEntry = event.getContactEntry();
        contactEntry.setIdentifier(event.getContactId().asString());

        contactRepository.save(contactEntry);
    }

    @EventHandler
    public void handle(ContactDeletedEvent event) {
        logger.debug("Received a ContactRemovedEvent for a contact with id : {}", event.getContactId());

        ContactEntry contactEntry = contactRepository.findOne(event.getContactId().asString());

        contactRepository.delete(contactEntry);
    }

    @EventHandler
    public void handle(ContactUpdatedEvent event) {
        logger.debug("Received a contactNameChangedEvent for a contact id : {}", event.getContactId());

        ContactEntry contactEntry = event.getContactEntry();
        contactEntry.setIdentifier(event.getContactId().asString());

        contactRepository.save(contactEntry);
    }

    @Autowired
    public void setContactRepository(ContactQueryRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
}
