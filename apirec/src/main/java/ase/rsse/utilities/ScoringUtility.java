package ase.rsse.utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import ase.rsse.apirec.transactions.NodeType;
import ase.rsse.apirec.transactions.Operation;
import ase.rsse.apirec.transactions.Transaction;
import ase.rsse.apirec.transactions.changecontext.AtomicChange;
import ase.rsse.apirec.transactions.query.QueryAtomicChange;
import ase.rsse.apirec.transactions.query.QueryChangeContext;
import ase.rsse.apirec.transactions.query.QueryCodeContext;

public final class ScoringUtility {
	public static ArrayList<Transaction> ALL_TRANSACTIONS = getAllTransactions();
	public static List<List<String>> changeContextScoreMatrix;
	public static List<List<String>> codeContextScoreMatrix;

	public static Set<AtomicChange> getAllCandidateChanges(QueryChangeContext queryChangeContext,
			QueryCodeContext queryCodeContext) {
		HashSet<AtomicChange> candidateChanges = new HashSet<>();

		Set<Transaction> candidateTransactions = ALL_TRANSACTIONS.stream()
				.filter(transaction -> !Collections.disjoint(transaction.getChangeContex().getAtomicChanges(), queryChangeContext.getQueryAtomicChanges())
				|| !Collections.disjoint(transaction.getCodeContext().getTokens(), queryCodeContext.getTokens()))
				.collect(Collectors.toSet());

		for (Transaction ts : candidateTransactions) {
			ts.getChangeContex().getAtomicChanges().stream()
					.filter(atomicChange -> atomicChange.getOperation() == Operation.ADD)
					.filter(atomicChange -> atomicChange.getNodeType() == NodeType.MethodInvocation)
					.forEach(atomicChange -> candidateChanges.add(new AtomicChange().withOperation(Operation.ADD)
							.withNodeType(NodeType.MethodInvocation).withLabel(atomicChange.getLabel())));
		}
		return candidateChanges;
	}

	public static HashMap<String, Double> scoreChangeContext(Set<AtomicChange> candidateChanges, QueryChangeContext queryChangeContext) {
		HashMap<String, Double> changeContextScores = new HashMap<>();
		for (AtomicChange candidateChange : candidateChanges) {
			double changeContextScore = 0f;
			for (QueryAtomicChange queryChange : queryChangeContext.getQueryAtomicChanges()) {
				double currentChangeContextScore = scoreChangeOccurences(candidateChange, queryChange);
				changeContextScore += currentChangeContextScore;
			}
			changeContextScores.put(candidateChange.getLabel(),	changeContextScore / queryChangeContext.getQueryAtomicChanges().size());
		}
		return changeContextScores;
	}

	public static HashMap<String, Double> scoreCodeContext(Set<AtomicChange> candidateChanges, QueryCodeContext queryCodeContext) {
		HashMap<String, Double> codeContextScores = new HashMap<>();
		for (AtomicChange candidateChange : candidateChanges) {
			double codeContextScore = scoreCodeOccurences(candidateChange, queryCodeContext);
			codeContextScores.put(candidateChange.getLabel(), codeContextScore);
		}
		return codeContextScores;
	}

	public static double scoreChangeOccurences(AtomicChange candidateChange, QueryAtomicChange queryChange) {
		List<Transaction> queryChangeOccurrences = getTransactionsWithQueryChangeOccurrence(queryChange);
		List<Transaction> changeOccurrences = getTransactionsWithCandidateChangeOccurrence(candidateChange);

		List<Transaction> numberOfCoOccurences = queryChangeOccurrences.stream()
				.filter(changeOccurrences::contains)
				.collect(Collectors.toList());

		if (numberOfCoOccurences.size() == 0) {
			return 0;
		}

		return (double) numberOfCoOccurences.size() / (queryChangeOccurrences.size());
	}

	private static List<Transaction> getTransactionsWithQueryChangeOccurrence(QueryAtomicChange queryChange) {
		return ALL_TRANSACTIONS.stream()
				.filter(transaction -> transaction.getChangeContex().getAtomicChanges().contains(queryChange))
				.collect(Collectors.toList());
	}

	private static List<Transaction> getTransactionsWithCandidateChangeOccurrence(AtomicChange candidateChange) {
		return ALL_TRANSACTIONS.stream()
				.filter(transaction -> transaction.getChangeContex().getAtomicChanges().contains(candidateChange))
				.collect(Collectors.toList());
	}

	public static double scoreCodeOccurences(AtomicChange candidateChange, QueryCodeContext queryCodeContext) {
		double codeContextScore = 0.0;
		
		for (String token: queryCodeContext.getTokens()) {
			List<Transaction> queryCodeOccurrences = getTransactionsWithToken(token);
			List<Transaction> candidateTokenOccurrences = getTransactionsWithToken(candidateChange.getLabel());
			
			int numberOfCoOccurences = queryCodeOccurrences.stream()
					.filter(candidateTokenOccurrences::contains)
					.collect(Collectors.toList()).size();
			
			if (numberOfCoOccurences != 0) {
				codeContextScore = ((double) numberOfCoOccurences / (queryCodeOccurrences.size()));
			}
		}
		return codeContextScore / queryCodeContext.getTokens().size();
	}

	public static ArrayList<Transaction> getAllTransactions() {
		File[] allFiles = IoUtility.findAllTransactions();
		ArrayList<Transaction> allTransactions = new ArrayList<>();
		int counter = 0;
		for (File file : allFiles) {
			try {
				String string = FileUtils.readFileToString(file);
				Transaction transaction = JsonUtility.fromJson(string);
				allTransactions.add(transaction);
				if((counter % 10000) == 0){
					System.out.println(counter);
				}
				counter++;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return allTransactions;
	}

	private static List<Transaction> getTransactionsWithToken(String queryToken) {
		return ALL_TRANSACTIONS.stream()
				.filter(transaction -> transaction.getCodeContext().getTokens().contains(queryToken))
				.collect(Collectors.toList());
	}

	public int getNumberOfQueryChangeOccurrences(QueryAtomicChange queryChange) {
		return ALL_TRANSACTIONS.stream()
				.filter(transaction -> transaction.getChangeContex().getAtomicChanges().contains(queryChange))
				.collect(Collectors.toList()).size();
	}
}
