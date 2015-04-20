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

import bg.swu.nlp.tools.bglang.GrammaticalLabel.PronounForm;
import static bg.swu.nlp.tools.bglang.I18n.i18n;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 */
public class BTBUtils {
	
	
	/**
	 * Returns the BTB-TS grammatical character representing the specified
	 * lexical class.
	 */
	public static char getChar(GrammaticalLabel.LexicalClass lexicalClass) {
		switch(lexicalClass) {
			case NOUN: return 'N';
			case ADJECTIVE: return 'A';
			case PRONOUN: return 'P';
			case NUMERAL: return 'M';
			case VERB: return 'V';
			case ADVERB: return 'D';
			case CONJUNCTION: return 'C';
			case INTERJECTION: return 'I';
			case PARTICLE: return 'T';
			case PREPOSITION: return 'R';
		}
		
		return '-';
	}	
	
	/**
	 * Returns the BTB-TS grammatical character of the
	 * lexical class encoded in the specified grammatical label UID.
	 */
	public static char getLexicalClassChar(int grammLabelUid) {
		return getChar(GrammaticalLabel.getLexicalClass(grammLabelUid));
	}
	
	/**
	 * Returns the lexical class represented by the specified
	 * BTB-TS grammatical character, or <code>null</code>.
	 */
	public static GrammaticalLabel.LexicalClass getLexicalClass(char c) {
		switch(c) {
			case 'N': return GrammaticalLabel.LexicalClass.NOUN;
			case 'A': return GrammaticalLabel.LexicalClass.ADJECTIVE;
			case 'P': return GrammaticalLabel.LexicalClass.PRONOUN;
			case 'M': return GrammaticalLabel.LexicalClass.NUMERAL;
			case 'V': return GrammaticalLabel.LexicalClass.VERB;
			case 'D': return GrammaticalLabel.LexicalClass.ADVERB;
			case 'C': return GrammaticalLabel.LexicalClass.CONJUNCTION;
			case 'I': return GrammaticalLabel.LexicalClass.INTERJECTION;
			case 'T': return GrammaticalLabel.LexicalClass.PARTICLE;
			case 'R': return GrammaticalLabel.LexicalClass.PREPOSITION;
		}
		
		return null;
	}
	
	/**
	 * Gets a nonstandard (fake) BG grammatical type based on the
	 * specified BTB-TS tag. This is useful for creating grammatical label
	 * for a word, when there is no information to which grammatical type
	 * this word belongs. This is needed because some of the
	 * grammatical information is retrieved from the grammatical type
	 * itself, like lexical class, etc. The fake grammatical types can be
	 * distinguished from the standard types by their suffixes.
	 * All fake grammatical types have a 'c' suffix, while the standard
	 * grammatical types can have only 'a' or 'b' suffix, or no suffix.
	 * @return A fake grammatical type corresponding to the specified tag,
	 * or <code>-1</code> if the specified tag is not a valid BTB-TS tag.
	 */
	public static int getFakeBgGrammarType(String tag) {
		if(tag == null || tag.length() < 1) return -1;
		
		switch(tag.charAt(0)) {
			case 'N':
				if(tag.length() > 2) {
					char c = tag.charAt(2);
					if(c == 'm') return BgGrammarType.getTypeId("40c");
					if(c == 'f') return BgGrammarType.getTypeId("53c");
				}
				
				return BgGrammarType.getTypeId("75c");
			case 'A': return BgGrammarType.getTypeId("89c");
			case 'P': return BgGrammarType.getTypeId("130c");
			case 'M': return BgGrammarType.getTypeId("141c");
			case 'V': return BgGrammarType.getTypeId("187c");
			case 'D': return BgGrammarType.getTypeId("188c");
			case 'C': return BgGrammarType.getTypeId("189c");
		}
		
		return -1;
	}
	
	/**
	 * Returns the BTB-TS grammatical character representing the specified
	 * pronoun type.
	 */
	public static char getChar(GrammaticalLabel.PronounType t) {
		switch(t) {
			case PERSONAL:      return 'p';
			case DEMONSTRATIVE: return 'd';
			case RELATIVE:      return 'r';
			case COLLECTIVE:    return 'c';
			case INTERROGATIVE: return 'i';
			case INDEFINITE:    return 'f';
			case NEGATIVE:      return 'n';
			case POSSESSIVE:    return 's';
		}
		
		return '-';
	}
	
	/**
	 * Returns the BTB-TS grammatical character of the
	 * pronoun type encoded in the specified grammatical label UID.
	 */
	public static char getPronounTypeChar(int grammLabelUid) {
		return getChar(GrammaticalLabel.getPronounType(grammLabelUid));
	}
	
	/**
	 * Returns the pronoun type represented by the specified
	 * BTB-TS grammatical character, or <code>null</code>.
	 */
	public static GrammaticalLabel.PronounType getPronounType(char c) {
		switch(c) {
			case 'p': return GrammaticalLabel.PronounType.PERSONAL;
			case 'd': return GrammaticalLabel.PronounType.DEMONSTRATIVE;
			case 'r': return GrammaticalLabel.PronounType.RELATIVE;
			case 'c': return GrammaticalLabel.PronounType.COLLECTIVE;
			case 'i': return GrammaticalLabel.PronounType.INTERROGATIVE;
			case 'f': return GrammaticalLabel.PronounType.INDEFINITE;
			case 'n': return GrammaticalLabel.PronounType.NEGATIVE;
			case 's': return GrammaticalLabel.PronounType.POSSESSIVE;
		}
		
		return null;
	}
	
	/**
	 * Encodes the pronoun type information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * pronoun type information should be encoded.
	 * @param pronounType the BTB-TS grammatical character specifying the
	 * pronoun type to be encoded in <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodePronounType(int grammLabelUid, char pronounType) {
		GrammaticalLabel.PronounType t = getPronounType(pronounType);
		return GrammaticalLabel.encodePronounType(grammLabelUid, t);
	}
	
	/**
	 * Returns the BTB-TS grammatical character representing the specified
	 * grammatical number.
	 */
	public static char getChar(GrammaticalLabel.Number number) {
		switch(number) {
			case SINGULAR:    return 's';
			case PLURAL:      return 'p';
			case ONLY_PLURAL: return 'l';
			case COUNT_FORM:  return 't';
		}
		
		return '-';
	}	
	
	/**
	 * Returns the BTB-TS grammatical character of the
	 * grammatical number encoded in the specified grammatical label UID.
	 */
	public static char getNumberChar(int grammLabelUid) {
		return getChar(GrammaticalLabel.getNumber(grammLabelUid));
	}
	
	/**
	 * Returns the grammatical number represented by the specified
	 * BTB-TS grammatical character, or <code>null</code>.
	 */
	public static GrammaticalLabel.Number getNumber(char c) {
		switch(c) {
			case 's': return GrammaticalLabel.Number.SINGULAR;
			case 'p': return GrammaticalLabel.Number.PLURAL;
			case 'l': return GrammaticalLabel.Number.ONLY_PLURAL;
			case 't': return GrammaticalLabel.Number.COUNT_FORM;
			case '-': return GrammaticalLabel.Number.NONE;
		}
		
		return null;
	}
	
	/**
	 * Encodes the grammatical number information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * grammatical number information should be encoded.
	 * @param number the BTB-TS grammatical character specifying the
	 * grammatical number to be encoded in <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeNumber(int grammLabelUid, char number) {
		GrammaticalLabel.Number n = getNumber(number);
		return GrammaticalLabel.encodeNumber(grammLabelUid, n);
	}
	
	/**
	 * Returns the BTB-TS grammatical character representing the specified
	 * gender.
	 */
	public static char getChar(GrammaticalLabel.Gender gender) {
		switch(gender) {
			case MASCULINE: return 'm';
			case FEMININE:  return 'f';
			case NEUTER:    return 'n';
		}
		
		return '-';
	}	
	
	/**
	 * Returns the BTB-TS grammatical character of the
	 * gender encoded in the specified grammatical label UID.
	 */
	public static char getGenderChar(int grammLabelUid) {
		return getChar(GrammaticalLabel.getGender(grammLabelUid));
	}
	
	/**
	 * Returns the gender represented by the specified
	 * BTB-TS grammatical character, or <code>null</code>.
	 */
	public static GrammaticalLabel.Gender getGender(char c) {
		switch(c) {
			case 'm': return GrammaticalLabel.Gender.MASCULINE;
			case 'f': return GrammaticalLabel.Gender.FEMININE;
			case 'n': return GrammaticalLabel.Gender.NEUTER;
			case '-': return GrammaticalLabel.Gender.NONE;
		}
		
		return null;
	}
	
	/**
	 * Encodes the gender information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * gender information should be encoded.
	 * @param gender the BTB-TS grammatical character specifying the
	 * gender to be encoded in <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeGender(int grammLabelUid, char gender) {
		GrammaticalLabel.Gender g = getGender(gender);
		return GrammaticalLabel.encodeGender(grammLabelUid, g);
	}
	
	/**
	 * Returns the BTB-TS grammatical character representing the specified
	 * person.
	 */
	public static char getChar(GrammaticalLabel.Person person) {
		switch(person) {
			case FIRST:  return '1';
			case SECOND: return '2';
			case THIRD:  return '3';
		}
		
		return '-';
	}	
	
	/**
	 * Returns the BTB-TS grammatical character of the
	 * person encoded in the specified grammatical label UID.
	 */
	public static char getPersonChar(int grammLabelUid) {
		return getChar(GrammaticalLabel.getPerson(grammLabelUid));
	}
	
	/**
	 * Returns the person represented by the specified
	 * BTB-TS grammatical character, or <code>null</code>.
	 */
	public static GrammaticalLabel.Person getPerson(char c) {
		switch(c) {
			case '1': return GrammaticalLabel.Person.FIRST;
			case '2': return GrammaticalLabel.Person.SECOND;
			case '3': return GrammaticalLabel.Person.THIRD;
			case '-': return GrammaticalLabel.Person.NONE;
		}
		
		return null;
	}
	
	/**
	 * Encodes the person information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * person information should be encoded.
	 * @param person the BTB-TS grammatical character specifying the
	 * person to be encoded in <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodePerson(int grammLabelUid, char person) {
		GrammaticalLabel.Person p = getPerson(person);
		return GrammaticalLabel.encodePerson(grammLabelUid, p);
	}
	
	/**
	 * Returns the BTB-TS grammatical character representing the specified
	 * grammatical number.
	 */
	public static char getChar(GrammaticalLabel.Article article) {
		return getChar(article, false);
	}
	
	/**
	 * Returns the BTB-TS grammatical character representing the specified
	 * grammatical number.
	 */
	public static char getChar(GrammaticalLabel.Article article, boolean masculine) {
		switch(article) {
			case INDEFINITE:    return 'i';
			case DEFINITE:      return  (masculine ? 'h' : 'd');
			case DEFINITE_FULL: return 'f';
			// TODO: short definite
		}
		
		return '-';
	}	
	
	/**
	 * Returns the BTB-TS grammatical character of the
	 * article encoded in the specified grammatical label UID.
	 */
	public static char getArticleChar(int grammLabelUid) {
		return getArticleChar(grammLabelUid, false);
	}	
	
	/**
	 * Returns the BTB-TS grammatical character of the
	 * article encoded in the specified grammatical label UID.
	 */
	public static char getArticleChar(int grammLabelUid, boolean masculine) {
		return getChar(GrammaticalLabel.getArticle(grammLabelUid), masculine);
	}
	
	/**
	 * Returns the article represented by the specified
	 * BTB-TS grammatical character, or <code>null</code>.
	 */
	public static GrammaticalLabel.Article getArticle(char c) {
		switch(c) {
			case '-': return GrammaticalLabel.Article.NONE;
			case 'i': return GrammaticalLabel.Article.INDEFINITE;
			case 'd': return GrammaticalLabel.Article.DEFINITE;
			case 'h': return GrammaticalLabel.Article.DEFINITE;
			case 'f': return GrammaticalLabel.Article.DEFINITE_FULL;
		}
		
		return null;
	}
	
	/**
	 * Encodes the article information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * article information should be encoded.
	 * @param article the BTB-TS grammatical character specifying the
	 * article to be encoded in <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeArticle(int grammLabelUid, char article) {
		GrammaticalLabel.Article a = getArticle(article);
		return GrammaticalLabel.encodeArticle(grammLabelUid, a);
	}
	
	/**
	 * Returns the BTB-TS grammatical character representing the specified
	 * pronoun case.
	 */
	public static char getChar(GrammaticalLabel.PronounCase pnCase) {
		switch(pnCase) {
			case NOMINATIVE: return 'o';
			case ACCUSATIVE: return 'a';
			case DATIVE:     return 'd';
			// TODO: dative possessive
		}
		
		return '-';
	}	
	
	/**
	 * Returns the BTB-TS grammatical character of the
	 * pronoun case encoded in the specified grammatical label UID.
	 */
	public static char getPronounCaseChar(int grammLabelUid) {
		return getChar(GrammaticalLabel.getPronounCase(grammLabelUid));
	}
	
	/**
	 * Returns the pronoun case represented by the specified
	 * BTB-TS grammatical character.
	 */
	public static GrammaticalLabel.PronounCase getPronounCase(char c) {
		switch(c) {
			case 'o': return GrammaticalLabel.PronounCase.NOMINATIVE;
			case 'a': return GrammaticalLabel.PronounCase.ACCUSATIVE;
			case 'd': return GrammaticalLabel.PronounCase.DATIVE;
			// TODO: dative possessive
		}
		
		return GrammaticalLabel.PronounCase.NONE;
	}
	
	/**
	 * Encodes the pronoun case information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * pronoun case information should be encoded.
	 * @param pronounCase the BTB-TS grammatical character specifying the
	 * pronoun case to be encoded in <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodePronounCase(int grammLabelUid, char pronounCase) {
		GrammaticalLabel.PronounCase c = getPronounCase(pronounCase);
		return GrammaticalLabel.encodePronounCase(grammLabelUid, c);
	}
	
	/**
	 * Returns the BTB-TS grammatical character representing the specified
	 * noun case.
	 */
	public static char getChar(GrammaticalLabel.NounCase nCase) {
		switch(nCase) {
			case NONE:       return '-';
			case VOCATIVE:   return 'v';
			case ACCUSATIVE: return 'a';
			case DATIVE:     return 'd';
		}
		
		return '-';
	}	
	
	/**
	 * Returns the BTB-TS grammatical character of the
	 * noun case encoded in the specified grammatical label UID.
	 */
	public static char getNounCaseChar(int grammLabelUid) {
		return getChar(GrammaticalLabel.getNounCase(grammLabelUid));
	}
	
	/**
	 * Returns the noun case represented by the specified
	 * BTB-TS grammatical character.
	 */
	public static GrammaticalLabel.NounCase getNounCase(char c) {
		switch(c) {
			case 'v': return GrammaticalLabel.NounCase.VOCATIVE;
			case 'a': return GrammaticalLabel.NounCase.ACCUSATIVE;
			case 'd': return GrammaticalLabel.NounCase.DATIVE;
		}
		
		return GrammaticalLabel.NounCase.NONE;
	}
	
	/**
	 * Encodes the noun case information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * noun case information should be encoded.
	 * @param nounCase the BTB-TS grammatical character specifying the
	 * noun case to be encoded in <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeNounCase(int grammLabelUid, char nounCase) {
		GrammaticalLabel.NounCase c = getNounCase(nounCase);
		return GrammaticalLabel.encodeNounCase(grammLabelUid, c);
	}
	
	/**
	 * Returns the BTB-TS grammatical character representing the specified
	 * noun type.
	 */
	public static char getChar(GrammaticalLabel.NounType nType) {
		switch(nType) {
			case COMMON: return 'c';
			case PROPER: return 'p';
		}
		
		return '-';
	}	
	
	/**
	 * Returns the BTB-TS grammatical character of the
	 * noun type encoded in the specified grammatical label UID.
	 */
	public static char getNounTypeChar(int grammLabelUid) {
		return getChar(GrammaticalLabel.getNounType(grammLabelUid));
	}
	
	/**
	 * Returns the noun type represented by the specified
	 * BTB-TS grammatical character.
	 */
	public static GrammaticalLabel.NounType getNounType(char c) {
		switch(c) {
			case 'c': return GrammaticalLabel.NounType.COMMON;
			case 'p': return GrammaticalLabel.NounType.PROPER;
		}
		
		return null;
	}
	
	/**
	 * Encodes the noun type information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * noun type information should be encoded.
	 * @param nounType the BTB-TS grammatical character specifying the
	 * noun type to be encoded in <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeNounType(int grammLabelUid, char nounType) {
		GrammaticalLabel.NounType t = getNounType(nounType);
		return GrammaticalLabel.encodeNounType(grammLabelUid, t);
	}
	
	/**
	 * Returns the BTB-TS grammatical character representing the specified
	 * verb type.
	 */
	public static char getChar(GrammaticalLabel.VerbType type) {
		switch(type) {
			case PERSONAL:  return 'p';
			case IMPERSONAL: return 'n';
			case AUXILIARY:  return 'x';
			case AUXILIARY2: return 'y';
			case AUXILIARY3: return 'i';
		}
		
		return '-';
	}	
	
	/**
	 * Returns the BTB-TS grammatical character of the
	 * verb type encoded in the specified grammatical label UID.
	 */
	public static char getVerbTypeChar(int grammLabelUid) {
		return getChar(GrammaticalLabel.getVerbType(grammLabelUid));
	}
	
	/**
	 * Returns the verb type represented by the specified
	 * BTB-TS grammatical character.
	 */
	public static GrammaticalLabel.VerbType getVerbType(char c) {
		switch(c) {
			case 'p': return GrammaticalLabel.VerbType.PERSONAL;
			case 'n': return GrammaticalLabel.VerbType.IMPERSONAL;
			case 'x': return GrammaticalLabel.VerbType.AUXILIARY;
			case 'y': return GrammaticalLabel.VerbType.AUXILIARY2;
			case 'i': return GrammaticalLabel.VerbType.AUXILIARY3;
		}
		
		return GrammaticalLabel.VerbType.NONE;
	}
	
	/**
	 * Encodes the verb type information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * verb type information should be encoded.
	 * @param verbType the BTB-TS grammatical character specifying the
	 * verb type to be encoded in <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeVerbType(int grammLabelUid, char verbType) {
		GrammaticalLabel.VerbType c = getVerbType(verbType);
		return GrammaticalLabel.encodeVerbType(grammLabelUid, c);
	}
	
	/**
	 * Returns the BTB-TS grammatical character representing the specified
	 * verb aspect.
	 */
	public static char getChar(GrammaticalLabel.Aspect aspect) {
		switch(aspect) {
			case IMPERFECTIVE: return 'i';
			case PERFECTIVE:   return 'p';
		}
		
		return '-';
	}	
	
	/**
	 * Returns the BTB-TS grammatical character of the
	 * verb aspect encoded in the specified grammatical label UID.
	 */
	public static char getAspectChar(int grammLabelUid) {
		return getChar(GrammaticalLabel.getAspect(grammLabelUid));
	}
	
	/**
	 * Returns the verb aspect represented by the specified
	 * BTB-TS grammatical character.
	 */
	public static GrammaticalLabel.Aspect getAspect(char c) {
		switch(c) {
			case 'i': return GrammaticalLabel.Aspect.IMPERFECTIVE;
			case 'p': return GrammaticalLabel.Aspect.PERFECTIVE;
		}
		
		return null;
	}
	
	/**
	 * Encodes the verb aspect information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * verb aspect information should be encoded.
	 * @param aspect the BTB-TS grammatical character specifying the
	 * verb aspect to be encoded in <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeAspect(int grammLabelUid, char aspect) {
		GrammaticalLabel.Aspect c = getAspect(aspect);
		return GrammaticalLabel.encodeAspect(grammLabelUid, c);
	}
	
	/**
	 * Returns the BTB-TS grammatical character representing the specified
	 * transitivity.
	 */
	public static char getChar(GrammaticalLabel.Transitivity t) {
		switch(t) {
			case TRANSITIVE:   return 't';
			case INTRANSITIVE: return 'i';
		}
		
		return '-';
	}	
	
	/**
	 * Returns the BTB-TS grammatical character of the
	 * transitivity encoded in the specified grammatical label UID.
	 */
	public static char getTransitivityChar(int grammLabelUid) {
		return getChar(GrammaticalLabel.getTransitivity(grammLabelUid));
	}
	
	/**
	 * Returns the transitivity represented by the specified
	 * BTB-TS grammatical character.
	 */
	public static GrammaticalLabel.Transitivity getTransitivity(char c) {
		switch(c) {
			case 't': return GrammaticalLabel.Transitivity.TRANSITIVE;
			case 'i': return GrammaticalLabel.Transitivity.INTRANSITIVE;
		}
		
		return null;
	}
	
	/**
	 * Encodes the transitivity information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * transitivity information should be encoded.
	 * @param tra the BTB-TS grammatical character specifying the
	 * transitivity to be encoded in <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeTransitivity(int grammLabelUid, char tra) {
		GrammaticalLabel.Transitivity t = getTransitivity(tra);
		return GrammaticalLabel.encodeTransitivity(grammLabelUid, t);
	}
	
	/**
	 * Returns the BTB-TS grammatical character representing the specified
	 * verb form.
	 */
	public static char getChar(GrammaticalLabel.VerbForm pnCase) {
		switch(pnCase) {
			case FINITE_INDICATIVE:    return 'f';
			case FINITE_IMPERATIVE:    return 'z';
			case FINITE_CONDITIONAL:   return 'u';
			case NONFINITE_PARTICIPLE: return 'c';
			case NONFINITE_GERUND:     return 'g';
		}
		
		return '-';
	}	
	
	/**
	 * Returns the BTB-TS grammatical character of the
	 * verb form encoded in the specified grammatical label UID.
	 */
	public static char getVerbFormChar(int grammLabelUid) {
		return getChar(GrammaticalLabel.getVerbForm(grammLabelUid));
	}
	
	/**
	 * Returns the verb form represented by the specified
	 * BTB-TS grammatical character.
	 */
	public static GrammaticalLabel.VerbForm getVerbForm(char c) {
		switch(c) {
			case 'f': return GrammaticalLabel.VerbForm.FINITE_INDICATIVE;
			case 'z': return GrammaticalLabel.VerbForm.FINITE_IMPERATIVE;
			case 'u': return GrammaticalLabel.VerbForm.FINITE_CONDITIONAL;
			case 'c': return GrammaticalLabel.VerbForm.NONFINITE_PARTICIPLE;
			case 'g': return GrammaticalLabel.VerbForm.NONFINITE_GERUND;
		}
		
		return null;
	}
	
	/**
	 * Encodes the verb form information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * verb form information should be encoded.
	 * @param verbForm the BTB-TS grammatical character specifying the
	 * verb form to be encoded in <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeVerbForm(int grammLabelUid, char verbForm) {
		GrammaticalLabel.VerbForm f = getVerbForm(verbForm);
		return GrammaticalLabel.encodeVerbForm(grammLabelUid, f);
	}
	
	/**
	 * Returns the BTB-TS grammatical character representing the specified
	 * voice of verb.
	 */
	public static char getChar(GrammaticalLabel.Voice voice) {
		switch(voice) {
			case ACTIVE:  return 'a';
			case PASSIVE: return 'v';
		}
		
		return '-';
	}	
	
	/**
	 * Returns the BTB-TS grammatical character of the
	 * voice of verb encoded in the specified grammatical label UID.
	 */
	public static char getVoiceChar(int grammLabelUid) {
		return getChar(GrammaticalLabel.getVoice(grammLabelUid));
	}
	
	/**
	 * Returns the voice of verb represented by the specified
	 * BTB-TS grammatical character.
	 */
	public static GrammaticalLabel.Voice getVoice(char c) {
		switch(c) {
			case 'a': return GrammaticalLabel.Voice.ACTIVE;
			case 'v': return GrammaticalLabel.Voice.PASSIVE;
		}
		
		return GrammaticalLabel.Voice.NONE;
	}
	
	/**
	 * Encodes the voice of verb information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * voice of verb information should be encoded.
	 * @param voice the BTB-TS grammatical character specifying the
	 * voice of verb to be encoded in <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeVoice(int grammLabelUid, char voice) {
		GrammaticalLabel.Voice v = getVoice(voice);
		return GrammaticalLabel.encodeVoice(grammLabelUid, v);
	}
	
	/**
	 * Returns the BTB-TS grammatical character representing the specified
	 * verb tense.
	 */
	public static char getChar(GrammaticalLabel.Tense tense) {
		switch(tense) {
			case PRESENT:   return 'r';
			case AORIST:    return 'o';
			case IMPERFECT: return 'm';
			case PAST:      return 't';
		}
		
		return '-';
	}	
	
	/**
	 * Returns the BTB-TS grammatical character of the
	 * verb tense encoded in the specified grammatical label UID.
	 */
	public static char getTenseChar(int grammLabelUid) {
		return getChar(GrammaticalLabel.getTense(grammLabelUid));
	}
	
	/**
	 * Returns the verb tense represented by the specified
	 * BTB-TS grammatical character.
	 */
	public static GrammaticalLabel.Tense getTense(char c) {
		switch(c) {
			case 'r': return GrammaticalLabel.Tense.PRESENT;
			case 'o': return GrammaticalLabel.Tense.AORIST;
			case 'm': return GrammaticalLabel.Tense.IMPERFECT;
			case 't': return GrammaticalLabel.Tense.PAST;
		}
		
		return GrammaticalLabel.Tense.NONE;
	}
	
	/**
	 * Encodes the verb tense information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * verb tense information should be encoded.
	 * @param tense the BTB-TS grammatical character specifying the
	 * verb tense to be encoded in <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeTense(int grammLabelUid, char tense) {
		GrammaticalLabel.Tense t = getTense(tense);
		return GrammaticalLabel.encodeTense(grammLabelUid, t);
	}
	
	/**
	 * Returns the BTB-TS grammatical character representing the specified
	 * numeral type.
	 */
	public static char getChar(GrammaticalLabel.NumeralType t) {
		switch(t) {
			case CARDINAL:  return 'c';
			case ORDINAL:   return 'o';
			case ADVERBIAL: return 'd';
			case FUZZY:     return 'y';
		}
		
		return '-';
	}
	
	/**
	 * Returns the BTB-TS grammatical character of the
	 * numeral type encoded in the specified grammatical label UID.
	 */
	public static char getNumeralTypeChar(int grammLabelUid) {
		return getChar(GrammaticalLabel.getNumeralType(grammLabelUid));
	}
	
	/**
	 * Returns the numeral type represented by the specified
	 * BTB-TS grammatical character, or <code>null</code>.
	 */
	public static GrammaticalLabel.NumeralType getNumeralType(char c) {
		switch(c) {
			case 'c': return GrammaticalLabel.NumeralType.CARDINAL;
			case 'o': return GrammaticalLabel.NumeralType.ORDINAL;
			case 'd': return GrammaticalLabel.NumeralType.ADVERBIAL;
			case 'y': return GrammaticalLabel.NumeralType.FUZZY;
			case '-': return GrammaticalLabel.NumeralType.NONE;
		}
		
		return null;
	}
	
	/**
	 * Encodes the numeral type information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * numeral type information should be encoded.
	 * @param numeralType the BTB-TS grammatical character specifying the
	 * numeral type to be encoded in <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeNumeralType(int grammLabelUid, char numeralType) {
		GrammaticalLabel.NumeralType t = getNumeralType(numeralType);
		return GrammaticalLabel.encodeNumeralType(grammLabelUid, t);
	}
	
	/**
	 * Returns the BTB-TS tag of the specified grammatical label UID.
	 */
	public static String getTag(int grammLabelUid) {
		StringBuffer sb = new StringBuffer();
		
		char lc = getLexicalClassChar(grammLabelUid);
		sb.append(lc);
		
		switch(lc) {
			case 'N': return getNounTag(grammLabelUid);
			case 'A': return getAdjectiveTag(grammLabelUid);
			case 'P': return getPronounTag(grammLabelUid);
			case 'M': return getNumeralTag(grammLabelUid);
			case 'V': return getVerbTag(grammLabelUid);
			case 'D': return getAdverbTag(grammLabelUid);
			case 'C': return getConjunctionTag(grammLabelUid);
			case 'I': return getInterjectionTag(grammLabelUid);
			case 'T': return getParticleTag(grammLabelUid);
			case 'R': return getPrepositionTag(grammLabelUid);
		}
		
		return sb.toString();
	}
	
	
	private static String getNounTag(int grammLabelUid) {
		StringBuffer sb = new StringBuffer("N");
		
		// P02
		sb.append(getNounTypeChar(grammLabelUid));
		
		// P03
		sb.append(getGenderChar(grammLabelUid));
		
		// P04
		sb.append(getNumberChar(grammLabelUid));
		
		// P05
		boolean masculine = false;
		GrammaticalLabel.Gender gender = GrammaticalLabel.getGender(grammLabelUid);
		if(gender ==  GrammaticalLabel.Gender.MASCULINE) masculine = true;
		
		sb.append(getArticleChar(grammLabelUid, masculine));
		
		// P06
		sb.append(getNounCaseChar(grammLabelUid));
		
		return sb.toString();
	}
	
	private static String getAdjectiveTag(int grammLabelUid) {
		StringBuffer sb = new StringBuffer("A");
		
		// P02
		sb.append(getGenderChar(grammLabelUid));
		
		// P03
		sb.append(getNumberChar(grammLabelUid));
		
		// P04
		sb.append(getArticleChar(grammLabelUid));
		
		// P05
		// TODO: 
		
		return sb.toString();
	}
	
	private static String getPronounTag(int grammLabelUid) {
		StringBuffer sb = new StringBuffer("P");
		
		// P02
		sb.append(getPronounTypeChar(grammLabelUid));
		
		// TODO: P03
		sb.append('-');
		///////
		
		// P04
		PronounForm f = GrammaticalLabel.getPronounForm(grammLabelUid);
		if(f == PronounForm.FULL) {
			sb.append('l');
		} else if(f == PronounForm.SHORT) {
			sb.append('t');
		} else {
			sb.append('-');
		}
		
		// P05
		sb.append(getPronounCaseChar(grammLabelUid));
		
		// P06
		sb.append(getNumberChar(grammLabelUid));
		
		// P07
		sb.append(getPersonChar(grammLabelUid));
		
		// P08
		sb.append(getGenderChar(grammLabelUid));
		
		// P09
		sb.append(getArticleChar(grammLabelUid));
		
		// P10
		// TODO: 
		
		return sb.toString();
	}
	
	private static String getNumeralTag(int grammLabelUid) {
		StringBuffer sb = new StringBuffer("M");
		
		// P02
		sb.append(getNumeralTypeChar(grammLabelUid));
		
		// P03
		sb.append(getGenderChar(grammLabelUid));
		
		// P04
		sb.append(getNumberChar(grammLabelUid));
		
		// P05
		sb.append(getArticleChar(grammLabelUid));
		
		return sb.toString();
	}
	
	private static String getVerbTag(int grammLabelUid) {
		StringBuffer sb = new StringBuffer("V");
		
		// P02
		sb.append(getVerbTypeChar(grammLabelUid));
		
		// P03
		sb.append(getAspectChar(grammLabelUid));
		
		// P04
		sb.append(getTransitivityChar(grammLabelUid));
		
		// P05
		sb.append(getVerbFormChar(grammLabelUid));
		
		// P06
		sb.append(getVoiceChar(grammLabelUid));
		
		// P07
		sb.append(getTenseChar(grammLabelUid));
		
		// P08
		sb.append(getPersonChar(grammLabelUid));
		
		// P09
		sb.append(getNumberChar(grammLabelUid));
		
		// P10
		sb.append(getGenderChar(grammLabelUid));
		
		// P11
		sb.append(getArticleChar(grammLabelUid));
		
		return stripTag(sb);
	}
	
	private static String getAdverbTag(int grammLabelUid) {
		StringBuffer sb = new StringBuffer("D");
		
		
		
		return sb.toString();
	}
	
	private static String getConjunctionTag(int grammLabelUid) {
		StringBuffer sb = new StringBuffer("C");
		
		
		
		return sb.toString();
	}
	
	private static String getInterjectionTag(int grammLabelUid) {
		return "I";
	}
	
	private static String getParticleTag(int grammLabelUid) {
		return "T";
	}
	
	private static String getPrepositionTag(int grammLabelUid) {
		return "R";
	}
	
	/**
	 * Returns the tag stripped from any trailing dashes.
	 * @param grammLabelUid
	 * @return 
	 */
	private static String stripTag(StringBuffer tag) {
		while(tag.charAt(tag.length() - 1) == '-') {
			tag.deleteCharAt(tag.length() - 1);
		}
		
		return tag.toString();
	}
	
	/**
	 * Returns the grammatical label UID of the specified BTB-TS tag,
	 * or <code>-1</code> if the specified tag is not a valid BTB-TS tag.
	 */
	public static int getGrammLabelUid(String tag) {
		return getGrammLabelUid(tag, getFakeBgGrammarType(tag));
	}
	
	/**
	 * Returns the grammatical label UID of the specified BTB-TS tag,
	 * or <code>-1</code> if the specified tag is not a valid BTB-TS tag.
	 * @param type Specifies the BG grammatical type of the word.
	 */
	public static int getGrammLabelUid(String tag, int type) {
		if(type == -1) return -1;
		type = BgGrammarType.getTypeId(type);
		
		switch(tag.charAt(0)) {
			case 'N': return type | getNounGrammLabelUid(tag);
			case 'A': return type | getAdjectiveGrammLabelUid(tag);
			case 'P': return type | getPronounGrammLabelUid(tag);
			case 'M': return type | getNumeralGrammLabelUid(tag);
			case 'V': return type | getVerbGrammLabelUid(tag);
			case 'D': return type | getAdverbGrammLabelUid(tag);
			case 'C': return type | getConjunctionGrammLabelUid(tag);
		}
		
		return -1;
	}
	
	/**
	 * Compare the specified tags ignoring fields that are not set ('-' fields)
	 * in at least one of the tags.
	 */
	public static boolean differentTags(String tag1, String tag2) {
		int size = tag1.length() < tag2.length() ? tag1.length() : tag2.length();
		
		for(int i = 0; i < size; i++) {
			if(tag1.charAt(i) == '-' || tag2.charAt(i) == '-') continue;
			if(tag1.charAt(i) != tag2.charAt(i)) return true;
		}
		
		return false;
	}
	
	/**
	 * Determines whether <code>tag</code> contains all the features specified
	 * in <code>featuresTag</code>.
	 */
	public static boolean hasFeatures(String tag, String featuresTag) {
		if(tag == null || featuresTag == null) return false;
		
		for(int i = 0; i < featuresTag.length(); i++) {
			if(featuresTag.charAt(i) == '-') continue;
			if(i >= tag.length()) return false;
			if(featuresTag.charAt(i) != tag.charAt(i)) return false;
		}
		
		return true;
	}
	
	private static int getNounGrammLabelUid(String tag) {
		int uid = 0;
		
		if(tag.length() < 2) return uid;
		uid = encodeNounType(uid, tag.charAt(1));
		
		if(tag.length() < 3) return uid;
		uid = encodeGender(uid, tag.charAt(2));
		
		if(tag.length() < 4) return uid;
		uid = encodeNumber(uid, tag.charAt(3));
		
		if(tag.length() < 5) return uid;
		uid = encodeArticle(uid, tag.charAt(4));
		
		if(tag.length() < 6) return uid;
		uid = encodeNounCase(uid, tag.charAt(5));
		
		return uid;
	}
	
	private static int getAdjectiveGrammLabelUid(String tag) {
		int uid = 0;
		
		if(tag.length() < 2) return uid;
		uid = encodeGender(uid, tag.charAt(1));
		
		if(tag.length() < 3) return uid;
		uid = encodeNumber(uid, tag.charAt(2));
		
		if(tag.length() < 4) return uid;
		uid = encodeArticle(uid, tag.charAt(3));
		
		// TODO: P05
		
		return uid;
	}
	
	private static int getPronounGrammLabelUid(String tag) {
		int uid = 0;
		
		if(tag.length() < 2) return uid;
		uid = encodePronounType(uid, tag.charAt(1));
		
		// TODO: P03
		
		if(tag.length() < 4) return uid;
		char c = tag.charAt(3);
		
		if(c == 'l') {
			uid = GrammaticalLabel.encodePronounForm(uid, PronounForm.FULL);
		} else if(c == 't') {
			uid = GrammaticalLabel.encodePronounForm(uid, PronounForm.SHORT);
		}
		
		if(tag.length() < 5) return uid;
		uid = encodePronounCase(uid, tag.charAt(4));
		
		if(tag.length() < 6) return uid;
		uid = encodeNumber(uid, tag.charAt(5));
		
		if(tag.length() < 7) return uid;
		uid = encodePerson(uid, tag.charAt(6));
		
		if(tag.length() < 8) return uid;
		uid = encodeGender(uid, tag.charAt(7));
		
		if(tag.length() < 9) return uid;
		uid = encodeArticle(uid, tag.charAt(8));
		
		// TODO: encode gender of the possessor
		//if(tag.length() < 10) return uid;
		//uid = encode(uid, tag.charAt(9));
		
		return uid;
	}
	
	private static int getNumeralGrammLabelUid(String tag) {
		int uid = 0;
		
		if(tag.length() > 1) {
			uid = encodeNumeralType(uid, tag.charAt(1));
		}
		
		if(tag.length() < 3) return uid;
		uid = encodeGender(uid, tag.charAt(2));
		
		if(tag.length() < 4) return uid;
		uid = encodeNumber(uid, tag.charAt(3));
		
		if(tag.length() < 5) return uid;
		uid = encodeArticle(uid, tag.charAt(4));
		
		return uid;
	}
	
	private static int getVerbGrammLabelUid(String tag) {
		int uid = 0;
		
		if(tag.length() > 1) {
			uid = encodeVerbType(uid, tag.charAt(1));
		}
		
		if(tag.length() < 3) return uid;
		uid = encodeAspect(uid, tag.charAt(2));
		
		if(tag.length() < 4) return uid;
		uid = encodeTransitivity(uid, tag.charAt(3));
		
		if(tag.length() < 5) return uid;
		uid = encodeVerbForm(uid, tag.charAt(4));
		
		if(tag.length() < 6) return uid;
		uid = encodeVoice(uid, tag.charAt(5));
		
		if(tag.length() < 7) return uid;
		uid = encodeTense(uid, tag.charAt(6));
		
		if(tag.length() < 8) return uid;
		uid = encodePerson(uid, tag.charAt(7));
		
		if(tag.length() < 9) return uid;
		uid = encodeNumber(uid, tag.charAt(8));
		
		if(tag.length() < 10) return uid;
		uid = encodeGender(uid, tag.charAt(9));
		
		if(tag.length() < 11) return uid;
		uid = encodeArticle(uid, tag.charAt(10));
		
		return uid;
	}
	
	private static int getAdverbGrammLabelUid(String tag) {
		int uid = 0;
		
		if(tag.length() > 1) {
			// TODO: 
		}
		
		return uid;
	}
	
	private static int getConjunctionGrammLabelUid(String tag) {
		int uid = 0;
		
		if(tag.length() > 1) {
			//uid = encodePronounType(uid, tag.charAt(1));
		}
		
		return uid;
	}
	
	public static void loadGrammarTypeResource(String file, BgDictionary dict) {
		File f = new File(file);
		
		if(!f.exists() || !f.isFile() || !f.canRead()) {
			String s = f.getAbsolutePath();
			String err = i18n.getError("BgDictionary.invalidFile", s);
			throw new IllegalArgumentException(err);
		}
		
		try {loadGrammarTypeResource(new FileInputStream(f), dict); }
		catch(Exception e) { e.printStackTrace(); }
	}
	
	public static void loadGrammarTypeResource(InputStream stream, BgDictionary dict) {
		BufferedReader reader;
		
		try {
			InputStreamReader r
				= new InputStreamReader(stream, "UTF-8");
			reader = new BufferedReader(r);
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
		
		try {
			int uid = -1;
			String line = reader.readLine();
			
			ArrayList<String> lines = new ArrayList<String>();
			
			while(line != null) {
				int i = BgGrammarType.getTypeId(line);
				if(i != -1) {
					uid = i;
					line = reader.readLine();
				}
				
				if(uid == -1) {
					throw new IllegalArgumentException("Unsupported format!");
				}
				
				while(line != null && !line.isEmpty()) {
					lines.add(line);
					line = reader.readLine();
				}
				
				readLexeme(lines, uid, dict);
				lines.clear();
				
				line = reader.readLine();
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try { reader.close(); }
			catch(Exception e) { e.printStackTrace(); }
		}
	}
	
	private static void readLexeme(ArrayList<String> lines, int uid, BgDictionary dict) throws Exception {
		if(lines.isEmpty()) return;
				
		WordEntry we = readWordEntry(lines.get(0), -1, uid);
		int lemma = we.id;
		dict.addWord(we);
			
		for(int i = 1; i < lines.size(); i++) {		
			dict.addWord(readWordEntry(lines.get(i), lemma, uid));
		}
	}
	
	private static WordEntry readWordEntry(String line, int lemma, int uid) {
		int idx = line.indexOf('\t');
		if(idx == -1) {
			throw new IllegalArgumentException("Unsupported format!");
		}
		
		String word = line.substring(0, idx);
		String tag = line.substring(idx + 1);
		int glu = getGrammLabelUid(tag);
		
		if(GrammaticalLabel.getLexicalClass(uid) != GrammaticalLabel.getLexicalClass(glu)) {
			throw new IllegalArgumentException("Lexical class mismatch in: " + word);
		}
		
		uid = BgGrammarType.encodeType(uid, glu);
		WordEntry we = new WordEntry(word, lemma, uid);
		
		return we;
	}
}
