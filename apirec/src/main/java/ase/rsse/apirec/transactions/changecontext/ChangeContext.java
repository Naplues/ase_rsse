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
