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

import java.util.Locale;

/**
 *
 */
public class I18n extends net.sf.juife.I18n {
	/** Provides the locale-specific data of this library. */
	public final static I18n i18n = new I18n();
	
	private static Locale[] locales = {  new Locale("en", "US") };
	
	private
	I18n() {
		//setButtonsBundle("bg.swu.nlp.tools.bglang.langprops.ButtonsLabelsBundle");
		setErrorsBundle("bg.swu.nlp.tools.bglang.langprops.ErrorsBundle");
		setLabelsBundle("bg.swu.nlp.tools.bglang.langprops.LabelsBundle");
		//setLogsBundle("bg.swu.nlp.tools.bglang.langprops.LogsBundle");
		//setMenusBundle("bg.swu.nlp.tools.bglang.langprops.MenuLabelsBundle");
		setMessagesBundle("bg.swu.nlp.tools.bglang.langprops.MessagesBundle");
	}
	
	/**
	 * Gets all available locales.
	 * @return All available locales.
	 */
	public static Locale[]
	getAvailableLocales() { return locales; }
}
