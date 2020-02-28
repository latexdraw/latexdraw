package net.sf.latexdraw.view.latex;

import java.util.Optional;
import net.sf.latexdraw.LatexdrawExtension;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Color;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(LatexdrawExtension.class)
public class TestDviPsColors {
	@Test
	void testConvertHTML2rgbsuccess() {
		assertEquals(ShapeFactory.INST.createColor(1d, 0, 100d / 255d, 1d), DviPsColors.INSTANCE.convertHTML2rgb("#FF0064"));
	}

	@Test
	void testConvertHTML2rgbfailtooshort() {
		assertThrows(IllegalArgumentException.class, () -> DviPsColors.INSTANCE.convertHTML2rgb("DU87"));
	}

	@Test
	void testConvertHTML2rgbfailtoolong() {
		assertThrows(IllegalArgumentException.class, () -> DviPsColors.INSTANCE.convertHTML2rgb("#FF00643"));
	}

	@Test
	void testConvertRGB2rgbsuccess() {
		assertEquals(ShapeFactory.INST.createColor(0d, 0.5, 1d, 1d), DviPsColors.INSTANCE.convertRGB2rgb(0, 255d / 2d, 255d));
	}

	@Test
	void testConvertRGB2rgbfail() {
		assertThrows(IllegalArgumentException.class, () -> DviPsColors.INSTANCE.convertRGB2rgb(-1d, -255d / 2d, -255d));
	}

	@Test
	void testConvertcmyk2rgbsuccess() {
		assertEquals(ShapeFactory.INST.createColor(1d, 0d, 99.96 / 255d, 1d), DviPsColors.INSTANCE.convertcmyk2rgb(0d, 1d, 0.608, 0d));
	}

	@Test
	void testConvertcmyk2rgbfail() {
		assertThrows(IllegalArgumentException.class, () -> DviPsColors.INSTANCE.convertcmyk2rgb(-1d, -2d, 1d, 3d));
	}

	@Test
	void testGetColorNameNULL() {
		assertEquals(Optional.empty(), DviPsColors.INSTANCE.getColourName(null));
	}

	@Test
	void testGetColorNameOK() {
		final Color c2 = ShapeFactory.INST.createColor(218d / 255d, 29d / 255d, 78d / 255d, 1d);
		final Optional<String> nameColour = DviPsColors.INSTANCE.addUserColour(c2);
		assertEquals(nameColour.orElseThrow(), DviPsColors.INSTANCE.getColourName(c2).orElseThrow());
	}

	@Test
	void testGetColorNamePredefinedColours() {
		assertEquals(DviPsColors.N_APRICOT, DviPsColors.INSTANCE.getColourName(DviPsColors.APRICOT).orElseThrow());
		assertEquals(DviPsColors.N_AQUAMARINE, DviPsColors.INSTANCE.getColourName(DviPsColors.AQUAMARINE).orElseThrow());
		assertEquals(DviPsColors.N_BITTERSWEET, DviPsColors.INSTANCE.getColourName(DviPsColors.BITTERSWEET).orElseThrow());
		assertEquals(DviPsColors.N_BLUEGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.BLUEGREEN).orElseThrow());
		assertEquals(DviPsColors.N_BLUEVIOLET, DviPsColors.INSTANCE.getColourName(DviPsColors.BLUEVIOLET).orElseThrow());
		assertEquals(DviPsColors.N_BRICKRED, DviPsColors.INSTANCE.getColourName(DviPsColors.BRICKRED).orElseThrow());
		assertEquals(DviPsColors.N_BURNT_ORANGE, DviPsColors.INSTANCE.getColourName(DviPsColors.BURNT_ORANGE).orElseThrow());
		assertEquals(DviPsColors.N_CADETBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.CADETBLUE).orElseThrow());
		assertEquals(DviPsColors.N_CARNATIONPINK, DviPsColors.INSTANCE.getColourName(DviPsColors.CARNATIONPINK).orElseThrow());
		assertEquals(DviPsColors.N_CERULEAN, DviPsColors.INSTANCE.getColourName(DviPsColors.CERULEAN).orElseThrow());
		assertEquals(DviPsColors.N_CORNFLOWERBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.CORNFLOWERBLUE).orElseThrow());
		assertEquals(DviPsColors.N_DANDELION, DviPsColors.INSTANCE.getColourName(DviPsColors.DANDELION).orElseThrow());
		assertEquals(DviPsColors.N_DARK_GRAY, DviPsColors.INSTANCE.getColourName(DviPsColors.DARKGRAY).orElseThrow());
		assertEquals(DviPsColors.N_DARKORCHID, DviPsColors.INSTANCE.getColourName(DviPsColors.DARKORCHID).orElseThrow());
		assertEquals(DviPsColors.N_EMERALD, DviPsColors.INSTANCE.getColourName(DviPsColors.EMERALD).orElseThrow());
		assertEquals(DviPsColors.N_FORESTGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.FORESTGREEN).orElseThrow());
		assertEquals(DviPsColors.N_FUSHIA, DviPsColors.INSTANCE.getColourName(DviPsColors.FUSHIA).orElseThrow());
		assertEquals(DviPsColors.N_GOLDEN_ROD, DviPsColors.INSTANCE.getColourName(DviPsColors.GOLDEN_ROD).orElseThrow());
		assertEquals(DviPsColors.N_GREEN_YELLOW, DviPsColors.INSTANCE.getColourName(DviPsColors.GREEN_YELLOW).orElseThrow());
		assertEquals(DviPsColors.N_JUNGLEGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.JUNGLEGREEN).orElseThrow());
		assertEquals(DviPsColors.N_LAVENDER, DviPsColors.INSTANCE.getColourName(DviPsColors.LAVENDER).orElseThrow());
		assertEquals(DviPsColors.N_LIGHT_GRAY, DviPsColors.INSTANCE.getColourName(DviPsColors.LIGHTGRAY).orElseThrow());
		assertEquals(DviPsColors.N_LIMEGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.LIMEGREEN).orElseThrow());
		assertEquals(DviPsColors.N_MAHOGANY, DviPsColors.INSTANCE.getColourName(DviPsColors.MAHOGANY).orElseThrow());
		assertEquals(DviPsColors.N_MAROON, DviPsColors.INSTANCE.getColourName(DviPsColors.MAROON).orElseThrow());
		assertEquals(DviPsColors.N_MELON, DviPsColors.INSTANCE.getColourName(DviPsColors.MELON).orElseThrow());
		assertEquals(DviPsColors.N_MIDNIGHTBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.MIDNIGHTBLUE).orElseThrow());
		assertEquals(DviPsColors.N_MULBERRY, DviPsColors.INSTANCE.getColourName(DviPsColors.MULBERRY).orElseThrow());
		assertEquals(DviPsColors.N_NAVYBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.NAVYBLUE).orElseThrow());
		assertEquals(DviPsColors.N_OLIVEGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.OLIVEGREEN).orElseThrow());
		assertEquals(DviPsColors.N_ORANGERED, DviPsColors.INSTANCE.getColourName(DviPsColors.ORANGERED).orElseThrow());
		assertEquals(DviPsColors.N_ORCHID, DviPsColors.INSTANCE.getColourName(DviPsColors.ORCHID).orElseThrow());
		assertEquals(DviPsColors.N_PEACH, DviPsColors.INSTANCE.getColourName(DviPsColors.PEACH).orElseThrow());
		assertEquals(DviPsColors.N_PERIWINKLE, DviPsColors.INSTANCE.getColourName(DviPsColors.PERIWINKLE).orElseThrow());
		assertEquals(DviPsColors.N_PINEGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.PINEGREEN).orElseThrow());
		assertEquals(DviPsColors.N_PINK, DviPsColors.INSTANCE.getColourName(DviPsColors.PINK).orElseThrow());
		assertEquals(DviPsColors.N_PLUM, DviPsColors.INSTANCE.getColourName(DviPsColors.PLUM).orElseThrow());
		assertEquals(DviPsColors.N_PROCESSBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.PROCESSBLUE).orElseThrow());
		assertEquals(DviPsColors.N_RAWSIENNA, DviPsColors.INSTANCE.getColourName(DviPsColors.RAWSIENNA).orElseThrow());
		assertEquals(DviPsColors.N_RED_ORANGE, DviPsColors.INSTANCE.getColourName(DviPsColors.RED_ORANGE).orElseThrow());
		assertEquals(DviPsColors.N_REDVIOLET, DviPsColors.INSTANCE.getColourName(DviPsColors.REDVIOLET).orElseThrow());
		assertEquals(DviPsColors.N_RHODAMINE, DviPsColors.INSTANCE.getColourName(DviPsColors.RHODAMINE).orElseThrow());
		assertEquals(DviPsColors.N_ROYALBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.ROYALBLUE).orElseThrow());
		assertEquals(DviPsColors.N_ROYALPURPLE, DviPsColors.INSTANCE.getColourName(DviPsColors.ROYALPURPLE).orElseThrow());
		assertEquals(DviPsColors.N_RUBINERED, DviPsColors.INSTANCE.getColourName(DviPsColors.RUBINERED).orElseThrow());
		assertEquals(DviPsColors.N_SALMON, DviPsColors.INSTANCE.getColourName(DviPsColors.SALMON).orElseThrow());
		assertEquals(DviPsColors.N_SEAGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.SEAGREEN).orElseThrow());
		assertEquals(DviPsColors.N_SEPIA, DviPsColors.INSTANCE.getColourName(DviPsColors.SEPIA).orElseThrow());
		assertEquals(DviPsColors.N_SKYBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.SKYBLUE).orElseThrow());
		assertEquals(DviPsColors.N_SPRINGGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.SPRINGGREEN).orElseThrow());
		assertEquals(DviPsColors.N_TAN, DviPsColors.INSTANCE.getColourName(DviPsColors.TAN).orElseThrow());
		assertEquals(DviPsColors.N_TEALBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.TEALBLUE).orElseThrow());
		assertEquals(DviPsColors.N_THISTLE, DviPsColors.INSTANCE.getColourName(DviPsColors.THISTLE).orElseThrow());
		assertEquals(DviPsColors.N_TURQUOISE, DviPsColors.INSTANCE.getColourName(DviPsColors.TURQUOISE).orElseThrow());
		assertEquals(DviPsColors.N_VIOLETRED, DviPsColors.INSTANCE.getColourName(DviPsColors.VIOLETRED).orElseThrow());
		assertEquals(DviPsColors.N_WILDSTRAWBERRY, DviPsColors.INSTANCE.getColourName(DviPsColors.WILDSTRAWBERRY).orElseThrow());
		assertEquals(DviPsColors.N_YELLOW_ORANGE, DviPsColors.INSTANCE.getColourName(DviPsColors.YELLOW_ORANGE).orElseThrow());
		assertEquals(DviPsColors.N_YELLOWGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.YELLOWGREEN).orElseThrow());
	}

	@Test
	void testGetColorKO() {
		assertEquals(Optional.empty(), DviPsColors.INSTANCE.getColour("testColour"));
	}

	@Test
	void testGetColor() {
		assertEquals(DviPsColors.APRICOT, DviPsColors.INSTANCE.getColour(DviPsColors.N_APRICOT).orElseThrow());
		assertEquals(DviPsColors.AQUAMARINE, DviPsColors.INSTANCE.getColour(DviPsColors.N_AQUAMARINE).orElseThrow());
		assertEquals(DviPsColors.BITTERSWEET, DviPsColors.INSTANCE.getColour(DviPsColors.N_BITTERSWEET).orElseThrow());
		assertEquals(DviPsColors.BLUEGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_BLUEGREEN).orElseThrow());
		assertEquals(DviPsColors.BLUEVIOLET, DviPsColors.INSTANCE.getColour(DviPsColors.N_BLUEVIOLET).orElseThrow());
		assertEquals(DviPsColors.BRICKRED, DviPsColors.INSTANCE.getColour(DviPsColors.N_BRICKRED).orElseThrow());
		assertEquals(DviPsColors.BURNT_ORANGE, DviPsColors.INSTANCE.getColour(DviPsColors.N_BURNT_ORANGE).orElseThrow());
		assertEquals(DviPsColors.CADETBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_CADETBLUE).orElseThrow());
		assertEquals(DviPsColors.CARNATIONPINK, DviPsColors.INSTANCE.getColour(DviPsColors.N_CARNATIONPINK).orElseThrow());
		assertEquals(DviPsColors.CERULEAN, DviPsColors.INSTANCE.getColour(DviPsColors.N_CERULEAN).orElseThrow());
		assertEquals(DviPsColors.CORNFLOWERBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_CORNFLOWERBLUE).orElseThrow());
		assertEquals(DviPsColors.DANDELION, DviPsColors.INSTANCE.getColour(DviPsColors.N_DANDELION).orElseThrow());
		assertEquals(DviPsColors.DARKGRAY, DviPsColors.INSTANCE.getColour(DviPsColors.N_DARK_GRAY).orElseThrow());
		assertEquals(DviPsColors.DARKORCHID, DviPsColors.INSTANCE.getColour(DviPsColors.N_DARKORCHID).orElseThrow());
		assertEquals(DviPsColors.EMERALD, DviPsColors.INSTANCE.getColour(DviPsColors.N_EMERALD).orElseThrow());
		assertEquals(DviPsColors.FORESTGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_FORESTGREEN).orElseThrow());
		assertEquals(DviPsColors.FUSHIA, DviPsColors.INSTANCE.getColour(DviPsColors.N_FUSHIA).orElseThrow());
		assertEquals(DviPsColors.GOLDEN_ROD, DviPsColors.INSTANCE.getColour(DviPsColors.N_GOLDEN_ROD).orElseThrow());
		assertEquals(DviPsColors.GREEN_YELLOW, DviPsColors.INSTANCE.getColour(DviPsColors.N_GREEN_YELLOW).orElseThrow());
		assertEquals(DviPsColors.JUNGLEGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_JUNGLEGREEN).orElseThrow());
		assertEquals(DviPsColors.LAVENDER, DviPsColors.INSTANCE.getColour(DviPsColors.N_LAVENDER).orElseThrow());
		assertEquals(DviPsColors.LIGHTGRAY, DviPsColors.INSTANCE.getColour(DviPsColors.N_LIGHT_GRAY).orElseThrow());
		assertEquals(DviPsColors.LIMEGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_LIMEGREEN).orElseThrow());
		assertEquals(DviPsColors.MAHOGANY, DviPsColors.INSTANCE.getColour(DviPsColors.N_MAHOGANY).orElseThrow());
		assertEquals(DviPsColors.MAROON, DviPsColors.INSTANCE.getColour(DviPsColors.N_MAROON).orElseThrow());
		assertEquals(DviPsColors.MELON, DviPsColors.INSTANCE.getColour(DviPsColors.N_MELON).orElseThrow());
		assertEquals(DviPsColors.MIDNIGHTBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_MIDNIGHTBLUE).orElseThrow());
		assertEquals(DviPsColors.MULBERRY, DviPsColors.INSTANCE.getColour(DviPsColors.N_MULBERRY).orElseThrow());
		assertEquals(DviPsColors.NAVYBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_NAVYBLUE).orElseThrow());
		assertEquals(DviPsColors.OLIVEGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_OLIVEGREEN).orElseThrow());
		assertEquals(DviPsColors.ORANGERED, DviPsColors.INSTANCE.getColour(DviPsColors.N_ORANGERED).orElseThrow());
		assertEquals(DviPsColors.ORCHID, DviPsColors.INSTANCE.getColour(DviPsColors.N_ORCHID).orElseThrow());
		assertEquals(DviPsColors.PEACH, DviPsColors.INSTANCE.getColour(DviPsColors.N_PEACH).orElseThrow());
		assertEquals(DviPsColors.PERIWINKLE, DviPsColors.INSTANCE.getColour(DviPsColors.N_PERIWINKLE).orElseThrow());
		assertEquals(DviPsColors.PINEGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_PINEGREEN).orElseThrow());
		assertEquals(DviPsColors.PINK, DviPsColors.INSTANCE.getColour(DviPsColors.N_PINK).orElseThrow());
		assertEquals(DviPsColors.PLUM, DviPsColors.INSTANCE.getColour(DviPsColors.N_PLUM).orElseThrow());
		assertEquals(DviPsColors.PROCESSBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_PROCESSBLUE).orElseThrow());
		assertEquals(DviPsColors.RAWSIENNA, DviPsColors.INSTANCE.getColour(DviPsColors.N_RAWSIENNA).orElseThrow());
		assertEquals(DviPsColors.RED_ORANGE, DviPsColors.INSTANCE.getColour(DviPsColors.N_RED_ORANGE).orElseThrow());
		assertEquals(DviPsColors.REDVIOLET, DviPsColors.INSTANCE.getColour(DviPsColors.N_REDVIOLET).orElseThrow());
		assertEquals(DviPsColors.RHODAMINE, DviPsColors.INSTANCE.getColour(DviPsColors.N_RHODAMINE).orElseThrow());
		assertEquals(DviPsColors.ROYALBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_ROYALBLUE).orElseThrow());
		assertEquals(DviPsColors.ROYALPURPLE, DviPsColors.INSTANCE.getColour(DviPsColors.N_ROYALPURPLE).orElseThrow());
		assertEquals(DviPsColors.RUBINERED, DviPsColors.INSTANCE.getColour(DviPsColors.N_RUBINERED).orElseThrow());
		assertEquals(DviPsColors.SALMON, DviPsColors.INSTANCE.getColour(DviPsColors.N_SALMON).orElseThrow());
		assertEquals(DviPsColors.SEAGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_SEAGREEN).orElseThrow());
		assertEquals(DviPsColors.SEPIA, DviPsColors.INSTANCE.getColour(DviPsColors.N_SEPIA).orElseThrow());
		assertEquals(DviPsColors.SKYBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_SKYBLUE).orElseThrow());
		assertEquals(DviPsColors.SPRINGGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_SPRINGGREEN).orElseThrow());
		assertEquals(DviPsColors.TAN, DviPsColors.INSTANCE.getColour(DviPsColors.N_TAN).orElseThrow());
		assertEquals(DviPsColors.TEALBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_TEALBLUE).orElseThrow());
		assertEquals(DviPsColors.THISTLE, DviPsColors.INSTANCE.getColour(DviPsColors.N_THISTLE).orElseThrow());
		assertEquals(DviPsColors.TURQUOISE, DviPsColors.INSTANCE.getColour(DviPsColors.N_TURQUOISE).orElseThrow());
		assertEquals(DviPsColors.VIOLETRED, DviPsColors.INSTANCE.getColour(DviPsColors.N_VIOLETRED).orElseThrow());
		assertEquals(DviPsColors.WILDSTRAWBERRY, DviPsColors.INSTANCE.getColour(DviPsColors.N_WILDSTRAWBERRY).orElseThrow());
		assertEquals(DviPsColors.YELLOW_ORANGE, DviPsColors.INSTANCE.getColour(DviPsColors.N_YELLOW_ORANGE).orElseThrow());
		assertEquals(DviPsColors.YELLOWGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_YELLOWGREEN).orElseThrow());
	}

	@Test
	void testAddUserColor() {
		final Color c2 = ShapeFactory.INST.createColor(18d / 255d, 29d / 255d, 78d / 255d, 1d);
		final Optional<String> nameColour = DviPsColors.INSTANCE.addUserColour(c2);
		assertEquals(nameColour.orElseThrow(), DviPsColors.INSTANCE.getColourName(c2).orElseThrow());
	}

	@Test
	void testGetUserColorsCodeKO() {
		final Color c = ShapeFactory.INST.createColor(230d / 255d, 65d / 255d, 78d / 255d, 1d);
		assertNotNull(DviPsColors.INSTANCE.getUsercolourCode(null));
		assertNotNull(DviPsColors.INSTANCE.getUsercolourCode(DviPsColors.INSTANCE.addUserColour(c).orElseThrow()));
	}

	@Test
	void testGetUserColorsCodeOK() {
		final Color c = ShapeFactory.INST.createColor(0.5, 0.2, 0.9, 0.8);
		assertEquals("\\definecolor{colour0}{rgb}{0.5,0.2,0.9}", DviPsColors.INSTANCE.getUsercolourCode(DviPsColors.INSTANCE.addUserColour(c).orElseThrow()));
	}

	@Test
	void testClearUserColours() {
		final String name = DviPsColors.INSTANCE.addUserColour(ShapeFactory.INST.createColor(0.5, 0.2, 0.9, 0.8)).orElseThrow();
		DviPsColors.INSTANCE.clearUserColours();
		assertTrue(DviPsColors.INSTANCE.getColour(name).isEmpty());
		assertEquals(name, DviPsColors.INSTANCE.addUserColour(ShapeFactory.INST.createColor(0.5, 0.2, 0.3, 0.4)).orElseThrow());
	}

	@ParameterizedTest
	@ValueSource(doubles = {-1d, 2d})
	void testCMYK2RGBKOc(final double value) {
		assertThrows(IllegalArgumentException.class, () -> DviPsColors.INSTANCE.convertcmyk2rgb(value, 0.5, 0.5, 0.5));
	}

	@ParameterizedTest
	@ValueSource(doubles = {-1d, 2d})
	void testCMYK2RGBKOm(final double value) {
		assertThrows(IllegalArgumentException.class, () -> DviPsColors.INSTANCE.convertcmyk2rgb(0.5, value, 0.5, 0.5));
	}

	@ParameterizedTest
	@ValueSource(doubles = {-1d, 2d})
	void testCMYK2RGBKOy(final double value) {
		assertThrows(IllegalArgumentException.class, () -> DviPsColors.INSTANCE.convertcmyk2rgb(0.5, 0.5, value, 0.5));
	}

	@ParameterizedTest
	@ValueSource(doubles = {-1d, 2d})
	void testCMYK2RGBKOk(final double value) {
		assertThrows(IllegalArgumentException.class, () -> DviPsColors.INSTANCE.convertcmyk2rgb(0.5, 0.5, 0.5, value));
	}

	@Test
	void testCMYK2RGBOK() {
		assertEquals(ShapeFactory.INST.createColor(51d / 255d, 91.8 / 255d, 71.4 / 255d, 1d),
			DviPsColors.INSTANCE.convertcmyk2rgb(0.5, 0.1, 0.3, 0.6));
	}

	@Test
	void testGray2RBG() {
		assertEquals(ShapeFactory.INST.createColor(1d, 1d, 1d, 1d), DviPsColors.INSTANCE.convertgray2rgb(1));
	}

	@ParameterizedTest
	@ValueSource(doubles = {-10d, 300d})
	void testGray2RBGKO(final double value) {
		assertThrows(IllegalArgumentException.class, () -> DviPsColors.INSTANCE.convertgray2rgb(value));
	}
}
