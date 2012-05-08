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

package org.axonframework.samples.trader.commandhandler;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.axonframework.samples.trader.command.CreateContactCommand;
import org.axonframework.samples.trader.command.RemoveContactCommand;
import org.axonframework.samples.trader.command.UpdateContactCommand;
import org.axonframework.samples.trader.domain.Contact;
import org.axonframework.unitofwork.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * <p>Command handler that can be used to create and update Contacts. It can also be used to register and remove
 * addresses.</p>
 * <p>The provided repository is used to store the changes.</p>
 *
 * @author Allard Buijze
 */
@Component
public class ContactCommandHandler {

    private final static Logger logger = LoggerFactory.getLogger(ContactCommandHandler.class);

    private Repository<Contact> repository;

    /**
     * Changes the provided data for the contact found based on the provided identifier
     * <p/>
     * An {@code AggregateNotFoundException} is thrown if the identifier does not represent a valid contact.
     *
     * @param command    UpdateContactCommand that contains the identifier and the data to be updated
     * @param unitOfWork Unit of work for the current running thread
     */
    @CommandHandler
    public void handleUpdateContact(final UpdateContactCommand command, UnitOfWork unitOfWork) {
        logger.debug("Received a updateContactCommand for id : {}", command.getContactId());
        Assert.notNull(command.getContactId(), "ContactIdentifier may not be null");

        Contact contact = repository.load(command.getContactId());
        Assert.notNull(contact.getIdentifier(), "Contact identifier cannot be null");

        contact.change(command.getContactEntry());
    }

    /**
     * Creates a new contact based on the provided data.
     *
     * @param command    CreateContactCommand object that contains the needed data to create a new contact
     * @param unitOfWork Unit of work for the current running thread
     */
    @CommandHandler
    public void handleCreateContact(final CreateContactCommand command, UnitOfWork unitOfWork) {
        logger.debug("Received a command for a new contact with id : {}", command.getContactId());

        Contact contact = new Contact(command.getContactId(), command.getContactEntry());
        repository.add(contact);
    }

    /**
     * Removes the contact matching the contactId provided by the command.
     *
     * @param command    RemoveContactCommand containing the identifier of the contact to be removed
     * @param unitOfWork Unit of work for the current running thread
     */
    @CommandHandler
    public void handleRemoveContact(RemoveContactCommand command, UnitOfWork unitOfWork) {
        logger.debug("Received a remove command for contact with id : {}", command.getContactId());
        Assert.notNull(command.getContactId(), "ContactIdentifier may not be null");

        Contact contact = repository.load(command.getContactId());
        contact.delete();
    }

    /**
     * Sets the contact domain event repository.
     *
     * @param repository the contact repository
     */
    @Autowired
    @Qualifier("contactRepository")
    public void setRepository(Repository<Contact> repository) {
        this.repository = repository;
    }
}
