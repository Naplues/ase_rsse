package ase.rsse.apirec.transactions.query;

import java.util.ArrayList;

public class QueryChangeContext {

	private ArrayList<QueryAtomicChange> QueryAtomicChanges;
	
	public QueryChangeContext() {
		QueryAtomicChanges = new ArrayList<>();
	}

	public ArrayList<QueryAtomicChange> getQueryAtomicChanges() {
		return QueryAtomicChanges;
	}

	public void setQueryAtomicChanges(ArrayList<QueryAtomicChange> QueryAtomicChanges) {
		this.QueryAtomicChanges = QueryAtomicChanges;
	}
	
	public void addQueryAtomicChange(QueryAtomicChange QueryAtomicChange) {
		QueryAtomicChanges.add(QueryAtomicChange);
	}
	
	public void addAllQueryAtomicChanges(ArrayList<QueryAtomicChange> QueryAtomicChanges) {
		this.QueryAtomicChanges.addAll(QueryAtomicChanges);
	}
	
}
