package demo;

import java.util.Set;

import ase.rsse.apirec.transactions.ITransactionConstants;
import ase.rsse.utilities.IoUtility;

public class RecommenderDemo {
	
	// as an input we need two completion events of an user for the same class
	Set<String> DEMO_ZIP = IoUtility.findAllFiles(ITransactionConstants.EVENTS_DIRECTORY, s -> s.endsWith("1179.zip"));
	
	

}
