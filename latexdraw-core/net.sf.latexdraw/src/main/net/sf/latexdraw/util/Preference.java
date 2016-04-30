package net.sf.latexdraw.util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Preference {
	private static Map<String, Node> preferences = null;

	public static Map<String, Node> readXMLPreferencesFromFile(final File xmlFile) {
		if(xmlFile==null || !xmlFile.canRead())
			return Collections.emptyMap();

		if(preferences!=null)
			return preferences;

		try {
			Node node = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile).getFirstChild();

			if(node==null || !node.getNodeName().equals(LNamespace.XML_ROOT_PREFERENCES))
				throw new IllegalArgumentException();

			final NodeList nl = node.getChildNodes();
			String name;

			preferences = new HashMap<>();

			for(int i=0, size=nl.getLength(); i<size; i++) {
				node = nl.item(i);
				name = node.getNodeName();

				if(name!=null && !name.isEmpty())
					preferences.put(name, node);
			}

			return Collections.unmodifiableMap(preferences);
		}catch(final Exception e) { e.printStackTrace(); }
		return Collections.emptyMap();
	}

	public static void flushPreferencesCache() {
		preferences = null;
	}
}
