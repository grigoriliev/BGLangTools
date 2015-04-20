/*
 *   BGLemmatizer - Cross-Platform Lemmatizer for Bulgarian
 *
 *   Copyright (C) 2014-2015 Grigor Iliev <grigor.iliev@swu.bg>
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

package bg.swu.nlp.tools.bglang.lem; 
 
import bg.swu.nlp.tools.bglang.BgDictionary;
import bg.swu.nlp.tools.bglang.WordEntry;
import bg.swu.nlp.tools.bglang.BTBUtils;

import java.util.ArrayList;
 
public class BGLemmatizer {
	public final static String version = "0.2";
	
	private int count = 0, missingCount = 0, ambiguityCount = 0;
	
	public BGLemmatizer() {
		
	}
	
	public void resetStat() {
		count = ambiguityCount = missingCount = 0;
	}
	
	public void printStat(String prefix) {
		System.out.println(prefix + "Number of tokens: " + count);
		System.out.println(prefix + "Not found: " + missingCount);
		System.out.println(prefix + "Ambiguity count: " + ambiguityCount);
	}
	
	public String getLemma(BgDictionary dict, String word, String tag) {
		return getLemma(dict, word, tag, false);
	}
	
	public String getLemma(BgDictionary dict, String word, String tag, boolean log) {
		count++;
		
		if(BgDictionary.isNumeric(word)) return word;
		
		if(BgDictionary.isShortForm(word)) {
			return word;
		}
		
		String prefix = null;
		if(word.startsWith("по-") || word.startsWith("По-")) {
			prefix = "по-";
			word = word.substring(3);
		} else if(word.startsWith("най-") || word.startsWith("Най-")) {
			prefix = "най-";
			word = word.substring(4);
		}
		
		String lemma = getLemma0(dict, word, tag, false, false);
		
		if(lemma == null) {
			lemma = getLemma0(dict, word.toLowerCase(), tag, log, true);
		}
		
		if(prefix != null && lemma != null) {
			return prefix + lemma;
		}
		
		return lemma;
	}
	
	private String getLemma0 (
		BgDictionary dict, String word, String tag, boolean log, boolean countIfMissing) {
		
		
		WordEntry[] ls = dict.findLemmas(word); // TODO: remove it
		ArrayList<WordEntry> entries = dict.findExactMatches(word);
		
		//if(log) System.out.println("word: " + word);
		
		if(ls.length == 0) {
			if(countIfMissing) missingCount++;
			if(log) {
				System.err.println("Missing word: " + word);
				System.err.println("gate tag: " + tag);
			}
			return null;
		}
		
		tag = removeNonessentials(tag);
		
		WordEntry[] lemmas = dict.findLemmas(word, tag);
		
		if(lemmas.length == 0) {
			lemmas = dict.findLemmas(word, stripTagStage1(tag));
			
			if(lemmas.length == 0) {
				lemmas = dict.findLemmas(word, stripTagStage2(tag));
			}
			
			if(lemmas.length == 1) {
				return lemmas[0].word;
			}
			
			if(lemmas.length > 1) {
				String s = getWordIfEquals(lemmas);
			
				if(s != null) {
					return s;
				}
				
				ambiguityCount++;
				if(log) printAmbiguity(dict, word, tag, entries, "2");
				return null;
			}
			
			lemmas = dict.findLemmas(word);
			
			if( lemmas.length == 1) {
				// TODO: strict lookup
				/*if(BgGrammarType.getCodeById(lemmas[0].grammLabelUid) > 187) {
					ann.getFeatures().put(outputFeatureName, lemmas[0].word);
					return;
				}*/
				
				return lemmas[0].word;
			}
			
			if( lemmas.length > 1) {
				String s = getWordIfEquals(lemmas);
			
				if(s != null) {
					return s;
				}
				
				ambiguityCount++;
				if(log) printAmbiguity(dict, word, tag, entries, "3");
				return null;
			}
			
			if(countIfMissing) missingCount++;
			
			if(log) {
				System.err.println("missing word: " + word);
				System.err.println("Wrong tags?:");
				
				for(WordEntry we : entries) {
					System.err.println(we.word + " " + BTBUtils.getTag(we.grammLabelUid));
					System.err.println("gate tag: " + tag);
				
				}
			
				System.err.println();
			}
			
			return null;
		}
		
		if(lemmas.length == 1) {
			return lemmas[0].word;
		}
		
		String s = getWordIfEquals(lemmas);
			
		if(s != null) {
			return s;
		} else {
			ambiguityCount++;
			if(log) printAmbiguity(dict, word, tag, entries, "");
		}
		
		return null;
	}
	
	private void printAmbiguity(BgDictionary dict, String word, String tag, ArrayList<WordEntry> words, String sufix) {
		System.err.println("Ambiguity" + sufix + ": ");
		System.err.println("gate tag: " + tag);
		for(WordEntry we : words) {
			//System.err.println(we.toString());
			System.err.print(we.word + " " + BTBUtils.getTag(we.grammLabelUid));
			WordEntry l =  we.lemmaId == -1 ? we : dict.getWordEntryById(we.lemmaId);
			System.err.println(" lemma: " + l.word + " " + BTBUtils.getTag(l.grammLabelUid));
		}
			
		System.err.println();
	}
	
	private static String getWordIfEquals(WordEntry[] words) {
		if(words == null || words.length == 0) return null;
		
		boolean eq = true;
			
		for(int i = 1; i < words.length; i++) {
			if(!words[i].word.equals(words[0].word)) {
				eq = false; break;
			}
		}
		
		if(eq) return words[0].word;
		
		return null;
	}
	
	private static String removeNonessentials(String tag) {
		if(tag == null) return null;
		
		if(tag.length() > 3 && tag.charAt(0) == 'V') {
			return tag.substring(0, 3) + "-" + tag.substring(4);
		}
		
		return tag;
	}
	
	private static String stripTagStage1(String tag) {
		if(tag == null) return null;
		
		if(tag.length() > 3 && tag.charAt(0) == 'V') {
			return "V---" + tag.substring(4);
		}
		
		return tag;
	}
	
	private static String stripTagStage2(String tag) {
		if(tag == null) return null;
		
		if(tag.length() > 0) {
			return tag.substring(0, 1);
		}
		
		return tag;
	}
}
