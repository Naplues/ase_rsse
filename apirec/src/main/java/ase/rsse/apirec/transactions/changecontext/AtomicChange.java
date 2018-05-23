/*Copyright [2018] [Kürsat Aydinli & Remo Schenker]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package ase.rsse.apirec.transactions.changecontext;

import com.google.common.base.Objects;

import ase.rsse.apirec.transactions.NodeType;
import ase.rsse.apirec.transactions.Operation;

public class AtomicChange {
	private Operation operation;
	private NodeType nodeType;
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
