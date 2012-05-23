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

import java.util.ArrayList;
import java.util.List;

import nl.enovation.addressbook.cqrs.event.ContactCreatedEvent;
import nl.enovation.addressbook.cqrs.event.ContactRemovedEvent;
import nl.enovation.addressbook.cqrs.event.ContactUpdatedEvent;
import nl.enovation.addressbook.cqrs.event.PhoneNumberAddedEvent;
import nl.enovation.addressbook.cqrs.event.PhoneNumberRemovedEvent;
import nl.enovation.addressbook.cqrs.pojo.PhoneNumberEntry;
import nl.enovation.addressbook.cqrs.query.repositories.ContactQueryRepositoryImpl;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author Jettro Coenradie
 */
@Component
public class ContactListener {
    private static final Logger logger = LoggerFactory.getLogger(ContactListener.class);

    private ContactQueryRepositoryImpl contactRepository;

    @EventHandler
    public void handle(ContactCreatedEvent event) {
        logger.debug("Received a contactCreatedEvent for a contact with id : {}", event.getContactId());

        ContactEntry contactEntry = event.getContactEntry();
        contactEntry.setIdentifier(event.getContactId().asString());

        contactRepository.save(contactEntry);
    }

    @EventHandler
    public void handle(ContactRemovedEvent event) {
        logger.debug("Received a ContactRemovedEvent for a contact with id : {}", event.getContactId());

        ContactEntry contactEntry = contactRepository.findOne(event.getContactId().asString());

        contactRepository.delete(contactEntry);
    }

    @EventHandler
    public void handle(ContactUpdatedEvent event) {
        logger.debug("Received a ContactUpdatedEvent for a contact id : {}", event.getContactId());

        ContactEntry contactEntry = event.getContactEntry();
        contactEntry.setIdentifier(event.getContactId().asString());

        contactRepository.save(contactEntry);
    }

    @EventHandler
    public void handle(PhoneNumberAddedEvent event) {
        logger.debug("Received a PhoneNumberAddedEvent for a contact id : {}", event.getContactId());

        // TODO: Implement a MongoDB-friendly way to add the phone number without having to load & save the entire model
        ContactEntry contact = contactRepository.findOne(event.getContactId().asString());
        List<PhoneNumberEntry> phoneNumbers = contact.getPhoneNumbers();
        logger.debug("Found contactEntry with id {} and phoneNumbers {}", contact.getIdentifier(), phoneNumbers);

        if (phoneNumbers == null) {
            phoneNumbers = new ArrayList<PhoneNumberEntry>();
        }

        phoneNumbers.add(event.getPhoneNumber());
        contact.setPhoneNumbers(phoneNumbers);
        logger.debug("Set new phone numbers {}", phoneNumbers);

        contactRepository.save(contact);
        logger.debug("Saved contact {}", contact.getIdentifier());
    }

    @EventHandler
    public void handle(PhoneNumberRemovedEvent event) {
        logger.debug("Received a PhoneNumberRemovedEvent for a contact id : {} with phone number", event.getContactId(), event.getPhoneNumber());

        // TODO: Implement a MongoDB-friendly way to remove the phone number without having to load & save the entire model
        ContactEntry contact = contactRepository.findOne(event.getContactId().asString());
        List<PhoneNumberEntry> phoneNumbers = contact.getPhoneNumbers();

        if (phoneNumbers == null) {
            phoneNumbers = new ArrayList<PhoneNumberEntry>();
        }

        int phoneNumberCount = phoneNumbers.size();

        // TODO: Start using some other identifier or more elegant data structure
        for (PhoneNumberEntry phoneNumberEntry : phoneNumbers) {
            if (phoneNumberEntry.getPhoneNumber().equals(event.getPhoneNumber())) {
                logger.debug("Found phoneNumber {}, removing it from Contact", phoneNumberEntry);
                phoneNumbers.remove(phoneNumberEntry);
                break;
            }
        }

        Assert.isTrue(phoneNumberCount - 1 == phoneNumbers.size(), "PhoneNumber array length should have decreased by one, instead received "
                                                                   + phoneNumberCount);
        contact.setPhoneNumbers(phoneNumbers);
        contactRepository.save(contact);
    }

    @Autowired
    @Qualifier("contactQueryRepository")
    public void setContactQueryRepository(ContactQueryRepositoryImpl contactRepository) {
        this.contactRepository = contactRepository;
    }
}
