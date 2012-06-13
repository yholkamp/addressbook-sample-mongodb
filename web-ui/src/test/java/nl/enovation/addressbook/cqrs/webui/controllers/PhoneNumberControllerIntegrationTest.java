package nl.enovation.addressbook.cqrs.webui.controllers;

import java.util.ArrayList;
import java.util.List;

import nl.enovation.addressbook.cqrs.command.CreateContactCommand;
import nl.enovation.addressbook.cqrs.command.CreatePhoneNumberCommand;
import nl.enovation.addressbook.cqrs.pojo.PhoneNumberEntry;
import nl.enovation.addressbook.cqrs.pojo.PhoneNumberType;
import nl.enovation.addressbook.cqrs.query.ContactEntry;
import nl.enovation.addressbook.cqrs.query.repositories.ContactQueryRepositoryImpl;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.domain.UUIDAggregateIdentifier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/persistence-infrastructure-context.xml",
                                   "classpath:/META-INF/spring/cqrs-infrastructure-context.xml", "classpath:/META-INF/spring/contacts-context.xml" })
public class PhoneNumberControllerIntegrationTest {
    @Mock
    private BindingResult mockBindingResult;

    @Autowired
    private CommandBus commandBus;

    @Autowired
    private PhoneNumberController controller;

    @Autowired
    private ContactQueryRepositoryImpl contactQueryRepositoryImpl;

    @Mock
    private Model model;

    @After
    public void after() {
        contactQueryRepositoryImpl.deleteAll();
    }

    private ContactEntry createContactEntry() {
        ContactEntry contactEntry = new ContactEntry();
        contactEntry.setFirstName("Foo");
        contactEntry.setLastName("Bar");
        contactEntry.setIdentifier(new UUIDAggregateIdentifier().asString());
        return contactEntry;
    }

    private PhoneNumberEntry createPhoneNumberEntry() {
        PhoneNumberEntry phoneNumber = new PhoneNumberEntry();
        phoneNumber.setPhoneNumber("+31123456789");
        phoneNumber.setPhoneNumberType(PhoneNumberType.WORK);
        return phoneNumber;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);

        controller = new PhoneNumberController(contactQueryRepositoryImpl, commandBus);
    }

    @Test
    public void testDeleteForm() {
        // Set up a contactEntry
        ContactEntry contactEntry = createContactEntry();
        CreateContactCommand setupContactCommand = new CreateContactCommand(new StringAggregateIdentifier(contactEntry.getIdentifier()), contactEntry);
        commandBus.dispatch(setupContactCommand);
        assertEquals("ContactEntry should be retrievable from repository", contactEntry, contactQueryRepositoryImpl.findOne(contactEntry.getIdentifier()));

        // Set up a phone number for the contactEntry
        PhoneNumberEntry phoneNumber = createPhoneNumberEntry();
        CreatePhoneNumberCommand setupPhoneNumberCommand = new CreatePhoneNumberCommand(new StringAggregateIdentifier(contactEntry.getIdentifier()),
                                                                                        phoneNumber);
        commandBus.dispatch(setupPhoneNumberCommand);
        assertEquals("ContactEntry should have the new phoneNumber set in the database", 1, contactQueryRepositoryImpl.findOne(contactEntry.getIdentifier())
                                                                                                                      .getPhoneNumbers().size());

        String view = controller.formDelete(contactEntry.getIdentifier(), phoneNumber.getPhoneNumber(), model);

        // Check that we returned back to the contact list
        assertEquals("phonenumbers/delete", view);
    }

    @Test
    public void testDeletePhoneNumber() {
        // Set up a contactEntry
        ContactEntry contactEntry = createContactEntry();
        CreateContactCommand setupContactCommand = new CreateContactCommand(new StringAggregateIdentifier(contactEntry.getIdentifier()), contactEntry);
        commandBus.dispatch(setupContactCommand);
        assertEquals("ContactEntry should be retrievable from repository", contactEntry, contactQueryRepositoryImpl.findOne(contactEntry.getIdentifier()));

        // Set up a phone number for the contactEntry
        PhoneNumberEntry phoneNumber = createPhoneNumberEntry();
        CreatePhoneNumberCommand setupPhoneNumberCommand = new CreatePhoneNumberCommand(new StringAggregateIdentifier(contactEntry.getIdentifier()),
                                                                                        phoneNumber);
        commandBus.dispatch(setupPhoneNumberCommand);
        assertEquals("ContactEntry should have the new phoneNumber set in the database", 1, contactQueryRepositoryImpl.findOne(contactEntry.getIdentifier())
                                                                                                                      .getPhoneNumbers().size());

        String view = controller.formDelete(contactEntry.getIdentifier(), phoneNumber, mockBindingResult);

        // Check that we returned back to the contact list
        assertEquals("redirect:/contacts/" + contactEntry.getIdentifier(), view);

        ContactEntry contactFromDb = contactQueryRepositoryImpl.findOne(contactEntry.getIdentifier());
        assertEquals("PhoneNumber should have been removed", new ArrayList<PhoneNumberEntry>(), contactFromDb.getPhoneNumbers());
    }

    @Test
    public void testNewPhoneNumber_failure() {
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
        PhoneNumberEntry phoneNumber = createPhoneNumberEntry();

        String view = controller.formNewSubmit("fake-identifier", phoneNumber, mockBindingResult);

        // Check that we'reback to the original form
        assertEquals("phonenumbers/new", view);
    }

    @Test
    public void testNewPhoneNumber_success() {
        ContactEntry contactEntry = createContactEntry();
        CreateContactCommand setupCommand = new CreateContactCommand(new StringAggregateIdentifier(contactEntry.getIdentifier()), contactEntry);
        commandBus.dispatch(setupCommand);
        assertEquals("ContactEntry should be retrievable from repository", contactEntry, contactQueryRepositoryImpl.findOne(contactEntry.getIdentifier()));

        PhoneNumberEntry phoneNumber = createPhoneNumberEntry();
        List<PhoneNumberEntry> phoneNumbers = contactEntry.getPhoneNumbers();
        if (phoneNumbers == null) {
            phoneNumbers = new ArrayList<PhoneNumberEntry>();
        }
        phoneNumbers.add(phoneNumber);

        String view = controller.formNewSubmit(contactEntry.getIdentifier(), phoneNumber, mockBindingResult);

        // Check that we're back to the overview
        assertEquals("redirect:/contacts/" + contactEntry.getIdentifier(), view);

        ContactEntry contactFromDb = contactQueryRepositoryImpl.findOne(contactEntry.getIdentifier());
        assertNotNull("Should be able to find our contact in the db", contactFromDb);
        assertEquals("PhoneNumber should have been added in the db", phoneNumbers, contactFromDb.getPhoneNumbers());
    }

    @Test
    public void testNewPhoneNumberForm() {
        String view = controller.formNew("fake-identifier", model);

        // Check that we'reback to the original form
        assertEquals("phonenumbers/new", view);
    }

}
