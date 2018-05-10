package ase.rsse.apirec.transactions;

import java.util.List;

import ase.rsse.apirec.transactions.changecontext.ChangeContext;
import ase.rsse.apirec.transactions.codecontext.CodeContext;
import cc.kave.commons.model.events.completionevents.IProposal;
import cc.kave.commons.model.events.completionevents.IProposalSelection;

public class Transaction {
	
	private String fileName;
	private CodeContext codeContext;
	private ChangeContext changeContex;
	private List<IProposal> proposal;
	private List<IProposalSelection> proposalSelection;
	
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

	public List<IProposal> getProposal() {
		return proposal;
	}

	public void setProposal(List<IProposal> proposal) {
		this.proposal = proposal;
	}

	public List<IProposalSelection> getProposalSelection() {
		return proposalSelection;
	}

	public void setProposalSelection(List<IProposalSelection> proposalSelection) {
		this.proposalSelection = proposalSelection;
	}
}
