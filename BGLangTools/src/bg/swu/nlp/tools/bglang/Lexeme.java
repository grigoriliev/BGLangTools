/*
 *   BGLangTools - language tools for processing Bulgarian text
 *
 *   Copyright (C) 2014 Grigor Iliev <grigor.iliev@swu.bg>
 *
 *   This file is part of BGLangTools.
 *
 *   BGLangTools is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License version 3
 *   as published by the Free Software Foundation.
 *
 *   BGLangTools is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with BGLangTools; if not, write to the Free Software
 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 *   MA  02111-1307  USA
 */

package bg.swu.nlp.tools.bglang;

/**
 *
 */
public class Lexeme {
	public final WordEntry lemma;
	public final WordEntry[] forms;
	
	Lexeme(WordEntry lemma, WordEntry[] forms) {
		this.lemma = lemma;
		this.forms = forms;
	}
	
	/**
	 * Checks whether the specified lexeme contains the same set of words
	 * with the same grammatical properties
	 * @param l
	 * @return 
	 */
	public boolean sameAs(Lexeme l) {
		if(!lemma.sameAs(l.lemma)) return false;
		
		if(forms.length != l.forms.length) return false;
		
		// note that we expect that there are no duplicate word forms in both lexemes
		for(int i = 0; i < forms.length; i++) {
			boolean found = false;
			
			for(int j = 0; j < l.forms.length; j++) {
				if(forms[i].sameAs(l.forms[j])) {
					found = true;
					break;
				}
			}
			
			if(!found) return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		String nl = System.getProperty("line.separator");
		
		sb.append(lemma.toString()).append(nl);
		
		for(WordEntry we : forms) {
			sb.append(we.toString()).append(nl);
		}
		
		return sb.toString();
	}
}
