package nl.enovation.addressbook.cqrs.query.repositories;

import java.util.List;

import nl.enovation.addressbook.cqrs.query.ContactEntry;
import nl.enovation.addressbook.cqrs.query.repository.ContactQueryRepositoryImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/persistence-infrastructure-context.xml",
                                   "classpath:/META-INF/spring/cqrs-infrastructure-context.xml", "classpath:/META-INF/spring/contacts-context.xml" })
public class ContactQueryRepositoryImplTest {

    @Autowired
    private ContactQueryRepositoryImpl contactQueryRepositoryImpl;

    private String searchValue;

    private ContactEntry contactTest;

    private ContactEntry contactTest2;

    private String firstName = "Foo";

    private String lastName = "Bar";

    @After
    public void after() {
        contactQueryRepositoryImpl.deleteAll();
    }

    @Before
    public void setup() {
        contactTest = new ContactEntry();
        contactTest.setFirstName(firstName);
        contactTest.setLastName(lastName);

        contactTest2 = new ContactEntry();
        contactTest2.setFirstName(firstName);
        contactTest2.setLastName(lastName);
    }

    @Test
    public void testDelete() {
        contactQueryRepositoryImpl.delete(contactTest);
        assertNull(contactQueryRepositoryImpl.findOne(contactTest.getIdentifier()));
    }

    @Test
    public void testDeleteAll() {
        contactQueryRepositoryImpl.save(contactTest);
        contactQueryRepositoryImpl.save(contactTest2);
        contactQueryRepositoryImpl.deleteAll();
        assertNull(contactQueryRepositoryImpl.findOne(contactTest.getIdentifier()));
        assertNull(contactQueryRepositoryImpl.findOne(contactTest2.getIdentifier()));
    }

    @Test
    public void testFindOneString() {
        contactQueryRepositoryImpl.save(contactTest);
        assertEquals(contactTest, contactQueryRepositoryImpl.findOne(contactTest.getIdentifier()));
    }

    @Test
    public void testSearchForNames() {
        contactQueryRepositoryImpl.deleteAll();

        String randomString = "&*(@$&(⁹823jsf";

        contactQueryRepositoryImpl.save(contactTest);

        searchValue = contactTest.getFirstName();
        List<ContactEntry> contacts = contactQueryRepositoryImpl.searchForNames(searchValue);
        assertTrue(contacts.contains(contactTest));

        searchValue = contactTest.getLastName();
        contacts.clear();
        contacts = contactQueryRepositoryImpl.searchForNames(searchValue);
        assertTrue(contacts.contains(contactTest));

        searchValue = randomString;
        contacts.clear();
        contacts = contactQueryRepositoryImpl.searchForNames(searchValue);
        assertFalse(contacts.contains(contactTest));
    }
}
