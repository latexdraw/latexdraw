package net.sf.latexdraw.parsers;

import java.util.Optional;
import net.sf.latexdraw.data.DoubleData;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Theories.class)
public class TestDviPsColors {
	@Rule public ExpectedException exceptionGrabber = ExpectedException.none();

	@Test
	public void testConvertHTML2rgbsuccess() {
		assertEquals(ShapeFactory.INST.createColor(1d, 0, 100d / 255d, 1d), DviPsColors.INSTANCE.convertHTML2rgb("#FF0064"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConvertHTML2rgbfailtooshort() {
		DviPsColors.INSTANCE.convertHTML2rgb("DU87");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConvertHTML2rgbfailtoolong() {
		DviPsColors.INSTANCE.convertHTML2rgb("#FF00643");
	}

	@Test
	public void testConvertRGB2rgbsuccess() {
		assertEquals(ShapeFactory.INST.createColor(0d, 0.5, 1d, 1d), DviPsColors.INSTANCE.convertRGB2rgb(0, 255d / 2d, 255d));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConvertRGB2rgbfail() {
		DviPsColors.INSTANCE.convertRGB2rgb(-1d, -255d / 2d, -255d);
	}

	@Test
	public void testConvertcmyk2rgbsuccess() {
		assertEquals(ShapeFactory.INST.createColor(1d, 0d, 100d / 255d, 1d), DviPsColors.INSTANCE.convertcmyk2rgb(0d, 1d, 0.608, 0d));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConvertcmyk2rgbfail() {
		DviPsColors.INSTANCE.convertcmyk2rgb(-1d, -2d, 1d, 3d);
	}

	@Test
	public void testGetColorNameNULL() {
		assertEquals(Optional.empty(), DviPsColors.INSTANCE.getColourName(null));
	}

	@SuppressWarnings("all")
	@Test
	public void testGetColorNameOK() {
		Color c2 = ShapeFactory.INST.createColor(218d / 255d, 29d / 255d, 78d / 255d, 1d);
		Optional<String> nameColour = DviPsColors.INSTANCE.addUserColour(c2);
		assertEquals(nameColour.get(), DviPsColors.INSTANCE.getColourName(c2).get());
	}

	@SuppressWarnings("all")
	@Test
	public void testGetColorNamePredefinedColours() {
		assertEquals(DviPsColors.N_APRICOT, DviPsColors.INSTANCE.getColourName(DviPsColors.APRICOT).get());
		assertEquals(DviPsColors.N_AQUAMARINE, DviPsColors.INSTANCE.getColourName(DviPsColors.AQUAMARINE).get());
		assertEquals(DviPsColors.N_BITTERSWEET, DviPsColors.INSTANCE.getColourName(DviPsColors.BITTERSWEET).get());
		assertEquals(DviPsColors.N_BLUEGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.BLUEGREEN).get());
		assertEquals(DviPsColors.N_BLUEVIOLET, DviPsColors.INSTANCE.getColourName(DviPsColors.BLUEVIOLET).get());
		assertEquals(DviPsColors.N_BRICKRED, DviPsColors.INSTANCE.getColourName(DviPsColors.BRICKRED).get());
		assertEquals(DviPsColors.N_BURNT_ORANGE, DviPsColors.INSTANCE.getColourName(DviPsColors.BURNT_ORANGE).get());
		assertEquals(DviPsColors.N_CADETBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.CADETBLUE).get());
		assertEquals(DviPsColors.N_CARNATIONPINK, DviPsColors.INSTANCE.getColourName(DviPsColors.CARNATIONPINK).get());
		assertEquals(DviPsColors.N_CERULEAN, DviPsColors.INSTANCE.getColourName(DviPsColors.CERULEAN).get());
		assertEquals(DviPsColors.N_CORNFLOWERBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.CORNFLOWERBLUE).get());
		assertEquals(DviPsColors.N_DANDELION, DviPsColors.INSTANCE.getColourName(DviPsColors.DANDELION).get());
		assertEquals(DviPsColors.N_DARK_GRAY, DviPsColors.INSTANCE.getColourName(DviPsColors.DARKGRAY).get());
		assertEquals(DviPsColors.N_DARKORCHID, DviPsColors.INSTANCE.getColourName(DviPsColors.DARKORCHID).get());
		assertEquals(DviPsColors.N_EMERALD, DviPsColors.INSTANCE.getColourName(DviPsColors.EMERALD).get());
		assertEquals(DviPsColors.N_FORESTGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.FORESTGREEN).get());
		assertEquals(DviPsColors.N_FUSHIA, DviPsColors.INSTANCE.getColourName(DviPsColors.FUSHIA).get());
		assertEquals(DviPsColors.N_GOLDEN_ROD, DviPsColors.INSTANCE.getColourName(DviPsColors.GOLDEN_ROD).get());
		assertEquals(DviPsColors.N_GREEN_YELLOW, DviPsColors.INSTANCE.getColourName(DviPsColors.GREEN_YELLOW).get());
		assertEquals(DviPsColors.N_JUNGLEGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.JUNGLEGREEN).get());
		assertEquals(DviPsColors.N_LAVENDER, DviPsColors.INSTANCE.getColourName(DviPsColors.LAVENDER).get());
		assertEquals(DviPsColors.N_LIGHT_GRAY, DviPsColors.INSTANCE.getColourName(DviPsColors.LIGHTGRAY).get());
		assertEquals(DviPsColors.N_LIMEGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.LIMEGREEN).get());
		assertEquals(DviPsColors.N_MAHOGANY, DviPsColors.INSTANCE.getColourName(DviPsColors.MAHOGANY).get());
		assertEquals(DviPsColors.N_MAROON, DviPsColors.INSTANCE.getColourName(DviPsColors.MAROON).get());
		assertEquals(DviPsColors.N_MELON, DviPsColors.INSTANCE.getColourName(DviPsColors.MELON).get());
		assertEquals(DviPsColors.N_MIDNIGHTBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.MIDNIGHTBLUE).get());
		assertEquals(DviPsColors.N_MULBERRY, DviPsColors.INSTANCE.getColourName(DviPsColors.MULBERRY).get());
		assertEquals(DviPsColors.N_NAVYBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.NAVYBLUE).get());
		assertEquals(DviPsColors.N_OLIVEGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.OLIVEGREEN).get());
		assertEquals(DviPsColors.N_ORANGERED, DviPsColors.INSTANCE.getColourName(DviPsColors.ORANGERED).get());
		assertEquals(DviPsColors.N_ORCHID, DviPsColors.INSTANCE.getColourName(DviPsColors.ORCHID).get());
		assertEquals(DviPsColors.N_PEACH, DviPsColors.INSTANCE.getColourName(DviPsColors.PEACH).get());
		assertEquals(DviPsColors.N_PERIWINKLE, DviPsColors.INSTANCE.getColourName(DviPsColors.PERIWINKLE).get());
		assertEquals(DviPsColors.N_PINEGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.PINEGREEN).get());
		assertEquals(DviPsColors.N_PINK, DviPsColors.INSTANCE.getColourName(DviPsColors.PINK).get());
		assertEquals(DviPsColors.N_PLUM, DviPsColors.INSTANCE.getColourName(DviPsColors.PLUM).get());
		assertEquals(DviPsColors.N_PROCESSBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.PROCESSBLUE).get());
		assertEquals(DviPsColors.N_RAWSIENNA, DviPsColors.INSTANCE.getColourName(DviPsColors.RAWSIENNA).get());
		assertEquals(DviPsColors.N_RED_ORANGE, DviPsColors.INSTANCE.getColourName(DviPsColors.RED_ORANGE).get());
		assertEquals(DviPsColors.N_REDVIOLET, DviPsColors.INSTANCE.getColourName(DviPsColors.REDVIOLET).get());
		assertEquals(DviPsColors.N_RHODAMINE, DviPsColors.INSTANCE.getColourName(DviPsColors.RHODAMINE).get());
		assertEquals(DviPsColors.N_ROYALBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.ROYALBLUE).get());
		assertEquals(DviPsColors.N_ROYALPURPLE, DviPsColors.INSTANCE.getColourName(DviPsColors.ROYALPURPLE).get());
		assertEquals(DviPsColors.N_RUBINERED, DviPsColors.INSTANCE.getColourName(DviPsColors.RUBINERED).get());
		assertEquals(DviPsColors.N_SALMON, DviPsColors.INSTANCE.getColourName(DviPsColors.SALMON).get());
		assertEquals(DviPsColors.N_SEAGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.SEAGREEN).get());
		assertEquals(DviPsColors.N_SEPIA, DviPsColors.INSTANCE.getColourName(DviPsColors.SEPIA).get());
		assertEquals(DviPsColors.N_SKYBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.SKYBLUE).get());
		assertEquals(DviPsColors.N_SPRINGGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.SPRINGGREEN).get());
		assertEquals(DviPsColors.N_TAN, DviPsColors.INSTANCE.getColourName(DviPsColors.TAN).get());
		assertEquals(DviPsColors.N_TEALBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.TEALBLUE).get());
		assertEquals(DviPsColors.N_THISTLE, DviPsColors.INSTANCE.getColourName(DviPsColors.THISTLE).get());
		assertEquals(DviPsColors.N_TURQUOISE, DviPsColors.INSTANCE.getColourName(DviPsColors.TURQUOISE).get());
		assertEquals(DviPsColors.N_VIOLETRED, DviPsColors.INSTANCE.getColourName(DviPsColors.VIOLETRED).get());
		assertEquals(DviPsColors.N_WILDSTRAWBERRY, DviPsColors.INSTANCE.getColourName(DviPsColors.WILDSTRAWBERRY).get());
		assertEquals(DviPsColors.N_YELLOW_ORANGE, DviPsColors.INSTANCE.getColourName(DviPsColors.YELLOW_ORANGE).get());
		assertEquals(DviPsColors.N_YELLOWGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.YELLOWGREEN).get());
	}

	@Test
	public void testGetColorKO() {
		assertEquals(Optional.empty(), DviPsColors.INSTANCE.getColour("testColour"));
	}

	@SuppressWarnings("all")
	@Test
	public void testGetColor() {
		assertEquals(DviPsColors.APRICOT, DviPsColors.INSTANCE.getColour(DviPsColors.N_APRICOT).get());
		assertEquals(DviPsColors.AQUAMARINE, DviPsColors.INSTANCE.getColour(DviPsColors.N_AQUAMARINE).get());
		assertEquals(DviPsColors.BITTERSWEET, DviPsColors.INSTANCE.getColour(DviPsColors.N_BITTERSWEET).get());
		assertEquals(DviPsColors.BLUEGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_BLUEGREEN).get());
		assertEquals(DviPsColors.BLUEVIOLET, DviPsColors.INSTANCE.getColour(DviPsColors.N_BLUEVIOLET).get());
		assertEquals(DviPsColors.BRICKRED, DviPsColors.INSTANCE.getColour(DviPsColors.N_BRICKRED).get());
		assertEquals(DviPsColors.BURNT_ORANGE, DviPsColors.INSTANCE.getColour(DviPsColors.N_BURNT_ORANGE).get());
		assertEquals(DviPsColors.CADETBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_CADETBLUE).get());
		assertEquals(DviPsColors.CARNATIONPINK, DviPsColors.INSTANCE.getColour(DviPsColors.N_CARNATIONPINK).get());
		assertEquals(DviPsColors.CERULEAN, DviPsColors.INSTANCE.getColour(DviPsColors.N_CERULEAN).get());
		assertEquals(DviPsColors.CORNFLOWERBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_CORNFLOWERBLUE).get());
		assertEquals(DviPsColors.DANDELION, DviPsColors.INSTANCE.getColour(DviPsColors.N_DANDELION).get());
		assertEquals(DviPsColors.DARKGRAY, DviPsColors.INSTANCE.getColour(DviPsColors.N_DARK_GRAY).get());
		assertEquals(DviPsColors.DARKORCHID, DviPsColors.INSTANCE.getColour(DviPsColors.N_DARKORCHID).get());
		assertEquals(DviPsColors.EMERALD, DviPsColors.INSTANCE.getColour(DviPsColors.N_EMERALD).get());
		assertEquals(DviPsColors.FORESTGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_FORESTGREEN).get());
		assertEquals(DviPsColors.FUSHIA, DviPsColors.INSTANCE.getColour(DviPsColors.N_FUSHIA).get());
		assertEquals(DviPsColors.GOLDEN_ROD, DviPsColors.INSTANCE.getColour(DviPsColors.N_GOLDEN_ROD).get());
		assertEquals(DviPsColors.GREEN_YELLOW, DviPsColors.INSTANCE.getColour(DviPsColors.N_GREEN_YELLOW).get());
		assertEquals(DviPsColors.JUNGLEGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_JUNGLEGREEN).get());
		assertEquals(DviPsColors.LAVENDER, DviPsColors.INSTANCE.getColour(DviPsColors.N_LAVENDER).get());
		assertEquals(DviPsColors.LIGHTGRAY, DviPsColors.INSTANCE.getColour(DviPsColors.N_LIGHT_GRAY).get());
		assertEquals(DviPsColors.LIMEGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_LIMEGREEN).get());
		assertEquals(DviPsColors.MAHOGANY, DviPsColors.INSTANCE.getColour(DviPsColors.N_MAHOGANY).get());
		assertEquals(DviPsColors.MAROON, DviPsColors.INSTANCE.getColour(DviPsColors.N_MAROON).get());
		assertEquals(DviPsColors.MELON, DviPsColors.INSTANCE.getColour(DviPsColors.N_MELON).get());
		assertEquals(DviPsColors.MIDNIGHTBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_MIDNIGHTBLUE).get());
		assertEquals(DviPsColors.MULBERRY, DviPsColors.INSTANCE.getColour(DviPsColors.N_MULBERRY).get());
		assertEquals(DviPsColors.NAVYBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_NAVYBLUE).get());
		assertEquals(DviPsColors.OLIVEGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_OLIVEGREEN).get());
		assertEquals(DviPsColors.ORANGERED, DviPsColors.INSTANCE.getColour(DviPsColors.N_ORANGERED).get());
		assertEquals(DviPsColors.ORCHID, DviPsColors.INSTANCE.getColour(DviPsColors.N_ORCHID).get());
		assertEquals(DviPsColors.PEACH, DviPsColors.INSTANCE.getColour(DviPsColors.N_PEACH).get());
		assertEquals(DviPsColors.PERIWINKLE, DviPsColors.INSTANCE.getColour(DviPsColors.N_PERIWINKLE).get());
		assertEquals(DviPsColors.PINEGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_PINEGREEN).get());
		assertEquals(DviPsColors.PINK, DviPsColors.INSTANCE.getColour(DviPsColors.N_PINK).get());
		assertEquals(DviPsColors.PLUM, DviPsColors.INSTANCE.getColour(DviPsColors.N_PLUM).get());
		assertEquals(DviPsColors.PROCESSBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_PROCESSBLUE).get());
		assertEquals(DviPsColors.RAWSIENNA, DviPsColors.INSTANCE.getColour(DviPsColors.N_RAWSIENNA).get());
		assertEquals(DviPsColors.RED_ORANGE, DviPsColors.INSTANCE.getColour(DviPsColors.N_RED_ORANGE).get());
		assertEquals(DviPsColors.REDVIOLET, DviPsColors.INSTANCE.getColour(DviPsColors.N_REDVIOLET).get());
		assertEquals(DviPsColors.RHODAMINE, DviPsColors.INSTANCE.getColour(DviPsColors.N_RHODAMINE).get());
		assertEquals(DviPsColors.ROYALBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_ROYALBLUE).get());
		assertEquals(DviPsColors.ROYALPURPLE, DviPsColors.INSTANCE.getColour(DviPsColors.N_ROYALPURPLE).get());
		assertEquals(DviPsColors.RUBINERED, DviPsColors.INSTANCE.getColour(DviPsColors.N_RUBINERED).get());
		assertEquals(DviPsColors.SALMON, DviPsColors.INSTANCE.getColour(DviPsColors.N_SALMON).get());
		assertEquals(DviPsColors.SEAGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_SEAGREEN).get());
		assertEquals(DviPsColors.SEPIA, DviPsColors.INSTANCE.getColour(DviPsColors.N_SEPIA).get());
		assertEquals(DviPsColors.SKYBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_SKYBLUE).get());
		assertEquals(DviPsColors.SPRINGGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_SPRINGGREEN).get());
		assertEquals(DviPsColors.TAN, DviPsColors.INSTANCE.getColour(DviPsColors.N_TAN).get());
		assertEquals(DviPsColors.TEALBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_TEALBLUE).get());
		assertEquals(DviPsColors.THISTLE, DviPsColors.INSTANCE.getColour(DviPsColors.N_THISTLE).get());
		assertEquals(DviPsColors.TURQUOISE, DviPsColors.INSTANCE.getColour(DviPsColors.N_TURQUOISE).get());
		assertEquals(DviPsColors.VIOLETRED, DviPsColors.INSTANCE.getColour(DviPsColors.N_VIOLETRED).get());
		assertEquals(DviPsColors.WILDSTRAWBERRY, DviPsColors.INSTANCE.getColour(DviPsColors.N_WILDSTRAWBERRY).get());
		assertEquals(DviPsColors.YELLOW_ORANGE, DviPsColors.INSTANCE.getColour(DviPsColors.N_YELLOW_ORANGE).get());
		assertEquals(DviPsColors.YELLOWGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_YELLOWGREEN).get());
	}

	@SuppressWarnings("all")
	@Test
	public void testAddUserColor() {
		Color c2 = ShapeFactory.INST.createColor(18d / 255d, 29d / 255d, 78d / 255d, 1d);
		Optional<String> nameColour = DviPsColors.INSTANCE.addUserColour(c2);
		assertEquals(nameColour.get(), DviPsColors.INSTANCE.getColourName(c2).get());
	}

	@SuppressWarnings("all")
	@Test
	public void testGetUserColorsCode() {
		Color c = ShapeFactory.INST.createColor(230d / 255d, 65d / 255d, 78d / 255d, 1d);
		Optional<String> nameColour = DviPsColors.INSTANCE.addUserColour(c);
		assertNotNull(DviPsColors.INSTANCE.getUsercolourCode(null));
		assertNotNull(DviPsColors.INSTANCE.getUsercolourCode(nameColour.get()));
	}

	@Theory
	public void testCMYK2RGBKOc(@DoubleData(vals = {-1d, 2d}) final double value) {
		exceptionGrabber.expect(IllegalArgumentException.class);
		DviPsColors.INSTANCE.convertcmyk2rgb(value, 0.5, 0.5, 0.5);
	}

	@Theory
	public void testCMYK2RGBKOm(@DoubleData(vals = {-1d, 2d}) final double value) {
		exceptionGrabber.expect(IllegalArgumentException.class);
		DviPsColors.INSTANCE.convertcmyk2rgb(0.5, value, 0.5, 0.5);
	}

	@Theory
	public void testCMYK2RGBKOy(@DoubleData(vals = {-1d, 2d}) final double value) {
		exceptionGrabber.expect(IllegalArgumentException.class);
		DviPsColors.INSTANCE.convertcmyk2rgb(0.5, 0.5, value, 0.5);
	}

	@Theory
	public void testCMYK2RGBKOk(@DoubleData(vals = {-1d, 2d}) final double value) {
		exceptionGrabber.expect(IllegalArgumentException.class);
		DviPsColors.INSTANCE.convertcmyk2rgb(0.5, 0.5, 0.5, value);
	}

	@Test
	public void testCMYK2RGBOK() {
		assertEquals(ShapeFactory.INST.createColor(51d / 255d, 92d / 255d, 71d / 255d, 1d), DviPsColors.INSTANCE.convertcmyk2rgb(0.5, 0.1, 0.3, 0.6));
	}

	@Test
	public void testGray2RBG() {
		assertEquals(ShapeFactory.INST.createColor(1d, 1d, 1d, 1d), DviPsColors.INSTANCE.convertgray2rgb(1));
	}

	@Theory
	public void testGray2RBGKO(@DoubleData(vals = {-10d, 300d}) final double value) {
		exceptionGrabber.expect(IllegalArgumentException.class);
		DviPsColors.INSTANCE.convertgray2rgb(value);
	}
}
