package ase.rsse.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ase.rsse.apirec.transactions.ITransactionConstants;
import ase.rsse.apirec.transactions.Transaction;
import ase.rsse.apirec.transactions.changecontext.ChangeContext;
import ase.rsse.apirec.transactions.codecontext.CodeContext;
import ase.rsse.utilities.IoUtility;
import ase.rsse.utilities.JsonUtility;

public class TransactionTest  {
	
	public static final String TEST_FILE_NAME = "test.json";
	public static ChangeContext CHANGE_CONTEXT;
	public static CodeContext CODE_CONTEXT;
	public static Transaction TRANSACTION;
	
	@BeforeClass
	public static void setUp() {
		CHANGE_CONTEXT = new ChangeContext();
		CODE_CONTEXT = new CodeContext();
		
		TRANSACTION = new Transaction();
		TRANSACTION.setFileName(TEST_FILE_NAME);
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
