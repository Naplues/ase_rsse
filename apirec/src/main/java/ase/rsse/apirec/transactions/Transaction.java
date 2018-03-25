package ase.rsse.apirec.transactions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import cc.kave.commons.utils.io.json.JsonUtils;

public class Transaction {
	
	private String fileName;
	private ArrayList<CodeContext> codeContext;
	private ArrayList<ChangeContext> changeContex;
	
	public Transaction() {
		codeContext = new ArrayList<>();
		setChangeContex(new ArrayList<>());
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ArrayList<CodeContext> getCodeContext() {
		return codeContext;
	}
	public void setCodeContext(ArrayList<CodeContext> m_codeContext) {
		this.codeContext = m_codeContext;
	}

	public ArrayList<ChangeContext> getChangeContex() {
		return changeContex;
	}

	public void setChangeContex(ArrayList<ChangeContext> m_changeContex) {
		this.changeContex = m_changeContex;
	}
	
	public void serialize() {
		String json = JsonUtils.toJson(this, Transaction.class);
		try {
			FileUtils.writeStringToFile(new File(fileName + ".json"), json, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
