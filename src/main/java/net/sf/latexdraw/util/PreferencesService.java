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
public class PreferencesService {
	private final Map<String, Node> preferences;

	public PreferencesService() {
		super();
		preferences = new HashMap<>();
	}

	public Map<String, Node> readXMLPreferencesFromFile(final File xmlFile) {
		if(xmlFile == null || !xmlFile.canRead()) {
			return Collections.emptyMap();
		}

		try {
			final Node root = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile).getFirstChild();

			if(root == null || !LNamespace.XML_ROOT_PREFERENCES.equals(root.getNodeName())) {
				throw new IllegalArgumentException();
			}

			final NodeList nl = root.getChildNodes();

			flushPreferencesCache();

			for(int i = 0, size = nl.getLength(); i < size; i++) {
				final Node node = nl.item(i);
				final String name = node.getNodeName();

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
		preferences.clear();
	}

	public Map<String, Node> getPreferences() {
		return Collections.unmodifiableMap(preferences);
	}
}
