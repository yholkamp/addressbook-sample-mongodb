/**
 * 
 */
package nl.enovation.addressbook.cqrs.query;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import nl.enovation.addressbook.cqrs.pojo.PhoneNumberEntry;
import nl.enovation.addressbook.cqrs.query.ContactListener;
import nl.enovation.addressbook.cqrs.query.repositories.ContactQueryRepository;
import nl.enovation.addressbook.cqrs.query.repositories.ContactQueryRepositoryImpl;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.UUIDAggregateIdentifier;
import nl.enovation.addressbook.cqrs.event.ContactCreatedEvent;
import nl.enovation.addressbook.cqrs.event.ContactRemovedEvent;
import nl.enovation.addressbook.cqrs.event.ContactUpdatedEvent;
import nl.enovation.addressbook.cqrs.event.PhoneNumberAddedEvent;
import nl.enovation.addressbook.cqrs.event.PhoneNumberRemovedEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Yorick Holkamp
 */
public class ContactListenerTest {
    private ContactListener contactListener;

    @Mock
    private ContactQueryRepositoryImpl mockContactRepository;

    @Mock
    private ContactEntry mockContactEntry;
    
    @Mock
    private PhoneNumberEntry mockPhoneNumber;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        contactListener = new ContactListener();
        contactListener.setContactQueryRepository(mockContactRepository);
    }

    @Test
    public final void testHandleContactCreatedEvent() {
        AggregateIdentifier id = new UUIDAggregateIdentifier();
        ContactCreatedEvent event = new ContactCreatedEvent(id, mockContactEntry);

        contactListener.handle(event);

        verify(mockContactRepository).save(mockContactEntry);
    }

    @Test
    public final void testHandleContactDeletedEvent() {
        AggregateIdentifier id = new UUIDAggregateIdentifier();
        ContactRemovedEvent event = new ContactRemovedEvent(id);

        when(mockContactRepository.findOne(id.asString())).thenReturn(mockContactEntry);
        contactListener.handle(event);

        verify(mockContactRepository).delete(mockContactEntry);
    }

    @Test
    public final void testHandleContactUpdatedEvent() {
        AggregateIdentifier id = new UUIDAggregateIdentifier();
        ContactUpdatedEvent event = new ContactUpdatedEvent(id, mockContactEntry);

        contactListener.handle(event);

        verify(mockContactRepository).save(mockContactEntry);
    }
    
    @Test
    public final void testHandlePhoneNumberAddedEvent() {
        AggregateIdentifier id = new UUIDAggregateIdentifier();
        PhoneNumberAddedEvent event = new PhoneNumberAddedEvent(id, mockPhoneNumber);

        when(mockContactRepository.findOne(id.asString())).thenReturn(mockContactEntry);

        contactListener.handle(event);

        List<PhoneNumberEntry> phoneNumbers = new ArrayList<PhoneNumberEntry>();
        phoneNumbers.add(mockPhoneNumber);
        verify(mockContactEntry).setPhoneNumbers(phoneNumbers);
        verify(mockContactRepository).save(mockContactEntry);
    }

    @Test
    public final void testHandlePhoneNumberRemovedEvent() {
        List<PhoneNumberEntry> phoneNumbers = new ArrayList<PhoneNumberEntry>();
        PhoneNumberEntry phoneNumberEntry = new PhoneNumberEntry();
        phoneNumberEntry.setPhoneNumber("123456");
        phoneNumbers.add(phoneNumberEntry);
        when(mockContactEntry.getPhoneNumbers()).thenReturn(phoneNumbers);
        
        AggregateIdentifier id = new UUIDAggregateIdentifier();
        PhoneNumberRemovedEvent event = new PhoneNumberRemovedEvent(id, "123456");

        when(mockContactRepository.findOne(id.asString())).thenReturn(mockContactEntry);
        
        contactListener.handle(event);

        phoneNumbers = new ArrayList<PhoneNumberEntry>();
        verify(mockContactEntry).setPhoneNumbers(phoneNumbers);
        verify(mockContactRepository).save(mockContactEntry);
    }
}
