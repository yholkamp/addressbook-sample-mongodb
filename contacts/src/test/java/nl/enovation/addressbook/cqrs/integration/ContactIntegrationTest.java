package nl.enovation.addressbook.cqrs.integration;

import static org.junit.Assert.assertNotNull;

import nl.enovation.addressbook.cqrs.commandhandler.ContactCommandHandler;
import org.axonframework.eventstore.EventStore;
import nl.enovation.addressbook.cqrs.query.repositories.ContactQueryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Allard Buijze Modified by Yorick
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/persistence-infrastructure-context.xml",
                                   "classpath:/META-INF/spring/cqrs-infrastructure-context.xml", "classpath:/META-INF/spring/configuration-context.xml",
                                   "classpath:/META-INF/spring/contacts-context.xml", "classpath:/META-INF/spring/contacts-query-context.xml", })
public class ContactIntegrationTest {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private ContactQueryRepository contactQueryRepository;

    @Test(timeout = 10000)
    public void testApplicationContext() throws InterruptedException {
        assertNotNull(eventStore);
        assertNotNull(taskExecutor);
        assertNotNull(contactQueryRepository);
        // assertNotNull(commandRepository);
    }

}
