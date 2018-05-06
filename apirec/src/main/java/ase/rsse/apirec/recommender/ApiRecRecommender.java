package ase.rsse.apirec.recommender;

import java.util.ArrayList;

import ase.rsse.apirec.transactions.Transaction;
import ase.rsse.apirec.transactions.query.QueryTransaction;
import cc.kave.commons.model.ssts.impl.SST;

public class ApiRecRecommender implements IApiRecRecommender {

	@Override
	public Transaction createTransaction(SST oldSST, SST newSST) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QueryTransaction createQueryTransaction(SST oldSST, SST newSST) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String predict(QueryTransaction queryTransaction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> predictKBest(QueryTransaction queryTransaction) {
		// TODO Auto-generated method stub
		return null;
	}

}
