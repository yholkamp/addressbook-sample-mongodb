package nl.enovation.addressbook.cqrs.query.repositories;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import nl.enovation.addressbook.cqrs.infra.mongo.CFMongoTemplate;
import nl.enovation.addressbook.cqrs.query.ContactEntry;

public class ContactQueryRepositoryImpl extends CFMongoTemplate implements ContactQueryRepositoryCustom {

    @Autowired
    public ContactQueryRepositoryImpl(MongoDbFactory mongoDbFactory) {
        super(mongoDbFactory);
    }

    public  List<ContactEntry> searchForNames(String searchValue)
    {        
        searchValue = ".*"+ searchValue + ".*";
        
        Criteria firstNameCriterion = new Criteria("firstName").regex(searchValue, "i");
        Criteria lastNameCriterion = new Criteria("lastName").regex(searchValue, "i");
        Criteria criteria = new Criteria().orOperator(firstNameCriterion, lastNameCriterion);
        
        Query query = Query.query(criteria);
        
        return super.find(query, ContactEntry.class);
    }
    
    public ContactEntry findOne(String contactIdentifier){
        Criteria idCriterion = new Criteria("_id").is(contactIdentifier);
        Query query = Query.query(idCriterion);
        return super.findOne(query, ContactEntry.class);
    }

    public void delete(ContactEntry contactEntry) {
        Criteria idCriterion = new Criteria("_id").is(contactEntry.getIdentifier());
        Query query = Query.query(idCriterion);
        super.remove(query, ContactEntry.class);
    }

    public void deleteAll() {
        Criteria idCriterion = new Criteria();
        Query query = Query.query(idCriterion);
        super.remove(query, ContactEntry.class);        
    }
}

