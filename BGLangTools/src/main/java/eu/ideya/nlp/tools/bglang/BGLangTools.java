/*
 *   BGLangTools - language tools for processing Bulgarian text
 *
 *   Copyright (C) 2014-2024 Grigor Iliev <grigor.iliev@swu.bg>
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

package eu.ideya.nlp.tools.bglang;

import eu.ideya.lingua.bg.core.BTBUtils;
import eu.ideya.lingua.bg.core.BgDictionary;
import eu.ideya.lingua.bg.core.BgGrammarType;
import eu.ideya.lingua.bg.core.WordEntry;

import java.util.ArrayList;


/**
 *
 */
public class BGLangTools {
	public final static String version = "0.2";
	
    /**
     * @param args the command line arguments
     */
	public static void main(String[] args) {
		
	}
	
	private static void printDuplicates(BgDictionary dict) {
		for(WordEntry we : dict.lemmas()) {
			int uid = BgGrammarType.getTypeId(we.grammLabelUid);
			
			ArrayList<WordEntry> entries = dict.findExactMatches(we.word);
			for(WordEntry we2 : entries) {
				//if(!we2.isLemma()) continue;
				if(uid != BgGrammarType.getTypeId(we2.grammLabelUid)) continue;
				if(we.id == we2.id) continue;
				String tag = BTBUtils.getTag(we.grammLabelUid);
				String tag2 = BTBUtils.getTag(we2.grammLabelUid);
				if(BTBUtils.differentTags(tag, tag2)) continue;
				
				System.out.println(BgGrammarType.getTypeById(we.grammLabelUid));
				System.out.println(we);
				System.out.println(we2);
				System.out.println();
			}
			
		}
	}
	
	private static void updateBuiltinDictionary(String bgOfficeDir, String bgLangToolsDir) {
		BgDictionary dict = new BgDictionary();
		BgOfficeScanner.getInstance().scan(bgOfficeDir, dict);
		
		dict.exportToFile(bgLangToolsDir + "/res/dict.dat");
	}
	
	public static void saveBuiltinDictionary() {
		
	}
	
	/**
	 * The BGLangTools library contains a resource file with
	 * words extracted from the Wiktionary and the BG Office project.
	 * Use this method to load the resource file in a dictionary.
	 */
	public static BgDictionary loadBuiltinDictionary() throws Exception {
		BgDictionary dict = new BgDictionary();
		System.out.println("Loading built-in dictionary. Please, wait...");
		dict.importFromStream(BGLangTools.class.getResourceAsStream("res/dict.dat"));
		
		System.out.println("done");
		
		return dict;
	}
}
