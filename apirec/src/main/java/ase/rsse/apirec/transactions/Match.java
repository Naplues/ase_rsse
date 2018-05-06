package ase.rsse.apirec.transactions;

import cc.kave.commons.model.ssts.visitor.ISSTNode;

public class Match {
	
	private ISSTNode oldNode;
	private ISSTNode newNode;
	private float similarity;
	
	public Match(ISSTNode oldNode, ISSTNode newNode, float similarity) {
		this.oldNode = oldNode;
		this.newNode = newNode;
		this.similarity = similarity;
	}

	public ISSTNode getOldNode() {
		return oldNode;
	}

	public void setOldNode(ISSTNode oldNode) {
		this.oldNode = oldNode;
	}

	public ISSTNode getNewNode() {
		return newNode;
	}

	public void setNewNode(ISSTNode newNode) {
		this.newNode = newNode;
	}

	public float getSimilarity() {
		return similarity;
	}

	public void setSimilarity(float similarity) {
		this.similarity = similarity;
	}

}
