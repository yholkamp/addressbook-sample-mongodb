package org.axonframework.sample.webui;

import org.axonframework.sample.app.command.ClaimedContactName;
import org.axonframework.sample.app.query.ContactEntry;
import org.axonframework.sample.app.query.repositories.ContactNameRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author Jettro Coenradie
 */
@Controller
@RequestMapping("/db")
public class ViewDatabaseController {
	private ContactNameRepository claimedNameRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String databaseQueries() {
        return "db/index";
    }

    @RequestMapping("/claimed")
    public String claimedNames(Model model) {
        @SuppressWarnings({"JpaQlInspection"})
        Iterable<ContactEntry> claimedContactNames = claimedNameRepository.findAll();

        model.addAttribute("claimedNames", claimedContactNames);
        return "db/claimed";
    }

    @RequestMapping("/events")
    public String events(Model model) {
//        Query nativeQuery = entityManager.createQuery("select e.id,e.aggregateIdentifier,e.sequenceNumber,e.timeStamp,e.type,e.serializedEvent from DomainEventEntry e");
//        List<Object[]> events = nativeQuery.getResultList();
//        for (Object[] event : events) {
//            event[5] = new String((byte[]) event[5], Charset.forName("UTF-8"));
//        }
//        model.addAttribute("events", events);
//        return "db/events";
    	return "";
    }
}
