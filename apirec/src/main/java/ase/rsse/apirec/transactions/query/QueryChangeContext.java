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

import java.util.ArrayList;

public class QueryChangeContext {

	private ArrayList<QueryAtomicChange> QueryAtomicChanges;
	
	public QueryChangeContext() {
		QueryAtomicChanges = new ArrayList<>();
	}

	public ArrayList<QueryAtomicChange> getQueryAtomicChanges() {
		return QueryAtomicChanges;
	}

	public void setQueryAtomicChanges(ArrayList<QueryAtomicChange> QueryAtomicChanges) {
		this.QueryAtomicChanges = QueryAtomicChanges;
	}
	
	public void addQueryAtomicChange(QueryAtomicChange QueryAtomicChange) {
		QueryAtomicChanges.add(QueryAtomicChange);
	}
	
	public void addAllQueryAtomicChanges(ArrayList<QueryAtomicChange> QueryAtomicChanges) {
		this.QueryAtomicChanges.addAll(QueryAtomicChanges);
	}
	
}
