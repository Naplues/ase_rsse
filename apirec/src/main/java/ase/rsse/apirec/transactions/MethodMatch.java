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
