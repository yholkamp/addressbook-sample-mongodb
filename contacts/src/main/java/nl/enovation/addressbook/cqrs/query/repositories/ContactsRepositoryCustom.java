package nl.enovation.addressbook.cqrs.query.repositories;

import java.util.List;

import nl.enovation.addressbook.cqrs.domain.Contact;

public interface ContactsRepositoryCustom {

    public List<Contact> searchForContacts(String searchValue);
}
