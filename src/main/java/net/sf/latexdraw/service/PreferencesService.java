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
package net.sf.latexdraw.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.Page;
import net.sf.latexdraw.util.SystemUtils;
import net.sf.latexdraw.util.Unit;
import net.sf.latexdraw.util.VersionChecker;
import net.sf.latexdraw.view.GridStyle;
import org.jetbrains.annotations.NotNull;
import org.malai.undo.UndoCollector;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PreferencesService {
	/** The recent files. */
	private final @NotNull ListProperty<String> recentFileNames;
	/** The current handled file. */
	private @NotNull Optional<File> currentFile;
	/** The current working folder. */
	private @NotNull Optional<File> currentFolder;
	private final @NotNull IntegerProperty nbRecentFiles;
	private final @NotNull IntegerProperty gridGap;
	private final @NotNull BooleanProperty openGL;
	private final @NotNull BooleanProperty checkVersion;
	private final @NotNull BooleanProperty magneticGrid;
	private final @NotNull StringProperty pathExport;
	private final @NotNull StringProperty pathOpen;
	private final @NotNull StringProperty includes;
	private final @NotNull ObjectProperty<Locale> lang;
	private final @NotNull ResourceBundle bundle;
	private final @NotNull ObjectProperty<GridStyle> gridStyle;
	private final @NotNull ObjectProperty<Unit> unit;
	private final @NotNull ObjectProperty<Page> page;

	public PreferencesService() {
		super();
		lang = new SimpleObjectProperty<>(readLang());
		bundle = loadResourceBundle(lang.get()).orElseThrow(() -> new IllegalArgumentException("Cannot read any resource bundle."));
		nbRecentFiles = new SimpleIntegerProperty(5);
		gridGap = new SimpleIntegerProperty(10);
		openGL = new SimpleBooleanProperty(true);
		checkVersion = new SimpleBooleanProperty(true);
		gridStyle = new SimpleObjectProperty<>(GridStyle.NONE);
		unit = new SimpleObjectProperty<>(Unit.CM);
		magneticGrid = new SimpleBooleanProperty(true);
		pathExport = new SimpleStringProperty("");
		pathOpen = new SimpleStringProperty("");
		includes = new SimpleStringProperty("");
		currentFile = Optional.empty();
		currentFolder = Optional.empty();
		recentFileNames = new SimpleListProperty<>(FXCollections.observableArrayList());
		page = new SimpleObjectProperty<>(Page.USLETTER);

		nbRecentFiles.addListener((observable, oldValue, newValue) -> {
			while(newValue.intValue() > recentFileNames.size() && !recentFileNames.isEmpty()) {
				recentFileNames.remove(recentFileNames.size() - 1);
			}
		});

		UndoCollector.INSTANCE.setBundle(bundle);
	}

	public @NotNull StringProperty includesProperty() {
		return includes;
	}

	public @NotNull IntegerProperty nbRecentFilesProperty() {
		return nbRecentFiles;
	}

	public @NotNull Locale getLang() {
		return lang.get();
	}

	public @NotNull Page getPage() {
		return page.get();
	}

	public void setPage(final @NotNull Page newPage) {
		page.set(newPage);
	}

	public @NotNull ObjectProperty<Page> pageProperty() {
		return page;
	}

	public @NotNull IntegerProperty gridGapProperty() {
		return gridGap;
	}

	public boolean isMagneticGrid() {
		return magneticGrid.get();
	}

	public @NotNull GridStyle getGridStyle() {
		return gridStyle.get();
	}

	public @NotNull ObjectProperty<GridStyle> gridStyleProperty() {
		return gridStyle;
	}

	public @NotNull Unit getUnit() {
		return unit.get();
	}

	public @NotNull ObjectProperty<Unit> unitProperty() {
		return unit;
	}

	public @NotNull ResourceBundle getBundle() {
		return bundle;
	}

	/**
	 * Gets the language of the program.
	 * @return The read language, the default language, or nothing.
	 */
	private @NotNull Locale readLang() {
		Optional<Locale> res = Optional.empty();

		try {
			final Node node =
				DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(Files.newInputStream(Paths.get(getPreferencesPath()))).getFirstChild();

			if(node != null && LNamespace.XML_ROOT_PREFERENCES.equals(node.getNodeName())) {
				final NodeList nl = node.getChildNodes();
				res = IntStream.range(0, nl.getLength()).
					filter(i -> LNamespace.XML_LANG.equals(nl.item(i).getNodeName())).
					mapToObj(i -> Locale.forLanguageTag(nl.item(i).getTextContent())).findFirst();
			}
		}catch(final InvalidPathException | SAXException | ParserConfigurationException | IOException ignored) {
			// No preferences file
		}

		return res.orElseGet(() -> Locale.getDefault());
	}


	public @NotNull List<Locale> getSupportedLocales() {
		return Stream.of("ar", "de", "es", "hu", "ja", "pl", "ru", "sv", "uk", "ca", "en-GB", "fr", "it", "nl", "pt-BR", "si", "ta", "vi", "cs", //NON-NLS
			"en-US", "gl", "iw", "oc", "pt", "sr", "tr", "zh-CN").map(id -> Locale.forLanguageTag(id)).collect(Collectors.toList()); //NON-NLS
	}


	private @NotNull Optional<ResourceBundle> loadResourceBundle(final Locale locale) {
		try {
			try {
				return Optional.ofNullable(ResourceBundle.getBundle("lang.bundle", locale, new UTF8Control())); //NON-NLS
			}catch(final UnsupportedOperationException ex) {
				// Java 9+ do not support 'control' anymore
				return Optional.ofNullable(ResourceBundle.getBundle("lang.bundle", locale)); //NON-NLS
			}
		}catch(final MissingResourceException | NullPointerException ex) {
			BadaboomCollector.INSTANCE.add(ex);
			return Optional.empty();
		}
	}

	public @NotNull BooleanProperty openGLProperty() {
		return openGL;
	}

	public @NotNull BooleanProperty checkVersionProperty() {
		return checkVersion;
	}

	public @NotNull String getPathExport() {
		return pathExport.get();
	}

	public @NotNull String getPathOpen() {
		return pathOpen.get();
	}

	public @NotNull BooleanProperty magneticGridProperty() {
		return magneticGrid;
	}

	public @NotNull ObjectProperty<Locale> langProperty() {
		return lang;
	}

	public int getNbRecentFiles() {
		return nbRecentFiles.get();
	}

	public void setNbRecentFiles(final int nbRecentFiles) {
		this.nbRecentFiles.set(nbRecentFiles);
	}

	public @NotNull StringProperty pathExportProperty() {
		return pathExport;
	}

	public @NotNull StringProperty pathOpenProperty() {
		return pathOpen;
	}

	public @NotNull Optional<File> getCurrentFolder() {
		return currentFolder;
	}

	public void setCurrentFolder(final File folder) {
		currentFolder = Optional.ofNullable(folder);
	}

	public @NotNull Optional<File> getCurrentFile() {
		return currentFile;
	}

	public void setCurrentFile(final File file) {
		currentFile = Optional.ofNullable(file);
	}

	public @NotNull ListProperty<String> getRecentFiles() {
		return recentFileNames;
	}

	private void setRecentFiles(final Node node) {
		final NodeList nl = node.getChildNodes();
		final NamedNodeMap nnm = node.getAttributes();

		if(nnm != null && nnm.getNamedItem(LNamespace.XML_NB_RECENT_FILES) != null) {
			Optional.ofNullable(nnm.getNamedItem(LNamespace.XML_NB_RECENT_FILES)).ifPresent(attr -> nbRecentFiles.setValue(Integer.valueOf(attr.getTextContent())));
		}

		recentFileNames.setAll(IntStream.range(0, Math.min(nbRecentFiles.get(), nl.getLength())).
			mapToObj(i -> nl.item(i)).
			filter(n -> LNamespace.XML_RECENT_FILE.equals(n.getNodeName()) && n.getTextContent() != null).
			map(n -> n.getTextContent()).
			collect(Collectors.toList()));
	}

	public void addRecentFile(final @NotNull String absolutePath) {
		final int i = recentFileNames.indexOf(absolutePath);

		if(i != -1) {
			recentFileNames.remove(i);
		}

		while(recentFileNames.size() >= nbRecentFiles.get()) {
			recentFileNames.remove(nbRecentFiles.get() - 1);
		}

		recentFileNames.add(0, absolutePath);
	}


	private @NotNull String getPreferencesPath() {
		return SystemUtils.getInstance().getPathLocalUser() + File.separator + ".preferences.xml"; //NON-NLS
	}


	public void writePreferences() {
		try {
			try(final OutputStream fos = Files.newOutputStream(Path.of(getPreferencesPath()))) {
				final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
				final Element root = document.createElement(LNamespace.XML_ROOT_PREFERENCES);
				Element elt;
				Element elt2;

				document.setXmlVersion("1.0"); //NON-NLS
				document.setXmlStandalone(true);
				document.appendChild(root);

				final Attr attr = document.createAttribute(LNamespace.XML_VERSION);
				attr.setTextContent(VersionChecker.VERSION);
				root.setAttributeNode(attr);

				elt = document.createElement(LNamespace.XML_OPENGL);
				elt.setTextContent(String.valueOf(openGL));
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_PATH_EXPORT);
				elt.setTextContent(pathExport.get());
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_PATH_OPEN);
				elt.setTextContent(pathOpen.get());
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_UNIT);
				elt.setTextContent(unit.getName());
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_CHECK_VERSION);
				elt.setTextContent(String.valueOf(checkVersion));
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_LANG);
				elt.setTextContent(lang.get().toLanguageTag());
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_MAGNETIC_GRID);
				elt.setTextContent(String.valueOf(magneticGrid.get()));
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_GRID_STYLE);
				elt.setTextContent(gridStyle.get().name());
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_GRID_GAP);
				elt.setTextContent(String.valueOf(gridGap.get()));
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_LATEX_INCLUDES);
				elt.setTextContent(includes.get());
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_RECENT_FILES);
				elt.setAttribute(LNamespace.XML_NB_RECENT_FILES, String.valueOf(nbRecentFiles.get()));
				root.appendChild(elt);

				for(final String recentFile : recentFileNames) {
					elt2 = document.createElement(LNamespace.XML_RECENT_FILE);
					elt2.setTextContent(recentFile);
					elt.appendChild(elt2);
				}

				final Transformer transformer = TransformerFactory.newInstance().newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //NON-NLS
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); //NON-NLS
				transformer.transform(new DOMSource(document), new StreamResult(fos));
			}
		}catch(final TransformerException | IllegalArgumentException | DOMException | IOException | ParserConfigurationException | FactoryConfigurationError ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}
	}


	public void readPreferences() {
		processXMLDataPreference(readPreferencesFromFile(new File(getPreferencesPath())));
	}


	private Map<String, Node> readPreferencesFromFile(final File xmlFile) {
		if(xmlFile == null || !xmlFile.canRead()) {
			return Collections.emptyMap();
		}

		try {
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true); //NON-NLS
			factory.setFeature("http://xml.org/sax/features/external-general-entities", false); //NON-NLS
			factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false); //NON-NLS
			factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false); //NON-NLS
			factory.setXIncludeAware(false);
			factory.setExpandEntityReferences(false);
			final DocumentBuilder builder = factory.newDocumentBuilder();
			builder.setErrorHandler(null);
			final Node root = builder.parse(xmlFile).getFirstChild();

			if(root == null || !LNamespace.XML_ROOT_PREFERENCES.equals(root.getNodeName())) {
				return Collections.emptyMap();
			}

			final Map<String, Node> preferences = new HashMap<>();
			final NodeList nl = root.getChildNodes();

			for(int i = 0, size = nl.getLength(); i < size; i++) {
				final Node node = nl.item(i);
				final String name = node.getNodeName();

				if(name != null && !name.isEmpty()) {
					preferences.put(name, node);
				}
			}

			return Collections.unmodifiableMap(preferences);
		}catch(final IOException | SAXException | FactoryConfigurationError | ParserConfigurationException ignored) {
			// Empty file or not ok
		}
		return Collections.emptyMap();
	}


	private void processXMLDataPreference(final Map<String, Node> prefMap) {
		includes.setValue(Optional.ofNullable(prefMap.get(LNamespace.XML_LATEX_INCLUDES)).map(n -> n.getTextContent()).orElse(""));

		openGL.setValue(Optional.ofNullable(prefMap.get(LNamespace.XML_OPENGL)).map(n -> Boolean.valueOf(n.getTextContent())).orElse(Boolean.TRUE));

		checkVersion.setValue(Optional.ofNullable(prefMap.get(LNamespace.XML_CHECK_VERSION)).map(n -> Boolean.valueOf(n.getTextContent())).orElse(Boolean.TRUE));

		gridStyle.set(Optional.ofNullable(prefMap.get(LNamespace.XML_GRID_STYLE)).
			map(n -> GridStyle.getStylefromName(n.getTextContent()).orElse(GridStyle.NONE)).orElse(GridStyle.NONE));

		gridGap.set(Optional.ofNullable(prefMap.get(LNamespace.XML_GRID_GAP)).
			map(node -> MathUtils.INST.parseInt(node.getTextContent()).orElse(10)).orElse(10));

		magneticGrid.setValue(Optional.ofNullable(prefMap.get(LNamespace.XML_MAGNETIC_GRID)).map(n -> Boolean.valueOf(n.getTextContent())).orElse(Boolean.TRUE));

		pathExport.set(Optional.ofNullable(prefMap.get(LNamespace.XML_PATH_EXPORT)).map(node -> node.getTextContent()).orElse(""));

		pathOpen.set(Optional.ofNullable(prefMap.get(LNamespace.XML_PATH_OPEN)).map(node -> node.getTextContent()).orElse(""));

		unit.set(Optional.ofNullable(prefMap.get(LNamespace.XML_UNIT)).map(node -> Unit.getUnit(node.getTextContent())).orElse(Unit.CM));

		Optional.ofNullable(prefMap.get(LNamespace.XML_RECENT_FILES)).ifPresent(node -> setRecentFiles(node));
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
					try {
						stream.close();
					}catch(final IOException ex) {
						BadaboomCollector.INSTANCE.add(ex);
					}
				}
			}
			return bundle;
		}
	}
}
