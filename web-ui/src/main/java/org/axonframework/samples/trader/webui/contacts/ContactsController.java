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

package org.axonframework.samples.trader.webui.contacts;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.domain.UUIDAggregateIdentifier;
import org.axonframework.samples.trader.contacts.api.AbstractContactCrudCommand;
import org.axonframework.samples.trader.contacts.api.CreateContactCommand;
import org.axonframework.samples.trader.contacts.api.RemoveContactCommand;
import org.axonframework.samples.trader.contacts.api.UpdateContactCommand;
import org.axonframework.samples.trader.contacts.query.ContactEntry;
import org.axonframework.samples.trader.contacts.query.repositories.ContactQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * @author Jettro Coenradie, Yorick Holkamp
 */
@Controller
@RequestMapping("/contacts")
public class ContactsController {
    private final static Logger logger = LoggerFactory
            .getLogger(ContactsController.class);
    private ContactQueryRepository contactRepository;
    private CommandBus commandBus;

    @Autowired
    public ContactsController(ContactQueryRepository contactRepository,
                              CommandBus commandBus) {
        this.contactRepository = contactRepository;
        this.commandBus = commandBus;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("contacts", contactRepository.findAll());
        return "contacts/list";
    }

    @RequestMapping(value = "{identifier}", method = RequestMethod.GET)
    public String details(@PathVariable String identifier, Model model) {
        String name = contactRepository.findOne(identifier).getName();
        model.addAttribute("identifier", identifier);
        model.addAttribute("name", name);
        return "contacts/details";
    }

    @RequestMapping(value = "{identifier}/edit", method = RequestMethod.GET)
    public String formEdit(@PathVariable String identifier, Model model) {
        ContactEntry contact = contactRepository.findOne(identifier);
        if (contact == null) {
            throw new RuntimeException("contactRepository with ID " + identifier + " could not be found.");
        }
        model.addAttribute("contact", contact);
        return "contacts/edit";
    }

    @RequestMapping(value = "{identifier}/edit", method = RequestMethod.POST)
    public String formEditSubmit(
            @ModelAttribute("contact") @Valid ContactEntry contact,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "contacts/edit";
        }

        AbstractContactCrudCommand command = new UpdateContactCommand();
        command.setContactId(new StringAggregateIdentifier(contact.getIdentifier()));
        command.setContactEntry(contact);

        commandBus.dispatch(command);
        logger.debug("Dispatching command with name : {}", command.toString());

        return "redirect:/contacts";
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String formNew(Model model) {
        ContactEntry attributeValue = new ContactEntry();
        model.addAttribute("contactEntry", attributeValue);
        return "contacts/new";
    }

    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String formNewSubmit(@Valid ContactEntry contact, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "contacts/new";
        }

        AbstractContactCrudCommand command = new CreateContactCommand();
        command.setContactId(new UUIDAggregateIdentifier());
        command.setContactEntry(contact);

        logger.debug("Dispatching command with name : {}", command.toString());
        commandBus.dispatch(command);

        return "redirect:/contacts";
    }

    @RequestMapping(value = "{identifier}/delete", method = RequestMethod.GET)
    public String formDelete(@PathVariable String identifier, Model model) {
        ContactEntry contactEntry = contactRepository.findOne(identifier);
        model.addAttribute("contactEntry", contactEntry);
        return "contacts/delete";
    }

    @RequestMapping(value = "{identifier}/delete", method = RequestMethod.POST)
    public String formDelete(@ModelAttribute("contact") ContactEntry contact, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            RemoveContactCommand command = new RemoveContactCommand();
            command.setContactId(new StringAggregateIdentifier(contact.getIdentifier()));
            commandBus.dispatch((command));
            logger.debug("Dispatching command with name : {}", command.toString());

            return "redirect:/contacts";
        }
        return "contacts/delete";
    }
}