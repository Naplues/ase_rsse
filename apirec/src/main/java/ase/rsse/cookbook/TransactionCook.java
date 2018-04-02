package ase.rsse.cookbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ase.rsse.apirec.transactions.AtomicChange;
import ase.rsse.apirec.transactions.ChangeContext;
import ase.rsse.apirec.transactions.CodeContext;
import ase.rsse.apirec.transactions.NodeType;
import ase.rsse.apirec.transactions.Operation;
import ase.rsse.apirec.transactions.QueryCodeContext;
import ase.rsse.apirec.transactions.Transaction;
import ase.rsse.utilities.IoUtility;
import ase.rsse.utilities.JsonUtility;
import cc.kave.commons.model.events.IIDEEvent;
import cc.kave.commons.model.events.completionevents.CompletionEvent;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;
import cc.kave.commons.utils.ssts.SSTPrintingUtils;

public class TransactionCook {

	public static void main(String[] args) {
		
		// create a transaction for each diff we can find i.e. between any two code completion events
		
		// change and code context most likely have to be created in parallel while traversing the SST
		// TODO: find out how to traverse SSTs
		// TODO: find out how to create diff of SSTs
		ChangeContext chctx = new ChangeContext();
		
		chctx.addAtomicChange(
				new AtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.VariableDeclaration)
				.withLabel("VariableDeclarationStatement"));
		
		chctx.addAtomicChange(
				new AtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.MethodName)
				.withLabel("add"));

		chctx.addAtomicChange(
				new AtomicChange()
				.withOperation(Operation.ADD)
				// it is neither explained what this should be nor what its context is...
				.withNodeType(NodeType.TypeParameterName)
				.withLabel("result"));
		
		chctx.addAtomicChange(
				new AtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.VariableReference)
				.withLabel("some reference")
				);
		
		CodeContext coctx = new CodeContext();
		// idea: fill this set with all tokens we find while traversing the ISSTNodes
		coctx.addToken("token1");
		coctx.addToken("token2");
		coctx.addToken("token3");
		coctx.addToken("token4");
		
		// persist the the diff as new transaction
		Transaction ts = new Transaction()
				// file name is set according to sst
				.withFileName("some file")
				.withChangeContext(chctx)
				.withCodeContext(coctx);
		
		String json = JsonUtility.toJson(ts);
		try {
			IoUtility.writeTransactionToFile(ts.getFileName(), json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// now assume the user triggers code completion
		// we have to construct a query change context and a query code context
		// in addition to the normal context these have the following additional attributes:
		// - Map: AtomicChange -> distance
		// - Map: AtomicChange -> weightOfScope
		// - Map: AtomicChange -> weightOfDataDependency
		
		ChangeContext qchctx = new ChangeContext();
		AtomicChange atc = new AtomicChange()
				.withOperation(Operation.ADD)
				.withNodeType(NodeType.MethodName)
				.withLabel("add");
		qchctx.addAtomicChange(atc);
		
		QueryCodeContext qcocctx = new QueryCodeContext();
		qcocctx.addToken("firstToken");
		
		// get all events of a given user
		List<IIDEEvent> events = IoUtility
				.readEvent("2.zip");
		System.out.println("Total number of events: " + events.size());
		
		ArrayList<CompletionEvent> completionEvents = new ArrayList<>();
		ArrayList<String> fileNames = new ArrayList<>();
		
		for (IIDEEvent event : events) {
			if (event instanceof ICompletionEvent) {
				CompletionEvent completionEvent = (CompletionEvent) event;
				completionEvents.add(completionEvent);
				if (completionEvent.getContext().getTypeShape().getTypeHierarchy().getElement().getIdentifier().equals("MyKidds.Security.ClaimTypes, MyKidds")) {
				}
				fileNames.add(completionEvent.getContext().getTypeShape().getTypeHierarchy().getClass().toString());
			}
		}
		System.out.println("Total number of completion events: " + completionEvents.size());
		if (completionEvents.size() > 1) {
			List<CompletionEvent> ceSorted = completionEvents.stream()
					.filter(Objects::nonNull)
					.sorted(Comparator.comparing(CompletionEvent::getTriggeredAt))//Comparator.reverseOrder()
					.limit(50)
					.collect(Collectors.toList());
			
			for (CompletionEvent evt : ceSorted) {
				System.out.println("=========== SST ==========");
				System.out.println((SSTPrintingUtils.printSST(evt.getContext().getSST())));
			}
		}
	}
}
