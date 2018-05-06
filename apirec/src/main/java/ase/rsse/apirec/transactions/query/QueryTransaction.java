package ase.rsse.apirec.transactions.query;

public class QueryTransaction {
	
	private String fileName;
	private QueryCodeContext queryCodeContext;
	private QueryChangeContext changeContex;
	
	public QueryTransaction() {
		queryCodeContext = new QueryCodeContext();
		changeContex = new QueryChangeContext();
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public QueryTransaction withFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	public QueryCodeContext getQueryCodeContext() {
		return queryCodeContext;
	}
	public void setQueryCodeContext(QueryCodeContext QueryCodeContext) {
		this.queryCodeContext = QueryCodeContext;
	}

	public QueryTransaction withQueryCodeContext(QueryCodeContext QueryCodeContext) {
		this.queryCodeContext = QueryCodeContext;
		return this;
	}

	public QueryChangeContext getChangeContex() {
		return changeContex;
	}

	public void setChangeContex(QueryChangeContext changeContex) {
		this.changeContex = changeContex;
	}
	
	public QueryTransaction withQueryChangeContext(QueryChangeContext changeContex) {
		this.changeContex = changeContex;
		return this;
	}

}
