package ase.rsse.apirec.transactions;

import cc.kave.commons.model.ssts.visitor.ISSTNode;

public class CodeContext {
	
	private int index;
	private Operation operation;
	private ISSTNode nodeType;
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

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public ISSTNode getNodeType() {
		return nodeType;
	}

	public void setNodeType(ISSTNode nodeType) {
		this.nodeType = nodeType;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public float getWeightOfDependency() {
		return weightOfDependency;
	}

	public void setWeightOfDependency(float weightOfDependency) {
		this.weightOfDependency = weightOfDependency;
	}

	public float getWeightOfScope() {
		return weightOfScope;
	}

	public void setWeightOfScope(float weightOfScope) {
		this.weightOfScope = weightOfScope;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}
}
