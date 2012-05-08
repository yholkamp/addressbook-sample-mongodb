package org.axonframework.samples.trader.command;

import org.axonframework.domain.UUIDAggregateIdentifier;
import org.axonframework.repository.Repository;
import org.axonframework.samples.trader.command.ContactCommandHandler;
import org.axonframework.samples.trader.command.CreateContactCommand;
import org.axonframework.samples.trader.domain.Contact;
import org.axonframework.samples.trader.query.ContactEntry;
import org.axonframework.samples.trader.query.repositories.ContactQueryRepository;
import org.axonframework.unitofwork.UnitOfWork;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

/**
 * @author Jettro Coenradie
 */
public class ContactCommandHandlerTest {
	private ContactCommandHandler contactCommandHandler;
	@Mock
	private UnitOfWork mockUnitOfWork;
	@Mock
	private Repository<Contact> mockRepository;
	@Mock
	private ContactQueryRepository mockContactRepository;
	@Mock
	private Contact mockContact;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		contactCommandHandler = new ContactCommandHandler();
		// contactCommandHandler.setContactNameRepository(mockContactNameRepository);
		contactCommandHandler.setRepository(mockRepository);
	}

	@Test
	public void testHandleCreateContactCommand_success() throws Exception {
		CreateContactCommand command = new CreateContactCommand();
		command.setContactId(new UUIDAggregateIdentifier());
		command.setContactEntry(new ContactEntry());

		// when(mockContactNameRepository.claimContactName("Double name")).thenReturn(false);

		contactCommandHandler.handleCreateContact(command, mockUnitOfWork);

		verify(mockRepository).add((Contact) anyObject());
	}

	@Test
	public void testHandleCreateContactCommand_otherProblemWithTransaction()
			throws Exception {
		// CreateContactCommand command = new CreateContactCommand();
		// command.setContactId(new UUIDAggregateIdentifier());
		// command.setNewContactName("Good name");
		//
		// when(mockContactNameRepository.claimContactName("Good name")).thenReturn(true);
		//
		// contactCommandHandler.handle(command, mockUnitOfWork);
		//
		// ArgumentCaptor<UnitOfWorkListener> unitOfWorkListenerArgumentCaptor =
		// ArgumentCaptor.forClass(UnitOfWorkListener.class);
		// verify(mockContactNameRepository).claimContactName("Good name");
		// verify(mockUnitOfWork).registerListener(unitOfWorkListenerArgumentCaptor.capture());
		//
		// unitOfWorkListenerArgumentCaptor.getValue().onRollback(new
		// RuntimeException("Something went horribly wrong"));
		//
		// verify(mockContactNameRepository).cancelContactName("Good name");
	}

	@Test
	public void testHandleChangeNameContactCommand_doubleName() {
		// UpdateContactCommand command = new UpdateContactCommand();
		// command.setContactId(new UUIDAggregateIdentifier());
		// command.setContactNewName("Double New Name");
		//
		// Contact contact = mock(Contact.class);
		//
		// when(mockContactNameRepository.claimContactName("Double New Name")).thenReturn(false);
		//
		// try {
		// contactCommandHandler.handle(command, mockUnitOfWork);
		// fail("ContactNameAlreadyTakenException was expected to be thrown");
		// } catch (ContactNameAlreadyTakenException e) {
		// // as expected
		// }
		//
		// verify(mockContactNameRepository).claimContactName("Double New Name");
		// verify(contact, never()).change("Double New Name");
	}

	@Test
	public void testHandleChangeNameContactCommand_happypath() {
		// UpdateContactCommand command = new UpdateContactCommand();
		// command.setContactId(new UUIDAggregateIdentifier());
		// command.setContactNewName("Good New Name");
		//
		// ContactEntry mockContactEntry = mock(ContactEntry.class);
		//
		// when(mockContactNameRepository.claimContactName("Good New Name"))
		// .thenReturn(true);
		// when(mockRepository.load(isA(AggregateIdentifier.class)))
		// .thenReturn(mockContact);
		// when(mockContactRepository.findOne(command.getContactId().asString()))
		// .thenReturn(mockContactEntry);
		// when(mockContactEntry.getName()).thenReturn("Good Old Name");
		//
		// ArgumentCaptor<UnitOfWorkListener> unitOfWorkListenerArgumentCaptor =
		// ArgumentCaptor.forClass(UnitOfWorkListener.class);
		//
		// contactCommandHandler.handle(command, mockUnitOfWork);
		//
		// verify(mockContactNameRepository).claimContactName("Good New Name");
		// verify(mockContact).change("Good New Name");
		// verify(mockUnitOfWork,
		// times(2)).registerListener(unitOfWorkListenerArgumentCaptor.capture());
		// for (UnitOfWorkListener listener :
		// unitOfWorkListenerArgumentCaptor.getAllValues()) {
		// listener.afterCommit();
		// }
		//
		// verify(mockContactNameRepository).cancelContactName("Good Old Name");
	}

	@Test
	public void testHandleChangeNameContactCommand_otherProblemWithTransaction()
			throws Exception {
		// UpdateContactCommand command = new UpdateContactCommand();
		// command.setContactId(new UUIDAggregateIdentifier());
		// command.setContactNewName("Good New Name");
		//
		// when(mockContactNameRepository.claimContactName("Good New Name")).thenReturn(true);
		// when(mockRepository.load(isA(AggregateIdentifier.class))).thenReturn(mockContact);
		//
		// contactCommandHandler.handle(command, mockUnitOfWork);
		//
		// ArgumentCaptor<UnitOfWorkListener> unitOfWorkListenerArgumentCaptor =
		// ArgumentCaptor.forClass(UnitOfWorkListener.class);
		// verify(mockContactNameRepository).claimContactName("Good New Name");
		// verify(mockUnitOfWork,
		// times(2)).registerListener(unitOfWorkListenerArgumentCaptor.capture());
		// for (UnitOfWorkListener listener :
		// unitOfWorkListenerArgumentCaptor.getAllValues()) {
		// listener.onRollback(new
		// RuntimeException("Something went horribly wrong"));
		// }
		//
		// verify(mockContactNameRepository).cancelContactName("Good New Name");
		// verify(mockContactNameRepository,
		// never()).cancelContactName("Good Old Name");
	}

}