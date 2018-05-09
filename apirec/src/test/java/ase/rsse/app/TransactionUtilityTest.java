package ase.rsse.app;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ase.rsse.apirec.transactions.ITransactionConstants;
import ase.rsse.apirec.transactions.MethodMatch;
import ase.rsse.apirec.transactions.TransactionUtility;
import ase.rsse.utilities.IoUtility;
import cc.kave.commons.model.events.IIDEEvent;
import cc.kave.commons.model.events.completionevents.CompletionEvent;
import cc.kave.commons.model.ssts.impl.SST;

public class TransactionUtilityTest {
	
	public static File TRANSACTION_DIRECTORY;
	public static List<IIDEEvent> TEST_EVENTS;
	public static ArrayList<CompletionEvent> TEST_COMPLETION_EVENTS;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		TRANSACTION_DIRECTORY= new File(ITransactionConstants.TRANSACTION_DIRECTORY);
		TEST_EVENTS = IoUtility
				.readEvent("C:\\workspaces\\ase_rsse\\apirec\\Events-170301-2\\2016-08-06\\2.zip");
		TEST_COMPLETION_EVENTS = new ArrayList<>();
		for (IIDEEvent event : TEST_EVENTS) {
			if (event instanceof CompletionEvent) {
				CompletionEvent completionEvent = (CompletionEvent) event;
				TEST_COMPLETION_EVENTS.add(completionEvent);
			}
		}
		Assert.assertNotNull(TEST_EVENTS);
		Assert.assertTrue(TEST_EVENTS.size() > 200);
		Assert.assertTrue(TEST_COMPLETION_EVENTS.size() > 200);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		File testTransactionDirectory = new File(ITransactionConstants.TRANSACTION_DIRECTORY);
		for (File f: testTransactionDirectory.listFiles()) {
			if (f.getName().startsWith("test_")) {
				f.delete();
			}
		}
	}

	@Test
	public void testCreateMatch() {
		List<CompletionEvent> ceSorted = TEST_COMPLETION_EVENTS.stream()
				.filter(Objects::nonNull)
				.sorted(Comparator.comparing(CompletionEvent::getTriggeredAt, Comparator.reverseOrder()))
				.collect(Collectors.toList());

		// test case: everything stays the same -> no transaction created
		SST oldSST = (SST) ceSorted.get(0).getContext().getSST();
		SST newSST = oldSST;
		ArrayList<MethodMatch> matching = TransactionUtility.matchMethods(oldSST.getMethods(), newSST.getMethods());
		Assert.assertNotNull(matching);
		Assert.assertEquals(1, matching.get(0).getSimilarity(), 0.01);
		TransactionUtility.createTransaction(oldSST, newSST);
		File[] testFiles = TRANSACTION_DIRECTORY.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if (name.startsWith("test_")) {
					return true;
				}
				return false;
			}
		});
		// we do not persist empty transactions
		Assert.assertEquals(0, testFiles.length);
		
	}
	
	@Test
	public void testMatchWithDifferences() {
		List<CompletionEvent> ceSorted = TEST_COMPLETION_EVENTS.stream()
				.filter(Objects::nonNull)
				.sorted(Comparator.comparing(CompletionEvent::getTriggeredAt, Comparator.reverseOrder()))
				.collect(Collectors.toList());
		
		SST oldSST = (SST) ceSorted.get(185).getContext().getSST();
		SST newSST = (SST) ceSorted.get(192).getContext().getSST();
		
		ArrayList<MethodMatch> matching = TransactionUtility.matchMethods(oldSST.getMethods(), newSST.getMethods());
		Assert.assertNotNull(matching);
		
		TransactionUtility.createTransaction(oldSST, newSST);
		File[] testFiles = TRANSACTION_DIRECTORY.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if (name.startsWith("test_")) {
					return true;
				}
				return false;
			}
		});
		// we do not persist empty transactions
		Assert.assertTrue(testFiles.length > 0);
	}
}
