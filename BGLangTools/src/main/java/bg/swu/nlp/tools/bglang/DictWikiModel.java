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

package bg.swu.nlp.tools.bglang;

import eu.ideya.lingua.bg.BgDictionary;
import eu.ideya.lingua.bg.GrammaticalLabel;
import eu.ideya.lingua.bg.WordEntry;

import info.bliki.wiki.dump.WikiArticle;
import info.bliki.wiki.model.IEventListener;
import info.bliki.wiki.model.WikiModel;

/**
 *
 */
public class DictWikiModel extends WikiModel implements IEventListener {
	private String currentSection = "";
	
	private BgDictionary bgDictionary;
	private String bgType;
	
	/**
	 * 
	 * @param dict Provides the BG dictionary, in which the the
	 * Bulgarian words crawled from the Wiki will be added.
	 */
	public DictWikiModel(BgDictionary dict, String bgType) {
		super("${image}", "${title}");
		bgDictionary = dict;
		this.bgType = bgType;
	}
	
	/**
	 *
	 */
	public void process(WikiArticle article) {
		setUp();
		setPageName(article.getTitle());
		
		String rawWikiText = article.getText();
		
		parseEvents(this, rawWikiText);
	}
	
	
	/**
	 * Determines whether the current section's title consists of a
	 * cyrillic capital letter.
	 */
	public boolean isCyrLetterSection() {
		if(currentSection.length() != 1) return false;
		char c = currentSection.charAt(0);
		if(c >= 0x410 && c < 0x42F) return true;
		return false;
		
	}
	
	
	// IEventListener implementation
	@Override
	public void onHeader(char[] src, int startPosition, int endPosition, int rawStart, int rawEnd, int level) {
		currentSection = new String(src, rawStart, rawEnd - rawStart).trim();
		//System.out.println("onHeader: " + currentSection);
		//if(isCyrLetterSection()) System.out.println("onHeader: " + new String(src, startPosition, endPosition - startPosition));
		
	}
	
	@Override
	public void onTemplate(char[] src, int rawStart, int rawEnd) {
		//if(isCyrLetterSection()) System.out.println("onTemplate: " + new String(src, rawStart, rawEnd - rawStart));
	}
	
	@Override
	public void onWikiLink(char[] src, int rawStart, int rawEnd, java.lang.String suffix) {
		//System.out.println("onWikiLink: " + new String(src, rawStart, rawEnd - rawStart));
		if(!isCyrLetterSection()) return;
		String lemma = new String(src, rawStart, rawEnd - rawStart);
		GrammaticalLabel l = new GrammaticalLabel(bgType);
		 
		try {
			WordEntry e = bgDictionary.addWord(lemma, l.getUid(), -1);
			WordEntry[] entries = BgWordFormGenerator.getInstance().generateWordForms(e);
			if(entries != null) {
				for(WordEntry we : entries) {
					bgDictionary.addWord(we);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
