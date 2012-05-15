package nl.enovation.addressbook.cqrs.query.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;

import com.mongodb.DB;
import com.mongodb.DBCollection;

import nl.enovation.addressbook.cqrs.domain.Contact;
import nl.enovation.addressbook.cqrs.infra.mongo.CFMongoTemplate;
import nl.enovation.addressbook.cqrs.query.ContactEntry;

public abstract class ContactQueryRepositoryImpl extends org.springframework.data.mongodb.core.MongoTemplate implements ContactQueryRepositoryCustom {

      
    
    public ContactQueryRepositoryImpl(MongoDbFactory mongoDbFactory) {
        super(mongoDbFactory);
        // TODO Auto-generated constructor stub
    }

    public abstract List<ContactEntry> findByFirstNameOrLastNameLike(String regexFirstName, String regexlastName);
//    {
//        System.out.println("CONTACTS SEARCH DESTINATION");
//        
////        private ContactQueryRepository contactQueryRepository;
////        
////        Iterable<ContactEntry> all = contactQueryRepository.findAll();
////        ContactEntry contactEntry = all.iterator().next();
////        contactEntry.getFirstName().contains(s)
//        
////        DBCollection db = super.getDb().getCollection(super.getCollectionName(ContactEntry.class));
////        db.f
////        
////        db.users.find({name:/Joe/})
//        
//// Set<String> colls = super.getCollectionNames();
//// 
//// for(String s : colls){
////     System.out.println(s);
//// }
////        
//        
////        MongoTemplate mongo = super.getCollection(super.getCollectionName(ContactEntry.class));
////        mongo.
//        
//        ContactEntry contactTest = new ContactEntry();
//        contactTest.setFirstName("Test");
//        contactTest.setLastName("Test");
//        contactTest.setPhoneNumber("123456");
//        
//        List<ContactEntry> contacts = new ArrayList<ContactEntry>();
//        contacts.add(contactTest);      
//        
//        return contacts;   
//        
//    }
}

