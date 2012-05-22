package nl.enovation.addressbook.cqrs.query.repositories;

import java.util.List;

import nl.enovation.addressbook.cqrs.query.ContactEntry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/persistence-infrastructure-context.xml",
                                    "classpath:/META-INF/spring/cqrs-infrastructure-context.xml", "classpath:/META-INF/spring/contacts-context.xml" })

public class ContactQueryRepositoryTest {

    @Autowired
    @Qualifier("contactQueryRepository")
    private ContactQueryRepositoryImpl contactQueryRepositoryImpl;
    
    private String searchValue;

    @Test
    public void test() {
        contactQueryRepositoryImpl.deleteAll();
            
        ContactEntry contactTest = new ContactEntry();
        contactTest.setFirstName("first");
        contactTest.setLastName("last");
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
