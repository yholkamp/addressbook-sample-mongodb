/*
 * Copyright (c) 2010. Axon Framework
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

package org.axonframework.examples.addressbook.vaadin.data;

import com.vaadin.data.util.BeanItemContainer;
import org.axonframework.sample.app.query.ContactEntry;
import org.axonframework.sample.app.query.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author Jettro Coenradie
 */
@Component
public class ContactContainer extends BeanItemContainer<ContactEntry> implements Serializable {
    public static final Object[] NATURAL_COL_ORDER = new Object[]{"name", "identifier"};
    public static final String[] COL_HEADERS_ENGLISH = new String[]{"Name", "Identifier"};

    @Autowired
    private ContactRepository contactRepository;

    public ContactContainer() throws IllegalArgumentException {
        super(ContactEntry.class);
    }

    public void refreshContent() {
        Iterable<ContactEntry> allContacts = contactRepository.findAll();
        removeAllItems();
        addAll((Collection<? extends ContactEntry>) allContacts);
    }


}
