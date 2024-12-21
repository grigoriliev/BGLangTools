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
import eu.ideya.lingua.bg.BgGrammarType;
import info.bliki.wiki.dump.IArticleFilter;
import info.bliki.wiki.dump.Siteinfo;
import info.bliki.wiki.dump.WikiArticle;
import info.bliki.wiki.dump.WikiXMLParser;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 *
 */
public class BgWiktionaryScanner {
	private final static BgWiktionaryScanner bgWiktionaryScanner = new BgWiktionaryScanner();
	
	private BgWiktionaryScanner() { }
	
	public static BgWiktionaryScanner getInstance() {
		return bgWiktionaryScanner;
	}
	
	/**
	 * 
	 * @param dumpFile The BG Wiktionary dump file to process.
	 * @param dict The dictionary in which the lemmas and the automatically
	 * generated word forms should be added.
	 * @throws IllegalArgumentException if an error occurs.
	 */
	public void scan(String dumpFile, BgDictionary dict) throws UnsupportedEncodingException, IOException {
		IArticleFilter handler = new ArticleFilter(dict);
		try {
			WikiXMLParser wxp = new WikiXMLParser(new File(dumpFile), handler);
			wxp.parse();
		} catch(Exception e) {
			throw new IllegalArgumentException(e);
		}
		
		try {
			BgWordFormGenerator.loadBuiltinPronouns(dict);
			BgWordFormGenerator.loadBuiltinTypes142_143(dict);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

class ArticleFilter implements IArticleFilter {
	private BgDictionary bgDictionary;

	public ArticleFilter(BgDictionary dict) {
		bgDictionary = dict;
	}
	
	@Override
	public void process(WikiArticle page, Siteinfo siteinfo) {
		//if (!page.isMain()) return;
		//System.out.println("----------------------------------------");
		//System.out.println(page.getId());
		//System.out.println(page.getRevisionId());
		//System.out.println(page.getTitle());
		//System.out.println("----------------------------------------");
		//page.isTemplate();
		//if (page.isCategory()) System.out.println(page.getTitle());
		//if (page.isTemplate()) System.out.println(page.getTitle());
		//if (page.isProject()) System.out.println(page.getTitle());
		
		if (!(page.isMain() || page.isCategory() || page.isProject() || page.isTemplate())) return;
		
		
		//System.out.println("----------------------------------------");
		//System.out.println(page.getTitle());
		//System.out.println("----------------------------------------");
		
		//wikiModel.load(page);
		//System.out.println(page.getText());
		
		/*TagToken t = wikiModel.popNode();
		while(t != null) {
			t = wikiModel.popNode();
			*/
		
		String prefix = "Уикиречник:Български/Типове думи/";
		if(page.getTitle().startsWith(prefix)) {
			String bgType = page.getTitle().substring(prefix.length());
			
			if(BgGrammarType.getTypeId(bgType) == -1) {
				if(bgType.matches("[0-9]{1,3}[a-z]{0,1}/[а-яА-я]$")) {
					bgType = bgType.substring(0, bgType.length() - 2);
				}
				
				if(BgGrammarType.getTypeId(bgType) == -1) return;
			}
			
			/*System.out.println("----------------------------------------");
			System.out.println(page.getTitle());
			System.out.println("----------------------------------------");
			
			int t = BgGrammarType.getTypeId(bgType);
			System.out.print("type: " + bgType);
			System.out.print(" code: " + BgGrammarType.getCodeById(t));
			char c = BgGrammarType.getSuffixById(t);
			System.out.println(c == 0 ? "" : " suffix: " + c);*/
			
			DictWikiModel wikiModel = new DictWikiModel(bgDictionary, bgType);
			wikiModel.process(page);
		}
	}

}
