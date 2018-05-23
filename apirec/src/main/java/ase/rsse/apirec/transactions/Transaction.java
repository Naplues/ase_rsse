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

import ase.rsse.apirec.transactions.changecontext.ChangeContext;
import ase.rsse.apirec.transactions.codecontext.CodeContext;

public class Transaction {
	
	private String fileName;
	private CodeContext codeContext;
	private ChangeContext changeContex;
	
	public Transaction() {
		codeContext = new CodeContext();
		changeContex = new ChangeContext();
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public Transaction withFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	public CodeContext getCodeContext() {
		return codeContext;
	}
	public void setCodeContext(CodeContext codeContext) {
		this.codeContext = codeContext;
	}

	public Transaction withCodeContext(CodeContext codeContext) {
		this.codeContext = codeContext;
		return this;
	}

	public ChangeContext getChangeContex() {
		return changeContex;
	}

	public void setChangeContex(ChangeContext changeContex) {
		this.changeContex = changeContex;
	}
	
	public Transaction withChangeContext(ChangeContext changeContex) {
		this.changeContex = changeContex;
		return this;
	}
}
