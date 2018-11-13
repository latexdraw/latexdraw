package net.sf.latexdraw.view.jfx;

import java.util.Arrays;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.service.LaTeXDataService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestViewArrow {
	ViewPolyline view;
	ViewArrow varrow;

	@BeforeEach
	public void setUp() {
		view = (ViewPolyline) new ViewFactory(Mockito.mock(LaTeXDataService.class)).createView(ShapeFactory.INST.createPolyline(
			Arrays.asList(ShapeFactory.INST.createPoint(), ShapeFactory.INST.createPoint(10d, 20d), ShapeFactory.INST.createPoint(30d, 40d)))).
			orElseThrow(() -> new IllegalArgumentException());
		varrow = view.viewArrows.arrows.get(0);
	}

	@AfterEach
	public void tearDown() {
		view.flush();
		varrow.flush();
	}

	@Test
	public void testViewArrowShapesNotEnableByDefault() {
		assertTrue(varrow.getChildren().stream().allMatch(node -> node.isDisabled()));
	}
}
