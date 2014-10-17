package net.sf.latexdraw.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.latexdraw.badaboom.BadaboomCollector;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Defines the different localizations of LaTeXDraw.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 09/01/06<br>
 * @author Arnaud BLOUIN
 * @version 4.0
 */
public final class LangTool {
	public static final LangTool INSTANCE = new LangTool();
	
	private final ResourceBundle bundle;
	
	private LangTool() {
		super();
		bundle = readLang();
	}

	
	public ResourceBundle getBundle() {
		return bundle;
	}


	/**
	 * Allows to get the language of the program.
	 * @return The read language, or the default language.
	 */
	private ResourceBundle readLang() {
		try {
			final Path xml = Paths.get(LPath.PATH_PREFERENCES_XML_FILE);

			if(Files.exists(xml)) {
	            final Node node = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(Files.newInputStream(xml)).getFirstChild();

	            if(node!=null && node.getNodeName().equals(LNamespace.XML_ROOT_PREFERENCES)) {
	            	final NodeList nl = node.getChildNodes();

		            for(int i=0,size = nl.getLength() ; i<size; i++)
		            	if(nl.item(i).getNodeName().equals(LNamespace.XML_LANG)) {
		            		ResourceBundle bu = loadResourceBundle(Locale.forLanguageTag(nl.item(i).getTextContent()));
		            		if(bu==null) return getDefaultLanguage();
		            		return bu;
		            	}
	            }
			}
		}
		catch(final Exception ex) {
			ex.printStackTrace();
			BadaboomCollector.INSTANCE.add(ex); 
		}
		return getDefaultLanguage();
	}

	/** @return The language used by default. */
	private ResourceBundle getDefaultLanguage() {
		return loadResourceBundle(Locale.getDefault());
	}
	
	
	private ResourceBundle loadResourceBundle(Locale locale) {
		try {
			return ResourceBundle.getBundle("lang.bundle", locale);
		}catch(Exception ex) {
			ex.printStackTrace();
			BadaboomCollector.INSTANCE.add(ex); 
			return null;
		}
	}
}
