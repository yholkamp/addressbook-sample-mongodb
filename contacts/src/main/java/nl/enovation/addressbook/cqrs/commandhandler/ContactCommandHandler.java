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

package nl.enovation.addressbook.cqrs.commandhandler;

import nl.enovation.addressbook.cqrs.command.CreateContactCommand;
import nl.enovation.addressbook.cqrs.command.CreatePhoneNumberCommand;
import nl.enovation.addressbook.cqrs.command.RemoveContactCommand;
import nl.enovation.addressbook.cqrs.command.RemovePhoneNumberCommand;
import nl.enovation.addressbook.cqrs.command.UpdateContactCommand;
import nl.enovation.addressbook.cqrs.domain.Contact;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.axonframework.unitofwork.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * <p>
 * Command handler that can be used to create and update Contacts. It can also be used to register and remove addresses.
 * </p>
 * <p>
 * The provided contactRepository is used to store the changes.
 * </p>
 * 
 * @author Allard Buijze, Yorick Holkamp
 */
@Component
public class ContactCommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactCommandHandler.class);

    private Repository<Contact> contactRepository;

    public Repository<Contact> getContactRepository() {
        return contactRepository;
    }

    /**
     * Creates a new contact based on the provided data.
     * 
     * @param command
     *            CreateContactCommand object that contains the needed data to create a new contact
     * @param unitOfWork
     *            Unit of work for the current running thread
     */
    @CommandHandler
    public void handleCreateContact(final CreateContactCommand command, UnitOfWork unitOfWork) {
        LOGGER.debug("Received a command for a new contact with id : {}", command.getContactId());

        Contact contact = new Contact(command.getContactId(), command.getContactEntry());
        contactRepository.add(contact);
    }

    /**
     * Adds a new phone number to the list of phone numbers of the provided contact.
     * <p/>
     * An {@code AggregateNotFoundException} is thrown if the identifier does not represent a valid contact.
     * 
     * @param command
     *            UpdateContactCommand that contains the identifier and the data to be updated
     * @param unitOfWork
     *            Unit of work for the current running thread
     */
    @CommandHandler
    public void handleCreatePhoneNumber(final CreatePhoneNumberCommand command, UnitOfWork unitOfWork) {
        LOGGER.debug("Received a updateContactCommand for id : {}", command.getContactId());
        Assert.notNull(command.getContactId(), "ContactIdentifier may not be null");

        Contact contact = contactRepository.load(command.getContactId());
        Assert.notNull(contact.getIdentifier(), "Contact identifier cannot be null");

        contact.addPhoneNumber(command.getPhoneNumber());
    }

    /**
     * Removes the contact belonging to the contactId as provided by the command.
     * 
     * @param command
     *            RemoveContactCommand containing the identifier of the contact to be removed
     * @param unitOfWork
     *            Unit of work for the current running thread
     */
    @CommandHandler
    public void handleRemoveContact(RemoveContactCommand command, UnitOfWork unitOfWork) {
        LOGGER.debug("Received a remove command for contact with id : {}", command.getContactId());
        Assert.notNull(command.getContactId(), "ContactIdentifier may not be null");

        Contact contact = contactRepository.load(command.getContactId());
        LOGGER.debug("Contact identifier: " + contact.getIdentifier());
        contact.delete();
    }

    /**
     * Removes a phone number from the list of phone numbers of the provided contact.
     * <p/>
     * An {@code AggregateNotFoundException} is thrown if the identifier does not represent a valid contact.
     * 
     * @param command
     *            UpdateContactCommand that contains the identifier and the data to be updated
     * @param unitOfWork
     *            Unit of work for the current running thread
     */
    @CommandHandler
    public void handleRemovePhoneNumber(final RemovePhoneNumberCommand command, UnitOfWork unitOfWork) {
        LOGGER.debug("Received a updateContactCommand for id : {}", command.getContactId());
        Assert.notNull(command.getContactId(), "ContactIdentifier may not be null");

        Contact contact = contactRepository.load(command.getContactId());
        Assert.notNull(contact.getIdentifier(), "Contact identifier cannot be null");

        contact.removePhoneNumber(command.getPhoneNumberId());
    }

    /**
     * Changes the provided data for the contact found based on the provided identifier
     * <p/>
     * An {@code AggregateNotFoundException} is thrown if the identifier does not represent a valid contact.
     * 
     * @param command
     *            UpdateContactCommand that contains the identifier and the data to be updated
     * @param unitOfWork
     *            Unit of work for the current running thread
     */
    @CommandHandler
    public void handleUpdateContact(final UpdateContactCommand command, UnitOfWork unitOfWork) {
        LOGGER.debug("Received a updateContactCommand for id : {}", command.getContactId());
        Assert.notNull(command.getContactId(), "ContactIdentifier may not be null");

        Contact contact = contactRepository.load(command.getContactId());
        Assert.notNull(contact.getIdentifier(), "Contact identifier cannot be null");

        contact.change(command.getContactEntry());
    }

    /**
     * Sets the contact domain event contactRepository.
     * 
     * @param repository
     *            the contact contactRepository
     */
    @Autowired
    @Qualifier("contactRepository")
    public void setContactRepository(Repository<Contact> repository) {
        contactRepository = repository;
    }
}
