/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.util;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Arnaud Blouin
 */
public final class Preference {
	public static final Preference INSTANCE = new Preference();

	private Preference() {
		super();
	}

	private Map<String, Node> preferences = null;

	public Map<String, Node> readXMLPreferencesFromFile(final File xmlFile) {
		if(xmlFile == null || !xmlFile.canRead()) {
			return Collections.emptyMap();
		}

		if(preferences != null) {
			return preferences;
		}

		try {
			Node node = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile).getFirstChild();

			if(node == null || !node.getNodeName().equals(LNamespace.XML_ROOT_PREFERENCES)) {
				throw new IllegalArgumentException();
			}

			final NodeList nl = node.getChildNodes();
			String name;

			preferences = new HashMap<>();

			for(int i = 0, size = nl.getLength(); i < size; i++) {
				node = nl.item(i);
				name = node.getNodeName();

				if(name != null && !name.isEmpty()) {
					preferences.put(name, node);
				}
			}

			return Collections.unmodifiableMap(preferences);
		}catch(final IOException | SAXException | FactoryConfigurationError | ParserConfigurationException | IllegalArgumentException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}
		return Collections.emptyMap();
	}

	public void flushPreferencesCache() {
		preferences = null;
	}
}
