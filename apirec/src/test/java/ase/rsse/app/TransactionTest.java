package ase.rsse.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;

import ase.rsse.apirec.transactions.ChangeContext;
import ase.rsse.apirec.transactions.CodeContext;
import ase.rsse.apirec.transactions.ITransactionConstants;
import ase.rsse.apirec.transactions.Operation;
import ase.rsse.apirec.transactions.Transaction;
import ase.rsse.utilities.IoUtility;
import ase.rsse.utilities.JsonUtility;

public class TransactionTest  {
	
	public static final String TEST_FILE_NAME = "test.json";
	public static ChangeContext CHANGE_CONTEXT;
	public static CodeContext CODE_CONTEXT;
	public static Transaction TRANSACTION;
	
	@BeforeClass
	public static void setUp() {
		File file = new File(ITransactionConstants.TRANSACTION_DIRECTORY, TEST_FILE_NAME);
		file.delete();
		CHANGE_CONTEXT = new ChangeContext()
			.withIndex(0)
			.withLabel("change_context_label")
			.withOperation(Operation.ADD)
			.withDistance(2.5f)
			.withWeightOfScope(0.5f)
			.withWeightOfDependency(1f);
		
		CODE_CONTEXT = new CodeContext()
				.withIndex(0)
				.withLabel("code_context_label")
				.withOperation(Operation.ADD)
				.withDistance(5f)
				.withWeightOfScope(1f)
				.withWeightOfDependency(1f);
		
		TRANSACTION = new Transaction();
		TRANSACTION.setFileName(TEST_FILE_NAME);
		TRANSACTION.setChangeContex(Lists.newArrayList(CHANGE_CONTEXT));
		TRANSACTION.setCodeContext(Lists.newArrayList(CODE_CONTEXT));
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		File file = new File(ITransactionConstants.TRANSACTION_DIRECTORY, TEST_FILE_NAME);
		file.delete();
	}
	
	@Test
	public void testSerialization() {	
		String json = JsonUtility.toJson(TRANSACTION);
		try {
			IoUtility.writeTransactionToFile(TEST_FILE_NAME, json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		File file = new File(ITransactionConstants.TRANSACTION_DIRECTORY, TEST_FILE_NAME);
		assertTrue(file.exists());
		assertTrue(file.isFile());
	}
	
	@Test
	public void testDeserialization() {
		try {
			String contentOfTransaction = IoUtility.readContentOfTransaction(TEST_FILE_NAME);
			Transaction ts = JsonUtility.fromJson(contentOfTransaction);
			
			assertEquals(ts.getFileName(), TRANSACTION.getFileName());
			// ChangeContext
			assertEquals(ts.getChangeContex().get(0).getDistance(), TRANSACTION.getChangeContex().get(0).getDistance(), 0.01);
			assertEquals(ts.getChangeContex().get(0).getLabel(), TRANSACTION.getChangeContex().get(0).getLabel());
			// CodeContext
			assertEquals(ts.getCodeContext().get(0).getDistance(), TRANSACTION.getCodeContext().get(0).getDistance(), 0.01);
			assertEquals(ts.getChangeContex().get(0).getLabel(), TRANSACTION.getChangeContex().get(0).getLabel());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
