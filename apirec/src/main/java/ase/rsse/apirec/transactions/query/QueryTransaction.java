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

	public QueryChangeContext getQueryChangeContex() {
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
