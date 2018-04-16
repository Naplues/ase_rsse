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

import com.google.common.collect.Sets;
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
	public static List<List<String>> changeContextScoreMatrix;
	public static List<List<String>> codeContextScoreMatrix;

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

	public static List<List<String>> scoreChangeContext(Set<AtomicChange> candidateChanges, QueryChangeContext queryChangeContext) {
		System.out.println("Query Change Context size: " + queryChangeContext.getQueryAtomicChanges().size());

		List<String> columnQueryChanges = new ArrayList<>();
		columnQueryChanges.add("Query Context Atomic Changes");
		columnQueryChanges.add("============================");
		queryChangeContext.getQueryAtomicChanges().forEach(change -> {
			columnQueryChanges.add(
					change.getOperation().toString()+","+
					change.getNodeType().toString()+","+
					change.getLabel());
		});
		columnQueryChanges.add("===========Final Scores==========");
		changeContextScoreMatrix = new ArrayList<List<String>>();
		changeContextScoreMatrix.add(columnQueryChanges);
		// calculate the score for all candidate changes

		for (AtomicChange candidateChange : candidateChanges) {
			float changeContextScoreFinal = (float) 0.000;
			List<String> candidateChangeScores = new ArrayList<>();
			candidateChangeScores.add(candidateChange.getLabel());
			candidateChangeScores.add("===============");
			for (QueryAtomicChange queryChange : queryChangeContext.getQueryAtomicChanges()) {
				float changeContextScore = (float) 0.000;
				double logCoOccurences = scoreChangeOccurences(candidateChange, queryChange);
				float weightOfScope = queryChange.getWeihgtOfScope();
				float weightOfDataDependency = queryChange.getWeightOfDataDependency();
				float distance = queryChange.getDistance();
				changeContextScore += (weightOfScope * weightOfDataDependency) / distance * logCoOccurences;
				candidateChangeScores.add(String.format("%.4f", changeContextScore).substring(0,5));
				System.out.println(changeContextScore);
				changeContextScoreFinal += changeContextScore;
			}
			candidateChangeScores.add("|"+String.format("%.4f", changeContextScoreFinal).substring(0,5)+"|");
			changeContextScoreMatrix.add(candidateChangeScores);
		}
		return changeContextScoreMatrix;
	}

	public static List<List<String>> scoreCodeContext(Set<AtomicChange> candidateChanges, QueryCodeContext queryCodeContext) {
		//float codeContextScore = (float) 0.000;
		List<String> columnQueryChanges = new ArrayList<>();
		columnQueryChanges.add("Query Context Atomic Tokens");
		columnQueryChanges.add("============================");
		queryCodeContext.getTokens().forEach(token -> {
			columnQueryChanges.add(
					"Token,"+
					"Type,"+
					token);
		});
                columnQueryChanges.add("===========Final Scores==========");
		codeContextScoreMatrix = new ArrayList<List<String>>();
		codeContextScoreMatrix.add(columnQueryChanges);
		// calculate the score for all candidate changes

		for (AtomicChange candidateChange : candidateChanges) {
                        float codeContextScoreFinal = (float) 0.000;
			List<String> candidateChangeScores = new ArrayList<>();
			candidateChangeScores.add(candidateChange.getLabel());
			candidateChangeScores.add("===============");
			for (String token: queryCodeContext.getTokens()) {
                                float codeContextScore = (float) 0.000;
				double logCoOccurences = scoreCodeOccurences(candidateChange, token);
				float weightOfScope = queryCodeContext.getWeightOfScope().get(queryCodeContext.getTokens().indexOf(token));
				float weightOfDataDependency = queryCodeContext.getWeightOfDataDependency().get(queryCodeContext.getTokens().indexOf(token));
                                float dividend = (float) ((float) queryCodeContext.getTokens().indexOf(token)+1);
                   		codeContextScore += (weightOfScope * weightOfDataDependency) / dividend * logCoOccurences;
                                candidateChangeScores.add(String.format("%.4f", codeContextScore).substring(0,5));
                                codeContextScoreFinal += codeContextScore;
			}
                        candidateChangeScores.add(String.format("%.4f", codeContextScoreFinal).substring(0,5));
			codeContextScoreMatrix.add(candidateChangeScores);
		}
		return codeContextScoreMatrix;
	}

	public static double scoreCodeContextOld(Set<AtomicChange> candidateChanges, QueryCodeContext queryCodeContext) {
		return 0d;
	}

	public static double scoreChangeOccurences(AtomicChange candidateChange, AtomicChange queryChange) {
		// all occurrences of query change
		List<Transaction> queryChangeOccurrences = ALL_TRANSACTIONS.stream()
				.filter(transaction -> transaction.getChangeContex().getAtomicChanges().contains(queryChange))
				.collect(Collectors.toList());
		// all occurrences of candidate change
		List<Transaction> changeOccurrences = ALL_TRANSACTIONS.stream()
				.filter(transaction -> transaction.getChangeContex().getAtomicChanges().contains(candidateChange))
				.collect(Collectors.toList());
		// union of query changes and candidate changes
		List<Transaction> intersect = queryChangeOccurrences.stream()
				.filter(changeOccurrences::contains)
				.collect(Collectors.toList());
		List<Transaction> allOccurrences = Stream.concat(queryChangeOccurrences.stream(), changeOccurrences.stream()).distinct().collect(Collectors.toList());
		return Math.log((intersect.size() + 1.0) / (queryChangeOccurrences.size() + 1.0));
	}

	public static double scoreCodeOccurences(AtomicChange candidateChange, String token) {
		// all occurrences of query code token
		List<Transaction> queryCodeOccurrences = ALL_TRANSACTIONS.stream()
				.filter(transaction -> transaction.getCodeContext().getTokens().contains(token))
				.collect(Collectors.toList());
		// all occurrences of candidate change
		List<Transaction> changeOccurrences = ALL_TRANSACTIONS.stream()
				.filter(transaction -> transaction.getChangeContex().getAtomicChanges().contains(candidateChange))
				.collect(Collectors.toList());
		// union of query changes and candidate changes
		List<Transaction> allOccurrences = Stream.concat(queryCodeOccurrences.stream(), changeOccurrences.stream()).distinct().collect(Collectors.toList());
		return Math.log((allOccurrences.size() + 1.0) / (queryCodeOccurrences.size() + 1.0));
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
