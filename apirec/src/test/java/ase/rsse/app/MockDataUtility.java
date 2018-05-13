package ase.rsse.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import ase.rsse.apirec.transactions.NodeType;
import ase.rsse.apirec.transactions.Operation;
import ase.rsse.apirec.transactions.Transaction;
import ase.rsse.apirec.transactions.changecontext.AtomicChange;
import ase.rsse.apirec.transactions.changecontext.ChangeContext;
import ase.rsse.apirec.transactions.codecontext.CodeContext;
import ase.rsse.apirec.transactions.query.QueryAtomicChange;
import ase.rsse.apirec.transactions.query.QueryChangeContext;
import ase.rsse.apirec.transactions.query.QueryCodeContext;
import ase.rsse.apirec.transactions.query.QueryTransaction;
import ase.rsse.utilities.IoUtility;
import ase.rsse.utilities.JsonUtility;

public class MockDataUtility {
	
	public static String TEST_QUERY_TRANSACTION = "testQueryTransaction.json";
	public static ArrayList<String> MOCK_TRANSACTIONS = new ArrayList<>();
	public static QueryChangeContext QUERY_CHANGE_CONTEXT = new QueryChangeContext();
	public static QueryCodeContext QUERY_CODE_CONTEXT = new QueryCodeContext();

	public static void createTestQueryChangeContext() {
		
		ArrayList<QueryAtomicChange> queryChanges = new ArrayList<>();
		
		QueryAtomicChange c1 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.MethodInvocation)
				.withLabel("remove")
				.withDistance(1)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);
		queryChanges.add(c1);
		
		QueryAtomicChange c2 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.MethodInvocation)
				.withLabel("add")
				.withDistance(1)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);
		queryChanges.add(c2);

		QueryAtomicChange c3 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.MethodInvocation)
				.withLabel("addAll")
				.withDistance(1)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);
		queryChanges.add(c3);

		QueryAtomicChange c4 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.MethodInvocation)
				.withLabel("contains")
				.withDistance(1)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);
		queryChanges.add(c4);

		
		QueryAtomicChange c5 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.MethodInvocation)
				.withLabel("clear")
				.withDistance(1)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);
		queryChanges.add(c5);
		
		QUERY_CHANGE_CONTEXT.addAllQueryAtomicChanges(queryChanges);
		
		HashSet<String> queryTokens = new HashSet<>();
		queryTokens.add("results");
		queryTokens.add("execute");
		queryTokens.add("t");
		queryTokens.add("tasks");
		queryTokens.add("for"); 
		
		QUERY_CODE_CONTEXT.addTokens(queryTokens);

		for (int i=0; i<queryTokens.size(); i++) {
			QUERY_CODE_CONTEXT.addWeightOfDataDependency(i, 1);;
			QUERY_CODE_CONTEXT.addWeightOfScope(i, 1);;
		}
		
		QueryTransaction queryTransaction = new QueryTransaction()
				.withFileName(TEST_QUERY_TRANSACTION)
				.withQueryChangeContext(QUERY_CHANGE_CONTEXT)
				.withQueryCodeContext(QUERY_CODE_CONTEXT);
		
		
		String json = JsonUtility.toJson(queryTransaction);
		try {
			IoUtility.writeTransactionToFile(queryTransaction.getFileName(), json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void createTestTransactions() {
		
		ArrayList<Transaction> mockTransactions = new ArrayList<>();
		
		// mock 1 -> we expect add as candidate
		ChangeContext chctx1 = new ChangeContext();
		CodeContext coctx1 = new CodeContext();
		AtomicChange at1 = new AtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.MethodInvocation)
				.withLabel("add");
		chctx1.addAtomicChange(at1);
		coctx1.addToken("results");
		coctx1.addToken("add");

		Transaction t1 = new Transaction()
				.withChangeContext(chctx1)
				.withCodeContext(coctx1);
		mockTransactions.add(t1);
		
		// mock 2 -> we expect remove as candidate
		ChangeContext chctx2 = new ChangeContext();
		CodeContext coctx2 = new CodeContext();
		AtomicChange at2 = new AtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.MethodInvocation)
				.withLabel("remove");
		chctx2.addAtomicChange(at2);
		coctx2.addToken("execute");
		coctx2.addToken("remove");
		
		Transaction t2 = new Transaction()
				.withChangeContext(chctx2)
				.withCodeContext(coctx2);
		mockTransactions.add(t2);

		// mock 3 -> we expect contains as candidate
		ChangeContext chctx3 = new ChangeContext();
		CodeContext coctx3 = new CodeContext();
		AtomicChange at3 = new AtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.MethodInvocation)
				.withLabel("contains");
		chctx3.addAtomicChange(at3);
		coctx3.addToken("t");
		coctx3.addToken("contains");
		
		Transaction t3 = new Transaction()
				.withChangeContext(chctx3)
				.withCodeContext(coctx3);
		mockTransactions.add(t3);

		// mock 4 -> we expect addAll as candidate
		ChangeContext chctx4 = new ChangeContext();
		CodeContext coctx4 = new CodeContext();
		AtomicChange at4 = new AtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.MethodInvocation)
				.withLabel("addAll");
		chctx4.addAtomicChange(at4);
		coctx4.addToken("tasks");
		coctx4.addToken("addAll");
		
		Transaction t4 = new Transaction()
				.withChangeContext(chctx4)
				.withCodeContext(coctx4);
		mockTransactions.add(t4);

		// mock 5 -> we expect clear as candidate
		ChangeContext chctx5 = new ChangeContext();
		CodeContext coctx5 = new CodeContext();
		AtomicChange at5 = new AtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.MethodInvocation)
				.withLabel("clear");
		chctx5.addAtomicChange(at5);
		coctx5.addToken("for");
		coctx5.addToken("clear");
		
		Transaction t5 = new Transaction()
				.withChangeContext(chctx5)
				.withCodeContext(coctx5);
		mockTransactions.add(t5);
		
		
		try {
			int i = 1;
			for (Transaction ts : mockTransactions) {
				String json = JsonUtility.toJson(ts);
				String fileName = "mockTransaction_" + i + ".json";
				IoUtility.writeTransactionToFile(fileName, json);
				MOCK_TRANSACTIONS.add(fileName);
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
