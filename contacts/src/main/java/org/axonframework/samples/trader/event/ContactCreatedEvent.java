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

package org.axonframework.samples.trader.event;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.samples.trader.query.ContactEntry;

/**
 * @author Yorick Holkamp
 */
public class ContactCreatedEvent extends AbstractContactCrudEvent {

	private static final long serialVersionUID = 3077594865095716500L;

	public ContactCreatedEvent(AggregateIdentifier contactId, ContactEntry contact) {
        super(contactId, contact);
    }

}
