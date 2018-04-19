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
package net.sf.latexdraw.view.latex;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.Color;

/**
 * Colours used by pstricks.
 * @author Arnaud BLOUIN
 */
public final class DviPsColors {
	public static final Color TEAL = ShapeFactory.INST.createColor(0.0, 0.5, 0.5);
	public static final Color LIME = ShapeFactory.INST.createColor(0.75, 1, 0);
	public static final Color GREEN_YELLOW = ShapeFactory.INST.createColorInt(216, 255, 79);
	public static final Color YELLOW = ShapeFactory.INST.createColorInt(255, 255, 0);
	public static final Color GOLDEN_ROD = ShapeFactory.INST.createColorInt(255, 229, 40);
	public static final Color DANDELION = ShapeFactory.INST.createColorInt(255, 181, 40);
	public static final Color APRICOT = ShapeFactory.INST.createColorInt(255, 173, 122);
	public static final Color PEACH = ShapeFactory.INST.createColorInt(216, 127, 76);
	public static final Color MELON = ShapeFactory.INST.createColorInt(255, 137, 127);
	public static final Color YELLOW_ORANGE = ShapeFactory.INST.createColorInt(216, 147, 0);
	public static final Color ORANGE = ShapeFactory.INST.createColorInt(255, 99, 33);
	public static final Color BURNT_ORANGE = ShapeFactory.INST.createColorInt(255, 124, 0);
	public static final Color BITTERSWEET = ShapeFactory.INST.createColorInt(193, 2, 0);
	public static final Color RED_ORANGE = ShapeFactory.INST.createColorInt(255, 58, 33);
	public static final Color MAHOGANY = ShapeFactory.INST.createColorInt(165, 0, 0);
	public static final Color MAROON = ShapeFactory.INST.createColorInt(173, 0, 0);
	public static final Color BRICKRED = ShapeFactory.INST.createColorInt(183, 0, 0);
	public static final Color RED = ShapeFactory.INST.createColorInt(255, 0, 0);
	public static final Color ORANGERED = ShapeFactory.INST.createColorInt(255, 0, 127);
	public static final Color RUBINERED = ShapeFactory.INST.createColorInt(255, 0, 221);
	public static final Color WILDSTRAWBERRY = ShapeFactory.INST.createColorInt(255, 10, 155);
	public static final Color SALMON = ShapeFactory.INST.createColorInt(255, 119, 158);
	public static final Color CARNATIONPINK = ShapeFactory.INST.createColorInt(255, 94, 255);
	public static final Color MAGENTA = ShapeFactory.INST.createColorInt(255, 0, 255);
	public static final Color VIOLETRED = ShapeFactory.INST.createColorInt(255, 48, 255);
	public static final Color RHODAMINE = ShapeFactory.INST.createColorInt(255, 45, 255);
	public static final Color MULBERRY = ShapeFactory.INST.createColorInt(163, 20, 149);
	public static final Color REDVIOLET = ShapeFactory.INST.createColorInt(150, 0, 168);
	public static final Color FUSHIA = ShapeFactory.INST.createColorInt(114, 2, 234);
	public static final Color LAVENDER = ShapeFactory.INST.createColorInt(255, 132, 255);
	public static final Color THISTLE = ShapeFactory.INST.createColorInt(224, 104, 255);
	public static final Color ORCHID = ShapeFactory.INST.createColorInt(173, 91, 255);
	public static final Color DARKORCHID = ShapeFactory.INST.createColorInt(153, 51, 204);
	public static final Color PURPLE = ShapeFactory.INST.createColorInt(140, 35, 255);
	public static final Color PLUM = ShapeFactory.INST.createColorInt(127, 0, 255);
	public static final Color VIOLET = ShapeFactory.INST.createColorInt(53, 30, 255);
	public static final Color ROYALPURPLE = ShapeFactory.INST.createColorInt(63, 25, 255);
	public static final Color BLUEVIOLET = ShapeFactory.INST.createColorInt(25, 12, 244);
	public static final Color PERIWINKLE = ShapeFactory.INST.createColorInt(109, 114, 255);
	public static final Color CADETBLUE = ShapeFactory.INST.createColorInt(140, 35, 255);
	public static final Color CORNFLOWERBLUE = ShapeFactory.INST.createColorInt(89, 221, 255);
	public static final Color MIDNIGHTBLUE = ShapeFactory.INST.createColorInt(0, 112, 145);
	public static final Color NAVYBLUE = ShapeFactory.INST.createColorInt(15, 117, 255);
	public static final Color ROYALBLUE = ShapeFactory.INST.createColorInt(0, 127, 255);
	public static final Color BLUE = ShapeFactory.INST.createColorInt(0, 0, 255);
	public static final Color CERULEAN = ShapeFactory.INST.createColorInt(15, 226, 255);
	public static final Color CYAN = ShapeFactory.INST.createColorInt(0, 255, 255);
	public static final Color PROCESSBLUE = ShapeFactory.INST.createColorInt(10, 255, 255);
	public static final Color SKYBLUE = ShapeFactory.INST.createColorInt(96, 255, 224);
	public static final Color TURQUOISE = ShapeFactory.INST.createColorInt(38, 255, 204);
	public static final Color TEALBLUE = ShapeFactory.INST.createColorInt(30, 249, 163);
	public static final Color AQUAMARINE = ShapeFactory.INST.createColorInt(45, 255, 178);
	public static final Color BLUEGREEN = ShapeFactory.INST.createColorInt(38, 255, 170);
	public static final Color EMERALD = ShapeFactory.INST.createColorInt(0, 255, 127);
	public static final Color JUNGLEGREEN = ShapeFactory.INST.createColorInt(2, 255, 122);
	public static final Color SEAGREEN = ShapeFactory.INST.createColorInt(79, 255, 127);
	public static final Color GREEN = ShapeFactory.INST.createColorInt(0, 255, 0);
	public static final Color FORESTGREEN = ShapeFactory.INST.createColorInt(0, 224, 0);
	public static final Color PINEGREEN = ShapeFactory.INST.createColorInt(0, 191, 40);
	public static final Color LIMEGREEN = ShapeFactory.INST.createColorInt(127, 255, 0);
	public static final Color YELLOWGREEN = ShapeFactory.INST.createColorInt(142, 255, 66);
	public static final Color SPRINGGREEN = ShapeFactory.INST.createColorInt(188, 255, 61);
	public static final Color OLIVEGREEN = ShapeFactory.INST.createColorInt(0, 153, 0);
	public static final Color RAWSIENNA = ShapeFactory.INST.createColorInt(140, 0, 0);
	public static final Color SEPIA = ShapeFactory.INST.createColorInt(76, 0, 0);
	public static final Color BROWN = ShapeFactory.INST.createColorInt(102, 0, 0);
	public static final Color TAN = ShapeFactory.INST.createColorInt(219, 147, 112);
	public static final Color GRAY = ShapeFactory.INST.createColorInt(127, 127, 127);
	public static final Color BLACK = ShapeFactory.INST.createColor(0, 0, 0);
	public static final Color WHITE = ShapeFactory.INST.createColorInt(255, 255, 255);
	public static final Color PINK = ShapeFactory.INST.createColorInt(255, 192, 203);
	public static final Color DARKGRAY = ShapeFactory.INST.createColorInt(169, 169, 169);
	public static final Color LIGHTGRAY = ShapeFactory.INST.createColorInt(211, 211, 211);
	public static final Color OLIVE = ShapeFactory.INST.createColor(0.5, 0.5, 0);


	public static final String N_LIME = "lime"; //NON-NLS
	public static final String N_TEAL = "teal"; //NON-NLS
	public static final String N_GREEN_YELLOW = "GreenYellow"; //NON-NLS
	public static final String N_YELLOW = "Yellow"; //NON-NLS
	public static final String N_GOLDEN_ROD = "Goldenrod"; //NON-NLS
	public static final String N_DANDELION = "Dandelion"; //NON-NLS
	public static final String N_APRICOT = "Apricot"; //NON-NLS
	public static final String N_PEACH = "Peach"; //NON-NLS
	public static final String N_MELON = "Melon"; //NON-NLS
	public static final String N_YELLOW_ORANGE = "YellowOrange"; //NON-NLS
	public static final String N_ORANGE = "Orange"; //NON-NLS
	public static final String N_BURNT_ORANGE = "BurntOrange"; //NON-NLS
	public static final String N_BITTERSWEET = "Bittersweet"; //NON-NLS
	public static final String N_RED_ORANGE = "RedOrange"; //NON-NLS
	public static final String N_MAHOGANY = "Mahogany"; //NON-NLS
	public static final String N_MAROON = "Maroon"; //NON-NLS
	public static final String N_BRICKRED = "BrickRed"; //NON-NLS
	public static final String N_RED = "Red"; //NON-NLS
	public static final String N_ORANGERED = "OrangeRed"; //NON-NLS
	public static final String N_RUBINERED = "RubineRed"; //NON-NLS
	public static final String N_WILDSTRAWBERRY = "WildStrawberry"; //NON-NLS
	public static final String N_SALMON = "Salmon"; //NON-NLS
	public static final String N_CARNATIONPINK = "CarnationPink"; //NON-NLS
	public static final String N_MAGENTA = "Magenta"; //NON-NLS
	public static final String N_VIOLETRED = "VioletRed"; //NON-NLS
	public static final String N_RHODAMINE = "Rhodamine"; //NON-NLS
	public static final String N_MULBERRY = "Mulberry"; //NON-NLS
	public static final String N_REDVIOLET = "RedViolet"; //NON-NLS
	public static final String N_FUSHIA = "Fuchsia"; //NON-NLS
	public static final String N_LAVENDER = "Lavender"; //NON-NLS
	public static final String N_THISTLE = "Thistle"; //NON-NLS
	public static final String N_ORCHID = "Orchid"; //NON-NLS
	public static final String N_DARKORCHID = "DarkOrchid"; //NON-NLS
	public static final String N_PURPLE = "Purple"; //NON-NLS
	public static final String N_PLUM = "Plum"; //NON-NLS
	public static final String N_VIOLET = "Violet"; //NON-NLS
	public static final String N_ROYALPURPLE = "RoyalPurple"; //NON-NLS
	public static final String N_BLUEVIOLET = "BlueViolet"; //NON-NLS
	public static final String N_PERIWINKLE = "Periwinkle"; //NON-NLS
	public static final String N_CADETBLUE = "CadetBlue"; //NON-NLS
	public static final String N_CORNFLOWERBLUE = "CornflowerBlue"; //NON-NLS
	public static final String N_MIDNIGHTBLUE = "MidnightBlue"; //NON-NLS
	public static final String N_NAVYBLUE = "NavyBlue"; //NON-NLS
	public static final String N_ROYALBLUE = "RoyalBlue"; //NON-NLS
	public static final String N_BLUE = "Blue"; //NON-NLS
	public static final String N_CERULEAN = "Cerulean"; //NON-NLS
	public static final String N_CYAN = "Cyan"; //NON-NLS
	public static final String N_PROCESSBLUE = "ProcessBlue"; //NON-NLS
	public static final String N_SKYBLUE = "SkyBlue"; //NON-NLS
	public static final String N_TURQUOISE = "Turquoise"; //NON-NLS
	public static final String N_TEALBLUE = "TealBlue"; //NON-NLS
	public static final String N_AQUAMARINE = "Aquamarine"; //NON-NLS
	public static final String N_BLUEGREEN = "BlueGreen"; //NON-NLS
	public static final String N_EMERALD = "Emerald"; //NON-NLS
	public static final String N_JUNGLEGREEN = "JungleGreen"; //NON-NLS
	public static final String N_SEAGREEN = "SeaGreen"; //NON-NLS
	public static final String N_GREEN = "Green"; //NON-NLS
	public static final String N_FORESTGREEN = "ForestGreen"; //NON-NLS
	public static final String N_PINEGREEN = "PineGreen"; //NON-NLS
	public static final String N_LIMEGREEN = "LimeGreen"; //NON-NLS
	public static final String N_YELLOWGREEN = "YellowGreen"; //NON-NLS
	public static final String N_SPRINGGREEN = "SpringGreen"; //NON-NLS
	public static final String N_OLIVEGREEN = "OliveGreen"; //NON-NLS
	public static final String N_RAWSIENNA = "RawSienna"; //NON-NLS
	public static final String N_SEPIA = "Sepia"; //NON-NLS
	public static final String N_BROWN = "Brown"; //NON-NLS
	public static final String N_TAN = "Tan"; //NON-NLS
	public static final String N_GRAY = "Gray"; //NON-NLS
	public static final String N_BLACK = "Black"; //NON-NLS
	public static final String N_WHITE = "White"; //NON-NLS

	public static final String N_CYAN_2 = "cyan"; //NON-NLS
	public static final String N_GRAY_2 = "gray"; //NON-NLS
	public static final String N_BLACK_2 = "black"; //NON-NLS
	public static final String N_WHITE_2 = "white"; //NON-NLS
	public static final String N_YELLOW_2 = "yellow"; //NON-NLS
	public static final String N_VIOLET_2 = "violet"; //NON-NLS
	public static final String N_BLUE_2 = "blue"; //NON-NLS
	public static final String N_PURPLE_2 = "purple"; //NON-NLS
	public static final String N_RED_2 = "red"; //NON-NLS
	public static final String N_ORANGE_2 = "orange"; //NON-NLS
	public static final String N_GREEN_2 = "green"; //NON-NLS
	public static final String N_MAGENTA_2 = "magenta"; //NON-NLS
	public static final String N_BROWN_2 = "brown"; //NON-NLS
	public static final String N_DARK_GRAY = "darkgray"; //NON-NLS
	public static final String N_LIGHT_GRAY = "lightgray"; //NON-NLS
	public static final String N_PINK = "pink"; //NON-NLS
	public static final String N_OLIVE = "olive"; //NON-NLS

	public static final DviPsColors INSTANCE = new DviPsColors();

	private final Map<String, Color> colourHT = new HashMap<>();

	private final Map<Color, String> nameColourHT = new HashMap<>();

	/** The colours defined by the user and their name. */
	private final Map<String, Color> userColourHT = new HashMap<>();

	/** The colours defined by the user and their name. */
	private final Map<Color, String> userNameColourHT = new HashMap<>();

	/** The counter is used to name the user defined colours. */
	private int ctColours;


	private DviPsColors() {
		super();
		createColourHashTable();
		createNameColourHashTable();
		ctColours = 0;
	}

	public void clearUserColours() {
		userColourHT.clear();
		userNameColourHT.clear();
	}

	/**
	 * Creates the hashTable {@link #nameColourHT}.
	 * @since 1.9.2
	 */
	private void createColourHashTable() {
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
		colourHT.put(N_DARK_GRAY, DARKGRAY);
		colourHT.put(N_LIGHT_GRAY, LIGHTGRAY);
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
		nameColourHT.put(DARKGRAY, N_DARK_GRAY);
		nameColourHT.put(LIGHTGRAY, N_LIGHT_GRAY);
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
		nameColourHT.put(CORNFLOWERBLUE, N_CORNFLOWERBLUE);
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
	public Optional<Color> getPredefinedColour(final String name) {
		if(name == null) {
			return Optional.empty();
		}
		return Optional.ofNullable(colourHT.get(name));
	}


	/**
	 * Allows to get the name of  a given colour.
	 * @param colour The colour that we want the name .
	 * @return The name of the colour : null if it can not be found.
	 */
	public Optional<String> getColourName(final Color colour) {
		if(colour == null) {
			return Optional.empty();
		}
		String name = nameColourHT.get(colour);
		if(name == null) {
			name = userNameColourHT.get(colour);
		}
		return Optional.ofNullable(name);
	}


	/**
	 * Allows to get a colour with its name.
	 * @param name The name of the wanted colour.
	 * @return The colour, null if the name is invalid of do not correspond at any colour.
	 */
	public Optional<Color> getColour(final String name) {
		if(name == null) {
			return Optional.empty();
		}
		Color c = userColourHT.get(name);
		if(c == null) {
			c = colourHT.get(name);
		}
		return Optional.ofNullable(c);
	}


	/**
	 * Adds a colour defined by the user.
	 * @param colour The colour to add.
	 * @return The name of this colour.
	 */
	public Optional<String> addUserColour(final Color colour) {
		if(colour == null) {
			return Optional.empty();
		}
		final String name = generateColourName();
		addUserColour(colour, name);
		return Optional.of(name);
	}


	/**
	 * Adds a colour defined by the user.
	 * @param colour The colour to add.
	 * @param name The name of the colour.
	 */
	public void addUserColour(final Color colour, final String name) {
		if(colour != null && name != null && !name.isEmpty()) {
			userColourHT.put(name, colour);
			userNameColourHT.put(colour, name);
		}
	}


	/**
	 * @return A unique name for a user defined colour.
	 * @since 3.0
	 */
	private String generateColourName() {
		return "colour" + ctColours++; //NON-NLS
	}


	/**
	 * Allows to get the PSTricks code of a given colour.
	 * @param colourName The name of the colour used to generate the code.
	 * @return The code of the colour or an empty string if the given colour is not valid.
	 */
	public String getUsercolourCode(final String colourName) {
		final Optional<Color> colour = getColour(colourName);

		if(colour.isPresent()) {
			final Color col = colour.get();
			return "\\definecolor{" + colourName + "}{rgb}{" + //NON-NLS
				(float) (col.getR() / 255.) + ',' + (float) (col.getG() / 255.) + ',' + (float) (col.getB() / 255.) + "}\n";
		}
		return "";
	}


	/**
	 * Converts an HTML (i.e. hexa) colour to an rgb one.
	 * @param hexaCode The hexadecimal code of the colour.
	 * @return The corresponding rgb colour.
	 * @throws IllegalArgumentException If the given argument is not valid (null or its length lesser than 8 characters).
	 * @since 3.0
	 */
	public Color convertHTML2rgb(final String hexaCode) {
		if(hexaCode == null || hexaCode.length() < 7) {
			throw new IllegalArgumentException(hexaCode);
		}
		return ShapeFactory.INST.createColor(Integer.valueOf(hexaCode.substring(1, 3), 16) / 255.0, Integer.valueOf(hexaCode.substring(3, 5), 16) / 255.0,
			Integer.valueOf(hexaCode.substring(5), 16) / 255.0);
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
		if(r < 0 || g < 0 || b < 0) {
			throw new IllegalArgumentException(r + " " + g + " " + b); //NON-NLS
		}
		return ShapeFactory.INST.createColor(r / 255.0, g / 255.0, b / 255.0);
	}


	/**
	 * Converts a CMYK colour to an rgb one.
	 * @param c The c level between 0 and 1.
	 * @param m The m level between 0 and 1.
	 * @param y The y level between 0 and 1.
	 * @param k The k level between 0 and 1.
	 * @return The corresponding rgb colour.
	 * @throws IllegalArgumentException On an invalid parameter.
	 * @since 2.0.0
	 */
	public Color convertcmyk2rgb(final double c, final double m, final double y, final double k) {
		if(c < 0d || c > 1d || m < 0d || m > 1d || y < 0d || y > 1d || k < 0d || k > 1d) {
			throw new IllegalArgumentException(String.valueOf(c));
		}
		return ShapeFactory.INST.createColor((1d - c) * (1d - k), (1d - m) * (1d - k), (1d - y) * (1d - k));
	}


	/**
	 * Converts a gray colour in an rgb one.
	 * @param g The gray level between 0 an 1.
	 * @return The corresponding rgb colour.
	 * @since 2.0.0
	 */
	public Color convertgray2rgb(final double g) {
		if(g < 0d || g > 1d) {
			throw new IllegalArgumentException(String.valueOf(g));
		}
		return ShapeFactory.INST.createColor(g, g, g);
	}
}
