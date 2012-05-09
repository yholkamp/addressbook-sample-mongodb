package org.axonframework.samples.trader.integration;

import org.axonframework.eventstore.EventStore;
import org.axonframework.samples.trader.command.ContactCommandHandler;
import org.axonframework.samples.trader.query.repositories.ContactQueryRepository;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author Allard Buijze Modified by Yorick
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/META-INF/spring/persistence-infrastructure-context.xml", 
		"classpath:/META-INF/spring/cqrs-infrastructure-context.xml",
		"classpath:/META-INF/spring/configuration-context.xml",
		"classpath:/META-INF/spring/contacts-context.xml",
		"classpath:/META-INF/spring/contacts-query-context.xml",
		})
public class ContactIntegrationTest {

	@Autowired
	private ContactCommandHandler commandHandler;

	@Autowired
	private EventStore eventStore;

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private ContactQueryRepository contactQueryRepository;

	@Test(timeout = 10000)
	public void testApplicationContext() throws InterruptedException {
		assertNotNull(commandHandler);
		assertNotNull(eventStore);
		assertNotNull(taskExecutor);
		assertNotNull(contactQueryRepository);
		// assertNotNull(commandRepository);
	}

}
