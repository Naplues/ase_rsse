package ase.rsse.apirec.transactions;

import com.google.common.base.Objects;

public class AtomicChange {
	private Operation operation;
	private NodeType nodeType;
	// only used for the following AST types: method invocation, simple type, simple name, boolean constants, null value
	private String label;
	
	public AtomicChange() {
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
	public AtomicChange withOperation(Operation operation) {
		this.operation = operation;
		return this;
	}

	public NodeType getNodeType() {
		return nodeType;
	}

	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}
	
	public AtomicChange withNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
		return this;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public AtomicChange withLabel(String label) {
		this.label = label;
		return this;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(operation, nodeType, label);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AtomicChange)) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		AtomicChange other = (AtomicChange) obj;
		return operation == other.operation && nodeType == other.nodeType && Objects.equal(label, other.label);
	}
}
