/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
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


	private Locale getLocaleFromFileName(final String fileName) {
		return Locale.forLanguageTag(LFileUtils.INSTANCE.getFileNameNoExtension(fileName).replaceAll("bundle_", "").replaceAll("_", "-"));
	}

	/**
	 * @return The list of locales supported by the app.
	 */
	public List<Locale> getSupportedLocales() {
		try(final Stream<Path> list = Files.list(Paths.get(getClass().getResource("/lang").toURI()))) {
			return list.filter(f -> !f.toFile().isDirectory() && Files.isReadable(f)).
				map(f -> getLocaleFromFileName(f.getFileName().toString())).collect(Collectors.toList());
		}catch(final IOException | URISyntaxException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}
		return Collections.emptyList();
	}


	/**
	 * @return The language used by default, or nothing.
	 */
	private Optional<ResourceBundle> getDefaultLanguage() {
		return loadResourceBundle(Locale.getDefault());
	}


	private Optional<ResourceBundle> loadResourceBundle(final Locale locale) {
		try {
			return Optional.ofNullable(ResourceBundle.getBundle("lang.bundle", locale));
		}catch(final MissingResourceException | NullPointerException ex) {
			BadaboomCollector.INSTANCE.add(ex);
			return Optional.empty();
		}
	}
}
