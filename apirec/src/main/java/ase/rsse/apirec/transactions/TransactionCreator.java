/*Copyright [2018] [Kürsat Aydinli & Remo Schenker]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package ase.rsse.apirec.transactions;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import ase.rsse.utilities.IoUtility;
import cc.kave.commons.model.events.completionevents.CompletionEvent;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;

public class TransactionCreator {
	
	public static void main(String[] args) {
		// 1) get all events
		Set<String> allEventData = IoUtility.findAllFiles(ITransactionConstants.EVENTS_DIRECTORY, s -> s.endsWith(".zip"));
		System.out.println("Number of event data zips found: " + allEventData.size());
		
		System.out.println("Starting creation of transactions...");
		int counter = 0;
		for (String eventData: allEventData) {
			System.out.println(counter+" - "+eventData);
			// 2) for each event extract all completion events
			List<ICompletionEvent> events = IoUtility.readEvent(eventData);
			HashMap<String, ArrayList<CompletionEvent>> fileToEvents = prepareCompletionEvents(events);
			processCompletionEvents(fileToEvents);
			counter++;
		}
	}

	private static void processCompletionEvents(HashMap<String, ArrayList<CompletionEvent>> fileToEvents) {
		// process each file
		int numberOfFilesToProcess = fileToEvents.entrySet().size();
		int numberOfFilesProcessed = 0;
		System.out.println("Number of files to process: " +  numberOfFilesToProcess);
		System.out.println("Processing files...");
		for (String key: fileToEvents.keySet()) {
			
			ArrayList<CompletionEvent> completionEvents = fileToEvents.get(key);
			// 4) sort completion events by trigger time
			List<CompletionEvent> sortedCompletionEvents = completionEvents.stream()
					.filter(Objects::nonNull)
					.sorted(Comparator.comparing(CompletionEvent::getTriggeredAt))
					.collect(Collectors.toList());
			
			// process consecutive pairs
			for (int i = 0; i < sortedCompletionEvents.size() - 1; i++) {
				String fileName = TransactionUtility.clean(sortedCompletionEvents.get(i+1).getContext().getSST().getEnclosingType().getFullName());
				File file = new File(ITransactionConstants.TRANSACTION_DIRECTORY + fileName);
				if (!file.exists()) {
					System.out.println("Creating transaction...");
					// 5) create transaction of consecutive completion events
					TransactionUtility.createTransaction(sortedCompletionEvents.get(i), sortedCompletionEvents.get(i+1), i);
				} else {
					System.out.println("Transaction already exsisted...");
				}
			}

			
			numberOfFilesProcessed += 1;
			System.out.println("Number of files processed: " + numberOfFilesProcessed + " / " + numberOfFilesToProcess);
		}
	}

	public static HashMap<String, ArrayList<CompletionEvent>> prepareCompletionEvents(List<ICompletionEvent> events) {
		System.out.println("Preparing completion events...");
		HashMap<String, ArrayList<CompletionEvent>> fileToEvents = new HashMap<>();
		// 3) order by file name
		for (ICompletionEvent event: events) {
			CompletionEvent e = (CompletionEvent) event;
			String fullName = e.getContext().getSST().getEnclosingType().getFullName();
			if (fileToEvents.containsKey(fullName)) {
				fileToEvents.get(fullName).add(e);
			} else {
				ArrayList<CompletionEvent> evts = new ArrayList<>();
				evts.add(e);
				fileToEvents.put(fullName, evts);
			}
		}
		return fileToEvents;
	}
}
