package net.sf.latexdraw.lang;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LPath;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class defines the localisation.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
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
 * @version 3.0
 */
public final class LangTool {
	public static enum Lang {
		CA {
			@Override
			public String getName() {
				return "Català";//$NON-NLS-1$
			}

			@Override
			public String getToken() {
				return "lang.ca";//$NON-NLS-1$
			}
		},
		RU {
			@Override
			public String getName() {
				return "Russkiy yazyk";//$NON-NLS-1$
			}

			@Override
			public String getToken() {
				return "lang.ru";//$NON-NLS-1$
			}
		},
		CS {
			@Override
			public String getName() {
				return "Čeština";//$NON-NLS-1$
			}

			@Override
			public String getToken() {
				return "lang.cs";//$NON-NLS-1$
			}
		},SR {
			@Override
			public String getName() {
				return "\u0441\u0440\u043f\u0441\u043a\u0438";//$NON-NLS-1$
			}

			@Override
			public String getToken() {
				return "lang.sr";//$NON-NLS-1$
			}
		}, HU {
			@Override
			public String getName() {
				return "Magyar";//$NON-NLS-1$
			}

			@Override
			public String getToken() {
				return "lang.hu";//$NON-NLS-1$
			}
		}, VI {
			@Override
			public String getName() {
				return "Ti\u1ebfng Vi\u1ec7t";//$NON-NLS-1$
			}

			@Override
			public String getToken() {
				return "lang.vi";//$NON-NLS-1$
			}
		}, PT_BR {
			@Override
			public String getName() {
				return "Portugu\u00eas Brasileiro";//$NON-NLS-1$
			}

			@Override
			public String getToken() {
				return "lang.pt-BR";//$NON-NLS-1$
			}
		}, JA {
			@Override
			public String getName() {
				return "\u65e5\u672c\u8a9e";//$NON-NLS-1$
			}

			@Override
			public String getToken() {
				return "lang.ja";//$NON-NLS-1$
			}

		}, TR {
			@Override
			public String getName() {
				return "T\u00fcrk\u00e7e";//$NON-NLS-1$
			}
			@Override
			public String getToken() {
				return "lang.tr";//$NON-NLS-1$
			}
		}, EN_BR {
			@Override
			public String getName() {
				return "English";//$NON-NLS-1$
			}

			@Override
			public String getToken() {
				return "lang.en";//$NON-NLS-1$
			}
		}, EN_US {
			@Override
			public String getName() {
				return "English-US";//$NON-NLS-1$
			}

			@Override
			public String getToken() {
				return "lang.en-US";//$NON-NLS-1$
			}
		}, FR {
			@Override
			public String getName() {
				return "Fran\u00e7ais";//$NON-NLS-1$
			}

			@Override
			public String getToken() {
				return "lang.fr";//$NON-NLS-1$
			}
		}, ES {
			@Override
			public String getName() {
				return "Espa\u00f1ol";//$NON-NLS-1$
			}

			@Override
			public String getToken() {
				return "lang.es";//$NON-NLS-1$
			}
		}, DE {
			@Override
			public String getName() {
				return "Deutsch";//$NON-NLS-1$
			}

			@Override
			public String getToken() {
				return "lang.de";//$NON-NLS-1$
			}
		}, IT {
			@Override
			public String getName() {
				return "Italiano";//$NON-NLS-1$
			}

			@Override
			public String getToken() {
				return "lang.it";//$NON-NLS-1$
			}
		}, PL {
			@Override
			public String getName() {
				return "J\u0119zyk polski";//$NON-NLS-1$
			}

			@Override
			public String getToken() {
				return "lang.pl";//$NON-NLS-1$
			}
		};


		/**
		 * @return The name of the language.
		 * @since 3.0
		 */
		public abstract String getName();

		/**
		 * @return The token corresponding to the language.
		 * @since 3.0
		 */
		public abstract String getToken();

		/**
		 * @return The language used by default.
		 */
		public static Lang getDefaultLanguage() {
			return EN_BR;
		}

		/**
		 * @param name The name of the language.
		 * @return The Lang object corresponding to the given language name, or the default language is null or not found.
		 * @since 3.0
		 */
		public static Lang getLanguage(final String name) {
			final Lang lang = INSTANCE.getMapLangs().get(name);
			return lang==null ? EN_BR : lang;
		}


		/**
		 * @return The language token (e.g. LANG_FR) corresponding to the system language.
		 * @since 3.0
		 */
		public static Lang getSystemLanguage() {
			final Lang language = INSTANCE.getMapLangs().get(System.getProperty("user.language"));//$NON-NLS-1$
			return language==null ? Lang.getDefaultLanguage() : language;
		}
	}

	/** The string corresponding to a missing key. */
	public static final String MISSING_KEY = "!missingKey!";//$NON-NLS-1$

	private static final ResourceBundle RES_BUNDLE_LFRAME;

	private static final ResourceBundle RES_BUNDLE_DIALOG_FRAME;

	private static final ResourceBundle RES_BUNDLE_OTHERS;

	private static final ResourceBundle RES_BUNDLE_16;

	private static final ResourceBundle RES_BUNDLE_17;

	private static final ResourceBundle RES_BUNDLE_18;

	private static final ResourceBundle RES_BUNDLE_19;

	/** The singleton to use when wanting to use language utilities. */
	public static final LangTool INSTANCE = new LangTool();

	private static final Lang LANG_CURRENT = INSTANCE.readLang();

	/** This map provides an easy access to the language items. */
	private Map<String, Lang> mapLangs = new HashMap<>();

	static {
		final String token = LangTool.getCurrentLanguage().getToken();

		RES_BUNDLE_LFRAME 		 = ResourceBundle.getBundle(token+".LaTeXDrawFrame");	//$NON-NLS-1$
		RES_BUNDLE_DIALOG_FRAME	 = ResourceBundle.getBundle(token+".dialogFrames");		//$NON-NLS-1$
		RES_BUNDLE_OTHERS 		 = ResourceBundle.getBundle(token+".others"); 			//$NON-NLS-1$
		RES_BUNDLE_16 			 = ResourceBundle.getBundle(token+".1_6"); //$NON-NLS-1$
		RES_BUNDLE_17 			 = ResourceBundle.getBundle(token+".1_7"); //$NON-NLS-1$
		RES_BUNDLE_18 			 = ResourceBundle.getBundle(token+".1_8"); //$NON-NLS-1$
		RES_BUNDLE_19 			 = ResourceBundle.getBundle(token+".1_9"); //$NON-NLS-1$
	}


	private LangTool() {
		super();
		initMap();
	}


	private void initMap() {
		// Recording the languages
		mapLangs.put("fr", Lang.FR);
		mapLangs.put("en", Lang.EN_BR);
		mapLangs.put("es", Lang.ES);
		mapLangs.put("de", Lang.DE);
		mapLangs.put("it", Lang.IT);
		mapLangs.put("pl", Lang.PL);
		mapLangs.put("tr", Lang.TR);
		mapLangs.put("ja", Lang.JA);
		mapLangs.put("vi", Lang.VI);
		mapLangs.put("hu", Lang.HU);
		mapLangs.put("pt-BR", Lang.PT_BR);
		mapLangs.put("sr", Lang.SR);
		mapLangs.put("ru", Lang.RU);
		mapLangs.put("cs", Lang.CS);
		mapLangs.put("ca", Lang.CA);

		mapLangs.put(Lang.FR.getName(), Lang.FR);
		mapLangs.put(Lang.EN_BR.getName(), Lang.EN_BR);
		mapLangs.put(Lang.DE.getName(), Lang.DE);
		mapLangs.put(Lang.ES.getName(), Lang.ES);
		mapLangs.put(Lang.IT.getName(), Lang.IT);
		mapLangs.put(Lang.PL.getName(), Lang.PL);
		mapLangs.put(Lang.EN_US.getName(), Lang.EN_US);
		mapLangs.put(Lang.TR.getName(), Lang.TR);
		mapLangs.put(Lang.JA.getName(), Lang.JA);
		mapLangs.put(Lang.VI.getName(), Lang.VI);
		mapLangs.put(Lang.HU.getName(), Lang.HU);
		mapLangs.put(Lang.PT_BR.getName(), Lang.PT_BR);
		mapLangs.put(Lang.SR.getName(), Lang.SR);
		mapLangs.put(Lang.RU.getName(), Lang.RU);
		mapLangs.put(Lang.CS.getName(), Lang.CS);
		mapLangs.put(Lang.CA.getName(), Lang.CA);
	}


	/**
	 * @return the map containing mapping between name and languages.
	 * @since 3.0
	 */
	public Map<String, Lang> getMapLangs() {
		return mapLangs;
	}


	/**
	 * Allows to get a string of the others components
	 * @param key The key of the string
	 * @return The string
	 */
	public String getStringOthers(final String key) {
		return getStringFromResourceBundle(key, RES_BUNDLE_OTHERS);
	}




	/**
	 * Allows to get a string of the LaTeXDrawFrame
	 * @param key The key of the string
	 * @return The string
	 */
	public String getStringLaTeXDrawFrame(final String key) {
		return getStringFromResourceBundle(key, RES_BUNDLE_LFRAME);
	}



	/**
	 * Allows to get a string of the new 1.6 strings.
	 * @param key The key of the string.
	 * @return The string.
	 */
	public String getString16(final String key) {
		return getStringFromResourceBundle(key, RES_BUNDLE_16);
	}



	/**
	 * Allows to get a string of the new 1.7 strings.
	 * @param key The key of the string.
	 * @return The string.
	 * @since 1.7
	 */
	public String getString17(final String key) {
		return getStringFromResourceBundle(key, RES_BUNDLE_17);
	}



	/**
	 * Allows to get a string of the new 1.8 strings.
	 * @param key The key of the string.
	 * @return The string.
	 * @since 1.8
	 */
	public String getString18(final String key) {
		return getStringFromResourceBundle(key, RES_BUNDLE_18);
	}



	/**
	 * Allows to get a string of the new 1.8 strings.
	 * @param key The key of the string.
	 * @return The string.
	 * @since 1.8
	 */
	public String getString19(final String key) {
		return getStringFromResourceBundle(key, RES_BUNDLE_19);
	}



	/**
	 * Allows to get a string of the dialog frames.
	 * @param key The key of the string.
	 * @return The string.
	 */
	public String getStringDialogFrame(final String key) {
		return getStringFromResourceBundle(key, RES_BUNDLE_DIALOG_FRAME);
	}



	/**
	 * @param key The key of the string.
	 * @param bundle The bundle to search into.
	 * @return The found String or MISSING_KEY or !key!
	 * @since 3.0
	 */
	private String getStringFromResourceBundle(final String key, final ResourceBundle bundle) {
		String str;

		if(key==null || bundle==null)
			str = MISSING_KEY;
		else
			try { str = bundle.getString(key); }
			catch(final MissingResourceException e) { str = '!' + key + '!'; }

		return str;
	}




	/**
	 * Allows to get the language of the program.
	 * @return The read language, or the default language.
	 */
	public Lang readLang() {
		try {
			final Path xml = Paths.get(LPath.PATH_PREFERENCES_XML_FILE);

			if(Files.exists(xml)) {
	            final Node node = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(Files.newInputStream(xml)).getFirstChild();
	            NodeList nl;

	            if(node!=null && node.getNodeName().equals(LNamespace.XML_ROOT_PREFERENCES)) {
		            nl 				= node.getChildNodes();
		            final int size 	= nl.getLength();

		            for(int i=0 ; i<size; i++)
		            	if(nl.item(i).getNodeName().equals(LNamespace.XML_LANG))
		            		return Lang.getLanguage(nl.item(i).getTextContent());
	            }
			}
		}
		catch(final ParserConfigurationException | IOException | SAXException e) { BadaboomCollector.INSTANCE.add(e); }
		return Lang.getSystemLanguage();
	}


	/**
	 * Allows to get the language of the package.
	 * @return The language of the package.
	 */
	public static Lang getCurrentLanguage() {
		return LANG_CURRENT==null ? Lang.getSystemLanguage() : LANG_CURRENT ;
	}
}
