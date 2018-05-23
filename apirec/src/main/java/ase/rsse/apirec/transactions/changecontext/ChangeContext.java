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

import java.util.ArrayList;
import java.util.HashSet;

public class ChangeContext {
	
	private HashSet<AtomicChange> atomicChanges;
	
	public ChangeContext() {
		atomicChanges = new HashSet<>();
	}

	public HashSet<AtomicChange> getAtomicChanges() {
		return atomicChanges;
	}

	public void setAtomicChanges(HashSet<AtomicChange> atomicChanges) {
		this.atomicChanges = atomicChanges;
	}
	
	public void addAtomicChange(AtomicChange atomicChange) {
		atomicChanges.add(atomicChange);
	}
	
	public void addAllAtomicChanges(ArrayList<AtomicChange> atomicChanges) {
		this.atomicChanges.addAll(atomicChanges);
	}
}
