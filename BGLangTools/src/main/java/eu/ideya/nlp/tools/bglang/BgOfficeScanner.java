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

import eu.ideya.lingua.bg.BgDictionary;
import eu.ideya.lingua.bg.BgGrammarType;
import eu.ideya.lingua.bg.GrammaticalLabel;
import eu.ideya.lingua.bg.WordEntry;
import static eu.ideya.nlp.tools.bglang.I18n.i18n;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This class is used to retrieve all lemmas included in the BG Office project
 * and their grammatical type. Note that the packages over which this class 
 * works are located in the
 * <a href="http://sourceforge.net/projects/bgoffice/files/For%20Developers/">For developers</a>
 * folder of the BG Office project.
 * For more information about the data structures see the files in the
 * <b>specification</b> folder, which is located in the <b>docs</b> folder of
 * the developer package.
 * 
 */
public class BgOfficeScanner {
	private final static BgOfficeScanner bgOfficeScanner = new BgOfficeScanner();
	
	private BgOfficeScanner() { }
	
	public static BgOfficeScanner getInstance() {
		return bgOfficeScanner;
	}
	
	
	/**
	 * 
	 * @param bgOfficeDir The root directory of the BG Office developer
	 * package.
	 * @param dict The dictionary in which the lemmas and the automatically
	 * generated word forms should be added.
	 * @throws IllegalArgumentException if invalid directory path is provided.
	 */
	public void scan(String bgOfficeDir, BgDictionary dict) {
		File f = new File(bgOfficeDir, "data");
		
		if(!f.exists() || !f.isDirectory()) {
			String dir = f.getAbsolutePath();
			String sep = File.separator;
			
			f = new File(bgOfficeDir + sep + "trunk" + sep + "bgoffice" + sep + "data");
			if(!f.exists() || !f.isDirectory()) {
				String s = i18n.getError("BgOfficeScanner.missingDir", dir);
				throw new IllegalArgumentException(s);
			}
		}
		
		ArrayList<String> dataFiles = new ArrayList<String>();
		
		walk(f.getAbsolutePath(), f.list(), dataFiles);
		
		for(String s : dataFiles) {
			addLemmas(s, dict);
		}
		
		try {
			BgWordFormGenerator.loadBuiltinPronouns(dict);
			BgWordFormGenerator.loadBuiltinTypes142_143(dict);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds the lemmas contained in the specified data file
	 * to the specified dictionary.
	 * @throws IllegalArgumentException if unable to process the data file.
	 */
	private void addLemmas(String dataFile, BgDictionary dict) {
		ArrayList<String> lemmas = getLemmas(dataFile);
		
		String file = new File(dataFile).getName();
		String bgType = file.substring(2, file.length() - 4);
		GrammaticalLabel l;
		l = new GrammaticalLabel(bgType);
		
		for(String lemma : lemmas) {
			try {
				int code = BgGrammarType.getCodeById(l.getUid());
				if(code >= 90  && code <= 130) continue;
				if(code >= 142 && code <= 143) continue;
				
				WordEntry e = dict.addWord(lemma, l.getUid(), -1);
				WordEntry[] entries = BgWordFormGenerator.getInstance().generateWordForms(e);
				if(entries != null) {
					for(WordEntry we : entries) {
						dict.addWord(we);
					}
				}
			 } catch(Exception e) {
				e.printStackTrace();
			 }
		}
	}
	
	/**
	 * Returns the list of lemmas contained in the specified data file.
	 * @param dataFile BG office data file containing a list of lemmas
	 * @throws IllegalArgumentException if unable to process the data file.
	 */
	private ArrayList<String> getLemmas(String dataFile) {
		File f = new File(dataFile);
		if(!f.exists() || !f.isFile() || !f.canRead()) {
			String s = f.getAbsolutePath();
			String err = i18n.getError("BgOfficeScanner.invalidDataFile", s);
			throw new IllegalArgumentException(err);
		}
		
		BufferedReader reader;
		
		try {
			InputStreamReader r
				= new InputStreamReader(new FileInputStream(f), "Cp1251");
			reader = new BufferedReader(r);
		} catch(Exception e) {
			String s = f.getAbsolutePath();
			String err = i18n.getError("BgOfficeScanner.cantReadDataFile", s);
			throw new IllegalArgumentException(err, e);
		}
		
		ArrayList<String> lemmas = new ArrayList<String>();
		
		boolean valid = false;
		
		try {
			String line = reader.readLine();
			while(line != null) {
				if(line.equals("Думи:")) {
					valid = true;
					break;
				}
				
				line = reader.readLine();
			}
			
			if(!valid) {
				reader.close();
				String s = f.getAbsolutePath();
				String err = i18n.getError("BgOfficeScanner.invalidDataStructure", s);
				throw new IllegalArgumentException(err);
			}
			
			line = reader.readLine();
			while(line != null) {
				lemmas.add(line);
				line = reader.readLine();
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try { reader.close(); }
			catch(Exception e) { e.printStackTrace(); }
		}
		
		return lemmas;
	}
	
	private void walk(String parentPath, String[] files, ArrayList<String> dataFiles) {
		for(String s : files) {
			File f = new File(parentPath, s);
			if(f.isDirectory()) walk(f.getAbsolutePath(), f.list(), dataFiles);
			else if(s.matches("bg[0-9]{3}[a-z]{0,1}.dat") && f.canRead()) {
				dataFiles.add(f.getAbsolutePath());
			}
		}
	}
}
