package ase.rsse.apirec.recommender;

import java.util.ArrayList;

import ase.rsse.apirec.transactions.Transaction;
import ase.rsse.apirec.transactions.query.QueryTransaction;
import cc.kave.commons.model.ssts.impl.SST;

public interface IApiRecRecommender {
	
	public Transaction createTransaction(SST oldSST, SST newSST);
	
	public QueryTransaction createQueryTransaction(SST oldSST, SST newSST);
	
	public String predict(QueryTransaction queryTransaction);
	
	public ArrayList<String> predictKBest(QueryTransaction queryTransaction);

}
