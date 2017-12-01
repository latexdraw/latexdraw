package net.sf.latexdraw.view.jfx;

import java.util.Arrays;
import net.sf.latexdraw.models.ShapeFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestViewArrow {
	ViewPolyline view;
	ViewArrow varrow;

	@Before
	public void setUp() {
		view = (ViewPolyline) ViewFactory.INSTANCE.createView(ShapeFactory.INST.createPolyline(
			Arrays.asList(ShapeFactory.INST.createPoint(), ShapeFactory.INST.createPoint(10d, 20d), ShapeFactory.INST.createPoint(30d, 40d)))).
			orElseThrow(() -> new IllegalArgumentException());
		varrow = view.viewArrows.arrows.get(0);
	}

	@Test
	public void testViewArrowShapesNotEnableByDefault() {
		assertTrue(varrow.getChildren().stream().allMatch(node -> node.isDisabled()));
	}
}
