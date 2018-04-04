package ase.rsse.utilities;

import java.io.IOException;
import java.util.ArrayList;

import ase.rsse.apirec.transactions.AtomicChange;
import ase.rsse.apirec.transactions.ChangeContext;
import ase.rsse.apirec.transactions.CodeContext;
import ase.rsse.apirec.transactions.NodeType;
import ase.rsse.apirec.transactions.Operation;
import ase.rsse.apirec.transactions.QueryAtomicChange;
import ase.rsse.apirec.transactions.QueryChangeContext;
import ase.rsse.apirec.transactions.QueryCodeContext;
import ase.rsse.apirec.transactions.QueryTransaction;
import ase.rsse.apirec.transactions.Transaction;

public final class MockDataUtility {
	
	public static QueryChangeContext QUERY_CHANGE_CONTEXT = new QueryChangeContext();
	public static QueryCodeContext QUERY_CODE_CONTEXT = new QueryCodeContext();

	public static void createTestQueryChangeContext() {
		
		ArrayList<QueryAtomicChange> queryChanges = new ArrayList<>();
		
		QueryAtomicChange c1 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.VariableDeclarationStatement)
				.withLabel("VariableDeclarationStatement")
				.withDistance(19)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);	
		queryChanges.add(c1);
		
		QueryAtomicChange c2 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.ParameterizedType)
				.withLabel("ParameterizedType")
				.withDistance(18)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);
		queryChanges.add(c2);
		
		QueryAtomicChange c3 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.SimpleType)
				.withLabel("Set")
				.withDistance(17)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);
		queryChanges.add(c3);
		
		QueryAtomicChange c4 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.SimpleName)
				.withLabel("Set")
				.withDistance(16)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);
		queryChanges.add(c4);
		
		QueryAtomicChange c5 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.SimpleType)
				.withLabel("TaskResult")
				.withDistance(15)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);
		queryChanges.add(c5);
		
		QueryAtomicChange c6 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.SimpleName)
				.withLabel("TaskResult")
				.withDistance(14)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);
		queryChanges.add(c6);

		QueryAtomicChange c7 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.VariableDeclarationFragement)
				.withLabel("VariableDeclarationFragement")
				.withDistance(13)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);
		queryChanges.add(c7);

		QueryAtomicChange c8 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.SimpleName)
				.withLabel("SimpleName")
				.withDistance(12)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);
		queryChanges.add(c8);

		QueryAtomicChange c9 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.ClassInstanceCreation)
				.withLabel("ClassInstanceCreation")
				.withDistance(11)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);
		queryChanges.add(c9);

		QueryAtomicChange c10 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.ParameterizedType)
				.withLabel("ParameterizedType")
				.withDistance(10)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);
		queryChanges.add(c10);

		QueryAtomicChange c11 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.SimpleType)
				.withLabel("HashSet")
				.withDistance(9)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);
		queryChanges.add(c11);
		
		QueryAtomicChange c12 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.SimpleName)
				.withLabel("SimpleName")
				.withDistance(8)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);
		queryChanges.add(c12);

		QueryAtomicChange c13 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.ExpressionStatement)
				.withLabel("ExpressionStatement")
				.withDistance(7)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);
		queryChanges.add(c13);

		QueryAtomicChange c14 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.MethodInvocation)
				.withLabel("add")
				.withDistance(6)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);
		queryChanges.add(c14);

		QueryAtomicChange c15 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.SimpleName)
				.withLabel("SimpleName")
				.withDistance(5)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);
		queryChanges.add(c15);

		QueryAtomicChange c16 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.SimpleName)
				.withLabel("add")
				.withDistance(4)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);
		queryChanges.add(c16);
		
		QueryAtomicChange c17 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.MethodInvocation)
				.withLabel("getResult")
				.withDistance(3)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);
		queryChanges.add(c17);
		
		QueryAtomicChange c18 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.SimpleName)
				.withLabel("SimpleName")
				.withDistance(2)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);
		queryChanges.add(c18);

		QueryAtomicChange c19 = new QueryAtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.SimpleName)
				.withLabel("getResult")
				.withDistance(1)
				.withWeightOfDataDependency(1f)
				.withWeightOfScope(1f);
		queryChanges.add(c19);
		
		QUERY_CHANGE_CONTEXT.addAllQueryAtomicChanges(queryChanges);
		
		ArrayList<String> queryTokens = new ArrayList<>();
		queryTokens.add("results");
		queryTokens.add("execute");
		queryTokens.add("t");
		queryTokens.add("tasks");
		queryTokens.add("t");
		queryTokens.add("Task");
		queryTokens.add("for");
		queryTokens.add("HashSet");
		queryTokens.add("results");
		queryTokens.add("TaskResult");
		queryTokens.add("Set");
		
		QUERY_CODE_CONTEXT.addTokens(queryTokens);

		for (int i=0;i<queryTokens.size();i++) {
			QUERY_CODE_CONTEXT.addWeightOfDataDependency(i, 1);;
			QUERY_CODE_CONTEXT.addWeightOfScope(i, 1);;
		}
		
		QueryTransaction queryTransaction = new QueryTransaction()
				.withFileName("testQueryTransaction.json")
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
				.withNodeType(NodeType.VariableDeclarationStatement)
				.withLabel("VariableDeclarationStatement");

		AtomicChange at2 = new AtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.MethodInvocation)
				.withLabel("add");
		coctx1.addToken("randomToken");
		chctx1.addAtomicChange(at1);
		chctx1.addAtomicChange(at2);
		Transaction t1 = new Transaction()
				.withChangeContext(chctx1)
				.withCodeContext(coctx1);
		mockTransactions.add(t1);
		
		// mock 2 -> we expect remove as candidate
		ChangeContext chctx2 = new ChangeContext();
		CodeContext coctx2 = new CodeContext();
		AtomicChange at3 = new AtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.MethodInvocation)
				.withLabel("remove");
		chctx2.addAtomicChange(at3);
		coctx2.addToken("for");
		Transaction t2 = new Transaction()
				.withChangeContext(chctx2)
				.withCodeContext(coctx2);
		mockTransactions.add(t2);

		// mock 3 -> we expect contains as candidate
		ChangeContext chctx3 = new ChangeContext();
		CodeContext coctx3 = new CodeContext();
		AtomicChange at4 = new AtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.SimpleType)
				.withLabel("Set");
		AtomicChange at5 = new AtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.MethodInvocation)
				.withLabel("contains");
		chctx3.addAtomicChange(at1);
		chctx3.addAtomicChange(at4);
		chctx3.addAtomicChange(at5);
		coctx3.addToken("doesNotExist");
		Transaction t3 = new Transaction()
				.withChangeContext(chctx3)
				.withCodeContext(coctx3);
		mockTransactions.add(t3);

		// mock 4 -> we expect addAll as candidate
		ChangeContext chctx4 = new ChangeContext();
		CodeContext coctx4 = new CodeContext();
		AtomicChange at6 = new AtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.MethodInvocation)
				.withLabel("addAll");
		chctx4.addAtomicChange(at6);
		coctx4.addToken("Task");
		Transaction t4 = new Transaction()
				.withChangeContext(chctx4)
				.withCodeContext(coctx4);
		mockTransactions.add(t4);

		// mock 5 -> we expect clear as candidate
		ChangeContext chctx5 = new ChangeContext();
		CodeContext coctx5 = new CodeContext();
		AtomicChange at7 = new AtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.MethodInvocation)
				.withLabel("clear");
		AtomicChange at8 = new AtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.SimpleName)
				.withLabel("TaskResult");
		chctx5.addAtomicChange(at1); 
		chctx5.addAtomicChange(at3); 
		chctx5.addAtomicChange(at7);
		chctx5.addAtomicChange(at8);
		coctx5.addToken("DoesStillNotExist");
		Transaction t5 = new Transaction()
				.withChangeContext(chctx5)
				.withCodeContext(coctx5);
		mockTransactions.add(t5);
		
		try {
			int i = 1;
			for (Transaction ts : mockTransactions) {
				String json = JsonUtility.toJson(ts);
				IoUtility.writeTransactionToFile("mockTransaction_" + i + ".json", json);
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
