package org.axonframework.samples.trader.commandhandler;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.repository.Repository;
import org.axonframework.samples.trader.command.RemoveContactCommand;
import org.axonframework.samples.trader.command.UpdateContactCommand;
import org.axonframework.samples.trader.commandhandler.ContactCommandHandler;
import org.axonframework.samples.trader.domain.Contact;
import org.axonframework.samples.trader.event.ContactCreatedEvent;
import org.axonframework.samples.trader.event.ContactDeletedEvent;
import org.axonframework.samples.trader.event.ContactUpdatedEvent;
import org.axonframework.samples.trader.query.ContactEntry;
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
	private ContactCommandHandler commandHandler;
	private Repository<Contact> fixtureRepository;
	@Mock
	private ContactEntry mockContactEntry;

	private FixtureConfiguration fixture;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		fixture = Fixtures.newGivenWhenThenFixture();
		fixtureRepository = fixture.createGenericRepository(Contact.class);
		commandHandler = new ContactCommandHandler();
		commandHandler.setRepository(fixtureRepository);
		fixture.registerAnnotatedCommandHandler(commandHandler);
	}

	@Test
	public void testUpdateContactCommandPipeline() {
		AggregateIdentifier identifier = fixture.getAggregateIdentifier();
		UpdateContactCommand givenCommand = new UpdateContactCommand(
				identifier, mockContactEntry);

		ContactCreatedEvent createdEvent = new ContactCreatedEvent(identifier,
				mockContactEntry);

		// Check if our event will be properly fired
		fixture.given(createdEvent)
				.when(givenCommand)
				.expectEvents(
						new ContactUpdatedEvent(identifier, mockContactEntry));
	}

	@Test
	public void testRemoveContactCommandPipeline() {
		AggregateIdentifier identifier = fixture.getAggregateIdentifier();
		RemoveContactCommand removeCommand = new RemoveContactCommand(
				identifier);

		ContactCreatedEvent createdEvent = new ContactCreatedEvent(identifier,
				mockContactEntry);

		fixture.given(createdEvent).when(removeCommand)
				.expectEvents(new ContactDeletedEvent(identifier));
	}

}