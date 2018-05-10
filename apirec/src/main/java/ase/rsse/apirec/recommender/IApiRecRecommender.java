package ase.rsse.apirec.recommender;

import java.util.ArrayList;

import ase.rsse.apirec.transactions.query.QueryChangeContext;
import ase.rsse.apirec.transactions.query.QueryCodeContext;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;

public interface IApiRecRecommender {
	
	public QueryChangeContext createQueryChangeContext(ICompletionEvent  oldCompletinEvent, ICompletionEvent newCompletionEvent);

	public QueryCodeContext createQueryCodeContext(ICompletionEvent  oldCompletinEvent, ICompletionEvent newCompletionEvent);
	
	public ArrayList<String> predictKBest(ICompletionEvent  oldCompletinEvent, ICompletionEvent newCompletionEvent, int k);

}
