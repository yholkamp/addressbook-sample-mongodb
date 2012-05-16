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

package nl.enovation.addressbook.cqrs.domain;

import javax.validation.constraints.NotNull;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import nl.enovation.addressbook.cqrs.event.ContactCreatedEvent;
import nl.enovation.addressbook.cqrs.event.ContactRemovedEvent;
import nl.enovation.addressbook.cqrs.event.ContactUpdatedEvent;
import nl.enovation.addressbook.cqrs.event.PhoneNumberAddedEvent;
import nl.enovation.addressbook.cqrs.event.PhoneNumberRemovedEvent;
import nl.enovation.addressbook.cqrs.pojo.PhoneNumberEntry;
import nl.enovation.addressbook.cqrs.query.ContactEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * <p>
 * The Aggregate root component of the sample application. This component handles all contact as well as address domain events.
 * </p>
 * 
 * @author Allard Buijze, Yorick Holkamp
 */
public class Contact extends AbstractAnnotatedAggregateRoot {

    private static final Logger logger = LoggerFactory.getLogger(Contact.class);

    @NotNull
    private AggregateIdentifier identifier;

    /**
     * Constructor for the Contact model, available for internal usage by Axon.
     * 
     * @param identifier
     *            of the contact
     */
    public Contact(AggregateIdentifier identifier) {
        super(identifier);
        this.identifier = identifier;
    }

    /**
     * Creates a new event source-backed Contact from a given identifier and ContactEntry.
     * 
     * @param identifier
     * @param contact
     */
    public Contact(AggregateIdentifier identifier, ContactEntry contact) {
        super(identifier);
        this.identifier = identifier;
        apply(new ContactCreatedEvent(identifier, contact));
    }

    /**
     * Changes the attributes to match the attributes of a given ContactEntry.
     * 
     * @param contact
     *            to use as a source of attributes
     */
    public void change(ContactEntry contact) {
        Assert.notNull(getIdentifier(), "Identifier cannot be null");
        apply(new ContactUpdatedEvent(getIdentifier(), contact));
    }
    
    /**
     * Adds the provided phoneNumber
     * 
     * @param phoneNumber
     */
    public void addPhoneNumber(PhoneNumberEntry phoneNumber) {
        Assert.notNull(getIdentifier(), "Identifier cannot be null");
        
        apply(new PhoneNumberAddedEvent(getIdentifier(), phoneNumber));
    }
    
    public void removePhoneNumber(String phoneNumberId) {
        Assert.notNull(getIdentifier(), "Identifier cannot be null");
        Assert.notNull(phoneNumberId, "phoneNumber cannot be null");
        
        apply(new PhoneNumberRemovedEvent(getIdentifier(), phoneNumberId));        
    }

    /**
     * Deletes the contact from our domain
     */
    public void delete() {
        Assert.notNull(getIdentifier(), "Identifier cannot be null");
        
        apply(new ContactRemovedEvent(getIdentifier()));
    }

    /**
     * Returns the identifier for this contact.
     * 
     * @return identifier
     */
    @Override
    public AggregateIdentifier getIdentifier() {
        return identifier;
    }

    /**
     * Logs the propagation of a ContactCreatedEvent.
     * 
     * @param event
     */
    @EventHandler
    protected void handleContactCreatedEvent(ContactCreatedEvent event) {
        logger.debug("Contact received a ContactCreatedEvent, identifier: {}", identifier);
    }

    /**
     * Logs the propagation of a ContactUpdatedEvent.
     * 
     * @param event
     */
    @EventHandler
    protected void handlContactUpdatedEvent(ContactUpdatedEvent event) {
        logger.debug("Contact received a ContactUpdatedEvent, identifier: {}", identifier);
    }
    

    /**
     * Logs the propagation of a PhoneNumberCreatedEvent.
     * 
     * @param event
     */
    @EventHandler
    protected void handlePhoneNumberAddedEvent(PhoneNumberAddedEvent event) {
        logger.debug("Contact received a PhoneNumberCreatedEvent, identifier: {}", identifier);
    }

    /**
     * Logs the propagation of a ContactUpdatedEvent.
     * 
     * @param event
     */
    @EventHandler
    protected void handlePhoneNumberRemovedEvent(PhoneNumberRemovedEvent event) {
        logger.debug("Contact received a PhoneNumberRemovedEvent, identifier: {}", identifier);
    }
}
