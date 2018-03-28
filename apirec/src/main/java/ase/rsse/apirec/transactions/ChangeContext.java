package ase.rsse.apirec.transactions;

public class ChangeContext {
	
	private int index;
	private Operation operation;
	private String label;
	private float weightOfDependency;
	private float weightOfScope;
	private float distance;
	
	public ChangeContext() {
		
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public ChangeContext withIndex(int index) {
		this.index = index;
		return this;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
	public ChangeContext withOperation(Operation operation) {
		this.operation = operation;
		return this;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public ChangeContext withLabel(String label) {
		this.label = label;
		return this;
	}

	public float getWeightOfDependency() {
		return weightOfDependency;
	}

	public void setWeightOfDependency(float weightOfDependency) {
		this.weightOfDependency = weightOfDependency;
	}
	
	public ChangeContext withWeightOfDependency(float weightOfDependency) {
		this.weightOfDependency = weightOfDependency;
		return this;
	}

	public float getWeightOfScope() {
		return weightOfScope;
	}

	public void setWeightOfScope(float weightOfScope) {
		this.weightOfScope = weightOfScope;
	}
	
	public ChangeContext withWeightOfScope(float weightOfScope) {
		this.weightOfScope = weightOfScope;
		return this;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}
	
	public ChangeContext withDistance(float distance) {
		this.distance = distance;
		return this;
	}
}
