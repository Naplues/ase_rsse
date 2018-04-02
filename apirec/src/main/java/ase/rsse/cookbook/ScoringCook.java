package ase.rsse.cookbook;

import java.util.ArrayList;
import java.util.Set;

import ase.rsse.apirec.transactions.AtomicChange;
import ase.rsse.apirec.transactions.Transaction;
import ase.rsse.utilities.MockDataUtility;
import ase.rsse.utilities.ScoringUtility;

public class ScoringCook {

	public static void main(String[] args) {
		MockDataUtility.createTestQueryChangeContext();
		MockDataUtility.createTestTransactions();
		
		
		ArrayList<Transaction> allTransactions = ScoringUtility.getAllTransactions();
		System.out.println(allTransactions.size());
		
		Set<AtomicChange> allCandidateChanges = ScoringUtility.getAllCandidateChanges(MockDataUtility.QUERY_CHANGE_CONTEXT, MockDataUtility.QUERY_CODE_CONTEXT);
		for (AtomicChange atc : allCandidateChanges) {
			System.out.println(atc.getLabel());
		}
		double scoreChangeContext = ScoringUtility.scoreChangeContext(allCandidateChanges, MockDataUtility.QUERY_CHANGE_CONTEXT);
		System.out.println(scoreChangeContext);
	}
}
