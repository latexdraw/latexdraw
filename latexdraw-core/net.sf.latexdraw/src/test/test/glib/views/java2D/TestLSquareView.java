package test.glib.views.java2D;

import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.views.Java2D.interfaces.View2DTK;

import org.junit.Before;

public class TestLSquareView extends TestLRectangleView {
	@Before
	@Override
	public void setUp() {
		super.setUp();
		view = View2DTK.getFactory().createView(ShapeFactory.factory().createSquare(false));
	}
}
