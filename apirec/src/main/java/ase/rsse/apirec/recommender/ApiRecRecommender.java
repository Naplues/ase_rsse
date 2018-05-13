package ase.rsse.apirec.recommender;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ase.rsse.apirec.transactions.MethodMatch;
import ase.rsse.apirec.transactions.TransactionUtility;
import ase.rsse.apirec.transactions.changecontext.AtomicChange;
import ase.rsse.apirec.transactions.changecontext.ChangeContext;
import ase.rsse.apirec.transactions.codecontext.CodeContext;
import ase.rsse.apirec.transactions.query.QueryAtomicChange;
import ase.rsse.apirec.transactions.query.QueryChangeContext;
import ase.rsse.apirec.transactions.query.QueryCodeContext;
import ase.rsse.apirec.transactions.query.QueryTransaction;
import ase.rsse.utilities.ScoringUtility;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;
import cc.kave.commons.model.ssts.impl.SST;

public class ApiRecRecommender implements IApiRecRecommender {

	@Override
	public QueryTransaction createQueryTransaction(ICompletionEvent oldCompletinEvent, ICompletionEvent newCompletionEvent) {
		// initialize SSTs
		SST oldSst = (SST) oldCompletinEvent.getContext().getSST();
		SST newSst = (SST) newCompletionEvent.getContext().getSST();
		TransactionUtility.init(oldSst, newSst);
		
		// matching -> match old and new methods
		ArrayList<MethodMatch> matchedMethods = TransactionUtility.matchMethods(oldSst.getMethods(), newSst.getMethods());
		
		// prepare change and code context
		ChangeContext chctx = new ChangeContext();
		CodeContext coctx = new CodeContext();
		
		// filling -> fill matches into change an code Context
		TransactionUtility.fillTransactionWithMethods(matchedMethods, chctx, coctx);
		
		// create query change context and fill it with values of change context
		QueryChangeContext queryChangeContext = new QueryChangeContext();
		ArrayList<QueryAtomicChange> qac = new ArrayList<>();
		for (AtomicChange ac: chctx.getAtomicChanges()) {
			qac.add(new QueryAtomicChange()
					.withLabel(ac.getLabel())
					.withNodeType(ac.getNodeType())
					.withOperation(ac.getOperation()));
		}
		queryChangeContext.setQueryAtomicChanges(qac);
		
		// create query code context and fill it with values of change context
		QueryCodeContext queryCodeContext = new QueryCodeContext();
		queryCodeContext.setTokens(coctx.getTokens());
		
		// fill query change context and query code context into query transaction and return it
		return new QueryTransaction()
				.withQueryChangeContext(queryChangeContext)
				.withQueryCodeContext(queryCodeContext);
	}

	@Override
	public ArrayList<String> predictKBest(ICompletionEvent oldCompletinEvent, ICompletionEvent newCompletionEvent,
			int k) {
		// input -> should be difference between the two most recent commits
		QueryTransaction queryTransaction = createQueryTransaction(oldCompletinEvent, newCompletionEvent);
		
		// get all candidate transactions
		Set<AtomicChange> candidateChanges = ScoringUtility.getAllCandidateChanges(queryTransaction.getQueryChangeContex(), queryTransaction.getQueryCodeContext());
		
		// score change context
		HashMap<String,Double> changeContextScore = ScoringUtility.scoreChangeContext(candidateChanges, queryTransaction.getQueryChangeContex());
		HashMap<String, Double> codeContextScore = ScoringUtility.scoreCodeContext(candidateChanges, queryTransaction.getQueryCodeContext());
		
		// calculate weighted score
		HashMap<Double, String> weightedScores = calculateWeightedScores(changeContextScore, codeContextScore);
		
		// get k highest values
		List<Double> kHighestScores = weightedScores.keySet()
				.stream()
				.sorted(Comparator.comparing(Double::doubleValue))
				.limit(k)
				.collect(Collectors.toList());
		
		ArrayList<String> bestMethods = new ArrayList<>();
		for (Double score: kHighestScores) {
			bestMethods.add(weightedScores.get(score));
		}
		return bestMethods;
	}
	
	private HashMap<Double,String> calculateWeightedScores(HashMap<String,Double> changeContextScore, HashMap<String, Double> codeContextScore) {
		HashMap<Double, String> mergedScores = new HashMap<>();
		for (String method: changeContextScore.keySet()) {
			Double changeCtxScore = changeContextScore.get(method);
			Double codeCtxScore = codeContextScore.get(method);
			mergedScores.put(changeCtxScore + codeCtxScore, method);
		}
		return mergedScores;
	}

}
