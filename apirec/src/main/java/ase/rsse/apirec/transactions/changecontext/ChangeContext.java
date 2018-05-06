package ase.rsse.apirec.transactions.changecontext;

import java.util.ArrayList;

public class ChangeContext {
	
	private ArrayList<AtomicChange> atomicChanges;
	
	public ChangeContext() {
		atomicChanges = new ArrayList<>();
	}

	public ArrayList<AtomicChange> getAtomicChanges() {
		return atomicChanges;
	}

	public void setAtomicChanges(ArrayList<AtomicChange> atomicChanges) {
		this.atomicChanges = atomicChanges;
	}
	
	public void addAtomicChange(AtomicChange atomicChange) {
		atomicChanges.add(atomicChange);
	}
	
	public void addAllAtomicChanges(ArrayList<AtomicChange> atomicChanges) {
		this.atomicChanges.addAll(atomicChanges);
	}
}
