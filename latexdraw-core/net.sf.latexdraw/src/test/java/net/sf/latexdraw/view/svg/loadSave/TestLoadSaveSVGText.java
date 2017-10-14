package net.sf.latexdraw.view.svg.loadSave;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.models.interfaces.shape.TextPosition;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestLoadSaveSVGText extends TestLoadSaveSVGPositionShape<IText> {
	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createText();
	}

	@Override
	protected void setDefaultDimensions() {
		//
	}

	@Override
	protected void compareShapes(final IText sh2) {
		assertEquals(shape.getPosition().getX(), sh2.getPosition().getX(), 0.0001);
		assertEquals(shape.getPosition().getY(), sh2.getPosition().getY(), 0.0001);
	}

	@Test
	public void testTextPositionTOP() {
		shape.setText("coucou");
		shape.setPosition(11., 12.);
		shape.setTextPosition(TextPosition.TOP);
		compareShapes(generateShape());
	}

	@Test
	public void testTextPositionBOT() {
		shape.setText("coucou");
		shape.setPosition(-11., -12.);
		shape.setTextPosition(TextPosition.BOT);
		compareShapes(generateShape());
	}

	@Test
	public void testTextPositionTOPLEFT() {
		shape.setText("coucou");
		shape.setPosition(101., 1.);
		shape.setTextPosition(TextPosition.TOP_LEFT);
		compareShapes(generateShape());
	}

	@Test
	public void testTextPositionTOPRIGHT() {
		shape.setText("coucou");
		shape.setPosition(11., 12.);
		shape.setTextPosition(TextPosition.TOP_RIGHT);
		compareShapes(generateShape());
	}

	@Test
	public void testTextPositionBOTRIGHT() {
		shape.setText("coucou");
		shape.setPosition(1., 0.);
		shape.setTextPosition(TextPosition.BOT_RIGHT);
		compareShapes(generateShape());
	}

	@Test
	public void testTextPositionBOTLEFT() {
		shape.setText("coucou");
		shape.setPosition(111., 123.);
		shape.setTextPosition(TextPosition.BOT_LEFT);
		compareShapes(generateShape());
	}

	@Test
	public void testStandardText() {
		shape.setText("coucou");
		shape.setLineColour(DviPsColors.RED);
		compareShapes(generateShape());
	}

	@Test
	public void testMathLaTeXText() {
		shape.setText("$fd_{er}$");
		compareShapes(generateShape());
	}

	@Test
	public void testInvalidLaTeXText() {
		shape.setText("$fd_{er");
		compareShapes(generateShape());
	}

	@Test
	public void testMultilineText() {
		shape.setText("$fd_{er}$\n\n\\emph{coucou}");
		compareShapes(generateShape());
	}
}
