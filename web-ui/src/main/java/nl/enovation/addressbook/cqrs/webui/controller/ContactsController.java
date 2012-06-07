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

package nl.enovation.addressbook.cqrs.webui.controller;

import java.util.List;

import javax.validation.Valid;

import nl.enovation.addressbook.cqrs.command.AbstractContactCrudCommand;
import nl.enovation.addressbook.cqrs.command.CreateContactCommand;
import nl.enovation.addressbook.cqrs.command.RemoveContactCommand;
import nl.enovation.addressbook.cqrs.command.UpdateContactCommand;
import nl.enovation.addressbook.cqrs.query.ContactEntry;
import nl.enovation.addressbook.cqrs.query.repository.ContactQueryRepositoryImpl;
import nl.enovation.addressbook.cqrs.webui.pojo.SearchForm;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.domain.UUIDAggregateIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller handling all actions regarding contacts.
 * 
 * @author Jettro Coenradie, Yorick Holkamp
 */
@Controller
@RequestMapping("/contacts")
public class ContactsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactsController.class);

    @Autowired
    @Qualifier("contactQueryRepository")
    private ContactQueryRepositoryImpl contactRepositoryImpl;

    private CommandBus commandBus;

    @Autowired
    public ContactsController(@Qualifier("contactQueryRepository") ContactQueryRepositoryImpl contactRepository, CommandBus commandBus) {
        contactRepositoryImpl = contactRepository;
        this.commandBus = commandBus;
    }

    @RequestMapping(value = "{identifier}", method = RequestMethod.GET)
    public String details(@PathVariable String identifier, Model model) {
        ContactEntry contactEntry = contactRepositoryImpl.findOne(identifier);
        model.addAttribute("identifier", identifier);
        model.addAttribute("contact", contactEntry);
        return "controllers/details";
    }

    @RequestMapping(value = "{identifier}/delete", method = RequestMethod.POST)
    public String formDelete(@ModelAttribute("contact") ContactEntry contact, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            AggregateIdentifier identifier = new StringAggregateIdentifier(contact.getIdentifier());
            RemoveContactCommand command = new RemoveContactCommand(identifier);

            LOGGER.debug("Dispatching command with name : {}", command.toString());
            commandBus.dispatch(command);

            return "redirect:/controllers";
        }
        return "controllers/delete";
    }

    @RequestMapping(value = "{identifier}/delete", method = RequestMethod.GET)
    public String formDelete(@PathVariable String identifier, Model model) {
        ContactEntry contactEntry = contactRepositoryImpl.findOne(identifier);
        model.addAttribute("contact", contactEntry);
        return "controllers/delete";
    }

    @RequestMapping(value = "{identifier}/edit", method = RequestMethod.GET)
    public String formEdit(@PathVariable String identifier, Model model) {
        ContactEntry contact = contactRepositoryImpl.findOne(identifier);
        model.addAttribute("contact", contact);
        return "controllers/edit";
    }

    @RequestMapping(value = "{identifier}/edit", method = RequestMethod.POST)
    public String formEditSubmit(@ModelAttribute("contact") @Valid ContactEntry contact, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "controllers/edit";
        }

        AggregateIdentifier identifier = new StringAggregateIdentifier(contact.getIdentifier());
        AbstractContactCrudCommand command = new UpdateContactCommand(identifier, contact);

        LOGGER.debug("Dispatching command with name : {}", command.toString());
        commandBus.dispatch(command);

        return "redirect:/controllers/" + contact.getIdentifier();
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String formNew(Model model) {
        ContactEntry attributeValue = new ContactEntry();
        model.addAttribute("contact", attributeValue);
        return "controllers/new";
    }

    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String formNewSubmit(@ModelAttribute("contact") @Valid ContactEntry contact, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "controllers/new";
        }

        AbstractContactCrudCommand command = new CreateContactCommand(new UUIDAggregateIdentifier(), contact);

        LOGGER.debug("Dispatching command with name : {}", command.toString());
        commandBus.dispatch(command);

        return "redirect:/controllers/" + contact.getIdentifier();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        SearchForm searchForm = new SearchForm();
        model.addAttribute("controllers", contactRepositoryImpl.findAll(ContactEntry.class));
        model.addAttribute("searchForm", searchForm);
        return "controllers/list";
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String search(@ModelAttribute("searchForm") SearchForm searchForm, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "controllers/list";
        }
        List<ContactEntry> contacts = contactRepositoryImpl.searchForNames(searchForm.getSearchValue());
        model.addAttribute("controllers", contacts);
        return "controllers/list";
    }
}