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

package nl.enovation.addressbook.cqrs.webui.contacts;

import javax.validation.Valid;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.domain.UUIDAggregateIdentifier;
import nl.enovation.addressbook.cqrs.command.AbstractContactCrudCommand;
import nl.enovation.addressbook.cqrs.command.CreateContactCommand;
import nl.enovation.addressbook.cqrs.command.RemoveContactCommand;
import nl.enovation.addressbook.cqrs.command.UpdateContactCommand;
import nl.enovation.addressbook.cqrs.query.ContactEntry;
import nl.enovation.addressbook.cqrs.query.repositories.ContactQueryRepository;
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

/**
 * @author Yorick Holkamp
 */
@Controller
@RequestMapping("/contacts")
public class PhoneNumberController {
    private final static Logger logger = LoggerFactory.getLogger(PhoneNumberController.class);

    private ContactQueryRepository contactRepository;

    private CommandBus commandBus;

    @Autowired
    public PhoneNumberController(ContactQueryRepository contactRepository, CommandBus commandBus) {
        this.contactRepository = contactRepository;
        this.commandBus = commandBus;
    }

    @RequestMapping(value = "{contactIdentifier}/phonenumbers/{identifier}/delete", method = RequestMethod.POST)
    public String formDelete(@PathVariable String contactIdentifier, @PathVariable String identifier, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {

            return "redirect:/contacts/"+contactIdentifier;
        }
        return "phonenumbers/delete";
    }

    @RequestMapping(value = "{contactIdentifier}/phonenumbers/{identifier}/delete", method = RequestMethod.GET)
    public String formDelete(@PathVariable String contactIdentifier, @PathVariable String identifier,  Model model) {
        return "phonenumbers/delete";
    }

    @RequestMapping(value = "{contactIdentifier}/phonenumbers/new", method = RequestMethod.GET)
    public String formNew(@PathVariable String contactIdentifier, Model model) {
        ContactEntry attributeValue = new ContactEntry();
        model.addAttribute("contact", attributeValue);
        return "phonenumbers/new";
    }

    @RequestMapping(value = "{contactIdentifier}/phonenumbers/new", method = RequestMethod.POST)
    public String formNewSubmit(@PathVariable String contactIdentifier, @Valid ContactEntry contact, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "phonenumbers/new";
        }

        return "redirect:/contacts/"+contactIdentifier;
    }
}