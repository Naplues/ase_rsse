package ase.rsse.cookbook;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import ase.rsse.utilities.IoUtility;
import cc.kave.commons.model.events.IIDEEvent;
import cc.kave.commons.model.events.completionevents.CompletionEvent;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;
import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.ssts.IStatement;
import cc.kave.commons.model.ssts.declarations.IMethodDeclaration;

public class TransactionCook {

	public static void main(String[] args) {
		// get all events of a given user
		List<IIDEEvent> events = IoUtility
				.readEvent("2.zip");
		System.out.println("Total number of events: " + events.size());
		
		ArrayList<CompletionEvent> completionEvents = new ArrayList<>();
		ArrayList<String> fileNames = new ArrayList<>();
		
		for (IIDEEvent event : events) {
			if (event instanceof ICompletionEvent) {
				CompletionEvent completionEvent = (CompletionEvent) event;
				completionEvents.add(completionEvent);
				fileNames.add(completionEvent.getContext().getTypeShape().getTypeHierarchy().getClass().toString());
			}

		}
		System.out.println("Total number of completion events: " + completionEvents.size());
		if (completionEvents.size() > 1) {
			List<CompletionEvent> ceSorted = completionEvents.stream()
					.filter(Objects::nonNull)
					.sorted(Comparator.comparing(CompletionEvent::getTriggeredAt, Comparator.reverseOrder()))
					.limit(2)
					.collect(Collectors.toList());
			
			ISST sst1 = ceSorted.get(0).getContext().getSST();
			
			
			Set<IMethodDeclaration> methods = sst1.getMethods();
			for (IMethodDeclaration method : methods) {
				List<IStatement> body = method.getBody();
				for (IStatement stmt : body) {
					// do something
				}
			}
			
			System.out.println(ceSorted.get(0));
			System.out.println(ceSorted.get(1));
		}
	}
}
