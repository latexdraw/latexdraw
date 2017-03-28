package test.views.jfx;

import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.view.jfx.ViewFactory;
import net.sf.latexdraw.view.jfx.ViewPathShape;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

abstract class TestViewPolyPoint<R extends ViewPathShape<S>, S extends IModifiablePointsShape> extends TestViewBorderedShape<R, S, Path> {
	protected List<PathElement> before;

	protected static List<PathElement> duplicatePath(final List<PathElement> path) {
		return path.stream().map(elt -> {
			PathElement dupelt;
			if(elt instanceof MoveTo) {
				final MoveTo moveTo = (MoveTo) elt;
				dupelt = ViewFactory.INSTANCE.createMoveTo(moveTo.getX(), moveTo.getY());
			}else if(elt instanceof LineTo) {
				final LineTo lineTo = (LineTo) elt;
				dupelt = ViewFactory.INSTANCE.createLineTo(lineTo.getX(), lineTo.getY());
			}else if(elt instanceof ClosePath) {
				dupelt = ViewFactory.INSTANCE.createClosePath();
			}else if(elt instanceof CubicCurveTo) {
				final CubicCurveTo cct = (CubicCurveTo) elt;
				dupelt = ViewFactory.INSTANCE.createCubicCurveTo(cct.getControlX1(), cct.getControlY1(), cct.getControlX2(), cct.getControlY2(), cct.getX(), cct.getY());
			}else {
				throw new IllegalArgumentException();
			}

			dupelt.setAbsolute(elt.isAbsolute());
			return dupelt;
		}).collect(Collectors.toList());
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		before = duplicatePath(border.getElements());
	}

	@Override
	@Test
	public void testShadowPositionSameThanBorder() {
		assertEquals(border.getElements(), view.getShadow().get().getElements());
	}

	@Override
	@Test
	public void testOnTranslateX() {
		model.translate(11d, 0d);
		assertPathSameButNotEqual(before, border.getElements());
	}

	@Override
	@Test
	public void testOnTranslateY() {
		model.translate(0d, 11d);
		assertPathSameButNotEqual(before, border.getElements());
	}
}
