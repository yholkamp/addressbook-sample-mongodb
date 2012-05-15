package nl.enovation.addressbook.cqrs.commandhandler;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import nl.enovation.addressbook.cqrs.commandhandler.ContactCommandHandler;
import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.UUIDAggregateIdentifier;
import org.axonframework.repository.Repository;
import nl.enovation.addressbook.cqrs.command.CreateContactCommand;
import nl.enovation.addressbook.cqrs.command.RemoveContactCommand;
import nl.enovation.addressbook.cqrs.command.UpdateContactCommand;
import nl.enovation.addressbook.cqrs.domain.Contact;
import nl.enovation.addressbook.cqrs.query.ContactEntry;

import org.axonframework.unitofwork.UnitOfWork;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author Jettro Coenradie
 */
public class ContactCommandHandlerTest {
    private ContactCommandHandler contactCommandHandler;

    @Mock
    private UnitOfWork mockUnitOfWork;

    @Mock
    private Repository<Contact> mockContactRepository;

    @Mock
    private Contact mockContact;

    @Mock
    private ContactEntry mockContactEntry;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        contactCommandHandler = new ContactCommandHandler();
        contactCommandHandler.setContactRepository(mockContactRepository);
    }

    @Test
    public void testCreateContactCommand() {
        AggregateIdentifier identifier = new UUIDAggregateIdentifier();
        CreateContactCommand createCommand = new CreateContactCommand(identifier, mockContactEntry);

        when(mockContact.getIdentifier()).thenReturn(identifier);
        when(mockContactRepository.load(identifier)).thenReturn(mockContact);

        contactCommandHandler.handleCreateContact(createCommand, mockUnitOfWork);

        // Ensure the contact is being added to our repository
        verify(mockContactRepository).add((Contact) anyObject());
    }

    @Test
    public void testRemoveContactCommand() {
        AggregateIdentifier identifier = new UUIDAggregateIdentifier();
        RemoveContactCommand removeCommand = new RemoveContactCommand(identifier);

        when(mockContactRepository.load(identifier)).thenReturn(mockContact);

        contactCommandHandler.handleRemoveContact(removeCommand, mockUnitOfWork);

        // Verify a contact is loaded properly and deleted afterwards
        verify(mockContactRepository, times(1)).load(identifier);
        verify(mockContact).delete();
    }

    @Test
    public void testUpdateContactCommand() {
        AggregateIdentifier identifier = new UUIDAggregateIdentifier();
        UpdateContactCommand updateCommand = new UpdateContactCommand(identifier, mockContactEntry);

        when(mockContact.getIdentifier()).thenReturn(identifier);
        when(mockContactRepository.load(identifier)).thenReturn(mockContact);

        contactCommandHandler.handleUpdateContact(updateCommand, mockUnitOfWork);

        // Ensure changes are made (saving is handled by the Axon Framework)
        verify(mockContact).change(mockContactEntry);
    }
}