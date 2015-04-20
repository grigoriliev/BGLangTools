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

import static bg.swu.nlp.tools.bglang.BGLangTools.loadBuiltinDictionary;
import bg.swu.nlp.tools.bglang.BgDictionary;
import gate.Annotation;
import gate.AnnotationSet;
import gate.Resource;
import gate.Utils;
import gate.creole.AbstractLanguageAnalyser;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.creole.metadata.CreoleParameter;
import gate.creole.metadata.CreoleResource;
import gate.creole.metadata.Optional;
import gate.creole.metadata.RunTime;

/**
 *
 */
@CreoleResource(name = "BGLangTools Lemmatizer", 
                comment = "Lemmatizer for Bulgarian language") 
public class BGLemmatizerPR extends AbstractLanguageAnalyser {
	
	private BgDictionary dict;
	private BGLemmatizer lem = new BGLemmatizer();
 
	/* 
	 * this method gets called whenever an object of this 
	 * class is created either from GATE Developer GUI or if 
	 * initiated using Factory.createResource() method. 
	 */ 
	public Resource init() throws ResourceInstantiationException { 
		try { dict = loadBuiltinDictionary(); }
		catch(Exception e) { e.printStackTrace(); }
		
		return this; 
	} 
 
 
	/* 
	 * this method should provide the actual functionality of the PR 
	 * (from where the main execution begins). This method 
	 * gets called when user click on the "RUN" button in the 
	 * GATE Developer GUI’s application window. 
	 */ 
	public void execute() throws ExecutionException { 
		AnnotationSet allTokens =
			document.getAnnotations(inputASName).get(annotationType);
                
		//lem.resetStat();

		for(Annotation ann : allTokens) {
			String word = Utils.stringFor(document, ann);
			
			if(word == null) continue;
			
			String lemma = lem.getLemma(dict, word, getBtbTag(ann, word), false);
			
			if(lemma != null) {
				ann.getFeatures().put(outputFeatureName, lemma);
			}
			
		}
		
		System.out.println("BGLemmatizer: Document: " + document.getName());
		lem.printStat("BGLemmatizer: ");
		System.out.println();
	}
	
	private String getBtbTag(Annotation ann, String word) {
		String tag = null;
		Object o = ann.getFeatures().get(btbTagFeatureName);
		if(o != null) {
			tag = o.toString();
		} else {
			System.err.println("Missing BTB tag for token: " + word);
		}
		
		return tag;
	}
 
	/* this method is called to reinitialize the resource */ 
	public void reInit() throws ResourceInstantiationException { 
		// reinitialization code 
		//lem.resetStat();
	} 
 
	/* 
	 * There are two types of parameters 
	 * 1. Init time parameters − values for these parameters need to be 
	 * provided at the time of initializing a new resource and these 
	 * values are not supposed to be changed. 
	 * 2. Runtime parameters − values for these parameters are provided 
	 * at the time of executing the PR. These are runtime parameters and 
	 * can be changed before starting the execution 
	 * (i.e. before you click on the "RUN" button in GATE Developer) 
	 * A parameter myParam is specified by a pair of methods getMyParam 
	 * and setMyParam (with the first letter of the parameter name 
	 * capitalized in the normal Java Beans style), with the setter 
	 * annotated with a @CreoleParameter annotation. 
	 */ 
	String inputASName = null; 
 
	/* get<parameter name with first letter Capital>  */ 
	public String getInputASName() { 
		return inputASName; 
	} 
 
	@Optional 
	@RunTime 
	@CreoleParameter (comment = "name of the annotation set used for input") 
	public void setInputASName(String setName) { 
		this.inputASName = setName; 
	}
	
	String annotationType; 
 
	/* get<parameter name with first letter Capital>  */ 
	public String getAnnotationType() { 
		return annotationType; 
	} 
 
	@Optional 
	@RunTime 
	@CreoleParameter (
		comment = "The name of the annotation type used for input",
		defaultValue = "Token"
	) 
	public void setAnnotationType(String annotationType) { 
		this.annotationType = annotationType; 
	}
	
	String outputFeatureName; 
 
	public String getOutputFeatureName() { 
		return outputFeatureName; 
	} 
 
	@Optional 
	@RunTime 
	@CreoleParameter (
		comment = "The name of the annotation type used for output",
		defaultValue = "lemma"
	) 
	public void setOutputFeatureName(String outputFeatureName) { 
		this.outputFeatureName = outputFeatureName; 
	}
	
	String btbTagFeatureName; 
 
	public String getBtbTagFeatureName() { 
		return btbTagFeatureName; 
	} 
 
	@Optional 
	@RunTime 
	@CreoleParameter (
		comment = "The name of the annotation type used for BTB tag",
		defaultValue = "category"
	) 
	public void setBtbTagFeatureName(String btbTagFeatureName) { 
		this.btbTagFeatureName = btbTagFeatureName; 
	}
}
