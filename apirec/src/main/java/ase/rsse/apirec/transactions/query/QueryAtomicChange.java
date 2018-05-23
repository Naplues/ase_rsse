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

package ase.rsse.apirec.transactions.query;

import ase.rsse.apirec.transactions.NodeType;
import ase.rsse.apirec.transactions.Operation;
import ase.rsse.apirec.transactions.changecontext.AtomicChange;

public class QueryAtomicChange extends AtomicChange {
	
	private int distance;
	private float weightOfDataDependency;
	private float weihgtOfScope;
	
	@Override
	public QueryAtomicChange withLabel(String label) {
		return (QueryAtomicChange) super.withLabel(label);
	}
	
	@Override
	public QueryAtomicChange withNodeType(NodeType nodeType) {
		return (QueryAtomicChange) super.withNodeType(nodeType);
	}
	
	@Override
	public QueryAtomicChange withOperation(Operation operation) {
		return (QueryAtomicChange) super.withOperation(operation);
	}
	
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public QueryAtomicChange withDistance(int distance) {
		this.distance = distance;
		return this;
	}
	
	public float getWeightOfDataDependency() {
		return weightOfDataDependency;
	}
	
	public void setWeightOfDataDependency(float weightOfDataDependency) {
		this.weightOfDataDependency = weightOfDataDependency;
	}
	
	public QueryAtomicChange withWeightOfDataDependency(float weightOfDataDependency) {
		this.weightOfDataDependency = weightOfDataDependency;
		return this;
	}
	
	public float getWeihgtOfScope() {
		return weihgtOfScope;
	}
	
	public void setWeihgtOfScope(float weihgtOfScope) {
		this.weihgtOfScope = weihgtOfScope;
	}
	
	public QueryAtomicChange withWeightOfScope(float weightOfScope) {
		this.weihgtOfScope = weightOfScope;
		return this;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
