package net.sf.latexdraw.parsers;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.parsers.svg.CSSColors;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestCssColor {
	@Test
	public void testGetColour() {
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_ALICEBLUE_NAME), CSSColors.CSS_ALICEBLUE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_ANTIQUEWHITE_NAME), CSSColors.CSS_ANTIQUEWHITE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_AQUA_NAME), CSSColors.CSS_AQUA_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_AQUAMARINE_NAME), CSSColors.CSS_AQUAMARINE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_AZURE_NAME), CSSColors.CSS_AZURE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_BEIGE_NAME), CSSColors.CSS_BEIGE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_BISQUE_NAME), CSSColors.CSS_BISQUE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_BLACK_NAME), CSSColors.CSS_BLACK_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_BLANCHEDALMOND_NAME), CSSColors.CSS_BLANCHEDALMOND_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_BLUE_NAME), CSSColors.CSS_BLUE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_BLUEVIOLET_NAME), CSSColors.CSS_BLUEVIOLET_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_BROWN_NAME), CSSColors.CSS_BROWN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_BURLYWOOD_NAME), CSSColors.CSS_BURLYWOOD_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_CADETBLUE_NAME), CSSColors.CSS_CADETBLUE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_CHARTREUSE_NAME), CSSColors.CSS_CHARTREUSE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_CHOCOLATE_NAME), CSSColors.CSS_CHOCOLATE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_CORAL_NAME), CSSColors.CSS_CORAL_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_CORNFLOWERBLUE_NAME), CSSColors.CSS_CORNFLOWERBLUE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_CORNSILK_NAME), CSSColors.CSS_CORNSILK_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_CRIMSON_NAME), CSSColors.CSS_CRIMSON_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_CYAN_NAME), CSSColors.CSS_CYAN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DARKBLUE_NAME), CSSColors.CSS_DARKBLUE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DARKCYAN_NAME), CSSColors.CSS_DARKCYAN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DARKGOLDENROD_NAME), CSSColors.CSS_DARKGOLDENROD_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DARKGRAY_NAME), CSSColors.CSS_DARKGRAY_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DARKGREEN_NAME), CSSColors.CSS_DARKGREEN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DARKGREY_NAME), CSSColors.CSS_DARKGRAY_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DARKKHAKI_NAME), CSSColors.CSS_DARKKHAKI_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DARKMAGENTA_NAME), CSSColors.CSS_DARKMAGENTA_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DARKOLIVEGREEN_NAME), CSSColors.CSS_DARKOLIVEGREEN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DARKORANGE_NAME), CSSColors.CSS_DARKORANGE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DARKORCHID_NAME), CSSColors.CSS_DARKORCHID_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DARKRED_NAME), CSSColors.CSS_DARKRED_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DARKSALMON_NAME), CSSColors.CSS_DARKSALMON_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DARKSEAGREEN_NAME), CSSColors.CSS_DARKSEAGREEN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DARKSLATEBLUE_NAME), CSSColors.CSS_DARKSLATEBLUE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DARKSLATEGRAY_NAME), CSSColors.CSS_DARKSLATEGRAY_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DARKSLATEGREY_NAME), CSSColors.CSS_DARKSLATEGRAY_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DARKTURQUOISE_NAME), CSSColors.CSS_DARKTURQUOISE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DARKVIOLET_NAME), CSSColors.CSS_DARKVIOLET_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DEEPPINK_NAME), CSSColors.CSS_DEEPPINK_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DEEPSKYBLUE_NAME), CSSColors.CSS_DEEPSKYBLUE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DIMGRAY_NAME), CSSColors.CSS_DIMGRAY_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DIMGREY_NAME), CSSColors.CSS_DIMGRAY_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_DODGERBLUE_NAME), CSSColors.CSS_DODGERBLUE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_FIREBRICK_NAME), CSSColors.CSS_FIREBRICK_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_FLORALWHITE_NAME), CSSColors.CSS_FLORALWHITE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_FORESTGREEN_NAME), CSSColors.CSS_FORESTGREEN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_FUCHSIA_NAME), CSSColors.CSS_FUCHSIA_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_GAINSBORO_NAME), CSSColors.CSS_GAINSBORO_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_GHOSTWHITE_NAME), CSSColors.CSS_GHOSTWHITE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_GOLD_NAME), CSSColors.CSS_GOLD_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_GOLDENROD_NAME), CSSColors.CSS_GOLDENROD_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_GRAY_NAME), CSSColors.CSS_GRAY_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_GREEN_NAME), CSSColors.CSS_GREEN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_GREENYELLOW_NAME), CSSColors.CSS_GREENYELLOW_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_GREY_NAME), CSSColors.CSS_GRAY_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_HONEYDEW_NAME), CSSColors.CSS_HONEYDEW_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_HOTPINK_NAME), CSSColors.CSS_HOTPINK_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_INDIGO_NAME), CSSColors.CSS_INDIGO_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_IVORY_NAME), CSSColors.CSS_IVORY_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_INDIANRED_NAME), CSSColors.CSS_INDIANRED_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_KHAKI_NAME), CSSColors.CSS_KHAKI_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_LAVENDER_NAME), CSSColors.CSS_LAVENDER_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_LAVENDERBLUSH_NAME), CSSColors.CSS_LAVENDERBLUSH_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_LAWNGREEN_NAME), CSSColors.CSS_LAWNGREEN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_LEMONCHIFFON_NAME), CSSColors.CSS_LEMONCHIFFON_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_LIGHTBLUE_NAME), CSSColors.CSS_LIGHTBLUE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_LIGHTCORAL_NAME), CSSColors.CSS_LIGHTCORAL_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_LIGHTCYAN_NAME), CSSColors.CSS_LIGHTCYAN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_LIGHTGOLDENRODYELLOW_NAME), CSSColors.CSS_LIGHTGOLDENRODYELLOW_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_LIGHTGRAY_NAME), CSSColors.CSS_LIGHTGRAY_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_LIGHTGREEN_NAME), CSSColors.CSS_LIGHTGREEN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_LIGHTGREY_NAME), CSSColors.CSS_LIGHTGRAY_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_LIGHTPINK_NAME), CSSColors.CSS_LIGHTPINK_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_LIGHTSALMON_NAME), CSSColors.CSS_LIGHTSALMON_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_LIGHTSEAGREEN_NAME), CSSColors.CSS_LIGHTSEAGREEN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_LIGHTSKYBLUE_NAME), CSSColors.CSS_LIGHTSKYBLUE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_LIGHTSLATEGRAY_NAME), CSSColors.CSS_LIGHTSLATEGRAY_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_LIGHTSLATEGREY_NAME), CSSColors.CSS_LIGHTSLATEGRAY_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_LIGHTSTEELBLUE_NAME), CSSColors.CSS_LIGHTSTEELBLUE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_LIGHTYELLOW_NAME), CSSColors.CSS_LIGHTYELLOW_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_LIME_NAME), CSSColors.CSS_LIME_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_LIMEGREEN_NAME), CSSColors.CSS_LIMEGREEN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_MAGENTA_NAME), CSSColors.CSS_MAGENTA_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_MAROON_NAME), CSSColors.CSS_MAROON_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_MEDIUMAQUAMARINE_NAME), CSSColors.CSS_MEDIUMAQUAMARINE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_MEDIUMBLUE_NAME), CSSColors.CSS_MEDIUMBLUE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_MEDIUMORCHID_NAME), CSSColors.CSS_MEDIUMORCHID_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_MEDIUMPURPLE_NAME), CSSColors.CSS_MEDIUMPURPLE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_MEDIUMSEAGREEN_NAME), CSSColors.CSS_MEDIUMSEAGREEN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_MEDIUMSLATEBLUE_NAME), CSSColors.CSS_MEDIUMSLATEBLUE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_MEDIUMSPRINGGREEN_NAME), CSSColors.CSS_MEDIUMSPRINGGREEN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_MEDIUMTURQUOISE_NAME), CSSColors.CSS_MEDIUMTURQUOISE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_MEDIUMVIOLETRED_NAME), CSSColors.CSS_MEDIUMVIOLETRED_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_MIDNIGHTBLUE_NAME), CSSColors.CSS_MIDNIGHTBLUE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_MINTCREAM_NAME), CSSColors.CSS_MINTCREAM_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_MISTYROSE_NAME), CSSColors.CSS_MISTYROSE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_MOCCASIN_NAME), CSSColors.CSS_MOCCASIN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_NAVAJOWHITE_NAME), CSSColors.CSS_NAVAJOWHITE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_NAVY_NAME), CSSColors.CSS_NAVY_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_OLDLACE_NAME), CSSColors.CSS_OLDLACE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_OLIVEDRAB_NAME), CSSColors.CSS_OLIVEDRAB_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_OLIVE_NAME), CSSColors.CSS_OLIVE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_ORANGERED_NAME), CSSColors.CSS_ORANGERED_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_ORANGE_NAME), CSSColors.CSS_ORANGE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_ORCHID_NAME), CSSColors.CSS_ORCHID_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_PALEGOLDENROD_NAME), CSSColors.CSS_PALEGOLDENROD_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_PALEGREEN_NAME), CSSColors.CSS_PALEGREEN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_PALETURQUOISE_NAME), CSSColors.CSS_PALETURQUOISE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_PALEVIOLETRED_NAME), CSSColors.CSS_PALEVIOLETRED_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_PAPAYAWHIP_NAME), CSSColors.CSS_PAPAYAWHIP_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_PEACHPUFF_NAME), CSSColors.CSS_PEACHPUFF_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_PERU_NAME), CSSColors.CSS_PERU_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_PINK_NAME), CSSColors.CSS_PINK_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_PLUM_NAME), CSSColors.CSS_PLUM_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_POWDERBLUE_NAME), CSSColors.CSS_POWDERBLUE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_PURPLE_NAME), CSSColors.CSS_PURPLE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_RED_NAME), CSSColors.CSS_RED_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_ROSYBROWN_NAME), CSSColors.CSS_ROSYBROWN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_ROYALBLUE_NAME), CSSColors.CSS_ROYALBLUE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_SADDLEBROWN_NAME), CSSColors.CSS_SADDLEBROWN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_SALMON_NAME), CSSColors.CSS_SALMON_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_SANDYBROWN_NAME), CSSColors.CSS_SANDYBROWN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_SEAGREEN_NAME), CSSColors.CSS_SEAGREEN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_SEASHELL_NAME), CSSColors.CSS_SEASHELL_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_SIENNA_NAME), CSSColors.CSS_SIENNA_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_SILVER_NAME), CSSColors.CSS_SILVER_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_SKYBLUE_NAME), CSSColors.CSS_SKYBLUE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_SLATEBLUE_NAME), CSSColors.CSS_SLATEBLUE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_SLATEGRAY_NAME), CSSColors.CSS_SLATEGRAY_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_SLATEGREY_NAME), CSSColors.CSS_SLATEGRAY_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_SNOW_NAME), CSSColors.CSS_SNOW_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_SPRINGGREEN_NAME), CSSColors.CSS_SPRINGGREEN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_STEELBLUE_NAME), CSSColors.CSS_STEELBLUE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_TAN_NAME), CSSColors.CSS_TAN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_TEAL_NAME), CSSColors.CSS_TEAL_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_THISTLE_NAME), CSSColors.CSS_THISTLE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_TOMATO_NAME), CSSColors.CSS_TOMATO_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_TURQUOISE_NAME), CSSColors.CSS_TURQUOISE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_VIOLET_NAME), CSSColors.CSS_VIOLET_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_WHEAT_NAME), CSSColors.CSS_WHEAT_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_WHITESMOKE_NAME), CSSColors.CSS_WHITESMOKE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_WHITE_NAME), CSSColors.CSS_WHITE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor("white"), CSSColors.CSS_WHITE_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_YELLOWGREEN_NAME), CSSColors.CSS_YELLOWGREEN_RGB_VALUE);
		assertEquals(CSSColors.INSTANCE.getColor(CSSColors.CSS_YELLOW_NAME), CSSColors.CSS_YELLOW_RGB_VALUE);
	}

	@Test
	public void testGetColourNULL() {
		assertNull(CSSColors.INSTANCE.getColor(null));
	}

	@Test
	public void testGetColourKO() {
		assertNull(CSSColors.INSTANCE.getColor(""));
	}

	@Test
	public void testGetColorNameNULL() {
		assertNull(CSSColors.INSTANCE.getColorName(null, true));
	}

	@Test
	public void testGetColorNameKO() {
		assertNull(CSSColors.INSTANCE.getColorName(ShapeFactory.INST.createColorInt(3, 2, 1), false));
	}

	@Test
	public void testGetColorNameExistingNotSameObject() {
		assertNotNull(CSSColors.INSTANCE.getColorName(ShapeFactory.INST.createColorInt(4, 2, 1), true));
	}

	@Test
	public void testGetColorName() {
		assertEquals(CSSColors.CSS_ALICEBLUE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_ALICEBLUE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_ANTIQUEWHITE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_ANTIQUEWHITE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_AQUAMARINE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_AQUAMARINE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_AZURE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_AZURE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_BEIGE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_BEIGE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_BISQUE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_BISQUE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_BLACK_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_BLACK_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_BLANCHEDALMOND_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_BLANCHEDALMOND_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_BLUE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_BLUE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_BLUEVIOLET_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_BLUEVIOLET_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_BROWN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_BROWN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_BURLYWOOD_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_BURLYWOOD_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_CADETBLUE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_CADETBLUE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_CHARTREUSE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_CHARTREUSE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_CHOCOLATE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_CHOCOLATE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_CORAL_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_CORAL_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_CORNFLOWERBLUE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_CORNFLOWERBLUE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_CORNSILK_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_CORNSILK_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_CRIMSON_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_CRIMSON_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_CYAN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_CYAN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_DARKBLUE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_DARKBLUE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_DARKCYAN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_DARKCYAN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_DARKGOLDENROD_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_DARKGOLDENROD_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_DARKGRAY_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_DARKGRAY_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_DARKGREEN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_DARKGREEN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_DARKKHAKI_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_DARKKHAKI_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_DARKMAGENTA_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_DARKMAGENTA_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_DARKOLIVEGREEN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_DARKOLIVEGREEN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_DARKORANGE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_DARKORANGE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_DARKORCHID_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_DARKORCHID_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_DARKRED_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_DARKRED_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_DARKSALMON_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_DARKSALMON_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_DARKSEAGREEN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_DARKSEAGREEN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_DARKSLATEBLUE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_DARKSLATEBLUE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_DARKSLATEGRAY_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_DARKSLATEGRAY_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_DARKTURQUOISE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_DARKTURQUOISE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_DARKVIOLET_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_DARKVIOLET_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_DEEPPINK_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_DEEPPINK_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_DEEPSKYBLUE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_DEEPSKYBLUE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_DIMGRAY_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_DIMGRAY_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_DODGERBLUE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_DODGERBLUE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_FIREBRICK_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_FIREBRICK_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_FLORALWHITE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_FLORALWHITE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_FORESTGREEN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_FORESTGREEN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_GAINSBORO_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_GAINSBORO_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_GHOSTWHITE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_GHOSTWHITE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_GOLD_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_GOLD_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_GOLDENROD_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_GOLDENROD_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_GRAY_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_GRAY_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_GREEN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_GREEN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_GREENYELLOW_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_GREENYELLOW_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_HONEYDEW_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_HONEYDEW_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_HOTPINK_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_HOTPINK_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_INDIGO_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_INDIGO_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_IVORY_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_IVORY_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_INDIANRED_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_INDIANRED_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_KHAKI_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_KHAKI_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_LAVENDER_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_LAVENDER_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_LAVENDERBLUSH_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_LAVENDERBLUSH_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_LAWNGREEN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_LAWNGREEN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_LEMONCHIFFON_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_LEMONCHIFFON_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_LIGHTBLUE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_LIGHTBLUE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_LIGHTCORAL_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_LIGHTCORAL_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_LIGHTCYAN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_LIGHTCYAN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_LIGHTGOLDENRODYELLOW_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_LIGHTGOLDENRODYELLOW_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_LIGHTGRAY_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_LIGHTGRAY_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_LIGHTGREEN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_LIGHTGREEN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_LIGHTPINK_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_LIGHTPINK_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_LIGHTSALMON_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_LIGHTSALMON_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_LIGHTSEAGREEN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_LIGHTSEAGREEN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_LIGHTSKYBLUE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_LIGHTSKYBLUE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_LIGHTSLATEGRAY_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_LIGHTSLATEGRAY_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_LIGHTSTEELBLUE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_LIGHTSTEELBLUE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_LIGHTYELLOW_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_LIGHTYELLOW_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_LIME_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_LIME_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_LIMEGREEN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_LIMEGREEN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_MAGENTA_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_MAGENTA_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_MAROON_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_MAROON_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_MEDIUMAQUAMARINE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_MEDIUMAQUAMARINE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_MEDIUMBLUE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_MEDIUMBLUE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_MEDIUMORCHID_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_MEDIUMORCHID_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_MEDIUMPURPLE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_MEDIUMPURPLE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_MEDIUMSEAGREEN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_MEDIUMSEAGREEN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_MEDIUMSLATEBLUE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_MEDIUMSLATEBLUE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_MEDIUMSPRINGGREEN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_MEDIUMSPRINGGREEN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_MEDIUMTURQUOISE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_MEDIUMTURQUOISE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_MEDIUMVIOLETRED_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_MEDIUMVIOLETRED_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_MIDNIGHTBLUE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_MIDNIGHTBLUE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_MINTCREAM_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_MINTCREAM_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_MISTYROSE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_MISTYROSE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_MOCCASIN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_MOCCASIN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_NAVAJOWHITE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_NAVAJOWHITE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_NAVY_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_NAVY_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_OLDLACE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_OLDLACE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_OLIVEDRAB_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_OLIVEDRAB_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_OLIVE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_OLIVE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_ORANGERED_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_ORANGERED_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_ORANGE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_ORANGE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_ORCHID_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_ORCHID_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_PALEGOLDENROD_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_PALEGOLDENROD_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_PALEGREEN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_PALEGREEN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_PALETURQUOISE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_PALETURQUOISE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_PALEVIOLETRED_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_PALEVIOLETRED_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_PAPAYAWHIP_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_PAPAYAWHIP_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_PEACHPUFF_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_PEACHPUFF_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_PERU_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_PERU_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_PINK_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_PINK_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_PLUM_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_PLUM_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_POWDERBLUE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_POWDERBLUE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_PURPLE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_PURPLE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_RED_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_RED_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_ROSYBROWN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_ROSYBROWN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_ROYALBLUE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_ROYALBLUE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_SADDLEBROWN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_SADDLEBROWN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_SALMON_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_SALMON_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_SANDYBROWN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_SANDYBROWN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_SEAGREEN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_SEAGREEN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_SEASHELL_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_SEASHELL_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_SIENNA_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_SIENNA_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_SILVER_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_SILVER_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_SKYBLUE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_SKYBLUE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_SLATEBLUE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_SLATEBLUE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_SLATEGRAY_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_SLATEGRAY_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_SNOW_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_SNOW_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_SPRINGGREEN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_SPRINGGREEN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_STEELBLUE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_STEELBLUE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_TAN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_TAN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_TEAL_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_TEAL_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_THISTLE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_THISTLE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_TOMATO_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_TOMATO_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_TURQUOISE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_TURQUOISE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_VIOLET_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_VIOLET_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_WHEAT_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_WHEAT_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_WHITESMOKE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_WHITESMOKE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_WHITE_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_WHITE_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_WHITE_NAME, CSSColors.INSTANCE.getColorName(ShapeFactory.INST.createColorInt(255, 255, 255), false));
		assertEquals(CSSColors.CSS_YELLOWGREEN_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_YELLOWGREEN_RGB_VALUE, false));
		assertEquals(CSSColors.CSS_YELLOW_NAME, CSSColors.INSTANCE.getColorName(CSSColors.CSS_YELLOW_RGB_VALUE, false));
	}

	@Test
	public void testGetRGBColourNULL() {
		assertNull(CSSColors.INSTANCE.getRGBColour(null));
	}

	@Test
	public void testGetRGBColourKO() {
		assertNull(CSSColors.INSTANCE.getRGBColour(""));
	}

	@Test
	public void testGetRGBColourExistingColourNotSameObject() {
		assertEquals(ShapeFactory.INST.createColorInt(85, 136, 0), CSSColors.INSTANCE.getRGBColour("#580"));
	}

	@Test
	public void testGetRGBColourExistingColourHexa() {
		assertEquals(CSSColors.CSS_DEEPPINK_RGB_VALUE, CSSColors.INSTANCE.getRGBColour("#ff1493"));
	}

	@Test
	public void testGetRGBColourExistingColourPredefinedName() {
		assertEquals(CSSColors.CSS_BLUE_RGB_VALUE, CSSColors.INSTANCE.getRGBColour("blue"));
	}

	@Test
	public void testGetRGBColourExistingColourRGB1() {
		assertEquals(CSSColors.CSS_BLUE_RGB_VALUE, CSSColors.INSTANCE.getRGBColour("rgb(	  0% ,	0%    ,  100%  )"));
	}

	@Test
	public void testGetRGBColourExistingColourRGB2() {
		assertEquals(CSSColors.CSS_BLUE_RGB_VALUE, CSSColors.INSTANCE.getRGBColour("rgb(	  0 ,	0    ,  255  )"));
	}

	@Test
	public void testSVGRGBtoRGBKO() {
		assertNull(CSSColors.INSTANCE.svgRgbtoRgb(null));
	}

	@Test
	public void testSVGRGBtoRGBKO1() {
		assertNull(CSSColors.INSTANCE.svgRgbtoRgb("dsfdgdui"));
	}

	@Test
	public void testSVGRGBtoRGBKO2() {
		assertNull(CSSColors.INSTANCE.svgRgbtoRgb("rgb(a, b, c)"));
	}

	@Test
	public void testSVGRGBtoRGBOK1() {
		assertEquals(CSSColors.CSS_BLUE_RGB_VALUE, CSSColors.INSTANCE.svgRgbtoRgb("rgb(0,0,255)"));
	}

	@Test
	public void testSVGRGBtoRGBOK2() {
		assertEquals(CSSColors.CSS_BLUE_RGB_VALUE, CSSColors.INSTANCE.svgRgbtoRgb("rgb(	  0 ,	0    ,  255  )"));
	}

	@Test
	public void testSVGRGBtoRGBOK3() {
		assertEquals(CSSColors.CSS_BLUE_RGB_VALUE, CSSColors.INSTANCE.svgRgbtoRgb("rgb(	  0% ,	0%    ,  100%  )"));
	}
}
