package ase.rsse.app;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import ase.rsse.apirec.transactions.Match;
import ase.rsse.apirec.transactions.MatchCreator;
import ase.rsse.utilities.IoUtility;
import cc.kave.commons.model.events.IIDEEvent;
import cc.kave.commons.model.events.completionevents.CompletionEvent;
import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.ssts.impl.SST;

public class MatchCreatorTest {

	@Test
	public void testCreateMatch() {
		// get all events of a given user
		List<IIDEEvent> events = IoUtility
				.readEvent("C:\\workspaces\\ase_rsse\\apirec\\Events-170301-2\\2016-08-06\\2.zip");

		Assert.assertNotNull(events);

		ArrayList<CompletionEvent> completionEvents = new ArrayList<>();
		for (IIDEEvent event : events) {
			if (event instanceof CompletionEvent) {
				CompletionEvent completionEvent = (CompletionEvent) event;
				completionEvents.add(completionEvent);
			}
		}

		// order the events by date
		List<CompletionEvent> ceSorted = completionEvents.stream().filter(Objects::nonNull)
				.sorted(Comparator.comparing(CompletionEvent::getTriggeredAt, Comparator.reverseOrder()))
				.collect(Collectors.toList());

		// everything should match
		ISST oldSST = ceSorted.get(0).getContext().getSST();
		ISST newSST = oldSST;

		MatchCreator.init((SST) oldSST, (SST) newSST);
		ArrayList<Match> matching = MatchCreator.createMatching();

		Assert.assertNotNull(matching);
	}

}
