package nl.enovation.addressbook.cqrs.query.repository;

import java.util.List;

import nl.enovation.addressbook.cqrs.query.ContactEntry;

/**
 * Interface defining additional custom functionality for a contact query repository.
 * 
 * @author Maarten van Meijeren
 */
public interface ContactQueryRepositoryCustom {
    List<ContactEntry> searchForNames(String searchValue);
}
