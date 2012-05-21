package nl.enovation.addressbook.cqrs.query.repositories;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
//import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import nl.enovation.addressbook.cqrs.query.ContactEntry;

public class ContactQueryRepositoryImpl extends org.springframework.data.mongodb.core.MongoTemplate implements ContactQueryRepositoryCustom {
    
    @Autowired
    private static MongoDbFactory factory;

    @Autowired
    public ContactQueryRepositoryImpl(MongoDbFactory mongoDbFactory) {
        super(mongoDbFactory);
    }
    
    public ContactQueryRepositoryImpl() {
        super(factory);
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
}

