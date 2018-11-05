package net.sf.latexdraw.view.jfx;

import javafx.geometry.Bounds;
import javafx.scene.shape.Path;
import net.sf.latexdraw.data.ShapeSupplier;
import net.sf.latexdraw.model.api.shape.Triangle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(ApplicationExtension.class)
public class TestViewTriangle extends TestViewBorderedShape<ViewTriangle, Triangle, Path> {
	@Override
	protected Triangle createModel() {
		return ShapeSupplier.createTriangle();
	}

	@Test
	public void testDbleBorderNotEmpty() {
		assertFalse(view.dblBorder.getElements().isEmpty());
	}

	@Test
	public void testShadowNotEmpty() {
		assertFalse(view.shadow.getElements().isEmpty());
	}

	@Test
	@Override
	public void testOnTranslateX() {
		final Bounds before = view.getBorder().getBoundsInParent();
		model.translate(17d, 0d);
		view.getBorder().setTranslateX(-17d);
		assertEquals(before, view.getBorder().getBoundsInParent());
	}

	@Test
	@Override
	public void testOnTranslateY() {
		final Bounds before = view.getBorder().getBoundsInParent();
		model.translate(0d, -19d);
		view.getBorder().setTranslateY(19d);
		assertEquals(before, view.getBorder().getBoundsInParent());
	}

	@Test
	@Override
	public void testShadowPositionSameThanBorder() {
		assertEquals(view.getBorder().getElements(), view.getShadow().orElseThrow().getElements());
	}
}
