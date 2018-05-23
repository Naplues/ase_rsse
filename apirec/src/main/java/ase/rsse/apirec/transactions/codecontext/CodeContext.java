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
