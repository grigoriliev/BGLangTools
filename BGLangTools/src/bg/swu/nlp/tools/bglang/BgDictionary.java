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

import static bg.swu.nlp.tools.bglang.I18n.i18n;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 */
public class BgDictionary {
	
	
	public static class SearchQuery {
		public int glId = 0;   // grammatical label ID
		public int glMask = 0; // grammatical label mask
		
		public GrammaticalLabel.Gender gender = null;
		public GrammaticalLabel.Person person = null;
		public GrammaticalLabel.Article article = null;
		public GrammaticalLabel.Number number = null;
		public GrammaticalLabel.NounCase nounCase = null;
		public GrammaticalLabel.AdjectiveCase adjectiveCase = null;
		public GrammaticalLabel.PronounType pronounType = null;
		public GrammaticalLabel.PronounCase pronounCase = null;
		public GrammaticalLabel.PronounForm pronounForm = null;
		public GrammaticalLabel.VerbType verbType = null;
		public GrammaticalLabel.Aspect aspect = null;
		public GrammaticalLabel.Transitivity transitivity = null;
		public GrammaticalLabel.VerbForm verbForm = null;
		public GrammaticalLabel.Voice voice = null;
		public GrammaticalLabel.Tense tense = null;
		
		
		public void compile() {
			if(gender != null) {
				GrammaticalLabel.encodeGender(glId, gender);
				glMask |= GrammaticalLabel.genderMask;
			}
			
			if(person != null) {
				GrammaticalLabel.encodePerson(glId, person);
				glMask |= GrammaticalLabel.personMask;
			}
			
			if(article != null) {
				GrammaticalLabel.encodeArticle(glId, article);
				glMask |= GrammaticalLabel.articleMask;
			}
			
			if(number != null) {
				GrammaticalLabel.encodeNumber(glId, number);
				glMask |= GrammaticalLabel.numberMask;
			}
			
			if(nounCase != null) {
				GrammaticalLabel.encodeNounCase(glId, nounCase);
				glMask |= GrammaticalLabel.caseMask;
			}
			
			if(adjectiveCase != null) {
				GrammaticalLabel.encodeAdjectiveCase(glId, adjectiveCase);
				glMask |= GrammaticalLabel.caseMask;
			}
			
			if(pronounType != null) {
				GrammaticalLabel.encodePronounType(glId, pronounType);
				glMask |= GrammaticalLabel.pronounTypeMask;
			}
			
			if(pronounCase != null) {
				GrammaticalLabel.encodePronounCase(glId, pronounCase);
				glMask |= GrammaticalLabel.caseMask;
			}
			
			if(pronounForm != null) {
				GrammaticalLabel.encodePronounForm(glId, pronounForm);
				glMask |= GrammaticalLabel.pronounFormMask;
			}
			
			if(verbType != null) {
				GrammaticalLabel.encodeVerbType(glId, verbType);
				glMask |= GrammaticalLabel.verbTypeMask;
			}
			
			if(aspect != null) {
				GrammaticalLabel.encodeAspect(glId, aspect);
				glMask |= GrammaticalLabel.aspectMask;
			}
			
			if(transitivity != null) {
				GrammaticalLabel.encodeTransitivity(glId, transitivity);
				glMask |= GrammaticalLabel.transitivityMask;
			}
			
			if(verbForm != null) {
				GrammaticalLabel.encodeVerbForm(glId, verbForm);
				glMask |= GrammaticalLabel.verbFormMask;
			}
			
			if(voice != null) {
				GrammaticalLabel.encodeVoice(glId, voice);
				glMask |= GrammaticalLabel.voiceMask;
			}
			
			if(tense != null) {
				GrammaticalLabel.encodeTense(glId, tense);
				glMask |= GrammaticalLabel.tenseMask;
			}
		}
		
	}
	
	private NavigableMap<Integer, TreeSet<WordEntry>>
	getMapByLexicalClass(GrammaticalLabel.LexicalClass lexicalClass) {
		if(lexicalClass == null) return gluidMap;
		
		switch(lexicalClass) {
		case NOUN:
			/*return gluidMap.subMap (
				BgGrammarType.getFirstNounTypeId(), true,
				BgGrammarType.getLastNounTypeId(), true
			);*/
			
			return null; // TODO: handle common and proper nouns
		case ADJECTIVE:
			return gluidMap.subMap (
				BgGrammarType.getFirstAdjectiveTypeId(), true,
				BgGrammarType.getLastAdjectiveTypeId(), true
			);
		case PRONOUN:
			return gluidMap.subMap (
				BgGrammarType.getFirstPronounTypeId(), true,
				BgGrammarType.getLastPronounTypeId(), true
			);
		case NUMERAL:
			return gluidMap.subMap (
				BgGrammarType.getFirstNumeralTypeId(), true,
				BgGrammarType.getLastNumeralTypeId(), true
			);
		case VERB:
			return gluidMap.subMap (
				BgGrammarType.getFirstVerbTypeId(), true,
				BgGrammarType.getLastVerbTypeId(), true
			);
		case ADVERB:
			return gluidMap.subMap (
				BgGrammarType.getFirstAdverbTypeId(), true,
				BgGrammarType.getLastAdverbTypeId(), true
			);
		case CONJUNCTION:
			return gluidMap.subMap (
				BgGrammarType.getFirstConjunctionTypeId(), true,
				BgGrammarType.getLastConjunctionTypeId(), true
			);
		}
		
		return null;
	}
	
	private HashMap<String, TreeSet<WordEntry>> map = new HashMap();
	
	/** Grammatical label UID as a key */
	private TreeMap<Integer, TreeSet<WordEntry>> gluidMap = new TreeMap();
	
	/**
	 * Word ID as a key.
	 * It is expected that by design the words are added to this collection
	 * in specific order - the lemma first, followed by its inflected forms.
	 */
	private TreeMap<Integer, WordEntry> widMap = new TreeMap();
	
	/**
	 * Note that for the dictionary to be consistent and to work properly,
	 * the words need to be added in specific order - first the lemma,
	 * followed by all word forms.
	 * @param word
	 * @param grammLabelUid
	 * @param lemmaId The ID of the lemma, which represents this
	 * word form. Set to <code>-1</code> if the current word is a lemma.
	 * @return Returns the newly created word entry, or
	 * <code>null</code> if word duplication is detected.
	 */
	public WordEntry addWord(final String word, int grammLabelUid, int lemmaId) throws Exception {
		return addWord(new WordEntry(word, lemmaId, grammLabelUid));
	}
	
	/**
	 * Note that for the dictionary to be consistent and to work properly,
	 * the words need to be added in specific order - first the lemma,
	 * followed by all word forms.
	 * @param entry The word entry to add.
	 * @return Returns the same word entry if added, or
	 * <code>null</code> if word duplication is detected.
	 */
	public WordEntry addWord(WordEntry entry) throws Exception {
		return addWord(entry, true);
	}
	
	private boolean addWordToMap(WordEntry entry, boolean duplicateCheck) {
		TreeSet<WordEntry> entryList = map.get(entry.word);
		boolean newWord = true;
		 
		if(entryList == null) { // if no such word in the map
			entryList = new TreeSet<WordEntry>();
			
			entryList.add(entry);
			map.put(entry.word, entryList);
		} else {
			if(duplicateCheck) {
				for(WordEntry e : entryList) {
					if( e.lemmaId       == entry.lemmaId &&
					    e.grammLabelUid == entry.grammLabelUid ) {
						// TODO: implement notification system
						System.err.println("word already added: " + entry.toString());
						System.err.println("duplicate of word: " + e.toString());
						System.err.println();
						newWord = false;
						break;
					}
				}
			}
			
			if(newWord) entryList.add(entry);
		}
		
		return newWord;
	}
	
	private void addWordToGluidMap(WordEntry entry, boolean newWord, boolean duplicateCheck) throws Exception {
		boolean b = true;
		
		TreeSet<WordEntry> entryList = gluidMap.get(entry.grammLabelUid);
		
		if(entryList == null) { // if no such grammatical label in the map
			if(!newWord) throw new Exception("Internal structure error. This is a BUG!!!");
			entryList = new TreeSet<WordEntry>();
			entryList.add(entry);
			gluidMap.put(entry.grammLabelUid, entryList);
		} else {
			if(duplicateCheck) {
				for(WordEntry e : entryList) {
					if(e.lemmaId == entry.lemmaId && e.word.equals(entry.word)) {
						if(!newWord) System.out.println("existing word!");
						// TODO: implement notification system
						b = false;
						break;
					}
				}
			}
			
			if(newWord != b) {
				throw new Exception("Internal structure error. This is a BUG!!!");
			}
			
			if(b) entryList.add(entry);
		 }
	}
	
	/**
	 * Note that for the dictionary to be consistent and to work properly,
	 * the words need to be added in specific order - first the lemma,
	 * followed by all word forms.
	 * @param entry The word entry to add.
	 * @param duplicateCheck If <code>true</code> checks whether the
	 * word is already added. If word duplication is detected, the word is not
	 * added to the dictionary. Note that the duplicate check can be a 
	 * costly operation.
	 * @return Returns the same word entry if added, or
	 * <code>null</code> if word duplication is detected.
	 */
	public WordEntry addWord(WordEntry entry, boolean duplicateCheck) throws Exception {
		boolean newWord = addWordToMap(entry, duplicateCheck);
		
		addWordToGluidMap(entry, newWord, duplicateCheck);
		
		if(newWord) widMap.put(entry.id, entry);
		
		return newWord ? entry : null;
	}
	
	public int size() { return map.size(); }
	
	public int getTokenCount() { return widMap.size(); }
	
	/**
	 * Returns all words equals to the specified string.
	 */
	public WordEntry[] getWords(String s) {
		TreeSet<WordEntry> words = map.get(s);
		
		return words.toArray(new WordEntry[0]);
	}
	
	/**
	 * Returns the word entry representing the specified lemma
	 * (with the specified grammatical properties) in this dictionary, or
	 * <code>null</code> if the lemma is not found.
	 * @param gluid grammatical label ID
	 */
	public WordEntry findLemma(String lemma, int gluid) {
		TreeSet<WordEntry> words = map.get(lemma);
		
		for(WordEntry w : words) {
			if(w.isLemma() && w.grammLabelUid == gluid) return w;
		}
		
		return null;
	}
	
	/**
	 * Returns all lemmas which have word form <code>wordForm</code>.
	 * If the specified word is a lemma, it is also included.
	 * An empty array is returned if no lemmas are found.
	 */
	public WordEntry[] findLemmas(String wordForm) {
		return findLemmas(wordForm, null);
	}
	
	/**
	 * Returns all lemmas which have word form <code>wordForm</code>.
	 * If the specified word is a lemma, it is also included.
	 * An empty array is returned if no lemmas are found.
	 * @param tag If not <code>null</code>, words with different tags
	 * (in terms of {@link BTBUtils#differentTags}) are ignored.
	 */
	public WordEntry[] findLemmas(String wordForm, String tag) {
		//if(wordForm.startsWith("най-"))
		
		TreeSet<WordEntry> words = map.get(wordForm);
		if(words == null) return new WordEntry[0];
		ArrayList<WordEntry> lemmas = new ArrayList<WordEntry>();
		
		for(WordEntry w : words) {
			if(tag != null) {
				String tag2 = BTBUtils.getTag(w.grammLabelUid);
				if(BTBUtils.differentTags(tag, tag2)) continue;
			}
			
			int wid = w.isLemma() ? w.id : w.lemmaId;
			
			// two different word forms (from same lexeme) can be
			// represented by same string, so we need to check
			for(WordEntry l : lemmas) {
				if(l.id == wid) {
					wid = -2;
					break;
				}
			}
			
			if(wid == -2) continue; // already included
			
			if(w.isLemma()) lemmas.add(w);
			else lemmas.add(widMap.get(w.lemmaId));
		}
		
		return  lemmas.toArray(new WordEntry[0]);
	}
	
	public WordEntry[] getLemmas(String lemma) {
		TreeSet<WordEntry> words = map.get(lemma);
		if(words == null) return new WordEntry[0];
		
		ArrayList<WordEntry> lemmas = new ArrayList<WordEntry>();
		
		for(WordEntry w : words) {
			if(w.isLemma()) lemmas.add(w);
		}
		
		return lemmas.toArray(new WordEntry[0]);
	}
	
	public Lexeme[] getLexemes(String lemma) {
		WordEntry[] lemmas = getLemmas(lemma);
		Lexeme[] lexemes = new Lexeme[lemmas.length];
		
		for(int i = 0; i < lemmas.length; i++) {
			lexemes[i] = getLexeme(lemmas[i]);
		}
		
		return lexemes;
	}
	
	/**
	 * Finds all lexemes that are present in the specified dictionary
	 * <code>dict</code> and are missing in this dictionary.
	 */
	public ArrayList<Lexeme> findMissingLexemes(BgDictionary dict) {
		ArrayList<Lexeme> missing = new ArrayList<Lexeme>();
		
		for(Lexeme l : dict.lexemes()) {
			WordEntry lemma = findLemma(l.lemma.word, l.lemma.grammLabelUid);
			if(lemma == null) missing.add(l);
		}
		
		return missing;
	}
	
	public static class LexemePair {
		public final Lexeme lexeme1, lexeme2;
		
		LexemePair(Lexeme l1, Lexeme l2) {
			lexeme1 = l1;
			lexeme2 = l2;
		}
	}
	
	/**
	 * Compares all lexemes from this dictionary with the lexemes in
	 * the specified dictionary <code>dict</code> and returns
	 * a list with lexeme pairs which are with equal lemmas in both
	 * dictionaries but with unmatching sets of word forms.
	 * (<code>lexeme1</code> is the lexeme from
	 * this dictionary, <code>lexeme2</code> <code>dict</code> dictionary)
	 * (<code>lexeme1</code> is the lexeme from
	 * this dictionary, <code>lexeme2</code> <code>dict</code> dictionary)
	 */
	public ArrayList<LexemePair> findUnmatchingLexemes(BgDictionary dict) {
		ArrayList<LexemePair> unmatched = new ArrayList<LexemePair>();
		
		for(Lexeme l2 : dict.lexemes()) {
			WordEntry lemma = findLemma(l2.lemma.word, l2.lemma.grammLabelUid);
			if(lemma == null) continue;
			
			Lexeme l1 = getLexeme(lemma);
			if(!compareLexemes(l1, l2)) {
				unmatched.add(new LexemePair(l1, l2));
			}
		}
		
		return unmatched;
	}
	
	/**
	 * Returns <code>true</code> if the specified lexemes have equal
	 * sets of word forms.
	 */
	public boolean compareLexemes(Lexeme l1, Lexeme l2) {
		if(l1.forms.length != l2.forms.length) return false;
		
		for(WordEntry we : l1.forms) {
			boolean found = false;
			
			for(WordEntry we2 : l2.forms) {
				if(we.word.equals(we2.word) && we.grammLabelUid == we2.grammLabelUid) {
					found = true;
					break;
				}
			}
			
			if(!found) return false;
		}
		
		return true;
	}
	
	/**
	 * Returns the lexeme for the specified lemma.
	 * @param lemma Note that the specified lemma should be from this dictionary.
	 * @throws IllegalArgumentException if the specified word entry is not
	 * a lemma already added to this dictionary or if it is not a lemma at all.
	 */
	private Lexeme getLexeme(WordEntry lemma) {
		if(!lemma.isLemma()) {
			String s = lemma.toString();
			throw new IllegalArgumentException(i18n.getError("BgDictionary.notLemma", s));
		}
		
		ArrayList<WordEntry> words = new ArrayList<WordEntry>();
		
		if(widMap.get(lemma.id) == null) {
			String s = lemma.toString();
			throw new IllegalArgumentException(i18n.getError("BgDictionary.notFromDict", s));
		}
		
		Entry<Integer, WordEntry> entry = widMap.higherEntry(lemma.id);
		
		while(entry != null) {
			if (entry.getValue().isLemma()) break;
			
			words.add(entry.getValue());
			entry = widMap.higherEntry(entry.getKey());
		}
		
		return new Lexeme(lemma, words.toArray(new WordEntry[words.size()]));
	}
	
	public ArrayList<WordEntry> find(String s, SearchQuery q) {
		return find(s, true, q);
	}
	
	public ArrayList<WordEntry> find(String s, boolean exactMatch, SearchQuery q) {
		return find(s, exactMatch, null, q);
	}
	
	public ArrayList<WordEntry> find (
		String s, boolean exactMatch, GrammaticalLabel.LexicalClass c, SearchQuery q
	) {
		if(exactMatch) return findExactMatches(s, c, q);
		
		ArrayList<WordEntry> res = new ArrayList<WordEntry>();
		
		NavigableMap<Integer, TreeSet<WordEntry>> m;
		m = getMapByLexicalClass(c);
		
		if(m == null) m = gluidMap;
		
		q.compile();
		
		if(exactMatch) {
			for(TreeSet<WordEntry> entries : m.values()) {
				// entries can't be empty and all GLUIDs in the entries set are equal
				if((entries.first().grammLabelUid & q.glMask) != q.glId) continue;
			
				for(WordEntry we : entries) {
					if(we.word.equals(s)) res.add(we);
				}
			}
		} else {
			for(TreeSet<WordEntry> entries : m.values()) {
				// entries can't be empty and all GLUIDs in the entries set are equal
				if((entries.first().grammLabelUid & q.glMask) != q.glId) continue;
			
				for(WordEntry we : entries) {
					if(we.word.indexOf(s) != -1) res.add(we);
				}
			}
		}
		
		return res;
	}
	
	/**
	 * Returns a list of all words containing the specified string.
	 */
	public ArrayList<WordEntry> find(String s) {
		ArrayList<WordEntry> res = new ArrayList<WordEntry>();
		
		for(WordEntry we : widMap.values()) {
			if(we.word.indexOf(s) != -1) res.add(we);
		}
		
		return res;
	}
	
	/**
	 * Returns a list of all words that ends with the specified string.
	 */
	public ArrayList<WordEntry> endsWith(String suffix) {
		ArrayList<WordEntry> res = new ArrayList<WordEntry>();
		
		for(WordEntry we : widMap.values()) {
			if(we.word.endsWith(suffix)) res.add(we);
		}
		
		return res;
	}
	
	/**
	 * Returns a list of all words equals to the specified string.
	 */
	public ArrayList<WordEntry> findExactMatches(String s) {
		ArrayList<WordEntry> res = new ArrayList<WordEntry>();
		
		TreeSet<WordEntry> words = map.get(s);
		if(words == null) return res;
		
		for(WordEntry we : words) {
			res.add(we);
		}
		
		return res;
	}
	
	/**
	 * Returns a list of all words equals to the specified string.
	 */
	public ArrayList<WordEntry> findExactMatches (
		String s, final GrammaticalLabel.LexicalClass c, SearchQuery q
	) {
		ArrayList<WordEntry> res = new ArrayList<WordEntry>();
		
		TreeSet<WordEntry> words = map.get(s);
		if(words == null) return res;
		
		for(WordEntry we : words) {
			if(c != null && GrammaticalLabel.getLexicalClass(we.grammLabelUid) != c) {
				continue;
			}
			
			if((we.grammLabelUid & q.glMask) != q.glId) continue;
			
			res.add(we);
		}
		
		return res;
	}
	
	public int getLemmaCount() {
		int i = 0;
		
		for(TreeSet<WordEntry> set : map.values()) {
			for(WordEntry e : set) {
				if(e.isLemma()) i++;
			}
		}
		
		return i;
	}
	
	public void printAllLemmas() {
		for(TreeSet<WordEntry> set : map.values()) {
			for(WordEntry e : set) {
				if(!e.isLemma()) continue;
				System.out.println(e.word + " (" + ")");
			}
		}
	}
	
	public void printAllWords() {
		/*for(TreeSet<WordEntry> set : map.values()) {
			for(WordEntry e : set) {
				System.out.println(e.toString());
			}
		}*/
		
		for(WordEntry we : widMap.values()) {
			System.out.println(we.toString());
		}
	}
	
	public void printAllWordsByGrammType() {
		int t = BgGrammarType.getTypeId("1");
		while(t != -1) {
			 SortedMap<Integer, TreeSet<WordEntry>> map = getWordsByGrammTypeId(t);
			 if(!map.isEmpty()) {
				System.out.println();
				System.out.println(BgGrammarType.getTypeById(t));
				for(TreeSet<WordEntry> ts : map.values()) {
					for(WordEntry we : ts) {
						System.out.println(we.toString());
					}
				}
			 }
			
			t = BgGrammarType.getNextTypeId(t);
		}
	}
	
	public SortedMap<Integer, TreeSet<WordEntry>> getWordsByGrammType(String type) {
		return getWordsByGrammTypeId(BgGrammarType.getTypeId(type));
	}
	
	public SortedMap<Integer, TreeSet<WordEntry>> getWordsByGrammTypeId(int typeId) {
		int t2 = BgGrammarType.getNextTypeId(typeId);
		if(t2 == -1) return gluidMap.tailMap(typeId);
		
		return gluidMap.subMap(typeId, t2);
	}
	
	/**
	 * Returns the word entry with ID <code>id</code>, or <code>null</code>
	 * if a word entry with the specified ID does not exist.
	 */
	public WordEntry getWordEntryById(int id) {
		return widMap.get(id);
	}
	
	public void checkIntegrity() {
		// check for Bulgarian letters only
		for(WordEntry we : widMap.values()) {
			for(int i = 0; i < we.word.length(); i++) {
				if(!isBgLetter(we.word.charAt(i))) {
					System.err.println("Invalid characters: " + we.word);
					break;
				}
			}
		}
		
		for(WordEntry we : widMap.values()) {
			
		}
		
		// TODO: check type with lexical class
	}
	
	public void exportLemmasToFile(String file) {
		File f = new File(file);
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(f);
			
			for(WordEntry we : lemmas()) {
				fos.write(we.word.getBytes("UTF-8"));
				fos.write('\n');
				fos.write(String.valueOf(we.grammLabelUid).getBytes("UTF-8"));
				fos.write('\n');
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try { if(fos != null) fos.close(); }
			catch(Exception e) { e.printStackTrace(); }
		}
	}
	
	public void importLemmasFromFile(String file) throws Exception {
		File f = new File(file);
		if(!f.exists() || !f.isFile() || !f.canRead()) {
			String s = f.getAbsolutePath();
			String err = i18n.getError("BgDictionary.invalidFile", s);
			throw new IllegalArgumentException(err);
		}
		
		BufferedReader reader;
		
		try {
			InputStreamReader r
				= new InputStreamReader(new FileInputStream(f), "UTF-8");
			reader = new BufferedReader(r);
		} catch(Exception e) {
			String s = f.getAbsolutePath();
			String err = i18n.getError("BgDictionary.cantReadFile", s);
			throw new IllegalArgumentException(err, e);
		}
		
		try {
			String line = reader.readLine();
			while(line != null) {
				String gluid = reader.readLine();
				if(gluid == null) {
					System.err.println("Invalid file format!");
					break;
				}
				
				WordEntry e = addWord(line, Integer.parseInt(gluid), -1);
				
				line = reader.readLine();
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try { reader.close(); }
			catch(Exception e) { e.printStackTrace(); }
		}
	}
	
	public void exportToFile(String file) {
		File f = new File(file);
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(f);
			StringBuffer sb = new StringBuffer();
			
			for(WordEntry we : widMap.values()) {
				if(we.isLemma()) {
					sb.append('\n');
					fos.write(sb.toString().getBytes("UTF-8"));
					sb.setLength(0);
				}
				
				sb.append(we.word).append('\n');
				sb.append(we.grammLabelUid).append('\n');
			}
			
			if(sb.length() > 0) {
				fos.write(sb.toString().getBytes("UTF-8"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try { if(fos != null) fos.close(); }
			catch(Exception e) { e.printStackTrace(); }
		}
	}
	
	public void writeToFile(String file) {
		File f = new File(file);
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		
		try {
			fos = new FileOutputStream(f);
			out = new ObjectOutputStream(fos);
			
			out.writeObject(map);
			out.writeObject(gluidMap);
			out.writeObject(widMap);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(out != null) out.close();
				if(fos != null) fos.close();
			} catch(Exception e) { e.printStackTrace(); }
		}
	}
	
	public void readFromFile(String file) throws Exception {
		File f = new File(file);
		if(!f.exists() || !f.isFile() || !f.canRead()) {
			String s = f.getAbsolutePath();
			String err = i18n.getError("BgDictionary.invalidFile", s);
			throw new IllegalArgumentException(err);
		}
		
		FileInputStream fin = null;
		ObjectInputStream in = null;
		
		try {
			fin = new FileInputStream(f);
			in = new ObjectInputStream(fin);
			
			map = (HashMap<String, TreeSet<WordEntry>>) in.readObject();
			gluidMap = (TreeMap<Integer, TreeSet<WordEntry>>) in.readObject();
			widMap = (TreeMap<Integer, WordEntry>) in.readObject();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(in != null) in.close();
				if(fin != null) fin.close();
			} catch(Exception e) { e.printStackTrace(); }
		}
	}
	
	public void importFromFile(String file) throws Exception {
		File f = new File(file);
		if(!f.exists() || !f.isFile() || !f.canRead()) {
			String s = f.getAbsolutePath();
			String err = i18n.getError("BgDictionary.invalidFile", s);
			throw new IllegalArgumentException(err);
		}
		
		importFromStream(new FileInputStream(f));
	}
	
	public void importFromStream(InputStream stream) throws Exception {
		BufferedReader reader;
		
		InputStreamReader r = new InputStreamReader(stream, "UTF-8");
		reader = new BufferedReader(r);
		
		try {
			WordEntry lemma = null;
			
			String line = reader.readLine();
			
			while(line != null) {
				if(line.isEmpty()) {
					String word = reader.readLine();
					line = reader.readLine();
					if(line == null) {
						if(word == null || word.isEmpty()) break;
						System.err.println("Invalid file format");
						break;
					}
					
					WordEntry we;
					we = new WordEntry(word, -1, Integer.parseInt(line));
					//lemma = addWord(word, Integer.parseInt(line), -1);
					lemma = addWord(we, false);
					line = reader.readLine();
					continue;
				}
				
				String gluid = reader.readLine();
				if(gluid == null) {
					System.err.println("Invalid file format!");
					break;
				}
				
				WordEntry we = new WordEntry(line, lemma.id, Integer.parseInt(gluid));
				addWord(we, false);
				line = reader.readLine();
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try { reader.close(); }
			catch(Exception e) { e.printStackTrace(); }
		}
	}
	
	public TreeMap<Integer, Integer> getLemmasAmbiguityStat() {
		TreeMap<Integer, Integer> las = new TreeMap<Integer, Integer>();
		
		for(String s : map.keySet()) {
			int k = findLemmas(s).length;
			Integer v = las.get(k);
			
			if(v == null) {
				las.put(k, 1);
			} else {
				las.put(k, v + 1);
			}
		}
		
		return las;
	}
	
	public TreeMap<Integer, Integer> getWordsAmbiguityStat() {
		TreeMap<Integer, Integer> was = new TreeMap<Integer, Integer>();
		
		for(TreeSet<WordEntry> entries : map.values()) {
			int k = entries.size();
			Integer v = was.get(k);
			
			if(v == null) {
				was.put(k, 1);
			} else {
				was.put(k, v + 1);
			}
		}
		
		return was;
	}
	
	public ArrayList<TreeSet<WordEntry>> getWordsByAmbiguityCount(int ambiguityCount) {
		ArrayList<TreeSet<WordEntry>> words = new ArrayList<TreeSet<WordEntry>>();
		
		for(TreeSet<WordEntry> entries : map.values()) {
			if(entries.size() != ambiguityCount) continue;
			
			words.add(entries);
		}
		
		return words;
	}
	
	
	private class Lemmas implements Iterable<WordEntry>, Iterator<WordEntry> {
		Entry<Integer, WordEntry> entry;
		
		private Lemmas() {
			entry = widMap.firstEntry();
		}
		
		@Override
		public Iterator<WordEntry> iterator() {
			return new Lemmas();
		}
		
		@Override
		public boolean hasNext() {
			return entry != null;
		}
		
		@Override
		public WordEntry next() {
			if(entry == null) throw new NoSuchElementException();
			
			WordEntry we = entry.getValue();
			
			entry = widMap.higherEntry(entry.getKey());
			
			while(entry != null) {
				if (entry.getValue().isLemma()) break;
				
				entry = widMap.higherEntry(entry.getKey());
			}
			
			return we;
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	private class Lexemes implements Iterable<Lexeme>, Iterator<Lexeme> {
		Entry<Integer, WordEntry> entry;
		
		private Lexemes() {
			entry = widMap.firstEntry();
		}
		
		@Override
		public Iterator<Lexeme> iterator() {
			return new Lexemes();
		}
		
		@Override
		public boolean hasNext() {
			return entry != null;
		}
		
		@Override
		public Lexeme next() {
			if(entry == null) throw new NoSuchElementException();
			
			Lexeme l = getLexeme(entry.getValue());
			
			if(l.forms.length > 0) {
				entry = widMap.higherEntry(l.forms[l.forms.length - 1].id);
			} else {
				entry = widMap.higherEntry(l.lemma.id);
			}
			
			return l;
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	private Iterable<Lexeme> lexemes = new Lexemes();
	
	public Iterable<Lexeme> lexemes() {
		return lexemes;
	}
	
	private Iterable<WordEntry> lemmas = new Lemmas();
	
	public Iterable<WordEntry> lemmas() {
		return lemmas;
	}
	
	public static boolean isBgLetter(char c) {
		return Character.UnicodeBlock.CYRILLIC.equals(Character.UnicodeBlock.of(c));
	}
	
	public static boolean isNumeric(String str) {  
		try { double d = Double.parseDouble(str); }  
		catch(NumberFormatException e) { return false; }  
		
		return true;  
	}
	
	public static boolean isShortForm(String str) {  
		if(str.length() == 2) {
			if(str.charAt(1) == '.') return true;
		}
		
		if(str.length() == 3) {
			if(str.charAt(2) == '.') {
				return true;
			}
		}
		
		return false; 
	}
}
