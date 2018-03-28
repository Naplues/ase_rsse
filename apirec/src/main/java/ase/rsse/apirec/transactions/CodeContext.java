package ase.rsse.apirec.transactions;

public class CodeContext {
	
	private int index;
	private Operation operation;
	private String label;
	private float weightOfDependency;
	private float weightOfScope;
	private float distance;
	
	public CodeContext() {
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public CodeContext withIndex(int index) {
		this.index = index;
		return this;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
	public CodeContext withOperation(Operation operation) {
		this.operation = operation;
		return this;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public CodeContext withLabel(String label) {
		this.label = label;
		return this;
	}

	public float getWeightOfDependency() {
		return weightOfDependency;
	}

	public void setWeightOfDependency(float weightOfDependency) {
		this.weightOfDependency = weightOfDependency;
	}
	
	public CodeContext withWeightOfDependency(float weightOfDependency) {
		this.weightOfDependency = weightOfDependency;
		return this;
	}

	public float getWeightOfScope() {
		return weightOfScope;
	}

	public void setWeightOfScope(float weightOfScope) {
		this.weightOfScope = weightOfScope;
	}
	
	public CodeContext withWeightOfScope(float weightOfScope) {
		this.weightOfScope = weightOfScope;
		return this;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}
	
	public CodeContext withDistance(float distance) {
		this.distance = distance;
		return this;
	}
}
