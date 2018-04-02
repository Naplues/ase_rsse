package ase.rsse.apirec.transactions;

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
