package nl.enovation.addressbook.cqrs.query.repository;

import java.util.List;

import nl.enovation.addressbook.cqrs.infra.mongo.CFMongoTemplate;
import nl.enovation.addressbook.cqrs.query.ContactEntry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Implementation of the ContactQueryRepository and ContactQueryRepositoryCustom interfaces. Provides access to our
 * query models in the database using the MongoDB CloudFoundry driver.
 * 
 * @author Maarten van Meijeren
 */
public class ContactQueryRepositoryImpl extends CFMongoTemplate implements ContactQueryRepositoryCustom {

    @Autowired
    public ContactQueryRepositoryImpl(MongoDbFactory mongoDbFactory) {
        super(mongoDbFactory);
    }

    /**
     * Delete contactEntry equal to parameter
     * 
     * @param contactEntry
     *            contactEntry that has to be deleted
     */
    public void delete(ContactEntry contactEntry) {
        Criteria idCriterion = new Criteria("_id").is(contactEntry.getIdentifier());
        Query query = Query.query(idCriterion);
        super.remove(query, ContactEntry.class);
    }

    /**
     * Delete all contactEntries from the database
     */
    public void deleteAll() {
        Criteria idCriterion = new Criteria();
        Query query = Query.query(idCriterion);
        super.remove(query, ContactEntry.class);
    }

    /**
     * Find the contactEntry that has the identifier that is given by parameter
     * 
     * @param contactIdentifier
     *            identifier for contactEntry that has to be found
     * @return contactEntry with identifier equals to parameter
     */
    public ContactEntry findOne(String contactIdentifier) {
        Criteria idCriterion = new Criteria("_id").is(contactIdentifier);
        Query query = Query.query(idCriterion);
        return super.findOne(query, ContactEntry.class);
    }

    /**
     * Find List of contactEntries that contains (sub)string searchValue
     * 
     * @param searchValue
     *            contains (sub)string that has to be found
     * @return List<ContactEntry> list of contactEntries that contain searchValue in first or lastName
     */
    @Override
    public List<ContactEntry> searchForNames(String searchValue) {
        String regexSearchValue = ".*" + searchValue + ".*";

        Criteria firstNameCriterion = new Criteria("firstName").regex(regexSearchValue, "i");
        Criteria lastNameCriterion = new Criteria("lastName").regex(regexSearchValue, "i");
        Criteria criteria = new Criteria().orOperator(firstNameCriterion, lastNameCriterion);

        Query query = Query.query(criteria);

        return super.find(query, ContactEntry.class);
    }
}
