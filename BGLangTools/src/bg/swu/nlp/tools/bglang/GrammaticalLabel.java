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

/**
 *
 */
public class GrammaticalLabel implements Comparable<GrammaticalLabel> {
	public static enum LexicalClass {
		NOUN(i18n.getLabel("LexicalClass.NOUN")),
		ADJECTIVE(i18n.getLabel("LexicalClass.ADJECTIVE")),
		PRONOUN(i18n.getLabel("LexicalClass.PRONOUN")),
		NUMERAL(i18n.getLabel("LexicalClass.NUMERAL")),
		VERB(i18n.getLabel("LexicalClass.VERB")),
		ADVERB(i18n.getLabel("LexicalClass.ADVERB")),
		CONJUNCTION(i18n.getLabel("LexicalClass.CONJUNCTION")),
		INTERJECTION(i18n.getLabel("LexicalClass.INTERJECTION")),
		PARTICLE(i18n.getLabel("LexicalClass.PARTICLE")),
		PREPOSITION(i18n.getLabel("LexicalClass.PREPOSITION"));
		
		private final String name;
                
                LexicalClass(String name) { this.name = name; }
                
		@Override
                public String
                toString() { return name; }

	}
	
	public static enum Gender {
		NONE(""),
		MASCULINE(i18n.getLabel("Gender.MASCULINE")),
		FEMININE(i18n.getLabel("Gender.FEMININE")),
		NEUTER(i18n.getLabel("Gender.NEUTER"));
		
		private final String name;
                
                Gender(String name) { this.name = name; }
                
		@Override
                public String
                toString() { return name; }
	}
	
	public static enum Person {
		NONE(""),
		FIRST(i18n.getLabel("Person.FIRST")),
		SECOND(i18n.getLabel("Person.SECOND")),
		THIRD(i18n.getLabel("Person.THIRD"));
		
		private final String name;
                
                Person(String name) { this.name = name; }
                
		@Override
                public String
                toString() { return name; }
	}
	
	public static enum Article {
		NONE(""),
		INDEFINITE(i18n.getLabel("Article.INDEFINITE")),
		DEFINITE(i18n.getLabel("Article.DEFINITE")),
		DEFINITE_FULL(i18n.getLabel("Article.DEFINITE_FULL")) /* for masculine only */;
		
		private final String name;
                
                Article(String name) { this.name = name; }
                
		@Override
                public String
                toString() { return name; }
	}
	
	public static enum Number {
		NONE(""),
		SINGULAR(i18n.getLabel("Number.SINGULAR")),
		PLURAL(i18n.getLabel("Number.PLURAL")),
		ONLY_PLURAL(i18n.getLabel("Number.ONLY_PLURAL")),
		COUNT_FORM(i18n.getLabel("Number.COUNT_FORM"));
		
		private final String name;
                
                Number(String name) { this.name = name; }
                
		@Override
                public String
                toString() { return name; }
	}
	
	public static enum NounType {
		COMMON(i18n.getLabel("NounType.COMMON")), // common noun
		PROPER(i18n.getLabel("NounType.PROPER")); // proper noun
		
		private final String name;
                
                NounType(String name) { this.name = name; }
                
		@Override
                public String
                toString() { return name; }
	}
	
	public static enum NounCase {
		NONE(i18n.getLabel("NounCase.NONE")),
		VOCATIVE(i18n.getLabel("NounCase.VOCATIVE")),   //vocative form
		ACCUSATIVE(i18n.getLabel("NounCase.ACCUSATIVE")), //archaic accusative form
		DATIVE(i18n.getLabel("NounCase.DATIVE"));      //archaic dative form
		
		private final String name;
                
                NounCase(String name) { this.name = name; }
                
		@Override
                public String
                toString() { return name; }
	}
	
	public static enum AdjectiveCase {
		NONE,
		EXTENDED,   //extended form
		ACCUSATIVE, //archaic accusative form
		DATIVE      //archaic dative form
	}
	
	public static enum PronounType {
		PERSONAL(i18n.getLabel("PronounType.PERSONAL")),
		DEMONSTRATIVE(i18n.getLabel("PronounType.DEMONSTRATIVE")),
		RELATIVE(i18n.getLabel("PronounType.RELATIVE")),
		COLLECTIVE(i18n.getLabel("PronounType.COLLECTIVE")),
		INTERROGATIVE(i18n.getLabel("PronounType.INTERROGATIVE")),
		INDEFINITE(i18n.getLabel("PronounType.INDEFINITE")),
		NEGATIVE(i18n.getLabel("PronounType.NEGATIVE")),
		POSSESSIVE(i18n.getLabel("PronounType.POSSESSIVE"));
		
		private final String name;
                
                PronounType(String name) { this.name = name; }
                
		@Override
                public String
                toString() { return name; }

	}
	
	public static enum PronounCase {
		NONE(""),
		NOMINATIVE(i18n.getLabel("PronounCase.NOMINATIVE")),   // imenitelen padej
		ACCUSATIVE(i18n.getLabel("PronounCase.ACCUSATIVE")), // vinitelen padej
		DATIVE(i18n.getLabel("PronounCase.DATIVE"));      // datelen padej
		
		private final String name;
                
                PronounCase(String name) { this.name = name; }
                
		@Override
                public String
                toString() { return name; }
	}
	
	public static enum PronounForm {
		NONE(""),
		FULL(i18n.getLabel("PronounForm.FULL")),
		SHORT(i18n.getLabel("PronounForm.SHORT")),
		OLD(i18n.getLabel("PronounForm.OLD"));
		
		private final String name;
                
                PronounForm(String name) { this.name = name; }
                
		@Override
                public String
                toString() { return name; }
	}
	
	public static enum VerbType {
		NONE(""),
		PERSONAL(i18n.getLabel("VerbType.PERSONAL")), // lichen glagol
		IMPERSONAL(i18n.getLabel("VerbType.IMPERSONAL")), // bezlichen glagol
		AUXILIARY(i18n.getLabel("VerbType.AUXILIARY")), // sym
		AUXILIARY2(i18n.getLabel("VerbType.AUXILIARY")), // byda
		AUXILIARY3(i18n.getLabel("VerbType.AUXILIARY")); // bivam
		
		private final String name;
                
                VerbType(String name) { this.name = name; }
                
		@Override
                public String
                toString() { return name; }
	}
	
	
	public static enum Aspect { // Vid na glagola
		IMPERFECTIVE(i18n.getLabel("Aspect.IMPERFECTIVE")), // nesyvyrshen vid
		PERFECTIVE(i18n.getLabel("Aspect.PERFECTIVE")); // syvyrshen vid
		
		private final String name;
                
                Aspect(String name) { this.name = name; }
                
		@Override
                public String
                toString() { return name; }

	}
	
	public static enum Transitivity {
		TRANSITIVE(i18n.getLabel("Transitivity.TRANSITIVE")), // prehoden glagol
		INTRANSITIVE(i18n.getLabel("Transitivity.INTRANSITIVE")); // neprehoden glagol
		
		private final String name;
                
                Transitivity(String name) { this.name = name; }
                
		@Override
                public String
                toString() { return name; }

	}
	
	public static enum VerbForm {
		FINITE_INDICATIVE(i18n.getLabel("VerbForm.FINITE_INDICATIVE")), // Izyavitelno (realno) naklonenie
		FINITE_IMPERATIVE(i18n.getLabel("VerbForm.FINITE_IMPERATIVE")), // Povelitelno naklonenie
		FINITE_CONDITIONAL(i18n.getLabel("VerbForm.FINITE_CONDITIONAL")), // Uslovno naklonenie
		NONFINITE_PARTICIPLE(i18n.getLabel("VerbForm.NONFINITE_PARTICIPLE")), // Prichastie
		NONFINITE_GERUND(i18n.getLabel("VerbForm.NONFINITE_GERUND")); // Otglagolno syshtestvitelno?
		
		private final String name;
                
                VerbForm(String name) { this.name = name; }
                
		@Override
                public String
                toString() { return name; }

	}
	
	public static enum Voice { // Zalog na glagola
		NONE(""),
		ACTIVE(i18n.getLabel("Voice.ACTIVE")), // deyatelen zalog
		PASSIVE(i18n.getLabel("Voice.PASSIVE")); // stradatelen zalog
		
		private final String name;
                
                Voice(String name) { this.name = name; }
                
		@Override
                public String
                toString() { return name; }

	}
	
	public static enum Tense {
		NONE(""),
		PRESENT(i18n.getLabel("Tense.PRESENT")),
		AORIST(i18n.getLabel("Tense.AORIST")), // Minalo svyrsheno vreme
		IMPERFECT(i18n.getLabel("Tense.IMPERFECT")),
		PAST(i18n.getLabel("Tense.PAST"));
		
		private final String name;
                
                Tense(String name) { this.name = name; }
                
		@Override
                public String
                toString() { return name; }

	}
	
	public static enum NumeralType {
		NONE(""),
		CARDINAL(i18n.getLabel("NumeralType.CARDINAL")),
		ORDINAL(i18n.getLabel("NumeralType.ORDINAL")),
		ADVERBIAL(i18n.getLabel("NumeralType.ADVERBIAL")),
		FUZZY(i18n.getLabel("NumeralType.FUZZY"));
		
		private final String name;
                
                NumeralType(String name) { this.name = name; }
                
		@Override
                public String
                toString() { return name; }

	}
	
	// Gender
	private final static int genderOffset = 0;
	public  final static int genderMask   = 0b11 << genderOffset;
	
	// Article
	private final static int articleOffset = 2;
	public  final static int articleMask   = 0b11 << articleOffset;
	
	// Number
	private final static int numberOffset = 4;
	public  final static int numberMask   = 0b111 << numberOffset;
	
	// Person
	private final static int personOffset = 7;
	public  final static int personMask   = 0b11 << personOffset;
	
	// Case
	private final static int caseOffset = 9;
	public  final static int caseMask   = 0b11 << caseOffset;
	
	// Noun type
	private final static int nounTypeOffset = 11;
	public  final static int nounTypeMask   = 0b1 << nounTypeOffset;
	
	// Pronoun type
	private final static int pronounTypeOffset = 11;
	public  final static int pronounTypeMask   = 0b111 << pronounTypeOffset;
	
	// Pronoun form
	private final static int pronounFormOffset = 14;
	public  final static int pronounFormMask   = 0b11 << pronounFormOffset;
	
	// Note that verb info is overlapping Case, Pronoun type and Pronoun form
	
	// Verb type
	public  final static int verbTypeOffset = 9;
	public  final static int verbTypeMask = 0b11 << verbTypeOffset;
	
	// Verb aspect
	public  final static int aspectOffset = 11;
	public  final static int aspectMask = 0b1 << aspectOffset;
	
	// Verb transitivity
	public  final static int transitivityOffset = 12;
	public  final static int transitivityMask = 0b1 << transitivityOffset;
	
	// Verb form
	public  final static int verbFormOffset = 13;
	public  final static int verbFormMask = 0b111 << verbFormOffset;
	
	// Verb voice
	public  final static int voiceOffset = 16;
	public  final static int voiceMask = 0b11 << voiceOffset;
	
	// Verb tense
	public  final static int tenseOffset = 18;
	public  final static int tenseMask = 0b111 << tenseOffset;
	
	// Numeral type
	private final static int numeralTypeOffset = 7;
	public  final static int numeralTypeMask   = 0b111 << numeralTypeOffset;
	
	/** The unique identification number of the grammatical label */
	private int uid;
	private boolean lemma = false;
	
	private String bgGrammType;
	
	public GrammaticalLabel(String bgGrammType) {
		this.bgGrammType = bgGrammType;
		initUsingBgGrammType(bgGrammType);
	}
	
	/**
	 * Returns the unique identification number of this grammatical label.
	 */
	public int getUid() { return uid; }
	
	public boolean isLemma() { return lemma; }
	
	public LexicalClass getLexicalClass() {
		return getLexicalClass(uid);
	}
	
	/**
	 * Gets the UID representing the specified grammatical type.
	 * @throws IllegalArgumentException if the specified grammatical
	 * type is invalid.
	 */
	public static int getUidByGrammarType(String type) {
		int uid = BgGrammarType.getTypeId(type);
		if(uid == -1) throw new IllegalArgumentException("Invalid BG code: " + type);
		
		int code = BgGrammarType.getCodeById(uid);
		char suffix = BgGrammarType.getSuffixById(uid);
		
		if(code >= 1 && code <= 75) { //Noun
			uid = encodeNumber(uid, Number.SINGULAR);
			uid = encodeNounType(uid, NounType.COMMON);
			
			if(code <= 40) uid = encodeGender(uid, Gender.MASCULINE);
			else if(code <= 53) uid = encodeGender(uid, Gender.FEMININE);
			else uid = encodeGender(uid, Gender.NEUTER);
			
			if(code == 74 || code == 75) {
				uid = encodeNumber(uid, Number.ONLY_PLURAL);
			}
		} else if(code >= 76 && code <= 89) { // Adjective
			uid = encodeGender(uid, Gender.MASCULINE);
			uid = encodeNumber(uid, Number.SINGULAR);
			
			if(code == 89 && suffix == 0) {
				uid = GrammaticalLabel.encodeArticle(uid, Article.DEFINITE);
			}
			
		} else if(code >= 90 && code <= 130) { // Pronoun
			
			// TODO: handle 97a type
			if(code <= 97) uid = encodePronounType(uid, PronounType.PERSONAL);
			else if(code <= 105) uid = encodePronounType(uid, PronounType.DEMONSTRATIVE);
			else if(code <= 113) uid = encodePronounType(uid, PronounType.POSSESSIVE);
			else if(code <= 117) uid = encodePronounType(uid, PronounType.INTERROGATIVE);
			else if(code <= 120) uid = encodePronounType(uid, PronounType.RELATIVE);
			else if(code <= 123) uid = encodePronounType(uid, PronounType.INDEFINITE);
			else if(code <= 126) uid = encodePronounType(uid, PronounType.NEGATIVE);
			else uid = encodePronounType(uid, PronounType.COLLECTIVE);
		} else if(code >= 131 && code <= 141) { // Numeral
			if(code <= 139) uid = encodeNumeralType(uid, NumeralType.CARDINAL);
			else  uid = encodeNumeralType(uid, NumeralType.ORDINAL);
			
			if(code == 131) {
				uid = encodeGender(uid, Gender.MASCULINE);
				uid = encodeNumber(uid, Number.SINGULAR);
			} else if(code < 137) {
				uid = encodeGender(uid, Gender.MASCULINE);
				uid = encodeNumber(uid, Number.PLURAL);
			} else if(code == 137) {
				if(suffix == 0) {
					uid = encodeGender(uid, Gender.MASCULINE);
					uid = encodeNumber(uid, Number.PLURAL);
				} else {
					// TODO: handle type 137a
				}
			} else if(code == 138) {
				uid = encodeGender(uid, Gender.FEMININE);
				uid = encodeNumber(uid, Number.SINGULAR);
			} else {
				uid = encodeGender(uid, Gender.MASCULINE);
				uid = encodeNumber(uid, Number.SINGULAR);
			}
		} else if(code >= 142 && code <= 187) { // Verb
			if(code == 160 || code == 162 || code == 166 || code == 186 || code == 187) {
				uid = BTBUtils.getGrammLabelUid("V-i-f-r1s", uid);
			} else {
				uid = BTBUtils.getGrammLabelUid("V-p-f-r1s", uid);
			}
		} else if(code == 188) { // Adverb
			
		} else if(code == 189) { // Conjunction
			
		} else if(code >= 193 && code <= 207) { // Proper noun
			uid = encodeNounType(uid, NounType.PROPER);
			uid = encodeGender(uid, Gender.MASCULINE);
			uid = encodeNumber(uid, Number.SINGULAR);
			uid = GrammaticalLabel.encodeArticle(uid, Article.INDEFINITE);
			
			if(code == 196 || code == 204 || code == 207) {
				uid = encodeGender(uid, Gender.FEMININE);
			}
		}
		
		return uid;
	}
	
	/**
	 * Determines whether the specified grammatical label is representing a noun.
	 */
	public static boolean isNoun(int grammLabelUid) {
		return isCommonNoun(grammLabelUid) || isProperNoun(grammLabelUid);
	}
	
	/**
	 * Determines whether the specified grammatical label is representing a noun.
	 */
	public static boolean isCommonNoun(int grammLabelUid) {
		int code = BgGrammarType.getCodeById(grammLabelUid);
		if(code >= 1 && code <= 75) return true;
		return false;
	}
	
	/**
	 * Determines whether the specified grammatical label is representing a noun.
	 */
	public static boolean isProperNoun(int grammLabelUid) {
		int code = BgGrammarType.getCodeById(grammLabelUid);
		if(code >= 193 && code <= 207) return true;
		return false;
	}
	
	/**
	 * Determines whether the specified grammatical label is representing an adjective.
	 */
	public static boolean isAdjective(int grammLabelUid) {
		int code = BgGrammarType.getCodeById(grammLabelUid);
		if(code >= 76 && code <= 89) return true;
		return false;
	}
	
	/**
	 * Determines whether the specified grammatical label is representing a pronoun.
	 */
	public static boolean isPronoun(int grammLabelUid) {
		int code = BgGrammarType.getCodeById(grammLabelUid);
		if(code >= 90 && code <= 130) return true;
		return false;
	}
	
	/**
	 * Determines whether the specified grammatical label is representing a numeral.
	 */
	public static boolean isNumeral(int grammLabelUid) {
		int code = BgGrammarType.getCodeById(grammLabelUid);
		if(code >= 131 && code <= 141) return true;
		return false;
	}
	
	/**
	 * Determines whether the specified grammatical label is representing a verb.
	 */
	public static boolean isVerb(int grammLabelUid) {
		int code = BgGrammarType.getCodeById(grammLabelUid);
		if(code >= 142 && code <= 187) return true;
		return false;
	}
	
	/**
	 * Determines whether the specified grammatical label is representing an adverb.
	 */
	public static boolean isAdverb(int grammLabelUid) {
		int code = BgGrammarType.getCodeById(grammLabelUid);
		return code == 188;
	}
	
	/**
	 * Determines whether the specified grammatical label is representing a conjunction.
	 */
	public static boolean isConjunction(int grammLabelUid) {
		int code = BgGrammarType.getCodeById(grammLabelUid);
		return code == 189;
	}
	
	/**
	 * Determines whether the specified grammatical label is representing an interjection.
	 */
	public static boolean isInterjection(int grammLabelUid) {
		int code = BgGrammarType.getCodeById(grammLabelUid);
		return code == 190;
	}
	
	/**
	 * Determines whether the specified grammatical label is representing a particle.
	 */
	public static boolean isParticle(int grammLabelUid) {
		int code = BgGrammarType.getCodeById(grammLabelUid);
		return code == 191;
	}
	
	/**
	 * Determines whether the specified grammatical label is representing a preposition.
	 */
	public static boolean isPreposition(int grammLabelUid) {
		int code = BgGrammarType.getCodeById(grammLabelUid);
		return code == 192;
	}
	
	/**
	 * Returns the lexical class of the grammatical label with UID <code>grammLabelUid</code>.
	 * The lexical class is retrieved from the grammatical type encoded in the UID number.
	 */
	public static LexicalClass getLexicalClass(int grammLabelUid) {
		if(isNoun(grammLabelUid)) return LexicalClass.NOUN;
		if(isAdjective(grammLabelUid)) return LexicalClass.ADJECTIVE;
		if(isPronoun(grammLabelUid)) return LexicalClass.PRONOUN;
		if(isNumeral(grammLabelUid)) return LexicalClass.NUMERAL;
		if(isVerb(grammLabelUid)) return LexicalClass.VERB;
		if(isAdverb(grammLabelUid)) return LexicalClass.ADVERB;
		if(isConjunction(grammLabelUid)) return LexicalClass.CONJUNCTION;
		if(isInterjection(grammLabelUid)) return LexicalClass.INTERJECTION;
		if(isParticle(grammLabelUid)) return LexicalClass.PARTICLE;
		if(isPreposition(grammLabelUid)) return LexicalClass.PREPOSITION;
		
		String s = i18n.getError("GrammaticalLabel.unknownLexicalClass");
		throw new IllegalArgumentException(s);
	}
	
	/**
	 * Encodes the lexical class information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * lexical class information should be encoded.
	 * @param lexicalClass Specifies the lexical class to be encoded in
	 * <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	/*public static int encodeLexicalClass(int grammLabelUid, LexicalClass lexicalClass) {
		// remove any old lexical class info
		grammLabelUid &= (~lexicalClassMask);
		
		switch(lexicalClass) {
			case NOUN:        grammLabelUid |=  1 << lexicalClassOffset; 
				break;
			case ADJECTIVE:   grammLabelUid |=  2 << lexicalClassOffset; 
				break;
			case PRONOUN:     grammLabelUid |=  3 << lexicalClassOffset; 
				break;
			case NUMERAL:     grammLabelUid |=  4 << lexicalClassOffset; 
				break;
			case VERB:        grammLabelUid |=  5 << lexicalClassOffset; 
				break;
			case ADVERB:      grammLabelUid |=  6 << lexicalClassOffset; 
				break;
			case CONJUNCTION: grammLabelUid |=  7 << lexicalClassOffset;
		}
		
		return grammLabelUid;
	}*/
	
	/**
	 * Returns the noun case of the grammatical label with UID <code>grammLabelUid</code>.
	 * The noun case is retrieved from the information encoded in the UID number.
	 */
	public static NounCase getNounCase(int grammLabelUid) {
		int c = (grammLabelUid & caseMask) >> caseOffset;
		
		switch(c) {
			case 0: return NounCase.NONE;
			case 1: return NounCase.VOCATIVE;
			case 2: return NounCase.ACCUSATIVE;
			case 3: return NounCase.DATIVE;
		}
		
		String s = i18n.getError("GrammaticalLabel.unknownCase");
		throw new IllegalArgumentException(s);
	}
	
	/**
	 * Encodes the noun case information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * noun case information should be encoded.
	 * @param nounCase Specifies the noun case to be encoded in
	 * <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeNounCase(int grammLabelUid, NounCase nounCase) {
		// remove any old noun case info
		grammLabelUid &= (~caseMask);
		
		switch(nounCase) {
			case VOCATIVE:   grammLabelUid |=  1 << caseOffset; 
				break;
			case ACCUSATIVE: grammLabelUid |=  2 << caseOffset; 
				break;
			case DATIVE:     grammLabelUid |=  3 << caseOffset;
		}
		
		return grammLabelUid;
	}
	
	/**
	 * Returns the noun type of the grammatical label with UID <code>grammLabelUid</code>.
	 * The noun type is retrieved from the information encoded in the UID number.
	 */
	public static NounType getNounType(int grammLabelUid) {
		int c = (grammLabelUid & nounTypeMask) >> nounTypeOffset;
		
		switch(c) {
			case 0: return NounType.COMMON;
			case 1: return NounType.PROPER;
		}
		
		String s = i18n.getError("GrammaticalLabel.unknownNounType");
		throw new IllegalArgumentException(s);
	}
	
	/**
	 * Encodes the noun type information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * noun type information should be encoded.
	 * @param nounType Specifies the noun type to be encoded in
	 * <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeNounType(int grammLabelUid, NounType nounType) {
		// remove any old noun type info
		grammLabelUid &= (~nounTypeMask);
		if(nounType == null) return grammLabelUid; // FIXME: presume common noun?
		
		switch(nounType) {
			case PROPER: grammLabelUid |=  1 << nounTypeOffset; 
				break;
		}
		
		return grammLabelUid;
	}
	
	/**
	 * Returns the adjective case of the grammatical label with UID <code>grammLabelUid</code>.
	 * The adjective case is retrieved from the information encoded in the UID number.
	 */
	public static AdjectiveCase getAdjectiveCase(int grammLabelUid) {
		int c = (grammLabelUid & caseMask) >> caseOffset;
		
		switch(c) {
			case 0: return AdjectiveCase.NONE;
			case 1: return AdjectiveCase.EXTENDED;
			case 2: return AdjectiveCase.ACCUSATIVE;
			case 3: return AdjectiveCase.DATIVE;
		}
		
		String s = i18n.getError("GrammaticalLabel.unknownCase");
		throw new IllegalArgumentException(s);
	}
	
	/**
	 * Encodes the adjective case information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * adjective case information should be encoded.
	 * @param adjCase Specifies the adjective case to be encoded in
	 * <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeAdjectiveCase(int grammLabelUid, AdjectiveCase adjCase) {
		// remove any old adjective case info
		grammLabelUid &= (~caseMask);
		
		switch(adjCase) {
			case EXTENDED:   grammLabelUid |=  1 << caseOffset; 
				break;
			case ACCUSATIVE: grammLabelUid |=  2 << caseOffset; 
				break;
			case DATIVE:     grammLabelUid |=  3 << caseOffset;
		}
		
		return grammLabelUid;
	}
	
	/**
	 * Returns the pronoun type of the grammatical label with UID <code>grammLabelUid</code>.
	 * The pronoun type is retrieved from the information encoded in the UID number.
	 */
	public static PronounType getPronounType(int grammLabelUid) {
		int lc = (grammLabelUid & pronounTypeMask) >> pronounTypeOffset;
		
		switch(lc) {
			case 0: return PronounType.PERSONAL;
			case 1: return PronounType.DEMONSTRATIVE;
			case 2: return PronounType.RELATIVE;
			case 3: return PronounType.COLLECTIVE;
			case 4: return PronounType.INTERROGATIVE;
			case 5: return PronounType.INDEFINITE;
			case 6: return PronounType.NEGATIVE;
			case 7: return PronounType.POSSESSIVE;
		}
		
		String s = i18n.getError("GrammaticalLabel.unknownPronounType");
		throw new IllegalArgumentException(s);
	}
	
	/**
	 * Encodes the pronoun type information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * pronoun type information should be encoded.
	 * @param pronounType Specifies the pronoun type to be encoded in
	 * <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodePronounType(int grammLabelUid, PronounType pronounType) {
		// remove any old pronoun type info
		grammLabelUid &= (~pronounTypeMask);
		
		switch(pronounType) {
			case PERSONAL:      grammLabelUid |=  0 << pronounTypeOffset; 
				break;
			case DEMONSTRATIVE: grammLabelUid |=  1 << pronounTypeOffset; 
				break;
			case RELATIVE:      grammLabelUid |=  2 << pronounTypeOffset; 
				break;
			case COLLECTIVE:    grammLabelUid |=  3 << pronounTypeOffset; 
				break;
			case INTERROGATIVE: grammLabelUid |=  4 << pronounTypeOffset; 
				break;
			case INDEFINITE:    grammLabelUid |=  5 << pronounTypeOffset; 
				break;
			case NEGATIVE:      grammLabelUid |=  6 << pronounTypeOffset; 
				break;
			case POSSESSIVE:    grammLabelUid |=  7 << pronounTypeOffset;
		}
		
		return grammLabelUid;
	}
	
	/**
	 * Returns the pronoun case of the grammatical label with UID <code>grammLabelUid</code>.
	 * The pronoun case is retrieved from the information encoded in the UID number.
	 */
	public static PronounCase getPronounCase(int grammLabelUid) {
		int c = (grammLabelUid & caseMask) >> caseOffset;
		
		switch(c) {
			case 0: return PronounCase.NONE;
			case 1: return PronounCase.NOMINATIVE;
			case 2: return PronounCase.ACCUSATIVE;
			case 3: return PronounCase.DATIVE;
		}
		
		String s = i18n.getError("GrammaticalLabel.unknownCase");
		throw new IllegalArgumentException(s);
	}
	
	/**
	 * Encodes the pronoun case information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * pronoun case information should be encoded.
	 * @param pnCase Specifies the pronoun case to be encoded in
	 * <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodePronounCase(int grammLabelUid, PronounCase pnCase) {
		// remove any old pronoun case info
		grammLabelUid &= (~caseMask);
		
		switch(pnCase) {
			case NOMINATIVE:   grammLabelUid |=  1 << caseOffset; 
				break;
			case ACCUSATIVE: grammLabelUid |=  2 << caseOffset; 
				break;
			case DATIVE:     grammLabelUid |=  3 << caseOffset;
		}
		
		return grammLabelUid;
	}
	
	/**
	 * Returns the pronoun form of the grammatical label with UID <code>grammLabelUid</code>.
	 * The pronoun form is retrieved from the information encoded in the UID number.
	 */
	public static PronounForm getPronounForm(int grammLabelUid) {
		int c = (grammLabelUid & pronounFormMask) >> pronounFormOffset;
		
		switch(c) {
			case 0: return PronounForm.NONE;
			case 1: return PronounForm.FULL;
			case 2: return PronounForm.SHORT;
			case 3: return PronounForm.OLD;
		}
		
		String s = i18n.getError("GrammaticalLabel.unknownPronounForm");
		throw new IllegalArgumentException(s);
	}
	
	/**
	 * Encodes the pronoun form information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * pronoun form information should be encoded.
	 * @param form Specifies the pronoun form to be encoded in
	 * <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodePronounForm(int grammLabelUid, PronounForm form) {
		// remove any old pronoun form info
		grammLabelUid &= (~pronounFormMask);
		
		switch(form) {
			case FULL:  grammLabelUid |=  1 << pronounFormOffset; 
				break;
			case SHORT: grammLabelUid |=  2 << pronounFormOffset;
				break;
			case OLD: grammLabelUid   |=  3 << pronounFormOffset;
		}
		
		return grammLabelUid;
	}
	
	/**
	 * Returns the verb type of the grammatical label with UID <code>grammLabelUid</code>.
	 * The verb type is retrieved from the information encoded in the UID number.
	 * Note that the auxiliary type also depends on the grammatical type.
	 */
	public static VerbType getVerbType(int grammLabelUid) {
		int c = (grammLabelUid & verbTypeMask) >> verbTypeOffset;
		if(c == 2) {
			int t = BgGrammarType.getCodeById(grammLabelUid);
			if(t == 142) return VerbType.AUXILIARY;
			if(t == 143) return VerbType.AUXILIARY2;
			if(t == 186) return VerbType.AUXILIARY3;
			return VerbType.AUXILIARY; // TODO: exception?
		}
		
		switch(c) {
			case 0: return VerbType.PERSONAL;
			case 1: return VerbType.IMPERSONAL;
			case 3: return VerbType.NONE;
		}
		
		String s = i18n.getError("GrammaticalLabel.unknownVerbType");
		throw new IllegalArgumentException(s);
	}
	
	/**
	 * Encodes the verb type information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * verb type information should be encoded.
	 * @param type Specifies the verb type to be encoded in
	 * <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeVerbType(int grammLabelUid, VerbType type) {
		// remove any old verb type info
		grammLabelUid &= (~verbTypeMask);
		
		switch(type) {
			case IMPERSONAL: grammLabelUid |=  1 << verbTypeOffset; 
				break;
			case AUXILIARY:
			case AUXILIARY2:
			case AUXILIARY3:  grammLabelUid |=  2 << verbTypeOffset;
				break;
			case NONE:  grammLabelUid |=  3 << verbTypeOffset;
				break;
		}
		
		return grammLabelUid;
	}
	
	/**
	 * Returns the verb aspect of the grammatical label with UID <code>grammLabelUid</code>.
	 * The aspect is retrieved from the information encoded in the UID number.
	 */
	public static Aspect getAspect(int grammLabelUid) {
		int c = (grammLabelUid & aspectMask) >> aspectOffset;
		
		switch(c) {
			case 0: return Aspect.IMPERFECTIVE;
			case 1: return Aspect.PERFECTIVE;
		}
		
		String s = i18n.getError("GrammaticalLabel.unknownAspect");
		throw new IllegalArgumentException(s);
	}
	
	/**
	 * Encodes the verb aspect information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * aspect information should be encoded.
	 * @param aspect Specifies the aspect to be encoded in
	 * <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeAspect(int grammLabelUid, Aspect aspect) {
		// remove any old aspect info
		grammLabelUid &= (~aspectMask);
		
		if(aspect == null) return grammLabelUid; // TODO: implement NONE
		
		switch(aspect) {
			case PERFECTIVE:   grammLabelUid |=  1 << aspectOffset;
		}
		
		return grammLabelUid;
	}
	
	/**
	 * Returns the transitivity of the grammatical label with UID <code>grammLabelUid</code>.
	 * The transitivity is retrieved from the information encoded in the UID number.
	 */
	public static Transitivity getTransitivity(int grammLabelUid) {
		int c = (grammLabelUid & transitivityMask) >> transitivityOffset;
		
		switch(c) {
			case 0: return Transitivity.TRANSITIVE;
			case 1: return Transitivity.INTRANSITIVE;
		}
		
		String s = i18n.getError("GrammaticalLabel.unknownTransitivity");
		throw new IllegalArgumentException(s);
	}
	
	/**
	 * Encodes the transitivity information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * transitivity information should be encoded.
	 * @param t Specifies the transitivity to be encoded in
	 * <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeTransitivity(int grammLabelUid, Transitivity t) {
		// remove any old transitivity info
		grammLabelUid &= (~transitivityMask);
		
		if(t == null) return grammLabelUid; // TODO: implement NONE !!!
		
		switch(t) {
			case INTRANSITIVE: grammLabelUid |=  1 << transitivityOffset;
		}
		
		return grammLabelUid;
	}
	
	/**
	 * Returns the verb form of the grammatical label with UID <code>grammLabelUid</code>.
	 * The verb form is retrieved from the information encoded in the UID number.
	 */
	public static VerbForm getVerbForm(int grammLabelUid) {
		int c = (grammLabelUid & verbFormMask) >> verbFormOffset;
		
		switch(c) {
			case 0: return VerbForm.FINITE_INDICATIVE;
			case 1: return VerbForm.FINITE_IMPERATIVE;
			case 2: return VerbForm.FINITE_CONDITIONAL;
			case 3: return VerbForm.NONFINITE_PARTICIPLE;
			case 4: return VerbForm.NONFINITE_GERUND;
		}
		
		String s = i18n.getError("GrammaticalLabel.unknownVerbForm");
		throw new IllegalArgumentException(s);
	}
	
	/**
	 * Encodes the verb form information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * verb form information should be encoded.
	 * @param form Specifies the verb form to be encoded in
	 * <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeVerbForm(int grammLabelUid, VerbForm form) {
		// remove any old verb form info
		grammLabelUid &= (~verbFormMask);
		
		switch(form) {
			case FINITE_IMPERATIVE:    grammLabelUid |=  1 << verbFormOffset; 
				break;
			case FINITE_CONDITIONAL:   grammLabelUid |=  2 << verbFormOffset;
				break;
			case NONFINITE_PARTICIPLE: grammLabelUid |=  3 << verbFormOffset; 
				break;
			case NONFINITE_GERUND:     grammLabelUid |=  4 << verbFormOffset;
		}
		
		return grammLabelUid;
	}
	
	/**
	 * Returns the verb voice of the grammatical label with UID <code>grammLabelUid</code>.
	 * The voice is retrieved from the information encoded in the UID number.
	 */
	public static Voice getVoice(int grammLabelUid) {
		int c = (grammLabelUid & voiceMask) >> voiceOffset;
		
		switch(c) {
			case 0: return Voice.NONE;
			case 1: return Voice.ACTIVE;
			case 2: return Voice.PASSIVE;
		}
		
		String s = i18n.getError("GrammaticalLabel.unknownVoice");
		throw new IllegalArgumentException(s);
	}
	
	/**
	 * Encodes the verb voice information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * voice information should be encoded.
	 * @param voice Specifies the voice to be encoded in
	 * <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeVoice(int grammLabelUid, Voice voice) {
		// remove any old voice form info
		grammLabelUid &= (~voiceMask);
		
		switch(voice) {
			case ACTIVE:  grammLabelUid |=  1 << voiceOffset; 
				break;
			case PASSIVE: grammLabelUid |=  2 << voiceOffset;
				break;
		}
		
		return grammLabelUid;
	}
	
	/**
	 * Returns the tense of the grammatical label with UID <code>grammLabelUid</code>.
	 * The tense is retrieved from the information encoded in the UID number.
	 */
	public static Tense getTense(int grammLabelUid) {
		int c = (grammLabelUid & tenseMask) >> tenseOffset;
		
		switch(c) {
			case 0: return Tense.NONE;
			case 1: return Tense.PRESENT;
			case 2: return Tense.AORIST;
			case 3: return Tense.IMPERFECT;
			case 4: return Tense.PAST;
		}
		
		String s = i18n.getError("GrammaticalLabel.unknownTense");
		throw new IllegalArgumentException(s);
	}
	
	/**
	 * Encodes the tense information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * tense information should be encoded.
	 * @param tense Specifies the tense to be encoded in
	 * <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeTense(int grammLabelUid, Tense tense) {
		// remove any old tense info
		grammLabelUid &= (~tenseMask);
		
		switch(tense) {
			case PRESENT:   grammLabelUid |=  1 << tenseOffset; 
				break;
			case AORIST:    grammLabelUid |=  2 << tenseOffset; 
				break;
			case IMPERFECT: grammLabelUid |=  3 << tenseOffset;
				break;
			case PAST:      grammLabelUid |=  4 << tenseOffset;
				break; 
		}
		
		return grammLabelUid;
	}
	
	/**
	 * Returns the person of the grammatical label with UID <code>grammLabelUid</code>.
	 * The person is retrieved from the information encoded in the UID number.
	 */
	public static Person getPerson(int grammLabelUid) {
		int c = (grammLabelUid & personMask) >> personOffset;
		
		switch(c) {
			case 0: return Person.NONE;
			case 1: return Person.FIRST;
			case 2: return Person.SECOND;
			case 3: return Person.THIRD;
		}
		
		String s = i18n.getError("GrammaticalLabel.unknownPerson");
		throw new IllegalArgumentException(s);
	}
	
	/**
	 * Encodes the person information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * person information should be encoded.
	 * @param person Specifies the person to be encoded in
	 * <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodePerson(int grammLabelUid, Person person) {
		// remove any old person info
		grammLabelUid &= (~personMask);
		
		switch(person) {
			case FIRST:  grammLabelUid |=  1 << personOffset; 
				break;
			case SECOND: grammLabelUid |=  2 << personOffset;
				break;
			case THIRD:  grammLabelUid   |=  3 << personOffset;
		}
		
		return grammLabelUid;
	}
	
	/**
	 * Returns the gender of the grammatical label with UID <code>grammLabelUid</code>.
	 * The gender is retrieved from the information encoded in the UID number.
	 */
	public static Gender getGender(int grammLabelUid) {
		int c = (grammLabelUid & genderMask) >> genderOffset;
		
		switch(c) {
			case 0: return Gender.NONE;
			case 1: return Gender.MASCULINE;
			case 2: return Gender.FEMININE;
			case 3: return Gender.NEUTER;
		}
		
		return null;
	}
	
	/**
	 * Encodes the gender information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * gender information should be encoded.
	 * @param gender Specifies the gender to be encoded in <code>grammLabelUid</code>.
	 */
	public static int encodeGender(int grammLabelUid, Gender gender) {
		// remove any old gender info
		grammLabelUid &= (~genderMask);
		
		switch(gender) {
			case MASCULINE: grammLabelUid |=  1 << genderOffset; 
				break;
			case FEMININE:  grammLabelUid |=  2 << genderOffset;
				break;
			case NEUTER:    grammLabelUid |=  3 << genderOffset;
		}
		
		return grammLabelUid;
	}
	
	/**
	 * Returns the definiteness of the grammatical label with UID <code>grammLabelUid</code>.
	 * The definiteness is retrieved from the information encoded in the UID number.
	 */
	public static Article getArticle(int grammLabelUid) {
		int a = (grammLabelUid & articleMask) >> articleOffset;
		
		switch(a) {
			case 0: return Article.NONE;
			case 1: return Article.INDEFINITE;
			case 2: return Article.DEFINITE;
			case 3: return Article.DEFINITE_FULL;
		}
		
		return null;
	}
	
	/**
	 * Encodes the definiteness (grammar) information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * article information should be encoded.
	 * @param article Specifies the article to be encoded in <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeArticle(int grammLabelUid, Article article) {
		// remove any old article info
		grammLabelUid &= (~articleMask);
		
		switch(article) {
			case INDEFINITE:    grammLabelUid |=  1 << articleOffset; 
				break;
			case DEFINITE:      grammLabelUid |=  2 << articleOffset;
				break;
			case DEFINITE_FULL: grammLabelUid |=  3 << articleOffset;
		}
		
		return grammLabelUid;
	}
	
	/**
	 * Returns the (grammar) number of the grammatical label with UID
	 * <code>grammLabelUid</code>.
	 * The grammatical number is retrieved from the information encoded in
	 * the UID number.
	 */
	public static Number getNumber(int grammLabelUid) {
		int n = (grammLabelUid & numberMask) >> numberOffset;
		
		switch(n) {
			case 0: return Number.NONE;
			case 1: return Number.SINGULAR;
			case 2: return Number.PLURAL;
			case 3: return Number.COUNT_FORM;
			case 4: return Number.ONLY_PLURAL;
		}
		
		return null;
	}
	
	/**
	 * Encodes the (grammar) number information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * number information should be encoded.
	 * @param number Specifies the grammatical number to be encoded in
	 * <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeNumber(int grammLabelUid, Number number) {
		// remove any old number info
		grammLabelUid &= (~numberMask);
		
		switch(number) {
			case NONE:        grammLabelUid |=  0 << numberOffset; 
				break;
			case SINGULAR:    grammLabelUid |=  1 << numberOffset; 
				break;
			case PLURAL:      grammLabelUid |=  2 << numberOffset; 
				break;
			case COUNT_FORM:  grammLabelUid |=  3 << numberOffset; 
				break;
			case ONLY_PLURAL: grammLabelUid |=  4 << numberOffset; 
		}
		
		return grammLabelUid;
	}
	
	/**
	 * Returns the numeral type of the grammatical label with UID <code>grammLabelUid</code>.
	 * The numeral type is retrieved from the information encoded in the UID number.
	 */
	public static NumeralType getNumeralType(int grammLabelUid) {
		int lc = (grammLabelUid & numeralTypeMask) >> numeralTypeOffset;
		
		switch(lc) {
			case 0: return NumeralType.NONE;
			case 1: return NumeralType.CARDINAL;
			case 2: return NumeralType.ORDINAL;
			case 3: return NumeralType.ADVERBIAL;
			case 4: return NumeralType.FUZZY;
		}
		
		String s = i18n.getError("GrammaticalLabel.unknownNumeralType");
		throw new IllegalArgumentException(s);
	}
	
	/**
	 * Encodes the numeral type information in the grammatical label UID
	 * and returns the newly created UID.
	 * @param grammLabelUid The grammatical label UID in which the
	 * numeral type information should be encoded.
	 * @param numeralType Specifies the numeral type to be encoded in
	 * <code>grammLabelUid</code>.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeNumeralType(int grammLabelUid, NumeralType numeralType) {
		// remove any old numeral type info
		grammLabelUid &= (~numeralTypeMask);
		
		switch(numeralType) {
			case NONE:      grammLabelUid |=  0 << numeralTypeOffset; 
				break;
			case CARDINAL:  grammLabelUid |=  1 << numeralTypeOffset; 
				break;
			case ORDINAL:   grammLabelUid |=  2 << numeralTypeOffset; 
				break;
			case ADVERBIAL: grammLabelUid |=  3 << numeralTypeOffset; 
				break;
			case FUZZY:     grammLabelUid |=  4 << numeralTypeOffset; 
		}
		
		return grammLabelUid;
	}
	
	/**
	 * Initializes this grammatical label using the specified grammatical type.
	 * @throws IllegalArgumentException if the specified grammatical
	 * type is invalid.
	 */
	public void initUsingBgGrammType(String type) {
		lemma = true;
		
		uid = getUidByGrammarType(type);
		
		int code = BgGrammarType.getCodeById(uid);
		char suffix = BgGrammarType.getSuffixById(uid);
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null && !(obj instanceof GrammaticalLabel)) return false;
		return getUid() == ((GrammaticalLabel)obj).getUid();
	}
	
	@Override
	public int compareTo(GrammaticalLabel l) {
		return getUid() - l.getUid();
	}
}
