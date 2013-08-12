package net.sf.latexdraw.glib.views.latex;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * This class Defines colours used by pstricks.<br>
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
 * 04/06/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public final class DviPsColors  {
	public static final double MAX = 255.;

	public final static Color TEAL			 = new Color(0f, 0.5f, 0.5f);
	public final static Color LIME			 = new Color(0.75f, 1f, 0f);
	public final static Color GREEN_YELLOW   = new Color(216,255, 79);
	public final static Color YELLOW         = new Color(255,255,  0);
	public final static Color GOLDEN_ROD     = new Color(255,229, 40);
	public final static Color DANDELION      = new Color(255,181, 40);
	public final static Color APRICOT        = new Color(255,173,122);
	public final static Color PEACH          = new Color(216,127, 76);
	public final static Color MELON          = new Color(255,137,127);
	public final static Color YELLOW_ORANGE  = new Color(216,147,  0);
	public final static Color ORANGE         = new Color(255, 99, 33);
	public final static Color BURNT_ORANGE   = new Color(255,124,  0);
	public final static Color BITTERSWEET    = new Color(193,  2,  0);
	public final static Color RED_ORANGE     = new Color(255, 58, 33);
	public final static Color MAHOGANY       = new Color(165,  0,  0);
	public final static Color MAROON         = new Color(173,  0,  0);
	public final static Color BRICKRED       = new Color(183,  0,  0);
	public final static Color RED            = new Color(255,  0,  0);
	public final static Color ORANGERED      = new Color(255,  0,127);
	public final static Color RUBINERED      = new Color(255,  0,221);
	public final static Color WILDSTRAWBERRY = new Color(255, 10,155);
	public final static Color SALMON         = new Color(255,119,158);
	public final static Color CARNATIONPINK  = new Color(255, 94,255);
	public final static Color MAGENTA        = new Color(255,  0,255);
	public final static Color VIOLETRED      = new Color(255, 48,255);
	public final static Color RHODAMINE      = new Color(255, 45,255);
	public final static Color MULBERRY       = new Color(163, 20,149);
	public final static Color REDVIOLET      = new Color(150,  0,168);
	public final static Color FUSHIA         = new Color(114,  2,234);
	public final static Color LAVENDER       = new Color(255,132,255);
	public final static Color THISTLE        = new Color(224,104,255);
	public final static Color ORCHID         = new Color(173, 91,255);
	public final static Color DARKORCHID     = new Color(153, 51,204);
	public final static Color PURPLE         = new Color(140, 35,255);
	public final static Color PLUM           = new Color(127,  0,255);
	public final static Color VIOLET         = new Color( 53, 30,255);
	public final static Color ROYALPURPLE    = new Color( 63, 25,255);
	public final static Color BLUEVIOLET     = new Color( 25, 12,244);
	public final static Color PERIWINKLE     = new Color(109,114,255);
	public final static Color CADETBLUE      = new Color(140, 35,255);
	public final static Color CORNFLOWERBLUE = new Color( 89,221,255);
	public final static Color MIDNIGHTBLUE   = new Color(  0,112,145);
	public final static Color NAVYBLUE       = new Color( 15,117,255);
	public final static Color ROYALBLUE      = new Color(  0,127,255);
	public final static Color BLUE           = new Color(  0,  0,255);
	public final static Color CERULEAN       = new Color( 15,226,255);
	public final static Color CYAN           = new Color(  0,255,255);
	public final static Color PROCESSBLUE    = new Color( 10,255,255);
	public final static Color SKYBLUE        = new Color( 96,255,224);
	public final static Color TURQUOISE      = new Color( 38,255,204);
	public final static Color TEALBLUE       = new Color( 30,249,163);
	public final static Color AQUAMARINE     = new Color( 45,255,178);
	public final static Color BLUEGREEN      = new Color( 38,255,170);
	public final static Color EMERALD        = new Color(  0,255,127);
	public final static Color JUNGLEGREEN    = new Color(  2,255,122);
	public final static Color SEAGREEN       = new Color( 79,255,127);
	public final static Color GREEN          = new Color(  0,255,  0);
	public final static Color FORESTGREEN    = new Color(  0,224,  0);
	public final static Color PINEGREEN      = new Color(  0,191, 40);
	public final static Color LIMEGREEN      = new Color(127,255,  0);
	public final static Color YELLOWGREEN    = new Color(142,255, 66);
	public final static Color SPRINGGREEN    = new Color(188,255, 61);
	public final static Color OLIVEGREEN     = new Color(  0,153,  0);
	public final static Color RAWSIENNA      = new Color(140,  0,  0);
	public final static Color SEPIA          = new Color( 76,  0,  0);
	public final static Color BROWN          = new Color(102,  0,  0);
	public final static Color TAN            = new Color(219,147,112);
	public final static Color GRAY           = new Color(127,127,127);
	public final static Color BLACK          = new Color(  0,  0,  0);
	public final static Color WHITE          = new Color(255,255,255);
	public final static Color PINK           = new Color(255,192,203);
	public final static Color OLIVE          = new Color(0.5f, 0.5f, 0f);


	public final static String N_LIME		    = "lime"; //$NON-NLS-1$
	public final static String N_TEAL		    = "teal"; //$NON-NLS-1$
	public final static String N_GREEN_YELLOW   = "GreenYellow"; //$NON-NLS-1$
	public final static String N_YELLOW         = "Yellow"; //$NON-NLS-1$
	public final static String N_GOLDEN_ROD     = "Goldenrod"; //$NON-NLS-1$
	public final static String N_DANDELION      = "Dandelion"; //$NON-NLS-1$
	public final static String N_APRICOT        = "Apricot"; //$NON-NLS-1$
	public final static String N_PEACH          = "Peach"; //$NON-NLS-1$
	public final static String N_MELON          = "Melon"; //$NON-NLS-1$
	public final static String N_YELLOW_ORANGE  = "YellowOrange"; //$NON-NLS-1$
	public final static String N_ORANGE         = "Orange"; //$NON-NLS-1$
	public final static String N_BURNT_ORANGE   = "BurntOrange"; //$NON-NLS-1$
	public final static String N_BITTERSWEET    = "Bittersweet"; //$NON-NLS-1$
	public final static String N_RED_ORANGE     = "RedOrange"; //$NON-NLS-1$
	public final static String N_MAHOGANY       = "Mahogany"; //$NON-NLS-1$
	public final static String N_MAROON         = "Maroon"; //$NON-NLS-1$
	public final static String N_BRICKRED       = "BrickRed"; //$NON-NLS-1$
	public final static String N_RED            = "Red"; //$NON-NLS-1$
	public final static String N_ORANGERED      = "OrangeRed"; //$NON-NLS-1$
	public final static String N_RUBINERED      = "RubineRed"; //$NON-NLS-1$
	public final static String N_WILDSTRAWBERRY = "WildStrawberry"; //$NON-NLS-1$
	public final static String N_SALMON         = "Salmon"; //$NON-NLS-1$
	public final static String N_CARNATIONPINK  = "CarnationPink"; //$NON-NLS-1$
	public final static String N_MAGENTA        = "Magenta"; //$NON-NLS-1$
	public final static String N_VIOLETRED      = "VioletRed"; //$NON-NLS-1$
	public final static String N_RHODAMINE      = "Rhodamine"; //$NON-NLS-1$
	public final static String N_MULBERRY       = "Mulberry"; //$NON-NLS-1$
	public final static String N_REDVIOLET      = "RedViolet"; //$NON-NLS-1$
	public final static String N_FUSHIA         = "Fuchsia"; //$NON-NLS-1$
	public final static String N_LAVENDER       = "Lavender"; //$NON-NLS-1$
	public final static String N_THISTLE        = "Thistle"; //$NON-NLS-1$
	public final static String N_ORCHID         = "Orchid"; //$NON-NLS-1$
	public final static String N_DARKORCHID     = "DarkOrchid"; //$NON-NLS-1$
	public final static String N_PURPLE         = "Purple"; //$NON-NLS-1$
	public final static String N_PLUM           = "Plum"; //$NON-NLS-1$
	public final static String N_VIOLET         = "Violet"; //$NON-NLS-1$
	public final static String N_ROYALPURPLE    = "RoyalPurple"; //$NON-NLS-1$
	public final static String N_BLUEVIOLET     = "BlueViolet"; //$NON-NLS-1$
	public final static String N_PERIWINKLE     = "Periwinkle"; //$NON-NLS-1$
	public final static String N_CADETBLUE      = "CadetBlue"; //$NON-NLS-1$
	public final static String N_CORNFLOWERBLUE = "CornflowerBlue"; //$NON-NLS-1$
	public final static String N_MIDNIGHTBLUE   = "MidnightBlue"; //$NON-NLS-1$
	public final static String N_NAVYBLUE       = "NavyBlue"; //$NON-NLS-1$
	public final static String N_ROYALBLUE      = "RoyalBlue"; //$NON-NLS-1$
	public final static String N_BLUE           = "Blue"; //$NON-NLS-1$
	public final static String N_CERULEAN       = "Cerulean"; //$NON-NLS-1$
	public final static String N_CYAN           = "Cyan"; //$NON-NLS-1$
	public final static String N_PROCESSBLUE    = "ProcessBlue"; //$NON-NLS-1$
	public final static String N_SKYBLUE        = "SkyBlue"; //$NON-NLS-1$
	public final static String N_TURQUOISE      = "Turquoise"; //$NON-NLS-1$
	public final static String N_TEALBLUE       = "TealBlue"; //$NON-NLS-1$
	public final static String N_AQUAMARINE     = "Aquamarine"; //$NON-NLS-1$
	public final static String N_BLUEGREEN      = "BlueGreen"; //$NON-NLS-1$
	public final static String N_EMERALD        = "Emerald"; //$NON-NLS-1$
	public final static String N_JUNGLEGREEN    = "JungleGreen"; //$NON-NLS-1$
	public final static String N_SEAGREEN       = "SeaGreen"; //$NON-NLS-1$
	public final static String N_GREEN          = "Green"; //$NON-NLS-1$
	public final static String N_FORESTGREEN    = "ForestGreen"; //$NON-NLS-1$
	public final static String N_PINEGREEN      = "PineGreen"; //$NON-NLS-1$
	public final static String N_LIMEGREEN      = "LimeGreen"; //$NON-NLS-1$
	public final static String N_YELLOWGREEN    = "YellowGreen"; //$NON-NLS-1$
	public final static String N_SPRINGGREEN    = "SpringGreen"; //$NON-NLS-1$
	public final static String N_OLIVEGREEN     = "OliveGreen"; //$NON-NLS-1$
	public final static String N_RAWSIENNA      = "RawSienna"; //$NON-NLS-1$
	public final static String N_SEPIA          = "Sepia"; //$NON-NLS-1$
	public final static String N_BROWN          = "Brown"; //$NON-NLS-1$
	public final static String N_TAN            = "Tan"; //$NON-NLS-1$
	public final static String N_GRAY           = "Gray"; //$NON-NLS-1$
	public final static String N_BLACK          = "Black"; //$NON-NLS-1$
	public final static String N_WHITE          = "White"; //$NON-NLS-1$

	public final static String N_CYAN_2         = "cyan"; //$NON-NLS-1$
	public final static String N_GRAY_2         = "gray"; //$NON-NLS-1$
	public final static String N_BLACK_2        = "black"; //$NON-NLS-1$
	public final static String N_WHITE_2        = "white"; //$NON-NLS-1$
	public final static String N_YELLOW_2       = "yellow"; //$NON-NLS-1$
	public final static String N_VIOLET_2       = "violet"; //$NON-NLS-1$
	public final static String N_BLUE_2         = "blue"; //$NON-NLS-1$
	public final static String N_PURPLE_2       = "purple"; //$NON-NLS-1$
	public final static String N_RED_2          = "red"; //$NON-NLS-1$
	public final static String N_ORANGE_2       = "orange"; //$NON-NLS-1$
	public final static String N_GREEN_2        = "green"; //$NON-NLS-1$
	public final static String N_MAGENTA_2      = "magenta"; //$NON-NLS-1$
	public final static String N_BROWN_2        = "brown"; //$NON-NLS-1$
	public final static String N_DARK_GRAY      = "darkgray"; //$NON-NLS-1$
	public final static String N_LIGHT_GRAY     = "lightgray"; //$NON-NLS-1$
	public final static String N_PINK     		= "pink"; //$NON-NLS-1$
	public final static String N_OLIVE			= "olive"; //$NON-NLS-1$

	public static final DviPsColors INSTANCE 	= new DviPsColors();

	private Map<String, Color> colourHT 		= new HashMap<>();

	private Map<Color, String> nameColourHT 	= new HashMap<>();

	/** The colours defined by the user and their name. */
	private Map<String, Color> userColourHT 		= new HashMap<>();

	/** The colours defined by the user and their name. */
	private Map<Color, String> userNameColourHT 	= new HashMap<>();

	/** The counter is used to name the user defined colours. */
	private int ctColours;


	private DviPsColors() {
		super();

		createColourHashTable();
		createNameColourHashTable();
		ctColours = 0;
	}



	/**
	 * Creates the hashTable {@link #nameColourHT}.
	 * @since 1.9.2
	 */
	private void createColourHashTable() {
		colourHT.clear();
		colourHT.put(N_LIME, LIME);
		colourHT.put(N_TEAL, TEAL);
		colourHT.put(N_OLIVE, OLIVE);
		colourHT.put(N_CYAN_2, CYAN);
		colourHT.put(N_GRAY_2, GRAY);
		colourHT.put(N_BLACK_2, BLACK);
		colourHT.put(N_WHITE_2, WHITE);
		colourHT.put(N_YELLOW_2, YELLOW);
		colourHT.put(N_VIOLET_2, VIOLET);
		colourHT.put(N_BLUE_2, BLUE);
		colourHT.put(N_PURPLE_2, PURPLE);
		colourHT.put(N_RED_2, RED);
		colourHT.put(N_ORANGE_2, ORANGE);
		colourHT.put(N_GREEN_2, GREEN);
		colourHT.put(N_MAGENTA_2, MAGENTA);
		colourHT.put(N_BROWN_2, BROWN);
		colourHT.put(N_PINK, PINK);
		colourHT.put(N_GRAY, GRAY);
		colourHT.put(N_BLACK, BLACK);
		colourHT.put(N_WHITE, WHITE);
		colourHT.put(N_RED, RED);
		colourHT.put(N_GREEN, GREEN);
		colourHT.put(N_BLUE, BLUE);
		colourHT.put(N_VIOLET, VIOLET);
		colourHT.put(N_ORANGE, ORANGE);
		colourHT.put(N_PURPLE, PURPLE);
		colourHT.put(N_DARK_GRAY, Color.DARK_GRAY);
		colourHT.put(N_LIGHT_GRAY, Color.LIGHT_GRAY);
		colourHT.put(N_PINK, PINK);
		colourHT.put(N_GREEN_YELLOW, GREEN_YELLOW);
		colourHT.put(N_YELLOW, YELLOW);
		colourHT.put(N_GOLDEN_ROD, GOLDEN_ROD);
		colourHT.put(N_DANDELION, DANDELION);
		colourHT.put(N_APRICOT, APRICOT);
		colourHT.put(N_PEACH, PEACH);
		colourHT.put(N_MELON, MELON);
		colourHT.put(N_YELLOW_ORANGE, YELLOW_ORANGE);
		colourHT.put(N_BURNT_ORANGE, BURNT_ORANGE);
		colourHT.put(N_BITTERSWEET, BITTERSWEET);
		colourHT.put(N_RED_ORANGE, RED_ORANGE);
		colourHT.put(N_MAHOGANY, MAHOGANY);
		colourHT.put(N_MAROON, MAROON);
		colourHT.put(N_BRICKRED, BRICKRED);
		colourHT.put(N_ORANGERED, ORANGERED);
		colourHT.put(N_RUBINERED, RUBINERED);
		colourHT.put(N_WILDSTRAWBERRY, WILDSTRAWBERRY);
		colourHT.put(N_SALMON, SALMON);
		colourHT.put(N_CARNATIONPINK, CARNATIONPINK);
		colourHT.put(N_MAGENTA, MAGENTA);
		colourHT.put(N_VIOLETRED, VIOLETRED);
		colourHT.put(N_RHODAMINE, RHODAMINE);
		colourHT.put(N_MULBERRY, MULBERRY);
		colourHT.put(N_REDVIOLET, REDVIOLET);
		colourHT.put(N_FUSHIA, FUSHIA);
		colourHT.put(N_LAVENDER, LAVENDER);
		colourHT.put(N_THISTLE, THISTLE);
		colourHT.put(N_ORCHID, ORCHID);
		colourHT.put(N_DARKORCHID, DARKORCHID);
		colourHT.put(N_PLUM, PLUM);
		colourHT.put(N_ROYALPURPLE, ROYALPURPLE);
		colourHT.put(N_BLUEVIOLET, BLUEVIOLET);
		colourHT.put(N_PERIWINKLE, PERIWINKLE);
		colourHT.put(N_CADETBLUE, CADETBLUE);
		colourHT.put(N_CORNFLOWERBLUE, CORNFLOWERBLUE);
		colourHT.put(N_MIDNIGHTBLUE, MIDNIGHTBLUE);
		colourHT.put(N_NAVYBLUE, NAVYBLUE);
		colourHT.put(N_ROYALBLUE, ROYALBLUE);
		colourHT.put(N_CERULEAN, CERULEAN);
		colourHT.put(N_CYAN, CYAN);
		colourHT.put(N_PROCESSBLUE, PROCESSBLUE);
		colourHT.put(N_SKYBLUE, SKYBLUE);
		colourHT.put(N_TURQUOISE, TURQUOISE);
		colourHT.put(N_TEALBLUE, TEALBLUE);
		colourHT.put(N_AQUAMARINE, AQUAMARINE);
		colourHT.put(N_BLUEGREEN, BLUEGREEN);
		colourHT.put(N_EMERALD, EMERALD);
		colourHT.put(N_JUNGLEGREEN, JUNGLEGREEN);
		colourHT.put(N_SEAGREEN, SEAGREEN);
		colourHT.put(N_FORESTGREEN, FORESTGREEN);
		colourHT.put(N_PINEGREEN, PINEGREEN);
		colourHT.put(N_LIMEGREEN, LIMEGREEN);
		colourHT.put(N_YELLOWGREEN, YELLOWGREEN);
		colourHT.put(N_SPRINGGREEN, SPRINGGREEN);
		colourHT.put(N_OLIVEGREEN, OLIVEGREEN);
		colourHT.put(N_RAWSIENNA, RAWSIENNA);
		colourHT.put(N_SEPIA, SEPIA);
		colourHT.put(N_BROWN, BROWN);
		colourHT.put(N_TAN, TAN);
	}



	/**
	 * Creates the hashTable {@link #colourHT}.
	 * @since 1.9.2
	 */
	private void createNameColourHashTable() {
		nameColourHT.clear();
		nameColourHT.put(LIME, N_LIME);
		nameColourHT.put(TEAL, N_TEAL);
		nameColourHT.put(OLIVE, N_OLIVE);
		nameColourHT.put(GRAY, N_GRAY_2);
		nameColourHT.put(BLACK, N_BLACK_2);
		nameColourHT.put(WHITE, N_WHITE_2);
		nameColourHT.put(RED, N_RED_2);
		nameColourHT.put(GREEN, N_GREEN_2);
		nameColourHT.put(BLUE, N_BLUE_2);
		nameColourHT.put(VIOLET, N_VIOLET_2);
		nameColourHT.put(ORANGE, N_ORANGE_2);
		nameColourHT.put(PURPLE, N_PURPLE_2);
		nameColourHT.put(Color.DARK_GRAY, N_DARK_GRAY);
		nameColourHT.put(Color.LIGHT_GRAY, N_LIGHT_GRAY);
		nameColourHT.put(PINK, N_PINK);
		nameColourHT.put(GREEN_YELLOW, N_GREEN_YELLOW);
		nameColourHT.put(YELLOW, N_YELLOW_2);
		nameColourHT.put(GOLDEN_ROD, N_GOLDEN_ROD);
		nameColourHT.put(DANDELION, N_DANDELION);
		nameColourHT.put(APRICOT, N_APRICOT);
		nameColourHT.put(PEACH, N_PEACH);
		nameColourHT.put(MELON, N_MELON);
		nameColourHT.put(YELLOW_ORANGE, N_YELLOW_ORANGE);
		nameColourHT.put(BURNT_ORANGE, N_BURNT_ORANGE);
		nameColourHT.put(BITTERSWEET, N_BITTERSWEET);
		nameColourHT.put(RED_ORANGE, N_RED_ORANGE);
		nameColourHT.put(MAHOGANY, N_MAHOGANY);
		nameColourHT.put(MAROON, N_MAROON);
		nameColourHT.put(BRICKRED, N_BRICKRED);
		nameColourHT.put(ORANGERED, N_ORANGERED);
		nameColourHT.put(RUBINERED, N_RUBINERED);
		nameColourHT.put(WILDSTRAWBERRY, N_WILDSTRAWBERRY);
		nameColourHT.put(SALMON, N_SALMON);
		nameColourHT.put(CARNATIONPINK, N_CARNATIONPINK);
		nameColourHT.put(MAGENTA, N_MAGENTA_2);
		nameColourHT.put(VIOLETRED, N_VIOLETRED);
		nameColourHT.put(RHODAMINE, N_RHODAMINE);
		nameColourHT.put(MULBERRY, N_MULBERRY);
		nameColourHT.put(REDVIOLET, N_REDVIOLET);
		nameColourHT.put(FUSHIA, N_FUSHIA);
		nameColourHT.put(LAVENDER, N_LAVENDER);
		nameColourHT.put(THISTLE, N_THISTLE);
		nameColourHT.put(ORCHID, N_ORCHID);
		nameColourHT.put(DARKORCHID, N_DARKORCHID);
		nameColourHT.put(PLUM, N_PLUM);
		nameColourHT.put(ROYALPURPLE, N_ROYALPURPLE);
		nameColourHT.put(BLUEVIOLET, N_BLUEVIOLET);
		nameColourHT.put(PERIWINKLE, N_PERIWINKLE);
		nameColourHT.put(CADETBLUE, N_CADETBLUE);
		nameColourHT.put(CORNFLOWERBLUE ,N_CORNFLOWERBLUE);
		nameColourHT.put(MIDNIGHTBLUE, N_MIDNIGHTBLUE);
		nameColourHT.put(NAVYBLUE, N_NAVYBLUE);
		nameColourHT.put(ROYALBLUE, N_ROYALBLUE);
		nameColourHT.put(CERULEAN, N_CERULEAN);
		nameColourHT.put(CYAN, N_CYAN_2);
		nameColourHT.put(PROCESSBLUE, N_PROCESSBLUE);
		nameColourHT.put(SKYBLUE, N_SKYBLUE);
		nameColourHT.put(TURQUOISE, N_TURQUOISE);
		nameColourHT.put(TEALBLUE, N_TEALBLUE);
		nameColourHT.put(AQUAMARINE, N_AQUAMARINE);
		nameColourHT.put(BLUEGREEN, N_BLUEGREEN);
		nameColourHT.put(EMERALD, N_EMERALD);
		nameColourHT.put(JUNGLEGREEN, N_JUNGLEGREEN);
		nameColourHT.put(SEAGREEN, N_SEAGREEN);
		nameColourHT.put(FORESTGREEN, N_FORESTGREEN);
		nameColourHT.put(PINEGREEN, N_PINEGREEN);
		nameColourHT.put(LIMEGREEN, N_LIMEGREEN);
		nameColourHT.put(YELLOWGREEN, N_YELLOWGREEN);
		nameColourHT.put(SPRINGGREEN, N_SPRINGGREEN);
		nameColourHT.put(OLIVEGREEN, N_OLIVEGREEN);
		nameColourHT.put(RAWSIENNA, N_RAWSIENNA);
		nameColourHT.put(SEPIA, N_SEPIA);
		nameColourHT.put(BROWN, N_BROWN_2);
		nameColourHT.put(TAN, N_TAN);
	}


	/**
	 * @param name The name of the searched colour.
	 * @return The corresponding predefined colour or null.
	 * @since 3.0
	 */
	public Color getPredefinedColour(final String name) {
		return colourHT.get(name);
	}



	/**
	 * Allows to get the name of  a given colour.
	 * @param colour The colour that we want the name .
	 * @return The name of the colour : null if it can not be found.
	 */
	public String getColourName(final Color colour) {
		if(colour==null)
			return null;

		String name = nameColourHT.get(colour);

		if(name==null)
			name = userNameColourHT.get(colour);

		return name;
	}




	/**
	 * Allows to get a colour with its name.
	 * @param name The name of the wanted colour.
	 * @return The colour, null if the name is invalid of do not correspond at any colour.
	 */
	public Color getColour(final String name) {
		if(name==null || name.length()==0) return null;

		Color c = userColourHT.get(name);

		if(c==null)
			c = colourHT.get(name);

		return c;
	}




	/**
	 * Adds a colour defined by the user.
	 * @param colour The colour to add.
	 * @return The name of this colour.
	 */
	public String addUserColour(final Color colour) {
		String name;

		if(colour!=null) {
			name = generateColourName();
			addUserColour(colour, name);
		}else name = null;

		return name;
	}


	/**
	 * Adds a colour defined by the user.
	 * @param colour The colour to add.
	 * @param name The name of the colour.
	 */
	public void addUserColour(final Color colour, final String name) {
		if(colour!=null && name!=null && name.length()>0) {
			userColourHT.put(name, colour);
			userNameColourHT.put(colour, name);
		}
	}


	/**
	 * @return A unique name for a user defined colour.
	 * @since 3.0
	 */
	protected String generateColourName() {
		return "colour" + ctColours++; //$NON-NLS-1$
	}


	/**
	 * Allows to get the PSTricks code of a given colour.
	 * @param colourName The name of the colour used to generate the code.
	 * @return The code of the colour or an empty string if the given colour is not valid.
	 */
	public String getUsercolourCode(final String colourName) {
		final Color colour = getColour(colourName);
		final String code;

		if(colour==null)
			code = ""; //$NON-NLS-1$
		else
			code = "\\definecolor{" + colourName + "}{rgb}{" + //$NON-NLS-1$ //$NON-NLS-2$
					(float)(colour.getRed()/MAX) + ',' + (float)(colour.getGreen()/MAX) +
					',' + (float)(colour.getBlue()/MAX) + "}\n"; //$NON-NLS-1$

		return code;
	}


	/**
	 * Converts an HTML (i.e. hexa) colour to an rgb one.
	 * @param hexaCode The hexadecimal code of the colour.
	 * @return The corresponding rgb colour.
	 * @throws IllegalArgumentException If the given argument is not valid (null or its length lesser than 8 characters).
	 * @since 3.0
	 */
	public Color convertHTML2rgb(String hexaCode) {
		if(hexaCode==null || hexaCode.length()<7)
			throw new IllegalArgumentException(hexaCode);

		 return new Color(Integer.valueOf(hexaCode.substring(1, 3), 16),
		            Integer.valueOf(hexaCode.substring(3, 5), 16),
		            Integer.valueOf(hexaCode.substring(5), 16));
	}


	/**
	 * Converts an RGB [0-255] colour to an rgb [0-1] one.
	 * @param r The red level between 0 and 255.
	 * @param g The green level between 0 and 255.
	 * @param b The blue level between 0 and 255.
	 * @return The corresponding rgb colour.
	 * @throws IllegalArgumentException If one of the given arguments is not valid.
	 * @since 3.0
	 */
	public Color convertRGB2rgb(final double r, final double g, final double b) {
		if(r<0 || g<0 || b<0)
			throw new IllegalArgumentException(String.valueOf(r) + ""  + String.valueOf(g) + " " + String.valueOf(b));

		final float factor = 1f/255f;
		return new Color((float)r*factor, (float)g*factor, (float)b*factor);
	}


	/**
	 * Converts a CMYK colour to an rgb one.
	 * @param c The c level between 0 and 1.
	 * @param m The m level between 0 and 1.
	 * @param y The y level between 0 and 1.
	 * @param k The k level between 0 and 1.
	 * @return The corresponding rgb colour.
	 * @since 2.0.0
	 */
	public Color convertcmyk2rgb(final double c, final double m, final double y, final double k) {
		if(c < 0 || c > 1)
			throw new IllegalArgumentException(String.valueOf(c));

		if(m < 0 || m > 1)
			throw new IllegalArgumentException(String.valueOf(m));

		if(y < 0 || y > 1)
			throw new IllegalArgumentException(String.valueOf(y));

		if(k < 0 || k > 1)
			throw new IllegalArgumentException(String.valueOf(k));

		return new Color((float)(1 - (c * (1 - k) + k)), (float)(1-(m*(1-k)+k)), (float)(1-(y*(1-k)+k)));
	}



	/**
	 * Converts a gray colour in an rgb one.
	 * @param g The gray level between 0 an 1.
	 * @return The corresponding rgb colour.
	 * @since 2.0.0
	 */
    public Color convertgray2rgb(final double g) {
		if(g < 0 || g > 1)
			throw new IllegalArgumentException(String.valueOf(g));

		return new Color((float)g, (float)g, (float)g);
	}
}
