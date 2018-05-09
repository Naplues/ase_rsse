package ase.rsse.apirec.transactions.codecontext;

import java.util.Collections;
import java.util.HashSet;

public class CodeContext {
	
	protected HashSet<String> tokens;

	public CodeContext() {
		tokens = new HashSet<>();
	}

	public HashSet<String> getTokens() {
		return tokens;
	}
	
	public void setTokens(HashSet<String> tokens) {
		this.tokens = tokens;
	}

	public void addToken(String token) {
		tokens.add(token);
	}
	
	public void addTokens(HashSet<String> tokens) {
		this.tokens.addAll(tokens);
	}
	
	public boolean contains(String token) {
		return tokens.contains(token);
	}
	
	public boolean containsAny(HashSet<String> tokens) {
		return !Collections.disjoint(this.tokens, tokens);
	}
}
