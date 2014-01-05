package test.svg.loadSave;

import java.awt.Color;

import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.IText;
import net.sf.latexdraw.glib.models.interfaces.IText.TextPosition;

import org.junit.Before;

public class TestLoadSaveSVGText extends TestLoadSaveSVG<IText> {
	@Before
	@Override
	public void setUp() {
		shape = ShapeFactory.factory().createText(false);
	}

	@Override
	protected void setDefaultDimensions() {
		//
	}

	@Override
	protected void compareShapes(final IText sh2) {
		//TODO
//		assertTrue(shape.isParametersEquals(sh2, true));
		assertEquals(shape.getPosition().getX(), sh2.getPosition().getX());
		assertEquals(shape.getPosition().getY(), sh2.getPosition().getY());
	}


	public void testTextPositionTOP() {
		shape.setText("coucou");
		shape.setPosition(11., 12.);
		shape.setTextPosition(TextPosition.TOP);
		compareShapes(generateShape());
	}

	public void testTextPositionBOT() {
		shape.setText("coucou");
		shape.setPosition(-11., -12.);
		shape.setTextPosition(TextPosition.BOT);
		compareShapes(generateShape());
	}

	public void testTextPositionTOP_LEFT() {
		shape.setText("coucou");
		shape.setPosition(101., 1.);
		shape.setTextPosition(TextPosition.TOP_LEFT);
		compareShapes(generateShape());
	}

	public void testTextPositionTOP_RIGHT() {
		shape.setText("coucou");
		shape.setPosition(11., 12.);
		shape.setTextPosition(TextPosition.TOP_RIGHT);
		compareShapes(generateShape());
	}

	public void testTextPositionBOT_RIGHT() {
		shape.setText("coucou");
		shape.setPosition(1., 0.);
		shape.setTextPosition(TextPosition.BOT_RIGHT);
		compareShapes(generateShape());
	}

	public void testTextPositionBOT_LEFT() {
		shape.setText("coucou");
		shape.setPosition(111., 123.);
		shape.setTextPosition(TextPosition.BOT_LEFT);
		compareShapes(generateShape());
	}


	public void testStandardText() {
		shape.setText("coucou");
		shape.setLineColour(Color.RED);
		compareShapes(generateShape());
	}

	public void testMathLaTeXText() {
		shape.setText("$fd_{er}$");
		compareShapes(generateShape());
	}

	public void testInvalidLaTeXText() {
		shape.setText("$fd_{er");
		compareShapes(generateShape());
	}

	public void testMultilineText() {
		shape.setText("$fd_{er}$\n\n\\emph{coucou}");
		compareShapes(generateShape());
	}
}
