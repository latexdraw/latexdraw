/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */
package net.sf.latexdraw.util;

/**
 * The supported languages
 */
public enum Lang {
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
	},
	SI {
		@Override
		public String getName() {
			return "\u0dc3\u0dd2\u0d82\u0dc4\u0dbd";//$NON-NLS-1$
		}

		@Override
		public String getToken() {
			return "lang.si";//$NON-NLS-1$
		}
	},
	SR {
		@Override
		public String getName() {
			return "\u0441\u0440\u043f\u0441\u043a\u0438";//$NON-NLS-1$
		}

		@Override
		public String getToken() {
			return "lang.sr";//$NON-NLS-1$
		}
	},
	HU {
		@Override
		public String getName() {
			return "Magyar";//$NON-NLS-1$
		}

		@Override
		public String getToken() {
			return "lang.hu";//$NON-NLS-1$
		}
	},
	VI {
		@Override
		public String getName() {
			return "Ti\u1ebfng Vi\u1ec7t";//$NON-NLS-1$
		}

		@Override
		public String getToken() {
			return "lang.vi";//$NON-NLS-1$
		}
	},
	PT_BR {
		@Override
		public String getName() {
			return "Portugu\u00eas Brasileiro";//$NON-NLS-1$
		}

		@Override
		public String getToken() {
			return "lang.pt-BR";//$NON-NLS-1$
		}
	},
	JA {
		@Override
		public String getName() {
			return "\u65e5\u672c\u8a9e";//$NON-NLS-1$
		}

		@Override
		public String getToken() {
			return "lang.ja";//$NON-NLS-1$
		}

	},
	TR {
		@Override
		public String getName() {
			return "T\u00fcrk\u00e7e";//$NON-NLS-1$
		}

		@Override
		public String getToken() {
			return "lang.tr";//$NON-NLS-1$
		}
	},
	EN_BR {
		@Override
		public String getName() {
			return "English";//$NON-NLS-1$
		}

		@Override
		public String getToken() {
			return "lang.en";//$NON-NLS-1$
		}
	},
	EN_US {
		@Override
		public String getName() {
			return "English-US";//$NON-NLS-1$
		}

		@Override
		public String getToken() {
			return "lang.en-US";//$NON-NLS-1$
		}
	},
	FR {
		@Override
		public String getName() {
			return "Fran\u00e7ais";//$NON-NLS-1$
		}

		@Override
		public String getToken() {
			return "lang.fr";//$NON-NLS-1$
		}
	},
	ES {
		@Override
		public String getName() {
			return "Espa\u00f1ol";//$NON-NLS-1$
		}

		@Override
		public String getToken() {
			return "lang.es";//$NON-NLS-1$
		}
	},
	DE {
		@Override
		public String getName() {
			return "Deutsch";//$NON-NLS-1$
		}

		@Override
		public String getToken() {
			return "lang.de";//$NON-NLS-1$
		}
	},
	IT {
		@Override
		public String getName() {
			return "Italiano";//$NON-NLS-1$
		}

		@Override
		public String getToken() {
			return "lang.it";//$NON-NLS-1$
		}
	},
	PL {
		@Override
		public String getName() {
			return "J\u0119zyk polski";//$NON-NLS-1$
		}

		@Override
		public String getToken() {
			return "lang.pl";//$NON-NLS-1$
		}
	},
	UK {
		@Override
		public String getName() {
			return "українська мова";//$NON-NLS-1$
		}

		@Override
		public String getToken() {
			return "lang.uk";//$NON-NLS-1$
		}
	},
	zh_CN {
		@Override
		public String getName() {
			return "\u4e2d\u6587";//$NON-NLS-1$
		}

		@Override
		public String getToken() {
			return "lang.zh_CN";//$NON-NLS-1$
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
	 * @return The Lang object corresponding to the given language name, or the default language is
	 *         null or not found.
	 * @since 3.0
	 */
	public static Lang getLanguage(final String name) {
//		final Lang lang = LangTool.INSTANCE.getMapLangs().get(name);
//		return lang == null?EN_BR:lang;
		return EN_BR;
	}

	/**
	 * @return The language token (e.g. LANG_FR) corresponding to the system language.
	 * @since 3.0
	 */
	public static Lang getSystemLanguage() {
//		final Lang language = LangTool.INSTANCE.getMapLangs().get(System.getProperty("user.language"));//$NON-NLS-1$
//		return language == null?Lang.getDefaultLanguage():language;
		return Lang.getDefaultLanguage();
	}
}
