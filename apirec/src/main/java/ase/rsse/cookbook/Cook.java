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
//		List<Context> contexts = IoUtility.readContext(
//				"F:\\Wifo_Msc\\2. Semester\\Advanced SW Eng\\ase_rsse\\apirec\\src\\main\\java\\ase\\rsse\\cookbook\\resharper-angularjs.sln-contexts.zip");
//		for (Context ctx : contexts) {
//			System.out.println(ctx);
//		}

		// get all events of a given user
		List<IIDEEvent> events = IoUtility
				.readEvent("F:\\Wifo_Msc\\2. Semester\\Advanced SW Eng\\ase_rsse\\apirec\\src\\main\\java\\ase\\rsse\\cookbook\\2.zip");

		System.out.println("########## Total number of Events: " + events.size()+" ##########");

		ArrayList<VersionControlEvent> versionControlEvents = new ArrayList<>();
		ArrayList<CompletionEvent> completionEvents = new ArrayList<>();

		for (IIDEEvent event : events) {

			// get all the commits of the user
//			if (event instanceof VersionControlEvent) {
//				VersionControlEvent versionControlEvent = (VersionControlEvent) event;
//				versionControlEvents.add(versionControlEvent);
//			} else if (event instanceof CompletionEvent) {
//				CompletionEvent completionEvent = (CompletionEvent) event;
//				completionEvents.add(completionEvent);
//			}
			if(event instanceof CompletionEvent){
				completionEvents.add((CompletionEvent) event);
			}

			// order the events by date
//			List<VersionControlEvent> vceSorted = versionControlEvents.stream()
//					.filter(Objects::nonNull)
//					.sorted(Comparator.comparing(VersionControlEvent::getTriggeredAt, Comparator.reverseOrder()))
//					.collect(Collectors.toList());
		}
		System.out.println("########## Total number of Completion Events: " + completionEvents.size()+" ##########");
		List<CompletionEvent> ceSorted = completionEvents.stream()
				.filter(Objects::nonNull)
				.sorted(Comparator.comparing(CompletionEvent::getTriggeredAt))
				.collect(Collectors.toList());
		System.out.println("########## Total number of sorted Completion Events: " + ceSorted.size()+" ##########");
		for(CompletionEvent completionEvent:ceSorted){
			System.out.println("Event Trigger at: " + completionEvent.getTriggeredAt());
		}
		System.out.println("########## First SST ##########");
		System.out.println(ceSorted.get(0).getContext().getSST());
		System.out.println("########## Filename of First SST ##########");
		System.out.println(ceSorted.get(0).getContext().getTypeShape().getTypeHierarchy().getElement());
	}
}