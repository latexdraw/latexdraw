package test.parser;

import java.awt.Color;

import junit.framework.TestCase;

import net.sf.latexdraw.glib.views.latex.DviPsColors;

import org.junit.Test;

public class TestDviPsColors extends TestCase {
	@Test
	public void testGetColorName() {
		Color c2 = new Color(218, 29, 78);

		assertNull(DviPsColors.INSTANCE.getColourName(null));
		String nameColour = DviPsColors.INSTANCE.addUserColour(c2);
		assertEquals(nameColour, DviPsColors.INSTANCE.getColourName(c2));

		assertEquals(DviPsColors.N_APRICOT, DviPsColors.INSTANCE.getColourName(DviPsColors.APRICOT));
		assertEquals(DviPsColors.N_AQUAMARINE, DviPsColors.INSTANCE.getColourName(DviPsColors.AQUAMARINE));
		assertEquals(DviPsColors.N_BITTERSWEET, DviPsColors.INSTANCE.getColourName(DviPsColors.BITTERSWEET));
		assertEquals(DviPsColors.N_BLUEGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.BLUEGREEN));
		assertEquals(DviPsColors.N_BLUEVIOLET, DviPsColors.INSTANCE.getColourName(DviPsColors.BLUEVIOLET));
		assertEquals(DviPsColors.N_BRICKRED, DviPsColors.INSTANCE.getColourName(DviPsColors.BRICKRED));
		assertEquals(DviPsColors.N_BURNT_ORANGE, DviPsColors.INSTANCE.getColourName(DviPsColors.BURNT_ORANGE));
		assertEquals(DviPsColors.N_CADETBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.CADETBLUE));
		assertEquals(DviPsColors.N_CARNATIONPINK, DviPsColors.INSTANCE.getColourName(DviPsColors.CARNATIONPINK));
		assertEquals(DviPsColors.N_CERULEAN, DviPsColors.INSTANCE.getColourName(DviPsColors.CERULEAN));
		assertEquals(DviPsColors.N_CORNFLOWERBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.CORNFLOWERBLUE));
		assertEquals(DviPsColors.N_DANDELION, DviPsColors.INSTANCE.getColourName(DviPsColors.DANDELION));
		assertEquals(DviPsColors.N_DARK_GRAY, DviPsColors.INSTANCE.getColourName(Color.DARK_GRAY));
		assertEquals(DviPsColors.N_DARKORCHID, DviPsColors.INSTANCE.getColourName(DviPsColors.DARKORCHID));
		assertEquals(DviPsColors.N_EMERALD, DviPsColors.INSTANCE.getColourName(DviPsColors.EMERALD));
		assertEquals(DviPsColors.N_FORESTGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.FORESTGREEN));
		assertEquals(DviPsColors.N_FUSHIA, DviPsColors.INSTANCE.getColourName(DviPsColors.FUSHIA));
		assertEquals(DviPsColors.N_GOLDEN_ROD, DviPsColors.INSTANCE.getColourName(DviPsColors.GOLDEN_ROD));
		assertEquals(DviPsColors.N_GREEN_YELLOW, DviPsColors.INSTANCE.getColourName(DviPsColors.GREEN_YELLOW));
		assertEquals(DviPsColors.N_JUNGLEGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.JUNGLEGREEN));
		assertEquals(DviPsColors.N_LAVENDER, DviPsColors.INSTANCE.getColourName(DviPsColors.LAVENDER));
		assertEquals(DviPsColors.N_LIGHT_GRAY, DviPsColors.INSTANCE.getColourName(Color.LIGHT_GRAY));
		assertEquals(DviPsColors.N_LIMEGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.LIMEGREEN));
		assertEquals(DviPsColors.N_MAHOGANY, DviPsColors.INSTANCE.getColourName(DviPsColors.MAHOGANY));
		assertEquals(DviPsColors.N_MAROON, DviPsColors.INSTANCE.getColourName(DviPsColors.MAROON));
		assertEquals(DviPsColors.N_MELON, DviPsColors.INSTANCE.getColourName(DviPsColors.MELON));
		assertEquals(DviPsColors.N_MIDNIGHTBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.MIDNIGHTBLUE));
		assertEquals(DviPsColors.N_MULBERRY, DviPsColors.INSTANCE.getColourName(DviPsColors.MULBERRY));
		assertEquals(DviPsColors.N_NAVYBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.NAVYBLUE));
		assertEquals(DviPsColors.N_OLIVEGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.OLIVEGREEN));
		assertEquals(DviPsColors.N_ORANGERED, DviPsColors.INSTANCE.getColourName(DviPsColors.ORANGERED));
		assertEquals(DviPsColors.N_ORCHID, DviPsColors.INSTANCE.getColourName(DviPsColors.ORCHID));
		assertEquals(DviPsColors.N_PEACH, DviPsColors.INSTANCE.getColourName(DviPsColors.PEACH));
		assertEquals(DviPsColors.N_PERIWINKLE, DviPsColors.INSTANCE.getColourName(DviPsColors.PERIWINKLE));
		assertEquals(DviPsColors.N_PINEGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.PINEGREEN));
		assertEquals(DviPsColors.N_PINK, DviPsColors.INSTANCE.getColourName(DviPsColors.PINK));
		assertEquals(DviPsColors.N_PLUM, DviPsColors.INSTANCE.getColourName(DviPsColors.PLUM));
		assertEquals(DviPsColors.N_PROCESSBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.PROCESSBLUE));
		assertEquals(DviPsColors.N_RAWSIENNA, DviPsColors.INSTANCE.getColourName(DviPsColors.RAWSIENNA));
		assertEquals(DviPsColors.N_RED_ORANGE, DviPsColors.INSTANCE.getColourName(DviPsColors.RED_ORANGE));
		assertEquals(DviPsColors.N_REDVIOLET, DviPsColors.INSTANCE.getColourName(DviPsColors.REDVIOLET));
		assertEquals(DviPsColors.N_RHODAMINE, DviPsColors.INSTANCE.getColourName(DviPsColors.RHODAMINE));
		assertEquals(DviPsColors.N_ROYALBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.ROYALBLUE));
		assertEquals(DviPsColors.N_ROYALPURPLE, DviPsColors.INSTANCE.getColourName(DviPsColors.ROYALPURPLE));
		assertEquals(DviPsColors.N_RUBINERED, DviPsColors.INSTANCE.getColourName(DviPsColors.RUBINERED));
		assertEquals(DviPsColors.N_SALMON, DviPsColors.INSTANCE.getColourName(DviPsColors.SALMON));
		assertEquals(DviPsColors.N_SEAGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.SEAGREEN));
		assertEquals(DviPsColors.N_SEPIA, DviPsColors.INSTANCE.getColourName(DviPsColors.SEPIA));
		assertEquals(DviPsColors.N_SKYBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.SKYBLUE));
		assertEquals(DviPsColors.N_SPRINGGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.SPRINGGREEN));
		assertEquals(DviPsColors.N_TAN, DviPsColors.INSTANCE.getColourName(DviPsColors.TAN));
		assertEquals(DviPsColors.N_TEALBLUE, DviPsColors.INSTANCE.getColourName(DviPsColors.TEALBLUE));
		assertEquals(DviPsColors.N_THISTLE, DviPsColors.INSTANCE.getColourName(DviPsColors.THISTLE));
		assertEquals(DviPsColors.N_TURQUOISE, DviPsColors.INSTANCE.getColourName(DviPsColors.TURQUOISE));
		assertEquals(DviPsColors.N_VIOLETRED, DviPsColors.INSTANCE.getColourName(DviPsColors.VIOLETRED));
		assertEquals(DviPsColors.N_WILDSTRAWBERRY, DviPsColors.INSTANCE.getColourName(DviPsColors.WILDSTRAWBERRY));
		assertEquals(DviPsColors.N_YELLOW_ORANGE, DviPsColors.INSTANCE.getColourName(DviPsColors.YELLOW_ORANGE));
		assertEquals(DviPsColors.N_YELLOWGREEN, DviPsColors.INSTANCE.getColourName(DviPsColors.YELLOWGREEN));
	}





	@Test
	public void testGetColor() {
		assertEquals(null, DviPsColors.INSTANCE.getColour("testColour"));
		assertEquals(DviPsColors.APRICOT, DviPsColors.INSTANCE.getColour(DviPsColors.N_APRICOT));
		assertEquals(DviPsColors.AQUAMARINE, DviPsColors.INSTANCE.getColour(DviPsColors.N_AQUAMARINE));
		assertEquals(DviPsColors.BITTERSWEET, DviPsColors.INSTANCE.getColour(DviPsColors.N_BITTERSWEET));
		assertEquals(DviPsColors.BLUEGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_BLUEGREEN));
		assertEquals(DviPsColors.BLUEVIOLET, DviPsColors.INSTANCE.getColour(DviPsColors.N_BLUEVIOLET));
		assertEquals(DviPsColors.BRICKRED, DviPsColors.INSTANCE.getColour(DviPsColors.N_BRICKRED));
		assertEquals(DviPsColors.BURNT_ORANGE, DviPsColors.INSTANCE.getColour(DviPsColors.N_BURNT_ORANGE));
		assertEquals(DviPsColors.CADETBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_CADETBLUE));
		assertEquals(DviPsColors.CARNATIONPINK, DviPsColors.INSTANCE.getColour(DviPsColors.N_CARNATIONPINK));
		assertEquals(DviPsColors.CERULEAN, DviPsColors.INSTANCE.getColour(DviPsColors.N_CERULEAN));
		assertEquals(DviPsColors.CORNFLOWERBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_CORNFLOWERBLUE));
		assertEquals(DviPsColors.DANDELION, DviPsColors.INSTANCE.getColour(DviPsColors.N_DANDELION));
		assertEquals(Color.DARK_GRAY, DviPsColors.INSTANCE.getColour(DviPsColors.N_DARK_GRAY));
		assertEquals(DviPsColors.DARKORCHID, DviPsColors.INSTANCE.getColour(DviPsColors.N_DARKORCHID));
		assertEquals(DviPsColors.EMERALD, DviPsColors.INSTANCE.getColour(DviPsColors.N_EMERALD));
		assertEquals(DviPsColors.FORESTGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_FORESTGREEN));
		assertEquals(DviPsColors.FUSHIA, DviPsColors.INSTANCE.getColour(DviPsColors.N_FUSHIA));
		assertEquals(DviPsColors.GOLDEN_ROD, DviPsColors.INSTANCE.getColour(DviPsColors.N_GOLDEN_ROD));
		assertEquals(DviPsColors.GREEN_YELLOW, DviPsColors.INSTANCE.getColour(DviPsColors.N_GREEN_YELLOW));
		assertEquals(DviPsColors.JUNGLEGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_JUNGLEGREEN));
		assertEquals(DviPsColors.LAVENDER, DviPsColors.INSTANCE.getColour(DviPsColors.N_LAVENDER));
		assertEquals(Color.LIGHT_GRAY, DviPsColors.INSTANCE.getColour(DviPsColors.N_LIGHT_GRAY));
		assertEquals(DviPsColors.LIMEGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_LIMEGREEN));
		assertEquals(DviPsColors.MAHOGANY, DviPsColors.INSTANCE.getColour(DviPsColors.N_MAHOGANY));
		assertEquals(DviPsColors.MAROON, DviPsColors.INSTANCE.getColour(DviPsColors.N_MAROON));
		assertEquals(DviPsColors.MELON, DviPsColors.INSTANCE.getColour(DviPsColors.N_MELON));
		assertEquals(DviPsColors.MIDNIGHTBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_MIDNIGHTBLUE));
		assertEquals(DviPsColors.MULBERRY, DviPsColors.INSTANCE.getColour(DviPsColors.N_MULBERRY));
		assertEquals(DviPsColors.NAVYBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_NAVYBLUE));
		assertEquals(DviPsColors.OLIVEGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_OLIVEGREEN));
		assertEquals(DviPsColors.ORANGERED, DviPsColors.INSTANCE.getColour(DviPsColors.N_ORANGERED));
		assertEquals(DviPsColors.ORCHID, DviPsColors.INSTANCE.getColour(DviPsColors.N_ORCHID));
		assertEquals(DviPsColors.PEACH, DviPsColors.INSTANCE.getColour(DviPsColors.N_PEACH));
		assertEquals(DviPsColors.PERIWINKLE, DviPsColors.INSTANCE.getColour(DviPsColors.N_PERIWINKLE));
		assertEquals(DviPsColors.PINEGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_PINEGREEN));
		assertEquals(DviPsColors.PINK, DviPsColors.INSTANCE.getColour(DviPsColors.N_PINK));
		assertEquals(DviPsColors.PLUM, DviPsColors.INSTANCE.getColour(DviPsColors.N_PLUM));
		assertEquals(DviPsColors.PROCESSBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_PROCESSBLUE));
		assertEquals(DviPsColors.RAWSIENNA, DviPsColors.INSTANCE.getColour(DviPsColors.N_RAWSIENNA));
		assertEquals(DviPsColors.RED_ORANGE, DviPsColors.INSTANCE.getColour(DviPsColors.N_RED_ORANGE));
		assertEquals(DviPsColors.REDVIOLET, DviPsColors.INSTANCE.getColour(DviPsColors.N_REDVIOLET));
		assertEquals(DviPsColors.RHODAMINE, DviPsColors.INSTANCE.getColour(DviPsColors.N_RHODAMINE));
		assertEquals(DviPsColors.ROYALBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_ROYALBLUE));
		assertEquals(DviPsColors.ROYALPURPLE, DviPsColors.INSTANCE.getColour(DviPsColors.N_ROYALPURPLE));
		assertEquals(DviPsColors.RUBINERED, DviPsColors.INSTANCE.getColour(DviPsColors.N_RUBINERED));
		assertEquals(DviPsColors.SALMON, DviPsColors.INSTANCE.getColour(DviPsColors.N_SALMON));
		assertEquals(DviPsColors.SEAGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_SEAGREEN));
		assertEquals(DviPsColors.SEPIA, DviPsColors.INSTANCE.getColour(DviPsColors.N_SEPIA));
		assertEquals(DviPsColors.SKYBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_SKYBLUE));
		assertEquals(DviPsColors.SPRINGGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_SPRINGGREEN));
		assertEquals(DviPsColors.TAN, DviPsColors.INSTANCE.getColour(DviPsColors.N_TAN));
		assertEquals(DviPsColors.TEALBLUE, DviPsColors.INSTANCE.getColour(DviPsColors.N_TEALBLUE));
		assertEquals(DviPsColors.THISTLE, DviPsColors.INSTANCE.getColour(DviPsColors.N_THISTLE));
		assertEquals(DviPsColors.TURQUOISE, DviPsColors.INSTANCE.getColour(DviPsColors.N_TURQUOISE));
		assertEquals(DviPsColors.VIOLETRED, DviPsColors.INSTANCE.getColour(DviPsColors.N_VIOLETRED));
		assertEquals(DviPsColors.WILDSTRAWBERRY, DviPsColors.INSTANCE.getColour(DviPsColors.N_WILDSTRAWBERRY));
		assertEquals(DviPsColors.YELLOW_ORANGE, DviPsColors.INSTANCE.getColour(DviPsColors.N_YELLOW_ORANGE));
		assertEquals(DviPsColors.YELLOWGREEN, DviPsColors.INSTANCE.getColour(DviPsColors.N_YELLOWGREEN));
	}


	@Test
	public void testAddUserColor() {
		Color c2 = new Color(18, 29, 78);

		String nameColour = DviPsColors.INSTANCE.addUserColour(c2);
		assertEquals(nameColour, DviPsColors.INSTANCE.getColourName(c2));
	}



	@Test
	public void testGetUserColorsCode() {
		Color c = new Color(230, 65, 78);

		String nameColour = DviPsColors.INSTANCE.addUserColour(c);
		assertNotNull(DviPsColors.INSTANCE.getUsercolourCode(null));
		assertNotNull(DviPsColors.INSTANCE.getUsercolourCode(nameColour));
	}



	@Test
	public void testCMYK2RGB() {
		try {
			DviPsColors.INSTANCE.cmyk2Rgb(-1, 0.5, 0.5, 0.5);
			fail();
		}catch(IllegalArgumentException e) { /* Good. */ }

		try {
			DviPsColors.INSTANCE.cmyk2Rgb(2, 0.5, 0.5, 0.5);
			fail();
		}catch(IllegalArgumentException e) { /* Good. */ }

		try {
			DviPsColors.INSTANCE.cmyk2Rgb(0.5, -1, 0.5, 0.5);
			fail();
		}catch(IllegalArgumentException e) { /* Good. */ }

		try {
			DviPsColors.INSTANCE.cmyk2Rgb(0.5, 2, 0.5, 0.5);
			fail();
		}catch(IllegalArgumentException e) { /* Good. */ }

		try {
			DviPsColors.INSTANCE.cmyk2Rgb(0.5, 0.5, -1, 0.5);
			fail();
		}catch(IllegalArgumentException e) { /* Good. */ }

		try {
			DviPsColors.INSTANCE.cmyk2Rgb(0.5, 0.5, 2, 0.5);
			fail();
		}catch(IllegalArgumentException e) { /* Good. */ }

		try {
			DviPsColors.INSTANCE.cmyk2Rgb(0.5, 0.5, 0.5, -1);
			fail();
		}catch(IllegalArgumentException e) { /* Good. */ }

		try {
			DviPsColors.INSTANCE.cmyk2Rgb(0.5, 0.5, 0.5, 2);
			fail();
		}catch(IllegalArgumentException e) { /* Good. */ }

		assertEquals(new Color(51, 92, 71), DviPsColors.INSTANCE.cmyk2Rgb(0.5, 0.1, 0.3, 0.6));
	}


	@Test
	public void testGray2RBG() {
		try {
			DviPsColors.INSTANCE.gray2RBG(-10);
			fail();
		}catch(IllegalArgumentException e) { /* Good. */ }

		try {
			DviPsColors.INSTANCE.gray2RBG(300);
			fail();
		}catch(IllegalArgumentException e) { /* Good. */ }

		assertEquals(new Color(255, 255, 255), DviPsColors.INSTANCE.gray2RBG(1));
	}
}
