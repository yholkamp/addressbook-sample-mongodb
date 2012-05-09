/**
 * 
 */
package org.axonframework.samples.trader.query;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.UUIDAggregateIdentifier;
import org.axonframework.samples.trader.event.ContactCreatedEvent;
import org.axonframework.samples.trader.event.ContactDeletedEvent;
import org.axonframework.samples.trader.event.ContactUpdatedEvent;
import org.axonframework.samples.trader.query.repositories.ContactQueryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author Yorick Holkamp
 */
public class ContactListenerTest {
    private ContactListener contactListener;

    @Mock
    private ContactQueryRepository mockContactRepository;

    @Mock
    private ContactEntry mockContactEntry;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        contactListener = new ContactListener();
        contactListener.setContactRepository(mockContactRepository);
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
        ContactDeletedEvent event = new ContactDeletedEvent(id);

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

}
