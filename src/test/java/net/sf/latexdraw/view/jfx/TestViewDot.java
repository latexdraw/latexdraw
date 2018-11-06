package net.sf.latexdraw.view.jfx;

import java.util.List;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Dot;
import net.sf.latexdraw.model.api.shape.DotStyle;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestViewDot extends TestViewShape<ViewDot, Dot> {
	List<PathElement> pathBefore;
	Ellipse dotBefore;

	@BeforeEach
	void setUp() {
		pathBefore = duplicatePath(getPathView().getElements());
		final Ellipse dotView = getDotView();
		dotBefore = new Ellipse(dotView.getCenterX(), dotView.getCenterY(), dotView.getRadiusX(), dotView.getRadiusY());
	}

	@Override
	protected Dot createModel() {
		return ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint(101, 67));
	}

	private Ellipse getDotView() {
		return (Ellipse) view.getChildren().get(0);
	}

	private Path getPathView() {
		return (Path) view.getChildren().get(1);
	}

	@Test
	void testOnDotStyleDot() {
		checkDot();
		assertTrue(getPathView().getElements().isEmpty());
		assertFalse(getPathView().isVisible());
	}

	@Test
	void testOnDotStyleChangedO() {
		model.setDotStyle(DotStyle.O);
		assertTrue(getPathView().getElements().isEmpty());
		assertFalse(getPathView().isVisible());
		checkDot();
	}

	@Test
	void testOnDotStyleChangedOPLUS() {
		model.setDotStyle(DotStyle.OPLUS);
		checkDot();
		assertFalse(getPathView().getElements().isEmpty());
		assertTrue(getPathView().isVisible());
	}

	@Test
	void testOnDotStyleChangedOTIMES() {
		model.setDotStyle(DotStyle.OTIMES);
		checkDot();
		assertFalse(getPathView().getElements().isEmpty());
		assertTrue(getPathView().isVisible());
	}

	@Test
	void testUpdateOnLineColorChange() {
		final Paint fill = view.dot.getFill();
		final Paint stroke = view.dot.getStroke();
		model.setLineColour(DviPsColors.YELLOW);
		assertNotEquals(fill, view.dot.getFill());
		assertNotEquals(stroke, view.dot.getStroke());
	}

	private void checkDot() {
		assertTrue(getDotView().isVisible());
		assertEquals(model.getPosition().getX(), getDotView().getCenterX(), 0.0001);
		assertEquals(model.getPosition().getY(), getDotView().getCenterY(), 0.0001);
		assertEquals((model.getDiametre() - model.getOGap()) / 2d, getDotView().getRadiusX(), 0.0001);
		assertEquals((model.getDiametre() - model.getOGap()) / 2d, getDotView().getRadiusY(), 0.0001);
	}


	@Test
	void testOnDotStyleChangedASTERISK() {
		model.setDotStyle(DotStyle.ASTERISK);
		assertFalse(getDotView().isVisible());
		assertTrue(getPathView().isVisible());
		assertNotEquals(pathBefore.size(), getPathView().getElements().size());
	}

	@Test
	void testOnDotStyleChangedBAR() {
		model.setDotStyle(DotStyle.BAR);
		assertFalse(getDotView().isVisible());
		assertTrue(getPathView().isVisible());
		assertNotEquals(pathBefore.size(), getPathView().getElements().size());
	}

	@Test
	void testOnDotStyleChangedDIAMOND() {
		model.setDotStyle(DotStyle.DIAMOND);
		assertFalse(getDotView().isVisible());
		assertTrue(getPathView().isVisible());
		assertNotEquals(pathBefore.size(), getPathView().getElements().size());
	}

	@Test
	void testOnDotStyleChangedFDIAMOND() {
		model.setDotStyle(DotStyle.FDIAMOND);
		assertFalse(getDotView().isVisible());
		assertTrue(getPathView().isVisible());
		assertNotEquals(pathBefore.size(), getPathView().getElements().size());
	}

	@Test
	void testOnDotStyleChangedFPENTAGON() {
		model.setDotStyle(DotStyle.FPENTAGON);
		assertFalse(getDotView().isVisible());
		assertTrue(getPathView().isVisible());
		assertNotEquals(pathBefore.size(), getPathView().getElements().size());
	}

	@Test
	void testOnDotStyleChangedFSQUARE() {
		model.setDotStyle(DotStyle.FSQUARE);
		assertFalse(getDotView().isVisible());
		assertTrue(getPathView().isVisible());
		assertNotEquals(pathBefore.size(), getPathView().getElements().size());
	}

	@Test
	void testOnDotStyleChangedFTRIANGLE() {
		model.setDotStyle(DotStyle.FTRIANGLE);
		assertFalse(getDotView().isVisible());
		assertTrue(getPathView().isVisible());
		assertNotEquals(pathBefore.size(), getPathView().getElements().size());
	}

	@Test
	void testOnDotStyleChangedPENTAGON() {
		model.setDotStyle(DotStyle.PENTAGON);
		assertFalse(getDotView().isVisible());
		assertTrue(getPathView().isVisible());
		assertNotEquals(pathBefore.size(), getPathView().getElements().size());
	}

	@Test
	void testOnDotStyleChangedPLUS() {
		model.setDotStyle(DotStyle.PLUS);
		assertFalse(getDotView().isVisible());
		assertTrue(getPathView().isVisible());
		assertNotEquals(pathBefore.size(), getPathView().getElements().size());
	}

	@Test
	void testOnDotStyleChangedSQUARE() {
		model.setDotStyle(DotStyle.SQUARE);
		assertFalse(getDotView().isVisible());
		assertTrue(getPathView().isVisible());
		assertNotEquals(pathBefore.size(), getPathView().getElements().size());
	}

	@Test
	void testOnDotStyleChangedTRIANGLE() {
		model.setDotStyle(DotStyle.TRIANGLE);
		assertFalse(getDotView().isVisible());
		assertTrue(getPathView().isVisible());
		assertNotEquals(pathBefore.size(), getPathView().getElements().size());
	}

	@Test
	void testChangeDiametreDot() {
		model.setDiametre(model.getDiametre() * 1.33);
		checkDot();
	}

	@Test
	void testChangeDiametreNotDot() {
		model.setDotStyle(DotStyle.ASTERISK);
		pathBefore = duplicatePath(getPathView().getElements());
		model.setDiametre(model.getDiametre() * 1.33);
		assertPathSameButNotEqual(pathBefore, getPathView().getElements());
	}

	@Override
	@Test
	public void testOnTranslateX() {
		model.setDotStyle(DotStyle.ASTERISK);
		model.translate(11d, 0d);
		assertNotEquals(pathBefore.size(), getPathView().getElements().size());
	}

	@Override
	@Test
	public void testOnTranslateY() {
		model.setDotStyle(DotStyle.ASTERISK);
		model.translate(0d, 13d);
		assertNotEquals(pathBefore.size(), getPathView().getElements().size());
	}

	@Test
	void testOnTranslateXDot() {
		model.translate(17d, 0d);
		assertNotEquals(dotBefore.getCenterX(), getDotView().getCenterX());
		assertEquals(dotBefore.getCenterY(), getDotView().getCenterY(), 0.0001);
	}

	@Test
	void testOnTranslateYDot() {
		model.translate(0d, 13d);
		assertNotEquals(dotBefore.getCenterY(), getDotView().getCenterY());
		assertEquals(dotBefore.getCenterX(), getDotView().getCenterX(), 0.0001);
	}
}
