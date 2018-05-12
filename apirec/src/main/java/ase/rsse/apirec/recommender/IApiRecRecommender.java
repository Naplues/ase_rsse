package ase.rsse.apirec.recommender;

import java.util.ArrayList;

import ase.rsse.apirec.transactions.query.QueryTransaction;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;

public interface IApiRecRecommender {
	
	public QueryTransaction createQueryTransaction(ICompletionEvent  oldCompletinEvent, ICompletionEvent newCompletionEvent);

	public ArrayList<String> predictKBest(ICompletionEvent  oldCompletinEvent, ICompletionEvent newCompletionEvent, int k);

}
