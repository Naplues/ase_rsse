package ase.rsse.apirec.transactions;

import cc.kave.commons.model.ssts.declarations.IMethodDeclaration;

public class MethodMatch {
	
	private IMethodDeclaration oldMethodDecl;
	private IMethodDeclaration newMethodDecl;
	private float similarity;
	
	public MethodMatch(IMethodDeclaration oldMethodDecl, IMethodDeclaration newMethdoDecl, float similarity) {
		this.oldMethodDecl = oldMethodDecl;
		this.newMethodDecl = newMethdoDecl;
		this.similarity = similarity;
	}

	public IMethodDeclaration getOldMethodDecl() {
		return oldMethodDecl;
	}

	public void setOldMethodDecl(IMethodDeclaration oldNode) {
		this.oldMethodDecl = oldNode;
	}

	public IMethodDeclaration getNewMethodDecl() {
		return newMethodDecl;
	}

	public void setNewMethodDecl(IMethodDeclaration newNode) {
		this.newMethodDecl = newNode;
	}

	public float getSimilarity() {
		return similarity;
	}

	public void setSimilarity(float similarity) {
		this.similarity = similarity;
	}

}
