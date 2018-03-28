package ase.rsse.apirec.transactions;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
	
	private String fileName;
	private List<CodeContext> codeContext;
	private List<ChangeContext> changeContex;
	
	public Transaction() {
		codeContext = new ArrayList<>();
		changeContex = new ArrayList<>();
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

	public List<CodeContext> getCodeContext() {
		return codeContext;
	}
	public void setCodeContext(ArrayList<CodeContext> codeContext) {
		this.codeContext = codeContext;
	}

	public Transaction withCodeContext(List<CodeContext> codeContext) {
		this.codeContext = codeContext;
		return this;
	}
	
	public Transaction addCodeContext(CodeContext codeContext) {
		this.codeContext.add(codeContext);
		return this;
	}

	public List<ChangeContext> getChangeContex() {
		return changeContex;
	}

	public void setChangeContex(ArrayList<ChangeContext> changeContex) {
		this.changeContex = changeContex;
	}
	
	public Transaction withChangeContext(List<ChangeContext> changeContex) {
		this.changeContex = changeContex;
		return this;
	}
	
	public void addChangeContext(ChangeContext changeContext) {
		this.changeContex.add(changeContext);
	}
}
