package ase.rsse.apirec.transactions;

import java.util.ArrayList;

public class TransactionContainer {
	
	private ArrayList<Transaction> transactions;
	
	public TransactionContainer() {
		transactions = new ArrayList<>();
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}
	
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}
}
