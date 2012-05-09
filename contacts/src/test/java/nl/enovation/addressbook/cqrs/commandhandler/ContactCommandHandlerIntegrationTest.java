package nl.enovation.addressbook.cqrs.commandhandler;

import static org.mockito.Mockito.when;

import nl.enovation.addressbook.cqrs.commandhandler.ContactCommandHandler;
import org.axonframework.domain.AggregateIdentifier;
import nl.enovation.addressbook.cqrs.command.CreateContactCommand;
import nl.enovation.addressbook.cqrs.command.RemoveContactCommand;
import nl.enovation.addressbook.cqrs.command.UpdateContactCommand;
import nl.enovation.addressbook.cqrs.domain.Contact;
import nl.enovation.addressbook.cqrs.event.ContactCreatedEvent;
import nl.enovation.addressbook.cqrs.event.ContactDeletedEvent;
import nl.enovation.addressbook.cqrs.event.ContactUpdatedEvent;
import nl.enovation.addressbook.cqrs.query.ContactEntry;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author Yorick Holkamp
 */
public class ContactCommandHandlerIntegrationTest {
    @Mock
    private ContactEntry mockContactEntry;

    private FixtureConfiguration fixture;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        fixture = Fixtures.newGivenWhenThenFixture();
        ContactCommandHandler commandHandler = new ContactCommandHandler();
        commandHandler.setContactRepository(fixture.createGenericRepository(Contact.class));
        fixture.registerAnnotatedCommandHandler(commandHandler);
    }

    @Test
    public void testCreateContactCommandPipeline() {
        AggregateIdentifier identifier = fixture.getAggregateIdentifier();

        when(mockContactEntry.getIdentifier()).thenReturn(identifier.asString());
        CreateContactCommand createdEvent = new CreateContactCommand(identifier, mockContactEntry);

        // Check if our event will be properly fired
        fixture.given().when(createdEvent).expectEvents(new ContactCreatedEvent(identifier, mockContactEntry));
    }

    @Test
    public void testRemoveContactCommandPipeline() {
        AggregateIdentifier identifier = fixture.getAggregateIdentifier();
        RemoveContactCommand removeCommand = new RemoveContactCommand(identifier);

        ContactCreatedEvent createdEvent = new ContactCreatedEvent(identifier, mockContactEntry);

        fixture.given(createdEvent).when(removeCommand).expectEvents(new ContactDeletedEvent(identifier));
    }

    @Test
    public void testUpdateContactCommandPipeline() {
        AggregateIdentifier identifier = fixture.getAggregateIdentifier();
        UpdateContactCommand givenCommand = new UpdateContactCommand(identifier, mockContactEntry);

        when(mockContactEntry.getIdentifier()).thenReturn(identifier.asString());
        ContactCreatedEvent createdEvent = new ContactCreatedEvent(identifier, mockContactEntry);

        // Check if our event will be properly fired
        fixture.given(createdEvent).when(givenCommand).expectEvents(new ContactUpdatedEvent(identifier, mockContactEntry));
    }

}