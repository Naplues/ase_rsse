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

	public Float getWeightOfScope(int distance) {
		return weightOfScope.get(distance);
	}

	public void setWeightOfScope(HashMap<Integer, Float> weightOfScope) {
		this.weightOfScope = weightOfScope;
	}
	
	public void addWeightOfScope(Integer token, float weightOfScope) {
		this.weightOfScope.put(token, weightOfScope);
	}

	public Float getWeightOfDataDependency(int distance) {
		return weightOfDataDependency.get(distance);
	}

	public void setWeightOfDataDependency(HashMap<Integer, Float> weightOfDataDependency) {
		this.weightOfDataDependency = weightOfDataDependency;
	}
	
	public void addWeightOfDataDependency(Integer distanceOfToken, float weightOfDataDependency) {
		this.weightOfDataDependency.put(distanceOfToken, weightOfDataDependency);
	}
}
