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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The different localizations of LaTeXDraw.
 * @author Arnaud BLOUIN
 */
public final class LangTool {
	/**
	 * The singleton.
	 */
	public static final LangTool INSTANCE = new LangTool();

	private final ResourceBundle bundle;

	private LangTool() {
		super();
		bundle = readLang().orElseThrow(() -> new IllegalArgumentException("Cannot read any resource bundle."));
	}

	/**
	 * @return The lang bundle of the app.
	 */
	public ResourceBundle getBundle() {
		return bundle;
	}


	/**
	 * Gets the language of the program.
	 * @return The read language, the default language, or nothing.
	 */
	private Optional<ResourceBundle> readLang() {
		Optional<ResourceBundle> res = Optional.empty();

		try {
			final Path xml = Paths.get(LPath.PATH_PREFERENCES_XML_FILE);

			if(xml.toFile().exists()) {
				final Node node = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(Files.newInputStream(xml)).getFirstChild();

				if(node != null && LNamespace.XML_ROOT_PREFERENCES.equals(node.getNodeName())) {
					res = getLangNode(node.getChildNodes()).map(lang ->
						loadResourceBundle(Locale.forLanguageTag(lang.getTextContent())).orElseGet(() -> getDefaultLanguage().orElse(null)));
				}
			}
		}catch(final InvalidPathException | SAXException | ParserConfigurationException | IOException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}

		return Optional.ofNullable(res.orElseGet(() -> getDefaultLanguage().orElseGet(() -> loadResourceBundle(Locale.US).orElse(null))));
	}


	/**
	 * Gets the lang node in a node list.
	 * @param nl The node list to parse.
	 * @return The node lang or nothing.
	 */
	private Optional<Node> getLangNode(final NodeList nl) {
		for(int i = 0, size = nl.getLength(); i < size; i++) {
			if(nl.item(i).getNodeName().equals(LNamespace.XML_LANG)) {
				return Optional.ofNullable(nl.item(i));
			}
		}
		return Optional.empty();
	}


	/**
	 * @return The list of locales supported by the app.
	 */
	public List<Locale> getSupportedLocales() {
		return Stream.of("ar", "de", "es", "hu", "ja", "pl", "ru", "sv", "uk", "ca", "en-GB", "fr", "it", "nl", "pt-BR", "si", "ta", "vi", "cs", //NON-NLS
			"en-US", "gl", "iw", "oc", "pt", "sr", "tr", "zh-CN").map(id -> Locale.forLanguageTag(id)).collect(Collectors.toList()); //NON-NLS
	}


	/**
	 * @return The language used by default, or nothing.
	 */
	private Optional<ResourceBundle> getDefaultLanguage() {
		return loadResourceBundle(Locale.getDefault());
	}


	private Optional<ResourceBundle> loadResourceBundle(final Locale locale) {
		try {
			return Optional.ofNullable(ResourceBundle.getBundle("lang.bundle", locale, new UTF8Control())); //NON-NLS
		}catch(final MissingResourceException | NullPointerException ex) {
			BadaboomCollector.INSTANCE.add(ex);
			return Optional.empty();
		}
	}

	private static class UTF8Control extends ResourceBundle.Control {
		@Override
		public ResourceBundle newBundle(final String base, final Locale loc, final String fmt, final ClassLoader load, final boolean reload) throws IOException {
			final String bundleName = toBundleName(base, loc);
			final String resourceName = toResourceName(bundleName, "properties"); //NON-NLS
			ResourceBundle bundle = null;
			InputStream stream = null;
			if(reload) {
				final URL url = load.getResource(resourceName);
				if(url != null) {
					final URLConnection connection = url.openConnection();
					if(connection != null) {
						connection.setUseCaches(false);
						stream = connection.getInputStream();
					}
				}
			}else {
				stream = load.getResourceAsStream(resourceName);
			}
			if(stream != null) {
				try {
					bundle = new PropertyResourceBundle(new InputStreamReader(stream, Charset.forName("UTF-8")));
				}finally {
					stream.close();
				}
			}
			return bundle;
		}
	}
}
