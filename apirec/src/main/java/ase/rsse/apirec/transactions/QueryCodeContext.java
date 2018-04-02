package ase.rsse.apirec.transactions;

import java.util.HashMap;

public class QueryCodeContext extends CodeContext {
	
	private HashMap<Integer, Float> weightOfScope;
	private HashMap<Integer, Float> weightOfDataDependency;
	
	public QueryCodeContext() {
		super();
		weightOfScope = new HashMap<>();
		weightOfDataDependency = new HashMap<>();
	}

	public int getDistance(String token) {
		return tokens.indexOf(token);
	}

	public HashMap<Integer, Float> getWeightOfScope() {
		return weightOfScope;
	}

	public void setWeightOfScope(HashMap<Integer, Float> weightOfScope) {
		this.weightOfScope = weightOfScope;
	}
	
	public void addWeightOfScope(Integer token, float weightOfScope) {
		this.weightOfScope.put(token, weightOfScope);
	}

	public HashMap<Integer, Float> getWeightOfDataDependency() {
		return weightOfDataDependency;
	}

	public void setWeightOfDataDependency(HashMap<Integer, Float> weightOfDataDependency) {
		this.weightOfDataDependency = weightOfDataDependency;
	}
	
	public void addWeightOfDataDependency(Integer token, float weightOfDataDependency) {
		this.weightOfDataDependency.put(token, weightOfDataDependency);
	}
}
