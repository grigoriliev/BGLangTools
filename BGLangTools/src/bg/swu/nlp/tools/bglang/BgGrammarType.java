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
public class BgGrammarType {
	// used to numerically represent the BG grammatical types that ends with a and b
	private static final int bgSuffixOffset = 21;
	private static final int bgSuffixMask   = 0b11 << bgSuffixOffset;
	private static final int bgSuffixA      = 0b01 << bgSuffixOffset;
	private static final int bgSuffixB      = 0b10 << bgSuffixOffset;
	private static final int bgSuffixC      = 0b11 << bgSuffixOffset; // for fake types
	
	/**
	 * The offset of the numerical value of the BG grammatical type.
	 * The numerical value of the BG grammatical type is located for bit 23
	 * to bit 31 inclusive (zero-based).
	 */
	private static final int numBgTypeOffset = 23;
	
	/**
	 * Mask for the numerical value of the BG grammatical type.
	 * Used to strip the part of the type ID representing the
	 * <b>a</b> and <b>b</b> suffixes.
	 */
	private static final int numBgTypeMask = 0b11111111 << numBgTypeOffset;
	
	private int  code;
	private char suffix;
	
	
	public static int getFirstNounTypeId() {
		return (1 << numBgTypeOffset);
	}
	
	public static int getLastNounTypeId() {
		return (75 << numBgTypeOffset) | bgSuffixC;
	}
	
	public static int getFirstAdjectiveTypeId() {
		return (76 << numBgTypeOffset);
	}
	
	public static int getLastAdjectiveTypeId() {
		return (89 << numBgTypeOffset) | bgSuffixC;
	}
	
	public static int getFirstPronounTypeId() {
		return (90 << numBgTypeOffset);
	}
	
	public static int getLastPronounTypeId() {
		return (130 << numBgTypeOffset) | bgSuffixC;
	}
	
	public static int getFirstNumeralTypeId() {
		return (131 << numBgTypeOffset);
	}
	
	public static int getLastNumeralTypeId() {
		return (141 << numBgTypeOffset) | bgSuffixC;
	}
	
	public static int getFirstVerbTypeId() {
		return (142 << numBgTypeOffset);
	}
	
	public static int getLastVerbTypeId() {
		return (187 << numBgTypeOffset) | bgSuffixC;
	}
	
	public static int getFirstAdverbTypeId() {
		return (188 << numBgTypeOffset);
	}
	
	public static int getLastAdverbTypeId() {
		return (188 << numBgTypeOffset) | bgSuffixC;
	}
	
	public static int getFirstConjunctionTypeId() {
		return (189 << numBgTypeOffset);
	}
	
	public static int getLastConjunctionTypeId() {
		return (189 << numBgTypeOffset) | bgSuffixC;
	}
	
	/**
	 * Returns a numerical representation of the specified BG grammatical
	 * type, or <code>-1</code> if the specified string is not a valid 
	 * BG grammatical type. The BG grammatical type is encoded in the
	 * integer from bit 21 to bit 31 inclusive (zero-based).
	 * @param type A string representation of the BG grammatical type
	 */
	public static int getTypeId(String type) {
		if(type == null || type.length() == 0) return -1;
		
		char c = type.charAt(type.length() - 1);
		boolean hasSuffix = (c >= 'a' && c <= 'c') ;
		String strCode = hasSuffix ? type.substring(0, type.length() - 1) : type;
		int grammCode;
		try { grammCode = Integer.parseInt(strCode); }
		catch(NumberFormatException e) { return -1; }
		
		if(grammCode < 1 || grammCode > 208) return -1;
		
		int t;
		
		if(c == 'a') t = bgSuffixA | (grammCode << numBgTypeOffset);
		else if(c == 'b') t = bgSuffixB | (grammCode << numBgTypeOffset);
		else if(c == 'c') t = bgSuffixC | (grammCode << numBgTypeOffset);
		else t = grammCode << numBgTypeOffset;
		
		return t;
	}
	
	/**
	 * Encodes the specified grammatical type in the specified
	 * grammatical label.
	 * @param typeId BG grammatical type ID, or grammatical label UID.
	 * @return The new value of the <code>grammLabelUid</code>.
	 */
	public static int encodeType(int typeId, int grammLabelUid) {
		// remove any old grammatical type
		grammLabelUid &= (~(numBgTypeMask | bgSuffixMask));
		
		// remove any additional grammatical label UID info
		typeId &= (numBgTypeMask | bgSuffixMask);
		
		 return typeId | grammLabelUid;
	}
	
	/**
	 * Strips the specified label from any additional grammatical info
	 * and returns only the BG grammatical type, which is encoded in the
	 * integer from bit 21 to bit 31 inclusive (zero-based).
	 * @param grammLabelUid
	 */
	public static int getTypeId(int grammLabelUid) {
		return grammLabelUid & (numBgTypeMask | bgSuffixMask);
	}
	
	/**
	 * Returns the suffix of the BG grammatical type, or zero if
	 * the specified grammatical type ID does not hav a suffix.
	 * @param typeId BG grammatical type ID, or grammatical label UID.
	 */
	public static char getSuffixById(int typeId) {
		int suf = typeId & bgSuffixMask;
		
		if(suf == bgSuffixA) return 'a';
		if(suf == bgSuffixB) return 'b';
		if(suf == bgSuffixC) return 'c';
		return 0;
	}
	
	/**
	 * Returns the human readable numerical value of the BG grammatical type
	 * (with stripped suffix info).
	 * @param typeId BG grammatical type ID, or grammatical label UID.
	 */
	public static int getCodeById(int typeId) {
		return (numBgTypeMask & typeId) >> numBgTypeOffset;
	}
	
	/**
	 * Returns the string representation of the BG grammatical type
	 * with the specified ID.
	 * @param typeId BG grammatical type ID, or grammatical label UID.
	 */
	public static String getTypeById(int typeId) {
		String s = String.valueOf(getCodeById(typeId));
		char c = getSuffixById(typeId);
		if(c != 0) s += c;
		return s;
	}
	
	/**
	 * Returns the next BG grammatical type ID, or <code>-1</code> if the
	 * specified type ID is the last one.
	 * @param typeId BG grammatical type ID, or grammatical label UID.
	 */
	public static int getNextTypeId(int typeId) {
		// remove any additional grammatical label UID info
		typeId &= (numBgTypeMask | bgSuffixMask);
		if(typeId >= (numBgTypeMask | bgSuffixMask)) return -1;
		return typeId + (1 << bgSuffixOffset);
	}
	
	public static int getLastCode() {
		return 255;
	}
	
	/**
	 * 
	 * @param type A string representation of the BG grammatical type.
	 */
	public BgGrammarType(String type) {
		 int t = getTypeId(type);
		 code = getCodeById(t);
		 suffix = getSuffixById(t);
	}
	
	public char getSuffix(int typeId) {
		return suffix;
	}
}
