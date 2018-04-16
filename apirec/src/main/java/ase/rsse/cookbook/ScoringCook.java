package ase.rsse.cookbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ase.rsse.apirec.transactions.AtomicChange;
import ase.rsse.apirec.transactions.Transaction;
import ase.rsse.utilities.MockDataUtility;
import ase.rsse.utilities.ScoringUtility;

public class ScoringCook {
//	public static List<List<String>> changeContextScoreMatrixSceleton;
//	public static List<List<String>> codeContextScoreMatrixSceleton;

	public static void main(String[] args) {
		MockDataUtility.createTestQueryChangeContext();
		MockDataUtility.createTestTransactions();
		
		ArrayList<Transaction> allTransactions = ScoringUtility.getAllTransactions();
		System.out.println(allTransactions.size());
		
		Set<AtomicChange> allCandidateChanges = ScoringUtility.getAllCandidateChanges(MockDataUtility.QUERY_CHANGE_CONTEXT,
				MockDataUtility.QUERY_CODE_CONTEXT);

		List<List<String>> scoreChangeContext = ScoringUtility.scoreChangeContext(allCandidateChanges, MockDataUtility.QUERY_CHANGE_CONTEXT);
		List<List<String>> scoreCodeContext = ScoringUtility.scoreCodeContext(allCandidateChanges, MockDataUtility.QUERY_CODE_CONTEXT);

		// Print Change Context Score Matrix
		System.out.printf("%150s","---------- Change Context Scores ----------\n");
		for (int i = 0 ; i < scoreChangeContext.get(0).size(); i++) {
			System.out.printf("%80s\t%15s\t%15s\t%15s\t%15s\t%15s\n", scoreChangeContext.get(0).get(i),
					scoreChangeContext.get(1).get(i),
					scoreChangeContext.get(2).get(i),
					scoreChangeContext.get(3).get(i),
					scoreChangeContext.get(4).get(i),
					scoreChangeContext.get(5).get(i));
		}

		System.out.printf("%150s","---------- Code Context Scores ----------\n");
		for (int i = 0 ; i < scoreCodeContext.get(0).size(); i++) {
			System.out.printf("%80s\t%15s\t%15s\t%15s\t%15s\t%15s\n", scoreCodeContext.get(0).get(i),
					scoreCodeContext.get(1).get(i),
					scoreCodeContext.get(2).get(i),
					scoreCodeContext.get(3).get(i),
					scoreCodeContext.get(4).get(i),
					scoreCodeContext.get(5).get(i));
		}
	}
}
