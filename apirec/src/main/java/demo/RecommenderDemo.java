package demo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import ase.rsse.apirec.recommender.ApiRecRecommender;
import ase.rsse.apirec.transactions.ITransactionConstants;
import ase.rsse.apirec.transactions.TransactionCreator;
import ase.rsse.apirec.transactions.query.QueryAtomicChange;
import ase.rsse.apirec.transactions.query.QueryTransaction;
import ase.rsse.utilities.IoUtility;
import cc.kave.commons.model.events.completionevents.CompletionEvent;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;

public class RecommenderDemo {
	
	// as an input we need two completion events of an user for the same class
	public static Set<String> DEMO_ZIP = IoUtility.findAllFiles(ITransactionConstants.EVENTS_DIRECTORY + "\\2016-07-29", s -> s.endsWith("0.zip"));
	public static String DEMO_EVENT_FILE = "C:\\workspaces\\ase_rsse\\apirec\\Events-170301-2\\2016-07-29\\0.zip";
	
	public static void main(String[] args) {
		
		// read the demo event
		List<ICompletionEvent> demoEvents = IoUtility.readEvent(DEMO_EVENT_FILE);
		
		// order by file
		HashMap<String, ArrayList<CompletionEvent>> eventsByFile = TransactionCreator.prepareCompletionEvents(demoEvents);
		
		// get completion events for ITW.PresentationLayer.CustomJobInfo.iReview.eReviewExplorerJobLookupRow
		ArrayList<CompletionEvent> completionEvents = eventsByFile.get("ITW.PresentationLayer.CustomJobInfo.iReview.eReviewExplorer");
		
		// sort them by trigger time
		List<CompletionEvent> sortedCompletionEvents = completionEvents.stream()
				.filter(Objects::nonNull)
				.sorted(Comparator.comparing(CompletionEvent::getTriggeredAt))
				.collect(Collectors.toList());
		
		// take two consecutive completion events
		CompletionEvent oldCompletionEvent = sortedCompletionEvents.get(1);
		CompletionEvent newCompletionEvent = sortedCompletionEvents.get(2);
		
		// we can use the recommender to create query transaction out of two consecutive transactions
		// i.e. take a look at the query that results of the code completion events
		ApiRecRecommender recommender = new ApiRecRecommender();
		QueryTransaction queryTransaction = recommender.createQueryTransaction(sortedCompletionEvents.get(1), sortedCompletionEvents.get(2));
		
		// the query
		for (QueryAtomicChange qac: queryTransaction.getQueryChangeContex().getQueryAtomicChanges()) {
			System.out.println("Operation: " + qac.getOperation() + " NodeType: " + qac.getNodeType() + " Label: " + qac.getLabel());
		}
		
		ArrayList<String> kBest = recommender.predictKBest(oldCompletionEvent, newCompletionEvent, 5);
		for (int i = 0; i < kBest.size(); i++) {
			System.out.println((i+1) + ". -> " + kBest.get(i));
		}
	}
}
