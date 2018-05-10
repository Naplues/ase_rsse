package ase.rsse.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import ase.rsse.apirec.transactions.Transaction;
import ase.rsse.apirec.transactions.changecontext.AtomicChange;
import ase.rsse.utilities.IoUtility;
import ase.rsse.utilities.ScoringUtility;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;

public class ScoringUtilityTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockDataUtility.createTestQueryChangeContext();
		MockDataUtility.createTestTransactions();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		IoUtility.deleteTransactionFile(MockDataUtility.TEST_QUERY_TRANSACTION);
		for (String fileName: MockDataUtility.MOCK_TRANSACTIONS) {
			IoUtility.deleteTransactionFile(fileName);
		}
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Ignore
	public void testScoreChangeContext() {
		ArrayList<Transaction> allTransactions = ScoringUtility.getAllTransactions();
		Set<AtomicChange> allCandidateChanges = ScoringUtility.getAllCandidateChanges(MockDataUtility.QUERY_CHANGE_CONTEXT,	MockDataUtility.QUERY_CODE_CONTEXT);
		
		Assert.assertEquals(5, allTransactions.size());
		Assert.assertEquals(5, allCandidateChanges.size());
		
		HashMap<String, Double> changeContextScores = ScoringUtility.scoreChangeContext(allCandidateChanges, MockDataUtility.QUERY_CHANGE_CONTEXT);
		
		Assert.assertEquals(0.2, changeContextScores.get("add"), 0.01);
		Assert.assertEquals(0.2, changeContextScores.get("contains"), 0.01);
		Assert.assertEquals(0.2, changeContextScores.get("addAll"), 0.01);
		Assert.assertEquals(0.2, changeContextScores.get("clear"), 0.01);
		Assert.assertEquals(0.2, changeContextScores.get("remove"), 0.01);
	}
	
	@Test
	@Ignore
	public void testScoreCodeContext() {
		ArrayList<Transaction> allTransactions = ScoringUtility.getAllTransactions();
		Set<AtomicChange> allCandidateChanges = ScoringUtility.getAllCandidateChanges(MockDataUtility.QUERY_CHANGE_CONTEXT,	MockDataUtility.QUERY_CODE_CONTEXT);

		Assert.assertEquals(5, allTransactions.size());
		Assert.assertEquals(5, allCandidateChanges.size());
		
		HashMap<String,Double> codeContextScores = ScoringUtility.scoreCodeContext(allCandidateChanges, MockDataUtility.QUERY_CODE_CONTEXT);
		
		Assert.assertEquals(0.2, codeContextScores.get("add"), 0.01);
		Assert.assertEquals(0.2, codeContextScores.get("contains"), 0.01);
		Assert.assertEquals(0.2, codeContextScores.get("addAll"), 0.01);
		Assert.assertEquals(0.2, codeContextScores.get("clear"), 0.01);
		Assert.assertEquals(0.2, codeContextScores.get("remove"), 0.01);
	}
	
	@Test
	public void testScoring() {
		List<ICompletionEvent> firstEvent = IoUtility.readEvent("XPRD.AnalyticSystemMap.AdministrationModel.Db.EFConfiguration.Location13.json");
	}
}
