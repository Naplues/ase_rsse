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

package ase.rsse.apirec.recommender;

import java.util.ArrayList;

import ase.rsse.apirec.transactions.query.QueryTransaction;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;

public interface IApiRecRecommender {
	
	public QueryTransaction createQueryTransaction(ICompletionEvent  oldCompletinEvent, ICompletionEvent newCompletionEvent);

	public ArrayList<String> predictKBest(ICompletionEvent  oldCompletinEvent, ICompletionEvent newCompletionEvent, int k);

}
