/**
 * 
 */
package nl.enovation.addressbook.cqrs.query;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import nl.enovation.addressbook.cqrs.pojo.PhoneNumber;
import nl.enovation.addressbook.cqrs.query.ContactEntry;
import nl.enovation.addressbook.cqrs.query.ContactListener;
import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.UUIDAggregateIdentifier;
import nl.enovation.addressbook.cqrs.event.ContactCreatedEvent;
import nl.enovation.addressbook.cqrs.event.ContactRemovedEvent;
import nl.enovation.addressbook.cqrs.event.ContactUpdatedEvent;
import nl.enovation.addressbook.cqrs.event.PhoneNumberAddedEvent;
import nl.enovation.addressbook.cqrs.event.PhoneNumberRemovedEvent;
import nl.enovation.addressbook.cqrs.query.repositories.ContactQueryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Yorick Holkamp
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/persistence-infrastructure-context.xml",
                                   "classpath:/META-INF/spring/cqrs-infrastructure-context.xml", "classpath:/META-INF/spring/configuration-context.xml",
                                   "classpath:/META-INF/spring/contacts-context.xml", "classpath:/META-INF/spring/contacts-query-context.xml", })
public class ContactListenerTest {
    private ContactListener contactListener;

    @Mock
    private ContactQueryRepository mockContactRepository;

    @Mock
    private ContactEntry mockContactEntry;
    
    @Mock
    private PhoneNumber mockPhoneNumber;
    
    @Autowired
    private ContactQueryRepository contactRepository;
    
    private ContactEntry contactEntry;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        contactListener = new ContactListener();
        contactListener.setContactRepository(mockContactRepository);
        
        ContactEntry contactEntry = new ContactEntry();
        contactEntry.setFirstName("Foo");
        contactEntry.setLastName("Bar");
        contactRepository.save(contactEntry);
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

        verify(mockContactRepository).save(mockContactEntry);
    }

    @Test
    public final void testHandlePhoneNumberRemovedEvent() {
        AggregateIdentifier id = new UUIDAggregateIdentifier();
        PhoneNumberRemovedEvent event = new PhoneNumberRemovedEvent(id, mockPhoneNumber);

        when(mockContactRepository.findOne(id.asString())).thenReturn(mockContactEntry);
        
        contactListener.handle(event);

        verify(mockContactRepository).save(mockContactEntry);
    }
}
