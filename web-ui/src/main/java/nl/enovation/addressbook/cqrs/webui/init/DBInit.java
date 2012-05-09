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

package nl.enovation.addressbook.cqrs.webui.init;

import java.util.Set;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.domain.UUIDAggregateIdentifier;
import org.axonframework.eventstore.mongo.MongoEventStore;
import org.axonframework.saga.repository.mongo.MongoTemplate;
import nl.enovation.addressbook.cqrs.command.CreateContactCommand;
import nl.enovation.addressbook.cqrs.query.ContactEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Initializes the repository with a number of users, companiess and order books
 * </p>
 * 
 * @author Jettro Coenradie
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Component
public class DBInit {

    private CommandBus commandBus;

    private org.axonframework.eventstore.mongo.MongoTemplate systemAxonMongo;

    private MongoEventStore eventStore;

    private org.springframework.data.mongodb.core.MongoTemplate mongoTemplate;

    private MongoTemplate systemAxonSagaMongo;

    final static String[] departmentNames = { "Corporate Development", "Human Resources", "Legal", "Environment", "Quality Assurance",
                                             "Research and Development", "Production", "Sales", "Marketing" };

    final static String[] firstNames = { "Peter", "Alice", "Joshua", "Mike", "Olivia", "Nina", "Alex", "Rita", "Dan", "Umberto", "Henrik", "Rene", "Lisa",
                                        "Marge" };

    final static String[] lastNames = { "Smith", "Gordon", "Simpson", "Brown", "Clavel", "Simons", "Verne", "Scott", "Allison", "Gates", "Rowling", "Barks",
                                       "Ross", "Schneider", "Tate" };

    final static String cities[] = { "Amsterdam", "Berlin", "Helsinki", "Hong Kong", "London", "Luxemburg", "New York", "Oslo", "Paris", "Rome", "Stockholm",
                                    "Tokyo", "Turku" };

    final static String streets[] = { "4215 Blandit Av.", "452-8121 Sem Ave", "279-4475 Tellus Road", "4062 Libero. Av.", "7081 Pede. Ave", "6800 Aliquet St.",
                                     "P.O. Box 298, 9401 Mauris St.", "161-7279 Augue Ave", "P.O. Box 496, 1390 Sagittis. Rd.", "448-8295 Mi Avenue",
                                     "6419 Non Av.", "659-2538 Elementum Street", "2205 Quis St.", "252-5213 Tincidunt St.",
                                     "P.O. Box 175, 4049 Adipiscing Rd.", "3217 Nam Ave", "P.O. Box 859, 7661 Auctor St.", "2873 Nonummy Av.",
                                     "7342 Mi, Avenue", "539-3914 Dignissim. Rd.", "539-3675 Magna Avenue", "Ap #357-5640 Pharetra Avenue",
                                     "416-2983 Posuere Rd.", "141-1287 Adipiscing Avenue", "Ap #781-3145 Gravida St.", "6897 Suscipit Rd.",
                                     "8336 Purus Avenue", "2603 Bibendum. Av.", "2870 Vestibulum St.", "Ap #722 Aenean Avenue", "446-968 Augue Ave",
                                     "1141 Ultricies Street", "Ap #992-5769 Nunc Street", "6690 Porttitor Avenue", "Ap #105-1700 Risus Street",
                                     "P.O. Box 532, 3225 Lacus. Avenue", "736 Metus Street", "414-1417 Fringilla Street", "Ap #183-928 Scelerisque Road",
                                     "561-9262 Iaculis Avenue" };

    @Autowired
    public DBInit(CommandBus commandBus, org.axonframework.eventstore.mongo.MongoTemplate systemMongo, MongoEventStore eventStore,
                  org.springframework.data.mongodb.core.MongoTemplate mongoTemplate, MongoTemplate systemAxonSagaMongo) {
        this.commandBus = commandBus;
        systemAxonMongo = systemMongo;
        this.eventStore = eventStore;
        this.mongoTemplate = mongoTemplate;
        this.systemAxonSagaMongo = systemAxonSagaMongo;
    }

    private void createContacts() {
        for (int i = 0; i < departmentNames.length; i++) {
            ContactEntry entry = new ContactEntry();
            entry.setFirstName(firstNames[i]);
            entry.setLastName(lastNames[i]);
            entry.setStreet(streets[i]);
            entry.setCity(cities[i]);
            entry.setDepartment(departmentNames[i]);
            CreateContactCommand createContact = new CreateContactCommand(new UUIDAggregateIdentifier(), entry);
            commandBus.dispatch(createContact);
        }
    }

    public void createItems() {
        systemAxonMongo.domainEventCollection().drop();
        systemAxonMongo.snapshotEventCollection().drop();

        systemAxonSagaMongo.sagaCollection().drop();
        systemAxonSagaMongo.associationsCollection().drop();

        mongoTemplate.dropCollection(ContactEntry.class);

        createContacts();

        eventStore.ensureIndexes();
    }

    public String obtainInfo() {
        Set<String> collectionNames = systemAxonMongo.database().getCollectionNames();
        StringBuilder sb = new StringBuilder();
        for (String name : collectionNames) {
            sb.append(name);
            sb.append("  ");
        }
        return sb.toString();
    }
}
