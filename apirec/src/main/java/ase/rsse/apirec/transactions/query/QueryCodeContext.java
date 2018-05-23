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

import java.util.HashMap;

import ase.rsse.apirec.transactions.codecontext.CodeContext;

public class QueryCodeContext extends CodeContext {
	
	private HashMap<Integer, Float> weightOfScope;
	private HashMap<Integer, Float> weightOfDataDependency;
	
	public QueryCodeContext() {
		super();
		weightOfScope = new HashMap<>();
		weightOfDataDependency = new HashMap<>();
	}

	public int getDistance(String token) {
		return 1;
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
