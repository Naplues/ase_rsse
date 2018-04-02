package ase.rsse.utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

import ase.rsse.apirec.transactions.AtomicChange;
import ase.rsse.apirec.transactions.NodeType;
import ase.rsse.apirec.transactions.Operation;
import ase.rsse.apirec.transactions.QueryAtomicChange;
import ase.rsse.apirec.transactions.QueryChangeContext;
import ase.rsse.apirec.transactions.QueryCodeContext;
import ase.rsse.apirec.transactions.Transaction;

public final class ScoringUtility {

	public static ArrayList<Transaction> ALL_TRANSACTIONS = getAllTransactions();

	public static Set<AtomicChange> getAllCandidateChanges(QueryChangeContext queryChangeContext,
			QueryCodeContext queryCodeContext) {
		HashSet<AtomicChange> candidateChanges = new HashSet<>();
		Set<Transaction> candidateTransactions = ALL_TRANSACTIONS.stream().filter(transaction -> !Collections
				.disjoint(transaction.getChangeContex().getAtomicChanges(), queryChangeContext.getQueryAtomicChanges())
				|| !Collections.disjoint(transaction.getCodeContext().getTokens(), queryCodeContext.getTokens()))
				.collect(Collectors.toSet());

		for (Transaction ts : candidateTransactions) {
			ts.getChangeContex().getAtomicChanges().stream()
					.filter(atomicChange -> atomicChange.getOperation() == Operation.ADD)
					// TODO: figure out how to identify method invocations
					.filter(atomicChange -> atomicChange.getNodeType() == NodeType.MethodInvocation)
					.forEach(atomicChange -> candidateChanges.add(new AtomicChange().withOperation(Operation.ADD)
							.withNodeType(NodeType.MethodInvocation).withLabel(atomicChange.getLabel())));
		}
		return candidateChanges;
	}

	public static double scoreChangeContext(Set<AtomicChange> candidateChanges, QueryChangeContext queryChangeContext) {
		double changeContextScore = 0;
		// calcualte the score for all candidate changes
		for (AtomicChange candidateChange : candidateChanges) {
			for (QueryAtomicChange queryChange : queryChangeContext.getQueryAtomicChanges()) {
				double logCoOccurences = scoreOccurences(candidateChange, queryChange);
				float weightOfScope = queryChange.getWeihgtOfScope();
				float weightOfDataDependency = queryChange.getWeightOfDataDependency();
				float distance = queryChange.getDistance();
				changeContextScore += (weightOfScope * weightOfDataDependency) / distance * logCoOccurences;
				System.out.println(changeContextScore);
			}
		}
		return changeContextScore;
	}

	public static double scoreCodeContext(Set<AtomicChange> candidateChanges, QueryCodeContext queryCodeContext) {
		return 0d;
	}

	public static double scoreOccurences(AtomicChange candidateChange, AtomicChange queryChange) {
		// all occurrences of query change
		List<Transaction> queryChangeOccurrences = ALL_TRANSACTIONS.stream()
				.filter(transaction -> transaction.getChangeContex().getAtomicChanges().contains(queryChange))
				.collect(Collectors.toList());
		// all occurrences of candidate change
		List<Transaction> changeOccurrences = ALL_TRANSACTIONS.stream()
				.filter(transaction -> transaction.getChangeContex().getAtomicChanges().contains(candidateChange))
				.collect(Collectors.toList());
		// union of query changes and candidate changes
		List<Transaction> allOccurrences = Stream.concat(queryChangeOccurrences.stream(), changeOccurrences.stream()).distinct().collect(Collectors.toList());
		return Math.log((allOccurrences.size() + 1.0) / (queryChangeOccurrences.size() + 1.0));
	}

	public static ArrayList<Transaction> getAllTransactions() {
		File[] allFiles = IoUtility.findAllTransactions();
		ArrayList<Transaction> allTransactions = new ArrayList<>();
		for (File file : allFiles) {
			try {
				String string = FileUtils.readFileToString(file);
				Transaction transaction = JsonUtility.fromJson(string);
				allTransactions.add(transaction);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return allTransactions;
	}
}
