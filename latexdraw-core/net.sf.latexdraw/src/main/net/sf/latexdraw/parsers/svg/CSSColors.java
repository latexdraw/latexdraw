package net.sf.latexdraw.parsers.svg;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.latexdraw.badaboom.BadaboomCollector;

/**
 * Defines colours used in CSS2.<br>
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
 * @version 0.1
 */
public final class CSSColors {
//	/** The colours defined by the user. */
//	private List<Color> userColours = new ArrayList<Color>();
//
//	/** The name of the colours defined by the user. */
//	private List<String> usernameColours = new ArrayList<String>();

	private Map<String, Color> userColours;

	private Map<String, Color> colourHashtable;

	private Map<Color, String> nameColourHashtable;


    public static final String CSS_ALICEBLUE_NAME 		= "aliceblue";//$NON-NLS-1$
    public static final String CSS_ANTIQUEWHITE_NAME 	= "antiquewhite";//$NON-NLS-1$
    public static final String CSS_AQUAMARINE_NAME 		= "aquamarine";//$NON-NLS-1$
    public static final String CSS_AQUA_NAME 			= "aqua";//$NON-NLS-1$
    public static final String CSS_AZURE_NAME 			= "azure";//$NON-NLS-1$
    public static final String CSS_BEIGE_NAME 			= "beige";//$NON-NLS-1$
    public static final String CSS_BISQUE_NAME 			= "bisque";//$NON-NLS-1$
    public static final String CSS_BLACK_NAME 			= "black";//$NON-NLS-1$
    public static final String CSS_BLANCHEDALMOND_NAME 	= "blanchedalmond";//$NON-NLS-1$
    public static final String CSS_BLUEVIOLET_NAME		= "blueviolet";//$NON-NLS-1$
    public static final String CSS_BLUE_NAME 			= "blue";//$NON-NLS-1$
    public static final String CSS_BROWN_NAME 			= "brown";//$NON-NLS-1$
    public static final String CSS_BURLYWOOD_NAME 		= "burlywood";//$NON-NLS-1$
    public static final String CSS_CADETBLUE_NAME 		= "cadetblue";//$NON-NLS-1$
    public static final String CSS_CHARTREUSE_NAME 		= "chartreuse";//$NON-NLS-1$
    public static final String CSS_CHOCOLATE_NAME 		= "chocolate";//$NON-NLS-1$
    public static final String CSS_CORAL_NAME 			= "coral";//$NON-NLS-1$
    public static final String CSS_CORNFLOWERBLUE_NAME  = "cornflowerblue";//$NON-NLS-1$
    public static final String CSS_CORNSILK_NAME 		= "cornsilk";//$NON-NLS-1$
    public static final String CSS_CRIMSON_NAME 		= "crimson";//$NON-NLS-1$
    public static final String CSS_CYAN_NAME 			= "cyan";//$NON-NLS-1$
    public static final String CSS_DARKBLUE_NAME		= "darkblue";//$NON-NLS-1$
    public static final String CSS_DARKCYAN_NAME 		= "darkcyan";//$NON-NLS-1$
    public static final String CSS_DARKGOLDENROD_NAME 	= "darkgoldenrod";//$NON-NLS-1$
    public static final String CSS_DARKGRAY_NAME  		= "darkgray";//$NON-NLS-1$
    public static final String CSS_DARKGREEN_NAME  		= "darkgreen";//$NON-NLS-1$
    public static final String CSS_DARKGREY_NAME  		= "darkgrey";//$NON-NLS-1$
    public static final String CSS_DARKKHAKI_NAME  		= "darkkhaki";//$NON-NLS-1$
    public static final String CSS_DARKMAGENTA_NAME  	= "darkmagenta";//$NON-NLS-1$
    public static final String CSS_DARKOLIVEGREEN_NAME  = "darkolivegreen";//$NON-NLS-1$
    public static final String CSS_DARKORANGE_NAME  	= "darkorange";//$NON-NLS-1$
    public static final String CSS_DARKORCHID_NAME  	= "darkorchid";//$NON-NLS-1$
    public static final String CSS_DARKRED_NAME  		= "darkred";//$NON-NLS-1$
    public static final String CSS_DARKSALMON_NAME  	= "darksalmon";//$NON-NLS-1$
    public static final String CSS_DARKSEAGREEN_NAME  	= "darkseagreen";//$NON-NLS-1$
    public static final String CSS_DARKSLATEBLUE_NAME  	= "darkslateblue";//$NON-NLS-1$
    public static final String CSS_DARKSLATEGRAY_NAME 	= "darkslategray";//$NON-NLS-1$
    public static final String CSS_DARKSLATEGREY_NAME 	= "darkslategrey";//$NON-NLS-1$
    public static final String CSS_DARKTURQUOISE_NAME 	= "darkturquoise";//$NON-NLS-1$
    public static final String CSS_DARKVIOLET_NAME  	= "darkviolet";//$NON-NLS-1$
    public static final String CSS_DEEPPINK_NAME  		= "deeppink";//$NON-NLS-1$
    public static final String CSS_DEEPSKYBLUE_NAME  	= "deepskyblue";//$NON-NLS-1$
    public static final String CSS_DIMGRAY_NAME  		= "dimgray";//$NON-NLS-1$
    public static final String CSS_DIMGREY_NAME  		= "dimgrey";//$NON-NLS-1$
    public static final String CSS_DODGERBLUE_NAME  	= "dodgerblue";//$NON-NLS-1$
    public static final String CSS_FIREBRICK_NAME  		= "firebrick";//$NON-NLS-1$
    public static final String CSS_FLORALWHITE_NAME  	= "floralwhite";//$NON-NLS-1$
    public static final String CSS_FORESTGREEN_NAME  	= "forestgreen";//$NON-NLS-1$
    public static final String CSS_FUCHSIA_NAME  		= "fuchsia";//$NON-NLS-1$
    public static final String CSS_GAINSBORO_NAME  		= "gainsboro";//$NON-NLS-1$
    public static final String CSS_GHOSTWHITE_NAME  	= "ghostwhite";//$NON-NLS-1$
    public static final String CSS_GOLDENROD_NAME  		= "goldenrod";//$NON-NLS-1$
    public static final String CSS_GOLD_NAME  			= "gold";//$NON-NLS-1$
    public static final String CSS_GRAY_NAME  			= "gray";//$NON-NLS-1$
    public static final String CSS_GREENYELLOW_NAME  	= "greenyellow";//$NON-NLS-1$
    public static final String CSS_GREEN_NAME  			= "green";//$NON-NLS-1$
    public static final String CSS_GREY_NAME  			= "grey";//$NON-NLS-1$
    public static final String CSS_HONEYDEW_NAME  		= "honeydew";//$NON-NLS-1$
    public static final String CSS_HOTPINK_NAME  		= "hotpink";//$NON-NLS-1$
    public static final String CSS_INDIGO_NAME  		= "indigo";//$NON-NLS-1$
    public static final String CSS_IVORY_NAME  			= "ivory";//$NON-NLS-1$
    public static final String CSS_INDIANRED_NAME		= "indianred";//$NON-NLS-1$
    public static final String CSS_KHAKI_NAME  			= "khaki";//$NON-NLS-1$
    public static final String CSS_LAVENDERBLUSH_NAME 	= "lavenderblush";//$NON-NLS-1$
    public static final String CSS_LAVENDER_NAME  		= "lavender";//$NON-NLS-1$
    public static final String CSS_LAWNGREEN_NAME  		= "lawngreen";//$NON-NLS-1$
    public static final String CSS_LEMONCHIFFON_NAME  	= "lemonchiffon";//$NON-NLS-1$
    public static final String CSS_LIGHTBLUE_NAME  		= "lightblue";//$NON-NLS-1$
    public static final String CSS_LIGHTCORAL_NAME  	= "lightcoral";//$NON-NLS-1$
    public static final String CSS_LIGHTCYAN_NAME  		= "lightcyan";//$NON-NLS-1$
    public static final String CSS_LIGHTGOLDENRODYELLOW_NAME  	= "lightgoldenrodyellow";//$NON-NLS-1$
    public static final String CSS_LIGHTGRAY_NAME  		= "lightgray";//$NON-NLS-1$
    public static final String CSS_LIGHTGREEN_NAME  	= "lightgreen";//$NON-NLS-1$
    public static final String CSS_LIGHTGREY_NAME  		= "lightgrey";//$NON-NLS-1$
    public static final String CSS_LIGHTPINK_NAME  		= "lightpink";//$NON-NLS-1$
    public static final String CSS_LIGHTSALMON_NAME  	= "lightsalmon";//$NON-NLS-1$
    public static final String CSS_LIGHTSEAGREEN_NAME  	= "lightseagreen";//$NON-NLS-1$
    public static final String CSS_LIGHTSKYBLUE_NAME  	= "lightskyblue";//$NON-NLS-1$
    public static final String CSS_LIGHTSLATEGRAY_NAME 	= "lightslategray";//$NON-NLS-1$
    public static final String CSS_LIGHTSLATEGREY_NAME 	= "lightslategrey";//$NON-NLS-1$
    public static final String CSS_LIGHTSTEELBLUE_NAME	= "lightsteelblue";//$NON-NLS-1$
    public static final String CSS_LIGHTYELLOW_NAME  	= "lightyellow";//$NON-NLS-1$
    public static final String CSS_LIMEGREEN_NAME  		= "limegreen";//$NON-NLS-1$
    public static final String CSS_LIME_NAME  			= "lime";//$NON-NLS-1$
    public static final String CSS_MAGENTA_NAME  		= "magenta";//$NON-NLS-1$
    public static final String CSS_MAROON_NAME  		= "maroon";//$NON-NLS-1$
    public static final String CSS_MEDIUMAQUAMARINE_NAME  	= "mediumaquamarine";//$NON-NLS-1$
    public static final String CSS_MEDIUMBLUE_NAME  		= "mediumblue";//$NON-NLS-1$
    public static final String CSS_MEDIUMORCHID_NAME  		= "mediumorchid";//$NON-NLS-1$
    public static final String CSS_MEDIUMPURPLE_NAME  		= "mediumpurple";//$NON-NLS-1$
    public static final String CSS_MEDIUMSEAGREEN_NAME  	= "mediumseagreen";//$NON-NLS-1$
    public static final String CSS_MEDIUMSLATEBLUE_NAME  	= "mediumslateblue";//$NON-NLS-1$
    public static final String CSS_MEDIUMSPRINGGREEN_NAME  	= "mediumspringgreen";//$NON-NLS-1$
    public static final String CSS_MEDIUMTURQUOISE_NAME  	= "mediumturquoise";//$NON-NLS-1$
    public static final String CSS_MEDIUMVIOLETRED_NAME  	= "mediumvioletred";//$NON-NLS-1$
    public static final String CSS_MIDNIGHTBLUE_NAME  		= "midnightblue";//$NON-NLS-1$
    public static final String CSS_MINTCREAM_NAME  			= "mintcream";//$NON-NLS-1$
    public static final String CSS_MISTYROSE_NAME  			= "mistyrose";//$NON-NLS-1$
    public static final String CSS_MOCCASIN_NAME  			= "moccasin";//$NON-NLS-1$
    public static final String CSS_NAVAJOWHITE_NAME  		= "navajowhite";//$NON-NLS-1$
    public static final String CSS_NAVY_NAME  				= "navy";//$NON-NLS-1$
    public static final String CSS_OLDLACE_NAME  			= "oldlace";//$NON-NLS-1$
    public static final String CSS_OLIVEDRAB_NAME  			= "olivedrab";//$NON-NLS-1$
    public static final String CSS_OLIVE_NAME  				= "olive";//$NON-NLS-1$
    public static final String CSS_ORANGERED_NAME  			= "orangered";//$NON-NLS-1$
    public static final String CSS_ORANGE_NAME  			= "orange";//$NON-NLS-1$
    public static final String CSS_ORCHID_NAME  			= "orchid";//$NON-NLS-1$
    public static final String CSS_PALEGOLDENROD_NAME  		= "palegoldenrod";//$NON-NLS-1$
    public static final String CSS_PALEGREEN_NAME  			= "palegreen";//$NON-NLS-1$
    public static final String CSS_PALETURQUOISE_NAME  		= "paleturquoise";//$NON-NLS-1$
    public static final String CSS_PALEVIOLETRED_NAME  		= "palevioletred";//$NON-NLS-1$
    public static final String CSS_PAPAYAWHIP_NAME  		= "papayawhip";//$NON-NLS-1$
    public static final String CSS_PEACHPUFF_NAME  			= "peachpuff";//$NON-NLS-1$
    public static final String CSS_PERU_NAME  				= "peru";//$NON-NLS-1$
    public static final String CSS_PINK_NAME  				= "pink";//$NON-NLS-1$
    public static final String CSS_PLUM_NAME  				= "plum";//$NON-NLS-1$
    public static final String CSS_POWDERBLUE_NAME  		= "powderblue";//$NON-NLS-1$
    public static final String CSS_PURPLE_NAME  			= "purple";//$NON-NLS-1$
    public static final String CSS_RED_NAME  				= "red";//$NON-NLS-1$
    public static final String CSS_ROSYBROWN_NAME  			= "rosybrown";//$NON-NLS-1$
    public static final String CSS_ROYALBLUE_NAME  			= "royalblue";//$NON-NLS-1$
    public static final String CSS_SADDLEBROWN_NAME  		= "saddlebrown";//$NON-NLS-1$
    public static final String CSS_SALMON_NAME  			= "salmon";//$NON-NLS-1$
    public static final String CSS_SANDYBROWN_NAME  		= "sandybrown";//$NON-NLS-1$
    public static final String CSS_SEAGREEN_NAME  			= "seagreen";//$NON-NLS-1$
    public static final String CSS_SEASHELL_NAME  			= "seashell";//$NON-NLS-1$
    public static final String CSS_SIENNA_NAME  			= "sienna";//$NON-NLS-1$
    public static final String CSS_SILVER_NAME  			= "silver";//$NON-NLS-1$
    public static final String CSS_SKYBLUE_NAME  			= "skyblue";//$NON-NLS-1$
    public static final String CSS_SLATEBLUE_NAME  			= "slateblue";//$NON-NLS-1$
    public static final String CSS_SLATEGRAY_NAME  			= "slategray";//$NON-NLS-1$
    public static final String CSS_SLATEGREY_NAME  			= "slategrey";//$NON-NLS-1$
    public static final String CSS_SNOW_NAME  				= "snow";//$NON-NLS-1$
    public static final String CSS_SPRINGGREEN_NAME  		= "springgreen";//$NON-NLS-1$
    public static final String CSS_STEELBLUE_NAME  			= "steelblue";//$NON-NLS-1$
    public static final String CSS_TAN_NAME  				= "tan";//$NON-NLS-1$
    public static final String CSS_TEAL_NAME  				= "teal";//$NON-NLS-1$
    public static final String CSS_THISTLE_NAME  			= "thistle";//$NON-NLS-1$
    public static final String CSS_TOMATO_NAME  			= "tomato";//$NON-NLS-1$
    public static final String CSS_TURQUOISE_NAME  			= "turquoise";//$NON-NLS-1$
    public static final String CSS_VIOLET_NAME  			= "violet";//$NON-NLS-1$
    public static final String CSS_WHEAT_NAME  				= "wheat";//$NON-NLS-1$
    public static final String CSS_WHITESMOKE_NAME  		= "whitesmoke";//$NON-NLS-1$
    public static final String CSS_WHITE_NAME  				= "white";//$NON-NLS-1$
    public static final String CSS_YELLOWGREEN_NAME  		= "yellowgreen";//$NON-NLS-1$
    public static final String CSS_YELLOW_NAME  			= "yellow";//$NON-NLS-1$


    public final static Color CSS_YELLOW_RGB_VALUE         	 	= new Color(255, 255, 0);
    public final static Color CSS_RED_RGB_VALUE         	 	= new Color(255, 0, 0);
    public final static Color CSS_TEAL_RGB_VALUE         	 	= new Color(0, 128, 128);
    public final static Color CSS_PURPLE_RGB_VALUE         	 	= new Color(128, 0, 128);
    public final static Color CSS_SILVER_RGB_VALUE         	 	= new Color(192, 192, 192);
    public final static Color CSS_NAVY_RGB_VALUE         	 	= new Color(0, 0, 128);
    public final static Color CSS_OLIVE_RGB_VALUE         	 	= new Color(128, 128, 0);
    public final static Color CSS_LIME_RGB_VALUE         	 	= new Color(0, 255, 0);
    public final static Color CSS_MAROON_RGB_VALUE         	 	= new Color(128, 0, 0);
    public final static Color CSS_GRAY_RGB_VALUE         	 	= new Color(128, 128, 128);
    public final static Color CSS_GREEN_RGB_VALUE         	 	= new Color(0, 128, 0);
	public final static Color CSS_BLACK_RGB_VALUE         	 	= new Color(0, 0, 0);
	public final static Color CSS_WHITE_RGB_VALUE          		= new Color(255,255,255);
	public final static Color CSS_BLUE_RGB_VALUE          		= new Color(0, 0, 255);
	public final static Color CSS_FUCHSIA_RGB_VALUE          	= new Color(255, 0, 255);
    public static final Color CSS_ALICEBLUE_RGB_VALUE 			= new Color(240, 248, 255);
    public static final Color CSS_ANTIQUEWHITE_RGB_VALUE 		= new Color(250, 235, 215);
    public static final Color CSS_AQUA_RGB_VALUE 				= new Color(0, 255, 255);
    public static final Color CSS_AQUAMARINE_RGB_VALUE 			= new Color(127, 255, 212);
    public static final Color CSS_AZURE_RGB_VALUE 				= new Color(240, 255, 255);
    public static final Color CSS_BEIGE_RGB_VALUE 				= new Color(245, 245, 220);
    public static final Color CSS_BISQUE_RGB_VALUE 				= new Color(255, 228, 196);
    public static final Color CSS_BLANCHEDALMOND_RGB_VALUE		= new Color(255, 235, 205);
    public static final Color CSS_BLUEVIOLET_RGB_VALUE 			= new Color(138, 43, 226);
    public static final Color CSS_BROWN_RGB_VALUE 				= new Color(165, 42, 42);
    public static final Color CSS_BURLYWOOD_RGB_VALUE 			= new Color(222, 184, 135);
    public static final Color CSS_CADETBLUE_RGB_VALUE 			= new Color(95, 158, 160);
    public static final Color CSS_CHARTREUSE_RGB_VALUE 			= new Color(127, 255, 0);
    public static final Color CSS_CHOCOLATE_RGB_VALUE			= new Color(210, 105, 30);
    public static final Color CSS_CORAL_RGB_VALUE 				= new Color(255, 127, 80);
    public static final Color CSS_CORNFLOWERBLUE_RGB_VALUE 		= new Color(100, 149, 237);
    public static final Color CSS_CORNSILK_RGB_VALUE 			= new Color(255, 248, 220);
    public static final Color CSS_CRIMSON_RGB_VALUE 			= new Color(220, 20, 60);
    public static final Color CSS_CYAN_RGB_VALUE 				= new Color(0, 255, 255);
    public static final Color CSS_DARKBLUE_RGB_VALUE 			= new Color(0, 0, 139);
    public static final Color CSS_DARKCYAN_RGB_VALUE 			= new Color(0, 139, 139);
    public static final Color CSS_DARKGOLDENROD_RGB_VALUE 		= new Color(184, 134, 11);
    public static final Color CSS_DARKGRAY_RGB_VALUE 			= new Color(169, 169, 169);
    public static final Color CSS_DARKGREEN_RGB_VALUE 			= new Color(0, 100, 0);
    public static final Color CSS_DARKGREY_RGB_VALUE 			= new Color(169, 169, 169);
    public static final Color CSS_DARKKHAKI_RGB_VALUE 			= new Color(189, 183, 107);
    public static final Color CSS_DARKMAGENTA_RGB_VALUE 		= new Color(139, 0, 139);
    public static final Color CSS_DARKOLIVEGREEN_RGB_VALUE 		= new Color(85, 107, 47);
    public static final Color CSS_DARKORANGE_RGB_VALUE 			= new Color(255, 140, 0);
    public static final Color CSS_DARKORCHID_RGB_VALUE 			= new Color(153, 50, 204);
    public static final Color CSS_DARKRED_RGB_VALUE 			= new Color(139, 0, 0);
    public static final Color CSS_DARKSALMON_RGB_VALUE 			= new Color(233, 150, 122);
    public static final Color CSS_DARKSEAGREEN_RGB_VALUE 		= new Color(143, 188, 143);
    public static final Color CSS_DARKSLATEBLUE_RGB_VALUE 		= new Color(72, 61, 139);
    public static final Color CSS_DARKSLATEGRAY_RGB_VALUE 		= new Color(47, 79, 79);
    public static final Color CSS_DARKSLATEGREY_RGB_VALUE 		= new Color(47, 79, 79);
    public static final Color CSS_DARKTURQUOISE_RGB_VALUE 		= new Color(0, 206, 209);
    public static final Color CSS_DARKVIOLET_RGB_VALUE 			= new Color(148, 0, 211);
    public static final Color CSS_DEEPPINK_RGB_VALUE 			= new Color(255, 20, 147);
    public static final Color CSS_DEEPSKYBLUE_RGB_VALUE 		= new Color(0, 191, 255);
    public static final Color CSS_DIMGRAY_RGB_VALUE 			= new Color(105, 105, 105);
    public static final Color CSS_DIMGREY_RGB_VALUE 			= new Color(105, 105, 105);
    public static final Color CSS_DODGERBLUE_RGB_VALUE 			= new Color(30, 144, 255);
    public static final Color CSS_FIREBRICK_RGB_VALUE 			= new Color(178, 34, 34);
    public static final Color CSS_FLORALWHITE_RGB_VALUE 		= new Color(255, 250, 240);
    public static final Color CSS_FORESTGREEN_RGB_VALUE 		= new Color(34, 139, 34);
    public static final Color CSS_GAINSBORO_RGB_VALUE			= new Color(220, 200, 200);
    public static final Color CSS_GHOSTWHITE_RGB_VALUE 			= new Color(248, 248, 255);
    public static final Color CSS_GOLD_RGB_VALUE 				= new Color(255, 215, 0);
    public static final Color CSS_GOLDENROD_RGB_VALUE 			= new Color(218, 165, 32);
    public static final Color CSS_GREY_RGB_VALUE 				= new Color(128, 128, 128);
    public static final Color CSS_GREENYELLOW_RGB_VALUE 		= new Color(173, 255, 47);
    public static final Color CSS_HONEYDEW_RGB_VALUE 			= new Color(240, 255, 240);
    public static final Color CSS_HOTPINK_RGB_VALUE 			= new Color(255, 105, 180);
    public static final Color CSS_INDIANRED_RGB_VALUE 			= new Color(205, 92, 92);
    public static final Color CSS_INDIGO_RGB_VALUE 				= new Color(75, 0, 130);
    public static final Color CSS_IVORY_RGB_VALUE 				= new Color(255, 255, 240);
    public static final Color CSS_KHAKI_RGB_VALUE 				= new Color(240, 230, 140);
    public static final Color CSS_LAVENDER_RGB_VALUE 			= new Color(230, 230, 250);
    public static final Color CSS_LAVENDERBLUSH_RGB_VALUE 		= new Color(255, 240, 255);
    public static final Color CSS_LAWNGREEN_RGB_VALUE 			= new Color(124, 252, 0);
    public static final Color CSS_LEMONCHIFFON_RGB_VALUE 		= new Color(255, 250, 205);
    public static final Color CSS_LIGHTBLUE_RGB_VALUE 			= new Color(173, 216, 230);
    public static final Color CSS_LIGHTCORAL_RGB_VALUE 			= new Color(240, 128, 128);
    public static final Color CSS_LIGHTCYAN_RGB_VALUE 			= new Color(224, 255, 255);
    public static final Color CSS_LIGHTGOLDENRODYELLOW_RGB_VALUE= new Color(250, 250, 210);
    public static final Color CSS_LIGHTGRAY_RGB_VALUE 			= new Color(211, 211, 211);
    public static final Color CSS_LIGHTGREEN_RGB_VALUE 			= new Color(144, 238, 144);
    public static final Color CSS_LIGHTGREY_RGB_VALUE 			= new Color(211, 211, 211);
    public static final Color CSS_LIGHTPINK_RGB_VALUE 			= new Color(255, 182, 193);
    public static final Color CSS_LIGHTSALMON_RGB_VALUE 		= new Color(255, 160, 122);
    public static final Color CSS_LIGHTSEAGREEN_RGB_VALUE 		= new Color(32, 178, 170);
    public static final Color CSS_LIGHTSKYBLUE_RGB_VALUE 		= new Color(135, 206, 250);
    public static final Color CSS_LIGHTSLATEGRAY_RGB_VALUE 		= new Color(119, 136, 153);
    public static final Color CSS_LIGHTSLATEGREY_RGB_VALUE 		= new Color(119, 136, 153);
    public static final Color CSS_LIGHTSTEELBLUE_RGB_VALUE 		= new Color(176, 196, 222);
    public static final Color CSS_LIGHTYELLOW_RGB_VALUE 		= new Color(255, 255, 224);
    public static final Color CSS_LIMEGREEN_RGB_VALUE 			= new Color(50, 205, 50);
    public static final Color CSS_LINEN_RGB_VALUE 				= new Color(250, 240, 230);
    public static final Color CSS_MAGENTA_RGB_VALUE 			= new Color(255, 0, 255);
    public static final Color CSS_MEDIUMAQUAMARINE_RGB_VALUE 	= new Color(102, 205, 170);
    public static final Color CSS_MEDIUMBLUE_RGB_VALUE 			= new Color(0, 0, 205);
    public static final Color CSS_MEDIUMORCHID_RGB_VALUE 		= new Color(186, 85, 211);
    public static final Color CSS_MEDIUMPURPLE_RGB_VALUE 		= new Color(147, 112, 219);
    public static final Color CSS_MEDIUMSEAGREEN_RGB_VALUE 		= new Color(60, 179, 113);
    public static final Color CSS_MEDIUMSLATEBLUE_RGB_VALUE 	= new Color(123, 104, 238);
    public static final Color CSS_MEDIUMSPRINGGREEN_RGB_VALUE 	= new Color(0, 250, 154);
    public static final Color CSS_MEDIUMTURQUOISE_RGB_VALUE 	= new Color(72, 209, 204);
    public static final Color CSS_MEDIUMVIOLETRED_RGB_VALUE 	= new Color(199, 21, 133);
    public static final Color CSS_MIDNIGHTBLUE_RGB_VALUE 		= new Color(25, 25, 112);
    public static final Color CSS_MINTCREAM_RGB_VALUE 			= new Color(245, 255, 250);
    public static final Color CSS_MISTYROSE_RGB_VALUE 			= new Color(255, 228, 225);
    public static final Color CSS_MOCCASIN_RGB_VALUE 			= new Color(255, 228, 181);
    public static final Color CSS_NAVAJOWHITE_RGB_VALUE 		= new Color(255, 222, 173);
    public static final Color CSS_OLDLACE_RGB_VALUE 			= new Color(253, 245, 230);
    public static final Color CSS_OLIVEDRAB_RGB_VALUE 			= new Color(107, 142, 35);
    public static final Color CSS_ORANGE_RGB_VALUE				= new Color(255, 165, 0);
    public static final Color CSS_ORANGERED_RGB_VALUE 			= new Color(255, 69, 0);
    public static final Color CSS_ORCHID_RGB_VALUE 				= new Color(218, 112, 214);
    public static final Color CSS_PALEGOLDENROD_RGB_VALUE 		= new Color(238, 232, 170);
    public static final Color CSS_PALEGREEN_RGB_VALUE 			= new Color(152, 251, 152);
    public static final Color CSS_PALETURQUOISE_RGB_VALUE 		= new Color(175, 238, 238);
    public static final Color CSS_PALEVIOLETRED_RGB_VALUE 		= new Color(219, 112, 147);
    public static final Color CSS_PAPAYAWHIP_RGB_VALUE 			= new Color(255, 239, 213);
    public static final Color CSS_PEACHPUFF_RGB_VALUE 			= new Color(255, 218, 185);
    public static final Color CSS_PERU_RGB_VALUE 				= new Color(205, 133, 63);
    public static final Color CSS_PINK_RGB_VALUE 				= new Color(255, 192, 203);
    public static final Color CSS_PLUM_RGB_VALUE 				= new Color(221, 160, 221);
    public static final Color CSS_POWDERBLUE_RGB_VALUE 			= new Color(176, 224, 230);
    public static final Color CSS_ROSYBROWN_RGB_VALUE 			= new Color(188, 143, 143);
    public static final Color CSS_ROYALBLUE_RGB_VALUE 			= new Color(65, 105, 225);
    public static final Color CSS_SADDLEBROWN_RGB_VALUE 		= new Color(139, 69, 19);
    public static final Color CSS_SALMON_RGB_VALUE 				= new Color(250, 69, 114);
    public static final Color CSS_SANDYBROWN_RGB_VALUE 			= new Color(244, 164, 96);
    public static final Color CSS_SEAGREEN_RGB_VALUE 			= new Color(46, 139, 87);
    public static final Color CSS_SEASHELL_RGB_VALUE 			= new Color(255, 245, 238);
    public static final Color CSS_SIENNA_RGB_VALUE 				= new Color(160, 82, 45);
    public static final Color CSS_SKYBLUE_RGB_VALUE 			= new Color(135, 206, 235);
    public static final Color CSS_SLATEBLUE_RGB_VALUE 			= new Color(106, 90, 205);
    public static final Color CSS_SLATEGRAY_RGB_VALUE 			= new Color(112, 128, 144);
    public static final Color CSS_SLATEGREY_RGB_VALUE 			= new Color(112, 128, 144);
    public static final Color CSS_SNOW_RGB_VALUE 				= new Color(255, 250, 250);
    public static final Color CSS_SPRINGGREEN_RGB_VALUE 		= new Color(0, 255, 127);
    public static final Color CSS_STEELBLUE_RGB_VALUE 			= new Color(70, 130, 180);
    public static final Color CSS_TAN_RGB_VALUE 				= new Color(210, 180, 140);
    public static final Color CSS_THISTLE_RGB_VALUE 			= new Color(216, 91, 216);
    public static final Color CSS_TOMATO_RGB_VALUE 				= new Color(255, 99, 71);
    public static final Color CSS_TURQUOISE_RGB_VALUE 			= new Color(64, 224, 208);
    public static final Color CSS_VIOLET_RGB_VALUE 				= new Color(238, 130, 238);
    public static final Color CSS_WHEAT_RGB_VALUE 				= new Color(245, 222, 179);
    public static final Color CSS_WHITESMOKE_RGB_VALUE 			= new Color(245, 245, 245);
    public static final Color CSS_YELLOWGREEN_RGB_VALUE 		= new Color(154, 205, 50);

    /** The singleton. */
    public static final CSSColors INSTANCE = new CSSColors();

	private void createColourHashTable() {
		colourHashtable.clear();
	    colourHashtable.put(CSS_ALICEBLUE_NAME, CSS_ALICEBLUE_RGB_VALUE);
	    colourHashtable.put(CSS_ANTIQUEWHITE_NAME, CSS_ANTIQUEWHITE_RGB_VALUE);
	    colourHashtable.put(CSS_AQUAMARINE_NAME, CSS_AQUAMARINE_RGB_VALUE);
	    colourHashtable.put(CSS_AQUA_NAME, CSS_AQUA_RGB_VALUE);
	    colourHashtable.put(CSS_AZURE_NAME, CSS_AZURE_RGB_VALUE);
	    colourHashtable.put(CSS_BEIGE_NAME, CSS_BEIGE_RGB_VALUE);
	    colourHashtable.put(CSS_BISQUE_NAME, CSS_BISQUE_RGB_VALUE);
	    colourHashtable.put(CSS_BLACK_NAME, CSS_BLACK_RGB_VALUE);
	    colourHashtable.put(CSS_BLANCHEDALMOND_NAME, CSS_BLANCHEDALMOND_RGB_VALUE);
	    colourHashtable.put(CSS_BLUEVIOLET_NAME, CSS_BLUEVIOLET_RGB_VALUE);
	    colourHashtable.put(CSS_BLUE_NAME, CSS_BLUE_RGB_VALUE);
	    colourHashtable.put(CSS_BROWN_NAME, CSS_BROWN_RGB_VALUE);
	    colourHashtable.put(CSS_BURLYWOOD_NAME, CSS_BURLYWOOD_RGB_VALUE);
	    colourHashtable.put(CSS_CADETBLUE_NAME, CSS_CADETBLUE_RGB_VALUE);
	    colourHashtable.put(CSS_CHARTREUSE_NAME, CSS_CHARTREUSE_RGB_VALUE);
	    colourHashtable.put(CSS_CHOCOLATE_NAME, CSS_CHOCOLATE_RGB_VALUE);
	    colourHashtable.put(CSS_CORAL_NAME, CSS_CORAL_RGB_VALUE);
	    colourHashtable.put(CSS_CORNFLOWERBLUE_NAME, CSS_CORNFLOWERBLUE_RGB_VALUE);
	    colourHashtable.put(CSS_CORNSILK_NAME, CSS_CORNSILK_RGB_VALUE);
	    colourHashtable.put(CSS_CRIMSON_NAME, CSS_CRIMSON_RGB_VALUE);
	    colourHashtable.put(CSS_CYAN_NAME, CSS_CYAN_RGB_VALUE);
	    colourHashtable.put(CSS_DARKBLUE_NAME, CSS_DARKBLUE_RGB_VALUE);
	    colourHashtable.put(CSS_DARKCYAN_NAME, CSS_DARKCYAN_RGB_VALUE);
	    colourHashtable.put(CSS_DARKGOLDENROD_NAME, CSS_DARKGOLDENROD_RGB_VALUE);
	    colourHashtable.put(CSS_DARKGRAY_NAME, CSS_DARKGRAY_RGB_VALUE);
	    colourHashtable.put(CSS_DARKGREEN_NAME, CSS_DARKGREEN_RGB_VALUE);
	    colourHashtable.put(CSS_DARKGREY_NAME, CSS_DARKGREY_RGB_VALUE);
	    colourHashtable.put(CSS_DARKKHAKI_NAME, CSS_DARKKHAKI_RGB_VALUE);
	    colourHashtable.put(CSS_DARKMAGENTA_NAME, CSS_DARKMAGENTA_RGB_VALUE);
	    colourHashtable.put(CSS_DARKOLIVEGREEN_NAME, CSS_DARKOLIVEGREEN_RGB_VALUE);
	    colourHashtable.put(CSS_DARKORANGE_NAME, CSS_DARKORANGE_RGB_VALUE);
	    colourHashtable.put(CSS_DARKORCHID_NAME, CSS_DARKORCHID_RGB_VALUE);
	    colourHashtable.put(CSS_DARKRED_NAME, CSS_DARKRED_RGB_VALUE);
	    colourHashtable.put(CSS_DARKSALMON_NAME, CSS_DARKSALMON_RGB_VALUE);
	    colourHashtable.put(CSS_DARKSEAGREEN_NAME, CSS_DARKSEAGREEN_RGB_VALUE);
	    colourHashtable.put(CSS_DARKSLATEBLUE_NAME, CSS_DARKSLATEBLUE_RGB_VALUE);
	    colourHashtable.put(CSS_DARKSLATEGRAY_NAME, CSS_DARKSLATEGRAY_RGB_VALUE);
	    colourHashtable.put(CSS_DARKSLATEGREY_NAME, CSS_DARKSLATEGRAY_RGB_VALUE);
	    colourHashtable.put(CSS_DARKTURQUOISE_NAME, CSS_DARKTURQUOISE_RGB_VALUE);
	    colourHashtable.put(CSS_DARKVIOLET_NAME, CSS_DARKVIOLET_RGB_VALUE);
	    colourHashtable.put(CSS_DEEPPINK_NAME, CSS_DEEPPINK_RGB_VALUE);
	    colourHashtable.put(CSS_DEEPSKYBLUE_NAME, CSS_DEEPSKYBLUE_RGB_VALUE);
	    colourHashtable.put(CSS_DIMGRAY_NAME, CSS_DIMGRAY_RGB_VALUE);
	    colourHashtable.put(CSS_DIMGREY_NAME, CSS_DIMGREY_RGB_VALUE);
	    colourHashtable.put(CSS_DODGERBLUE_NAME, CSS_DODGERBLUE_RGB_VALUE);
	    colourHashtable.put(CSS_FIREBRICK_NAME, CSS_FIREBRICK_RGB_VALUE);
	    colourHashtable.put(CSS_FLORALWHITE_NAME, CSS_FLORALWHITE_RGB_VALUE);
	    colourHashtable.put(CSS_FORESTGREEN_NAME, CSS_FORESTGREEN_RGB_VALUE);
	    colourHashtable.put(CSS_FUCHSIA_NAME, CSS_FUCHSIA_RGB_VALUE);
	    colourHashtable.put(CSS_GAINSBORO_NAME, CSS_GAINSBORO_RGB_VALUE);
	    colourHashtable.put(CSS_GHOSTWHITE_NAME, CSS_GHOSTWHITE_RGB_VALUE);
	    colourHashtable.put(CSS_GOLDENROD_NAME, CSS_GOLDENROD_RGB_VALUE);
	    colourHashtable.put(CSS_GOLD_NAME, CSS_GOLD_RGB_VALUE);
	    colourHashtable.put(CSS_GRAY_NAME, CSS_GRAY_RGB_VALUE);
	    colourHashtable.put(CSS_GREENYELLOW_NAME, CSS_GREENYELLOW_RGB_VALUE);
	    colourHashtable.put(CSS_GREEN_NAME, CSS_GREEN_RGB_VALUE);
	    colourHashtable.put(CSS_GREY_NAME, CSS_GRAY_RGB_VALUE);
	    colourHashtable.put(CSS_HONEYDEW_NAME, CSS_HONEYDEW_RGB_VALUE);
	    colourHashtable.put(CSS_HOTPINK_NAME, CSS_HOTPINK_RGB_VALUE);
	    colourHashtable.put(CSS_INDIGO_NAME, CSS_INDIGO_RGB_VALUE);
	    colourHashtable.put(CSS_IVORY_NAME, CSS_IVORY_RGB_VALUE);
	    colourHashtable.put(CSS_INDIANRED_NAME, CSS_INDIANRED_RGB_VALUE);
	    colourHashtable.put(CSS_KHAKI_NAME, CSS_KHAKI_RGB_VALUE);
	    colourHashtable.put(CSS_LAVENDERBLUSH_NAME, CSS_LAVENDERBLUSH_RGB_VALUE);
	    colourHashtable.put(CSS_LAVENDER_NAME, CSS_LAVENDER_RGB_VALUE);
	    colourHashtable.put(CSS_LAWNGREEN_NAME, CSS_LAWNGREEN_RGB_VALUE);
	    colourHashtable.put(CSS_LEMONCHIFFON_NAME, CSS_LEMONCHIFFON_RGB_VALUE);
	    colourHashtable.put(CSS_LIGHTBLUE_NAME, CSS_LIGHTBLUE_RGB_VALUE);
	    colourHashtable.put(CSS_LIGHTCORAL_NAME, CSS_LIGHTCORAL_RGB_VALUE);
	    colourHashtable.put(CSS_LIGHTCYAN_NAME, CSS_LIGHTCYAN_RGB_VALUE);
	    colourHashtable.put(CSS_LIGHTGOLDENRODYELLOW_NAME, CSS_LIGHTGOLDENRODYELLOW_RGB_VALUE);
	    colourHashtable.put(CSS_LIGHTGRAY_NAME, CSS_LIGHTGRAY_RGB_VALUE);
	    colourHashtable.put(CSS_LIGHTGREEN_NAME, CSS_LIGHTGREEN_RGB_VALUE);
	    colourHashtable.put(CSS_LIGHTGREY_NAME, CSS_LIGHTGREY_RGB_VALUE);
	    colourHashtable.put(CSS_LIGHTPINK_NAME, CSS_LIGHTPINK_RGB_VALUE);
	    colourHashtable.put(CSS_LIGHTSALMON_NAME, CSS_LIGHTSALMON_RGB_VALUE);
	    colourHashtable.put(CSS_LIGHTSEAGREEN_NAME, CSS_LIGHTSEAGREEN_RGB_VALUE);
	    colourHashtable.put(CSS_LIGHTSKYBLUE_NAME, CSS_LIGHTSKYBLUE_RGB_VALUE);
	    colourHashtable.put(CSS_LIGHTSLATEGRAY_NAME, CSS_LIGHTSLATEGRAY_RGB_VALUE);
	    colourHashtable.put(CSS_LIGHTSLATEGREY_NAME, CSS_LIGHTSLATEGREY_RGB_VALUE);
	    colourHashtable.put(CSS_LIGHTSTEELBLUE_NAME, CSS_LIGHTSTEELBLUE_RGB_VALUE);
	    colourHashtable.put(CSS_LIGHTYELLOW_NAME, CSS_LIGHTYELLOW_RGB_VALUE);
	    colourHashtable.put(CSS_LIMEGREEN_NAME, CSS_LIMEGREEN_RGB_VALUE);
	    colourHashtable.put(CSS_LIME_NAME, CSS_LIME_RGB_VALUE);
	    colourHashtable.put(CSS_MAGENTA_NAME, CSS_MAGENTA_RGB_VALUE);
	    colourHashtable.put(CSS_MAROON_NAME, CSS_MAROON_RGB_VALUE);
	    colourHashtable.put(CSS_MEDIUMAQUAMARINE_NAME, CSS_MEDIUMAQUAMARINE_RGB_VALUE);
	    colourHashtable.put(CSS_MEDIUMBLUE_NAME, CSS_MEDIUMBLUE_RGB_VALUE);
	    colourHashtable.put(CSS_MEDIUMORCHID_NAME, CSS_MEDIUMORCHID_RGB_VALUE);
	    colourHashtable.put(CSS_MEDIUMPURPLE_NAME, CSS_MEDIUMPURPLE_RGB_VALUE);
	    colourHashtable.put(CSS_MEDIUMSEAGREEN_NAME, CSS_MEDIUMSEAGREEN_RGB_VALUE);
	    colourHashtable.put(CSS_MEDIUMSLATEBLUE_NAME, CSS_MEDIUMSLATEBLUE_RGB_VALUE);
	    colourHashtable.put(CSS_MEDIUMSPRINGGREEN_NAME, CSS_MEDIUMSPRINGGREEN_RGB_VALUE);
	    colourHashtable.put(CSS_MEDIUMTURQUOISE_NAME, CSS_MEDIUMTURQUOISE_RGB_VALUE);
	    colourHashtable.put(CSS_MEDIUMVIOLETRED_NAME, CSS_MEDIUMVIOLETRED_RGB_VALUE);
	    colourHashtable.put(CSS_MIDNIGHTBLUE_NAME, CSS_MIDNIGHTBLUE_RGB_VALUE);
	    colourHashtable.put(CSS_MINTCREAM_NAME, CSS_MINTCREAM_RGB_VALUE);
	    colourHashtable.put(CSS_MISTYROSE_NAME, CSS_MISTYROSE_RGB_VALUE);
	    colourHashtable.put(CSS_MOCCASIN_NAME, CSS_MOCCASIN_RGB_VALUE);
	    colourHashtable.put(CSS_NAVAJOWHITE_NAME, CSS_NAVAJOWHITE_RGB_VALUE);
	    colourHashtable.put(CSS_NAVY_NAME, CSS_NAVY_RGB_VALUE);
	    colourHashtable.put(CSS_OLDLACE_NAME, CSS_OLDLACE_RGB_VALUE);
	    colourHashtable.put(CSS_OLIVEDRAB_NAME, CSS_OLIVEDRAB_RGB_VALUE);
	    colourHashtable.put(CSS_OLIVE_NAME, CSS_OLIVE_RGB_VALUE);
	    colourHashtable.put(CSS_ORANGERED_NAME, CSS_ORANGERED_RGB_VALUE);
	    colourHashtable.put(CSS_ORANGE_NAME, CSS_ORANGE_RGB_VALUE);
	    colourHashtable.put(CSS_ORCHID_NAME, CSS_ORCHID_RGB_VALUE);
	    colourHashtable.put(CSS_PALEGOLDENROD_NAME, CSS_PALEGOLDENROD_RGB_VALUE);
	    colourHashtable.put(CSS_PALEGREEN_NAME, CSS_PALEGREEN_RGB_VALUE);
	    colourHashtable.put(CSS_PALETURQUOISE_NAME, CSS_PALETURQUOISE_RGB_VALUE);
	    colourHashtable.put(CSS_PALEVIOLETRED_NAME, CSS_PALEVIOLETRED_RGB_VALUE);
	    colourHashtable.put(CSS_PAPAYAWHIP_NAME, CSS_PAPAYAWHIP_RGB_VALUE);
	    colourHashtable.put(CSS_PEACHPUFF_NAME, CSS_PEACHPUFF_RGB_VALUE);
	    colourHashtable.put(CSS_PERU_NAME, CSS_PERU_RGB_VALUE);
	    colourHashtable.put(CSS_PINK_NAME, CSS_PINK_RGB_VALUE);
	    colourHashtable.put(CSS_PLUM_NAME, CSS_PLUM_RGB_VALUE);
	    colourHashtable.put(CSS_POWDERBLUE_NAME, CSS_POWDERBLUE_RGB_VALUE);
	    colourHashtable.put(CSS_PURPLE_NAME, CSS_PURPLE_RGB_VALUE);
	    colourHashtable.put(CSS_RED_NAME, CSS_RED_RGB_VALUE);
	    colourHashtable.put(CSS_ROSYBROWN_NAME, CSS_ROSYBROWN_RGB_VALUE);
	    colourHashtable.put(CSS_ROYALBLUE_NAME, CSS_ROYALBLUE_RGB_VALUE);
	    colourHashtable.put(CSS_SADDLEBROWN_NAME, CSS_SADDLEBROWN_RGB_VALUE);
	    colourHashtable.put(CSS_SALMON_NAME, CSS_SALMON_RGB_VALUE);
	    colourHashtable.put(CSS_SANDYBROWN_NAME, CSS_SANDYBROWN_RGB_VALUE);
	    colourHashtable.put(CSS_SEAGREEN_NAME, CSS_SEAGREEN_RGB_VALUE);
	    colourHashtable.put(CSS_SEASHELL_NAME, CSS_SEASHELL_RGB_VALUE);
	    colourHashtable.put(CSS_SIENNA_NAME, CSS_SIENNA_RGB_VALUE);
	    colourHashtable.put(CSS_SILVER_NAME, CSS_SILVER_RGB_VALUE);
	    colourHashtable.put(CSS_SKYBLUE_NAME, CSS_SKYBLUE_RGB_VALUE);
	    colourHashtable.put(CSS_SLATEBLUE_NAME, CSS_SLATEBLUE_RGB_VALUE);
	    colourHashtable.put(CSS_SLATEGRAY_NAME, CSS_SLATEGRAY_RGB_VALUE);
	    colourHashtable.put(CSS_SLATEGREY_NAME, CSS_SLATEGREY_RGB_VALUE);
	    colourHashtable.put(CSS_SNOW_NAME, CSS_SNOW_RGB_VALUE);
	    colourHashtable.put(CSS_SPRINGGREEN_NAME, CSS_SPRINGGREEN_RGB_VALUE);
	    colourHashtable.put(CSS_STEELBLUE_NAME, CSS_STEELBLUE_RGB_VALUE);
	    colourHashtable.put(CSS_TAN_NAME, CSS_TAN_RGB_VALUE);
	    colourHashtable.put(CSS_TEAL_NAME, CSS_TEAL_RGB_VALUE);
	    colourHashtable.put(CSS_THISTLE_NAME, CSS_THISTLE_RGB_VALUE);
	    colourHashtable.put(CSS_TOMATO_NAME, CSS_TOMATO_RGB_VALUE);
	    colourHashtable.put(CSS_TURQUOISE_NAME, CSS_TURQUOISE_RGB_VALUE);
	    colourHashtable.put(CSS_VIOLET_NAME, CSS_VIOLET_RGB_VALUE);
	    colourHashtable.put(CSS_WHEAT_NAME, CSS_WHEAT_RGB_VALUE);
	    colourHashtable.put(CSS_WHITESMOKE_NAME, CSS_WHITESMOKE_RGB_VALUE);
	    colourHashtable.put(CSS_WHITE_NAME, CSS_WHITE_RGB_VALUE);
	    colourHashtable.put(CSS_YELLOWGREEN_NAME, CSS_YELLOWGREEN_RGB_VALUE);
	    colourHashtable.put(CSS_YELLOW_NAME, CSS_YELLOW_RGB_VALUE);
	}


	private void createNameColourHashTable() {
		nameColourHashtable.clear();
	    nameColourHashtable.put(CSS_ALICEBLUE_RGB_VALUE, CSS_ALICEBLUE_NAME);
	    nameColourHashtable.put(CSS_ANTIQUEWHITE_RGB_VALUE, CSS_ANTIQUEWHITE_NAME);
	    nameColourHashtable.put(CSS_AQUAMARINE_RGB_VALUE, CSS_AQUAMARINE_NAME);
	    nameColourHashtable.put(CSS_AZURE_RGB_VALUE, CSS_AZURE_NAME);
	    nameColourHashtable.put(CSS_BEIGE_RGB_VALUE, CSS_BEIGE_NAME);
	    nameColourHashtable.put(CSS_BISQUE_RGB_VALUE, CSS_BISQUE_NAME);
	    nameColourHashtable.put(CSS_BLACK_RGB_VALUE, CSS_BLACK_NAME);
	    nameColourHashtable.put(CSS_BLANCHEDALMOND_RGB_VALUE, CSS_BLANCHEDALMOND_NAME);
	    nameColourHashtable.put(CSS_BLUEVIOLET_RGB_VALUE, CSS_BLUEVIOLET_NAME);
	    nameColourHashtable.put(CSS_BLUE_RGB_VALUE, CSS_BLUE_NAME);
	    nameColourHashtable.put(CSS_BROWN_RGB_VALUE, CSS_BROWN_NAME);
	    nameColourHashtable.put(CSS_BURLYWOOD_RGB_VALUE, CSS_BURLYWOOD_NAME);
	    nameColourHashtable.put(CSS_CADETBLUE_RGB_VALUE, CSS_CADETBLUE_NAME);
	    nameColourHashtable.put(CSS_CHARTREUSE_RGB_VALUE, CSS_CHARTREUSE_NAME);
	    nameColourHashtable.put(CSS_CHOCOLATE_RGB_VALUE, CSS_CHOCOLATE_NAME);
	    nameColourHashtable.put(CSS_CORAL_RGB_VALUE, CSS_CORAL_NAME);
	    nameColourHashtable.put(CSS_CORNFLOWERBLUE_RGB_VALUE, CSS_CORNFLOWERBLUE_NAME);
	    nameColourHashtable.put(CSS_CORNSILK_RGB_VALUE, CSS_CORNSILK_NAME);
	    nameColourHashtable.put(CSS_CRIMSON_RGB_VALUE, CSS_CRIMSON_NAME);
	    nameColourHashtable.put(CSS_CYAN_RGB_VALUE, CSS_CYAN_NAME);
	    nameColourHashtable.put(CSS_DARKBLUE_RGB_VALUE, CSS_DARKBLUE_NAME);
	    nameColourHashtable.put(CSS_DARKCYAN_RGB_VALUE, CSS_DARKCYAN_NAME);
	    nameColourHashtable.put(CSS_DARKGOLDENROD_RGB_VALUE, CSS_DARKGOLDENROD_NAME);
	    nameColourHashtable.put(CSS_DARKGRAY_RGB_VALUE, CSS_DARKGRAY_NAME);
	    nameColourHashtable.put(CSS_DARKGREEN_RGB_VALUE, CSS_DARKGREEN_NAME);
	    nameColourHashtable.put(CSS_DARKKHAKI_RGB_VALUE, CSS_DARKKHAKI_NAME);
	    nameColourHashtable.put(CSS_DARKMAGENTA_RGB_VALUE, CSS_DARKMAGENTA_NAME);
	    nameColourHashtable.put(CSS_DARKOLIVEGREEN_RGB_VALUE, CSS_DARKOLIVEGREEN_NAME);
	    nameColourHashtable.put(CSS_DARKORANGE_RGB_VALUE, CSS_DARKORANGE_NAME);
	    nameColourHashtable.put(CSS_DARKORCHID_RGB_VALUE, CSS_DARKORCHID_NAME);
	    nameColourHashtable.put(CSS_DARKRED_RGB_VALUE, CSS_DARKRED_NAME);
	    nameColourHashtable.put(CSS_DARKSALMON_RGB_VALUE, CSS_DARKSALMON_NAME);
	    nameColourHashtable.put(CSS_DARKSEAGREEN_RGB_VALUE, CSS_DARKSEAGREEN_NAME);
	    nameColourHashtable.put(CSS_DARKSLATEBLUE_RGB_VALUE, CSS_DARKSLATEBLUE_NAME);
	    nameColourHashtable.put(CSS_DARKSLATEGRAY_RGB_VALUE, CSS_DARKSLATEGRAY_NAME);
	    nameColourHashtable.put(CSS_DARKTURQUOISE_RGB_VALUE, CSS_DARKTURQUOISE_NAME);
	    nameColourHashtable.put(CSS_DARKVIOLET_RGB_VALUE, CSS_DARKVIOLET_NAME);
	    nameColourHashtable.put(CSS_DEEPPINK_RGB_VALUE, CSS_DEEPPINK_NAME);
	    nameColourHashtable.put(CSS_DEEPSKYBLUE_RGB_VALUE, CSS_DEEPSKYBLUE_NAME);
	    nameColourHashtable.put(CSS_DIMGRAY_RGB_VALUE, CSS_DIMGRAY_NAME);
	    nameColourHashtable.put(CSS_DODGERBLUE_RGB_VALUE, CSS_DODGERBLUE_NAME);
	    nameColourHashtable.put(CSS_FIREBRICK_RGB_VALUE, CSS_FIREBRICK_NAME);
	    nameColourHashtable.put(CSS_FLORALWHITE_RGB_VALUE, CSS_FLORALWHITE_NAME);
	    nameColourHashtable.put(CSS_FORESTGREEN_RGB_VALUE, CSS_FORESTGREEN_NAME);
	    nameColourHashtable.put(CSS_GAINSBORO_RGB_VALUE, CSS_GAINSBORO_NAME);
	    nameColourHashtable.put(CSS_GHOSTWHITE_RGB_VALUE, CSS_GHOSTWHITE_NAME);
	    nameColourHashtable.put(CSS_GOLDENROD_RGB_VALUE, CSS_GOLDENROD_NAME);
	    nameColourHashtable.put(CSS_GOLD_RGB_VALUE, CSS_GOLD_NAME);
	    nameColourHashtable.put(CSS_GRAY_RGB_VALUE, CSS_GRAY_NAME);
	    nameColourHashtable.put(CSS_GREENYELLOW_RGB_VALUE, CSS_GREENYELLOW_NAME);
	    nameColourHashtable.put(CSS_GREEN_RGB_VALUE, CSS_GREEN_NAME);
	    nameColourHashtable.put(CSS_HONEYDEW_RGB_VALUE, CSS_HONEYDEW_NAME);
	    nameColourHashtable.put(CSS_HOTPINK_RGB_VALUE, CSS_HOTPINK_NAME);
	    nameColourHashtable.put(CSS_INDIGO_RGB_VALUE, CSS_INDIGO_NAME);
	    nameColourHashtable.put(CSS_INDIANRED_RGB_VALUE, CSS_INDIANRED_NAME);
	    nameColourHashtable.put(CSS_IVORY_RGB_VALUE, CSS_IVORY_NAME);
	    nameColourHashtable.put(CSS_KHAKI_RGB_VALUE, CSS_KHAKI_NAME);
	    nameColourHashtable.put(CSS_LAVENDERBLUSH_RGB_VALUE, CSS_LAVENDERBLUSH_NAME);
	    nameColourHashtable.put(CSS_LAVENDER_RGB_VALUE, CSS_LAVENDER_NAME);
	    nameColourHashtable.put(CSS_LAWNGREEN_RGB_VALUE, CSS_LAWNGREEN_NAME);
	    nameColourHashtable.put(CSS_LEMONCHIFFON_RGB_VALUE, CSS_LEMONCHIFFON_NAME);
	    nameColourHashtable.put(CSS_LIGHTBLUE_RGB_VALUE, CSS_LIGHTBLUE_NAME);
	    nameColourHashtable.put(CSS_LIGHTCORAL_RGB_VALUE, CSS_LIGHTCORAL_NAME);
	    nameColourHashtable.put(CSS_LIGHTCYAN_RGB_VALUE, CSS_LIGHTCYAN_NAME);
	    nameColourHashtable.put(CSS_LIGHTGOLDENRODYELLOW_RGB_VALUE, CSS_LIGHTGOLDENRODYELLOW_NAME);
	    nameColourHashtable.put(CSS_LIGHTGRAY_RGB_VALUE, CSS_LIGHTGRAY_NAME);
	    nameColourHashtable.put(CSS_LIGHTGREEN_RGB_VALUE, CSS_LIGHTGREEN_NAME);
	    nameColourHashtable.put(CSS_LIGHTPINK_RGB_VALUE, CSS_LIGHTPINK_NAME);
	    nameColourHashtable.put(CSS_LIGHTSALMON_RGB_VALUE, CSS_LIGHTSALMON_NAME);
	    nameColourHashtable.put(CSS_LIGHTSEAGREEN_RGB_VALUE, CSS_LIGHTSEAGREEN_NAME);
	    nameColourHashtable.put(CSS_LIGHTSKYBLUE_RGB_VALUE, CSS_LIGHTSKYBLUE_NAME);
	    nameColourHashtable.put(CSS_LIGHTSLATEGRAY_RGB_VALUE, CSS_LIGHTSLATEGRAY_NAME);
	    nameColourHashtable.put(CSS_LIGHTSTEELBLUE_RGB_VALUE, CSS_LIGHTSTEELBLUE_NAME);
	    nameColourHashtable.put(CSS_LIGHTYELLOW_RGB_VALUE, CSS_LIGHTYELLOW_NAME);
	    nameColourHashtable.put(CSS_LIMEGREEN_RGB_VALUE, CSS_LIMEGREEN_NAME);
	    nameColourHashtable.put(CSS_LIME_RGB_VALUE, CSS_LIME_NAME);
	    nameColourHashtable.put(CSS_MAGENTA_RGB_VALUE, CSS_MAGENTA_NAME);
	    nameColourHashtable.put(CSS_MAROON_RGB_VALUE, CSS_MAROON_NAME);
	    nameColourHashtable.put(CSS_MEDIUMAQUAMARINE_RGB_VALUE, CSS_MEDIUMAQUAMARINE_NAME);
	    nameColourHashtable.put(CSS_MEDIUMBLUE_RGB_VALUE, CSS_MEDIUMBLUE_NAME);
	    nameColourHashtable.put(CSS_MEDIUMORCHID_RGB_VALUE, CSS_MEDIUMORCHID_NAME);
	    nameColourHashtable.put(CSS_MEDIUMPURPLE_RGB_VALUE, CSS_MEDIUMPURPLE_NAME);
	    nameColourHashtable.put(CSS_MEDIUMSEAGREEN_RGB_VALUE, CSS_MEDIUMSEAGREEN_NAME);
	    nameColourHashtable.put(CSS_MEDIUMSLATEBLUE_RGB_VALUE, CSS_MEDIUMSLATEBLUE_NAME);
	    nameColourHashtable.put(CSS_MEDIUMSPRINGGREEN_RGB_VALUE, CSS_MEDIUMSPRINGGREEN_NAME);
	    nameColourHashtable.put(CSS_MEDIUMTURQUOISE_RGB_VALUE, CSS_MEDIUMTURQUOISE_NAME);
	    nameColourHashtable.put(CSS_MEDIUMVIOLETRED_RGB_VALUE, CSS_MEDIUMVIOLETRED_NAME);
	    nameColourHashtable.put(CSS_MIDNIGHTBLUE_RGB_VALUE, CSS_MIDNIGHTBLUE_NAME);
	    nameColourHashtable.put(CSS_MINTCREAM_RGB_VALUE, CSS_MINTCREAM_NAME);
	    nameColourHashtable.put(CSS_MISTYROSE_RGB_VALUE, CSS_MISTYROSE_NAME);
	    nameColourHashtable.put(CSS_MOCCASIN_RGB_VALUE, CSS_MOCCASIN_NAME);
	    nameColourHashtable.put(CSS_NAVAJOWHITE_RGB_VALUE, CSS_NAVAJOWHITE_NAME);
	    nameColourHashtable.put(CSS_NAVY_RGB_VALUE, CSS_NAVY_NAME);
	    nameColourHashtable.put(CSS_OLDLACE_RGB_VALUE, CSS_OLDLACE_NAME);
	    nameColourHashtable.put(CSS_OLIVEDRAB_RGB_VALUE, CSS_OLIVEDRAB_NAME);
	    nameColourHashtable.put(CSS_OLIVE_RGB_VALUE, CSS_OLIVE_NAME);
	    nameColourHashtable.put(CSS_ORANGERED_RGB_VALUE, CSS_ORANGERED_NAME);
	    nameColourHashtable.put(CSS_ORANGE_RGB_VALUE, CSS_ORANGE_NAME);
	    nameColourHashtable.put(CSS_ORCHID_RGB_VALUE, CSS_ORCHID_NAME);
	    nameColourHashtable.put(CSS_PALEGOLDENROD_RGB_VALUE, CSS_PALEGOLDENROD_NAME);
	    nameColourHashtable.put(CSS_PALEGREEN_RGB_VALUE, CSS_PALEGREEN_NAME);
	    nameColourHashtable.put(CSS_PALETURQUOISE_RGB_VALUE, CSS_PALETURQUOISE_NAME);
	    nameColourHashtable.put(CSS_PALEVIOLETRED_RGB_VALUE, CSS_PALEVIOLETRED_NAME);
	    nameColourHashtable.put(CSS_PAPAYAWHIP_RGB_VALUE, CSS_PAPAYAWHIP_NAME);
	    nameColourHashtable.put(CSS_PEACHPUFF_RGB_VALUE, CSS_PEACHPUFF_NAME);
	    nameColourHashtable.put(CSS_PERU_RGB_VALUE, CSS_PERU_NAME);
	    nameColourHashtable.put(CSS_PINK_RGB_VALUE, CSS_PINK_NAME);
	    nameColourHashtable.put(CSS_PLUM_RGB_VALUE, CSS_PLUM_NAME);
	    nameColourHashtable.put(CSS_POWDERBLUE_RGB_VALUE, CSS_POWDERBLUE_NAME);
	    nameColourHashtable.put(CSS_PURPLE_RGB_VALUE, CSS_PURPLE_NAME);
	    nameColourHashtable.put(CSS_RED_RGB_VALUE, CSS_RED_NAME);
	    nameColourHashtable.put(CSS_ROSYBROWN_RGB_VALUE, CSS_ROSYBROWN_NAME);
	    nameColourHashtable.put(CSS_ROYALBLUE_RGB_VALUE, CSS_ROYALBLUE_NAME);
	    nameColourHashtable.put(CSS_SADDLEBROWN_RGB_VALUE, CSS_SADDLEBROWN_NAME);
	    nameColourHashtable.put(CSS_SALMON_RGB_VALUE, CSS_SALMON_NAME);
	    nameColourHashtable.put(CSS_SANDYBROWN_RGB_VALUE, CSS_SANDYBROWN_NAME);
	    nameColourHashtable.put(CSS_SEAGREEN_RGB_VALUE, CSS_SEAGREEN_NAME);
	    nameColourHashtable.put(CSS_SEASHELL_RGB_VALUE, CSS_SEASHELL_NAME);
	    nameColourHashtable.put(CSS_SIENNA_RGB_VALUE, CSS_SIENNA_NAME);
	    nameColourHashtable.put(CSS_SILVER_RGB_VALUE, CSS_SILVER_NAME);
	    nameColourHashtable.put(CSS_SKYBLUE_RGB_VALUE, CSS_SKYBLUE_NAME);
	    nameColourHashtable.put(CSS_SLATEBLUE_RGB_VALUE, CSS_SLATEBLUE_NAME);
	    nameColourHashtable.put(CSS_SLATEGRAY_RGB_VALUE, CSS_SLATEGRAY_NAME);
	    nameColourHashtable.put(CSS_SNOW_RGB_VALUE, CSS_SNOW_NAME);
	    nameColourHashtable.put(CSS_SPRINGGREEN_RGB_VALUE, CSS_SPRINGGREEN_NAME);
	    nameColourHashtable.put(CSS_STEELBLUE_RGB_VALUE, CSS_STEELBLUE_NAME);
	    nameColourHashtable.put(CSS_TAN_RGB_VALUE, CSS_TAN_NAME);
	    nameColourHashtable.put(CSS_TEAL_RGB_VALUE, CSS_TEAL_NAME);
	    nameColourHashtable.put(CSS_THISTLE_RGB_VALUE, CSS_THISTLE_NAME);
	    nameColourHashtable.put(CSS_TOMATO_RGB_VALUE, CSS_TOMATO_NAME);
	    nameColourHashtable.put(CSS_TURQUOISE_RGB_VALUE, CSS_TURQUOISE_NAME);
	    nameColourHashtable.put(CSS_VIOLET_RGB_VALUE, CSS_VIOLET_NAME);
	    nameColourHashtable.put(CSS_WHEAT_RGB_VALUE, CSS_WHEAT_NAME);
	    nameColourHashtable.put(CSS_WHITESMOKE_RGB_VALUE, CSS_WHITESMOKE_NAME);
	    nameColourHashtable.put(CSS_WHITE_RGB_VALUE, CSS_WHITE_NAME);
	    nameColourHashtable.put(CSS_YELLOWGREEN_RGB_VALUE, CSS_YELLOWGREEN_NAME);
	    nameColourHashtable.put(CSS_YELLOW_RGB_VALUE, CSS_YELLOW_NAME);
	}



	private CSSColors() {
		super();

		colourHashtable 	= new HashMap<>();
		nameColourHashtable = new HashMap<>();
		userColours			= new HashMap<>();

		createColourHashTable();
		createNameColourHashTable();
	}



	/**
	 * @param name The name of the wanted colour or null if the colour is not found.
	 * @return The colour.
	 */
	public Color getColor(final String name) {
		if(name==null || name.length()==0)
			return null;

		final Color col = colourHashtable.get(name);

		return col==null ? userColours.get(name) : col;
	}




	/**
	 * Adds a colour defined by the user.
	 * @param col The colour to add.
	 * @param name The name of this colour.
	 */
	public void addUserColor(final Color col, final String name) {
		if(name==null || col==null || name.length()==0)
			return ;

		userColours.put(name, col);
	}




	/**
	 * @param col The colour of the colour with the given name.
	 * @param create If true and if the colour is not found, then the colour will be created, added to the user's colours and returned.
	 * The created name will be then its hexadecimal value.
	 * @return The name of the colour or null.
	 */
	public String getColorName(final Color col, final boolean create) {
		if(col==null)
			return null;

		String name = nameColourHashtable.get(col);

		if(name!=null)
			return name;

		final Iterator<Entry<String, Color>> itCols = userColours.entrySet().iterator();
		Entry<String, Color> entry = null;

		while(itCols.hasNext() && entry==null) {
			entry = itCols.next();
			if(!entry.getValue().equals(col))
				entry = null;
		}

		if(entry!=null)
			return entry.getKey();

		if(create) {
			addUserColor(col, rgbToHex(col));
			return getColorName(col, false);
		}

		return null;
	}



	/**
	 * Converts an hexadecimal colour to an RBG one.
	 * @param hex The colour in hexadecimal.
	 * @return The corresponding colour or null if there is a problem.
	 * @since 2.0.0
	 */
	public Color hexToRBG(final String hex) {
		if(hex==null || !hex.startsWith("#")) //$NON-NLS-1$
			return null;

		try {
			String col = hex.substring(1);
			String r, g, b;

			switch(col.length()) {
				case 6:// #112233 for instance.
					r = col.substring(0, 2);
					g = col.substring(2, 4);
					b = col.substring(4, 6);
					break;

				case 3: // #123 for instance.
					r = String.valueOf(col.charAt(0));
					g = String.valueOf(col.charAt(1));
					b = String.valueOf(col.charAt(2));

					r+=r;
					g+=g;
					b+=b;
					break;

				default:
					throw new IllegalArgumentException();
			}

			return new Color(Integer.parseInt(r, 16), Integer.parseInt(g, 16), Integer.parseInt(b, 16));
		}
		catch(Exception e) { BadaboomCollector.INSTANCE.add(e); }
		return null;
	}




	/**
	 * Creates the corresponding hexadecimal colour given a RGB one.
	 * @param c The given RBG colour.
	 * @return The corresponding hexadecimal colour.
	 * @since 2.0.0
	 */
	public String rgbToHex(final Color c) {
		if(c==null)
			return ""; //$NON-NLS-1$

		String r = Integer.toHexString(c.getRed());
		String g = Integer.toHexString(c.getGreen());
		String b = Integer.toHexString(c.getBlue());

		if(r.length()==1)
			r = '0' + r;

		if(g.length()==1)
			g = '0' + g;

		if(b.length()==1)
			b = '0' + b;

		return '#' + r + g + b;
	}




	/**
	 * Allows to get a RGB colour from a string which can be either an hexadecimal colour (CSS) or an explicit colour name (blue,...)
	 * or an RGB colour in this format: rgb(r,g,b).
	 * If <code>str</code> is null, null is returned.
	 * @param str The colour to read.
	 * @return The found colour or null.
	 * @since 2.0.0
	 */
	public Color getRGBColour(final String str) {
		Color c = null;

		if(str!=null)
			if(str.startsWith("#"))//$NON-NLS-1$
				c = hexToRBG(str);
			else
				if(str.startsWith("rgb("))//$NON-NLS-1$
					c = svgRgbtoRgb(str);
				else
					c = getColor(str);

		return c;
	}



	/**
	 * Converts an SVG RGB colour: "rgb(int,int,int)" or "rgb(pc,pc,pc)" in an AWT colour.
	 * @param str The string to parse.
	 * @return The extracted colour or null.
	 * @since 2.0.0
	 */
	public Color svgRgbtoRgb(final String str) {
		if(str==null || !str.startsWith("rgb(") || !str.endsWith(")"))//$NON-NLS-1$//$NON-NLS-2$
			return null;

		Color c = null;
		String s = str.substring(4, str.length()-1);
		String[] rgbs = s.split(",");//$NON-NLS-1$

		try {
			if(rgbs!=null && rgbs.length==3)
			{
				rgbs[0] = rgbs[0].replaceAll("[\t ]", "");//$NON-NLS-1$//$NON-NLS-2$
				rgbs[1] = rgbs[1].replaceAll("[\t ]", "");//$NON-NLS-1$//$NON-NLS-2$
				rgbs[2] = rgbs[2].replaceAll("[\t ]", "");//$NON-NLS-1$//$NON-NLS-2$

				if(rgbs[0].contains("%")) {//$NON-NLS-1$
					if(rgbs[0].endsWith("%") && rgbs[1].endsWith("%") && rgbs[2].endsWith("%"))//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
						c = new Color((float)(Double.parseDouble(rgbs[0].substring(0, rgbs[0].length()-1))/100.),
								(float)(Double.parseDouble(rgbs[1].substring(0, rgbs[1].length()-1))/100.),
								(float)(Double.parseDouble(rgbs[2].substring(0, rgbs[2].length()-1))/100.));
				}
				else
					c = new Color(Integer.parseInt(rgbs[0]), Integer.parseInt(rgbs[1]), Integer.parseInt(rgbs[2]));
			}
		}
		catch(IllegalArgumentException e) { return null; }
		return c;
	}
}
