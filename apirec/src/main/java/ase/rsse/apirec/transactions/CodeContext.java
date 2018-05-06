package ase.rsse.apirec.transactions;

import java.util.ArrayList;
import java.util.Collections;

public class CodeContext {
	
	protected ArrayList<String> tokens;

	public CodeContext() {
		tokens = new ArrayList<>();
	}

	public ArrayList<String> getTokens() {
		return tokens;
	}
	
	public void setTokens(ArrayList<String> tokens) {
		this.tokens = tokens;
	}

	public void addToken(String token) {
		tokens.add(token);
	}
	
	public void addTokens(ArrayList<String> tokens) {
		this.tokens.addAll(tokens);
	}
	
	public boolean contains(String token) {
		return tokens.contains(token);
	}
	
	public boolean containsAny(ArrayList<String> tokens) {
		return !Collections.disjoint(this.tokens, tokens);
	}
}
