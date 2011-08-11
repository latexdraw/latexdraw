package net.sf.latexdraw.lang;

import java.io.File;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.latexdraw.badaboom.BordelCollector;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LPath;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class defines the localisation.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
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
		HU {
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
				return "Tiếng Việt";//$NON-NLS-1$
			}

			@Override
			public String getToken() {
				return "lang.vi";//$NON-NLS-1$
			}
		}, PT_BR {
			@Override
			public String getName() {
				return "Português Brasileiro";//$NON-NLS-1$
			}

			@Override
			public String getToken() {
				return "lang.pt-BR";//$NON-NLS-1$
			}
		}, JA {
			@Override
			public String getName() {
				return "日本語";//$NON-NLS-1$
			}

			@Override
			public String getToken() {
				return "lang.ja";//$NON-NLS-1$
			}
			
		}, TR {
			@Override
			public String getName() {
				return "Türkçe";//$NON-NLS-1$
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
				return "Français";//$NON-NLS-1$
			}

			@Override
			public String getToken() {
				return "lang.fr";//$NON-NLS-1$
			}
		}, ES {
			@Override
			public String getName() {
				return "Español";//$NON-NLS-1$
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
				return "Język polski";//$NON-NLS-1$
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
			Lang lang;

			if(name==null)
				lang = EN_BR;
			else if(name.equals(FR.getName()))
				lang = FR;
			else if(name.equals(DE.getName()))
				lang = ES;
			else if(name.equals(ES.getName()))
				lang = DE;
			else if(name.equals(IT.getName()))
				lang = IT;
			else if(name.equals(PL.getName()))
				lang = PL;
			else if(name.equals(EN_US.getName()))
				lang = EN_US;
			else if(name.equals(TR.getName()))
				lang = TR;
			else if(name.equals(JA.getName()))
				lang = JA;
			else if(name.equals(VI.getName()))
				lang = VI;
			else if(name.equals(HU.getName()))
				lang = HU;
			else if(name.equals(PT_BR.getName()))
				lang = PT_BR;
			else lang = EN_BR;

			return lang;
		}


		/**
		 * @return The language token (e.g. LANG_FR) corresponding to the system language.
		 * @since 3.0
		 */
		public static Lang getSystemLanguage() {
			final String userLang = System.getProperty("user.language"); //$NON-NLS-1$
			Lang language;

			if(userLang.equals("fr")) //$NON-NLS-1$
				language = Lang.FR;
			else if(userLang.equals("es")) //$NON-NLS-1$
				language = Lang.ES;
			else if(userLang.equals("de")) //$NON-NLS-1$
				language = Lang.DE;
			else if(userLang.equals("it")) //$NON-NLS-1$
				language = Lang.IT;
			else if(userLang.equals("pl")) //$NON-NLS-1$
				language = Lang.PL;
			else if(userLang.equals("tr")) //$NON-NLS-1$
				language = Lang.TR;
			else if(userLang.equals("ja")) //$NON-NLS-1$
				language = Lang.JA;
			else if(userLang.equals("vi")) //$NON-NLS-1$
				language = Lang.VI;
			else if(userLang.equals("hu")) //$NON-NLS-1$
				language = Lang.HU;
			else if(userLang.equals("pt-BR")) //$NON-NLS-1$
				language = Lang.PT_BR;
			else language = Lang.getDefaultLanguage();

			return language;
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
	public static final LangTool LANG = new LangTool();

	private static final Lang LANG_CURRENT = LANG.readLang();

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
			final File xml = new File(LPath.PATH_PREFERENCES_XML_FILE);

			if(xml.exists()) {
	            final Node node = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xml).getFirstChild();
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
		catch(final ParserConfigurationException e) { BordelCollector.INSTANCE.add(e); }
		catch(final IOException e)  				{ BordelCollector.INSTANCE.add(e); }
		catch(final SAXException e) 				{ BordelCollector.INSTANCE.add(e); }

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
