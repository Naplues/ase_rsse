package ase.rsse.cookbook;

import com.google.common.collect.Lists;

import ase.rsse.apirec.transactions.ChangeContext;
import ase.rsse.apirec.transactions.Operation;
import ase.rsse.apirec.transactions.Transaction;
import cc.kave.commons.model.ssts.impl.expressions.assignable.BinaryExpression;

public class TransactionCook {

	public static void main(String[] args) {
		ChangeContext cctx = new ChangeContext()
				.withIndex(0)
				.withLabel("label")
				.withNodeType(new BinaryExpression())
				.withOperation(Operation.ADD)
				.withDistance(2.5f)
				.withWeightOfScope(0.5f)
				.withWeightOfDependency(1f);
		
		// create and serialize empty transaction
		Transaction transaction = new Transaction();
		transaction.setFileName("test_empty");
		transaction.serialize();
		transaction.setFileName("test_change_context");
		transaction.setChangeContex(Lists.newArrayList(cctx));
		transaction.serialize();
	}
}
