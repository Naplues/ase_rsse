package ase.rsse.cookbook;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ase.rsse.utils.IoUtility;
import cc.kave.commons.model.events.IIDEEvent;
import cc.kave.commons.model.events.completionevents.CompletionEvent;
import cc.kave.commons.model.events.completionevents.Context;
import cc.kave.commons.model.events.versioncontrolevents.VersionControlEvent;

public class Cook {

	public static void main(String[] args) {
		// get all contexts for a given C# solution on GitHub
		List<Context> contexts = IoUtility.readContext(
				"C:\\workspaces\\ase_rsse\\apirec\\Contexts-170503\\JetBrains\\resharper-angularjs\\src\\resharper-angularjs.sln-contexts.zip");
		for (Context ctx : contexts) {
			System.out.println(ctx);
		}

		// get all events of a given user
		List<IIDEEvent> events = IoUtility
				.readEvent("2.zip");
		for (IIDEEvent event : events) {
			ArrayList<VersionControlEvent> versionControlEvents = new ArrayList<>();
			ArrayList<CompletionEvent> completionEvents = new ArrayList<>();

			// get all the commits of the user
			if (event instanceof VersionControlEvent) {
				VersionControlEvent versionControlEvent = (VersionControlEvent) event;
				versionControlEvents.add(versionControlEvent);
			} else if (event instanceof CompletionEvent) {
				CompletionEvent completionEvent = (CompletionEvent) event;
				completionEvents.add(completionEvent);
			}

			// order the events by date
			List<VersionControlEvent> vceSorted = versionControlEvents.stream()
					.filter(Objects::nonNull)
					.sorted(Comparator.comparing(VersionControlEvent::getTriggeredAt, Comparator.reverseOrder()))
					.collect(Collectors.toList());
			List<CompletionEvent> ceSorted = completionEvents.stream()
					.filter(Objects::nonNull)
					.sorted(Comparator.comparing(CompletionEvent::getTriggeredAt, Comparator.reverseOrder()))
					.collect(Collectors.toList());
		}
	}
}
