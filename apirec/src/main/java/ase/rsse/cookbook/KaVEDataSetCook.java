package ase.rsse.cookbook;

import java.util.*;
import java.util.stream.Collectors;

import ase.rsse.utilities.IoUtility;
import cc.kave.commons.model.events.IIDEEvent;
import cc.kave.commons.model.events.completionevents.CompletionEvent;
import cc.kave.commons.model.events.completionevents.Context;
import cc.kave.commons.model.events.versioncontrolevents.VersionControlEvent;

public class KaVEDataSetCook {

	public static void main(String[] args) {
		// get all contexts for a given C# solution on GitHub
//		List<Context> contexts = IoUtility.readContext(
//				"apirec/resharper-angularjs.sln-contexts.zip");
//		for (Context ctx : contexts) {
//			System.out.println(ctx);
//		}

		// get all events of a given user
		List<IIDEEvent> events = IoUtility
				.readEvent("F:\\Wifo_Msc\\2. Semester\\Advanced SW Eng\\ase_rsse\\apirec\\2.zip");
		for (IIDEEvent event : events) {
			ArrayList<VersionControlEvent> versionControlEvents = new ArrayList<>();
			ArrayList<CompletionEvent> completionEvents = new ArrayList<>();
			ArrayList<String> fileNames = new ArrayList<>();

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

//			System.out.println("########## Total number of Completion Events: " + ceSorted.size()+" ##########");
//
//			System.out.println("########## Total number of files: " + fileNames.size()+" ##########");
//
//			Set<String> distinctFiles = new LinkedHashSet<>(fileNames);
//			System.out.println("########## Total number of distinct files: " + distinctFiles.size()+" ##########");
//			System.out.println("########## First SST ##########");
//			System.out.println(ceSorted.get(0).getContext().getSST());
//			System.out.println("########## Filename of First SST ##########");
//			System.out.println(ceSorted.get(0).getContext().getTypeShape().getTypeHierarchy().getElement());
		}
	}
}
