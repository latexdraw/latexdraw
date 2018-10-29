package net.sf.latexdraw.view.jfx;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.shape.Path;
import net.sf.latexdraw.data.ShapeSupplier;
import net.sf.latexdraw.models.interfaces.shape.IRhombus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestViewRhombus extends TestViewBorderedShape<ViewRhombus, IRhombus, Path> {
	@BeforeAll
	public static void beforeClass() {
		try {
			Platform.startup(() -> {
			});
		}catch(final IllegalStateException ex) {
			// Ok
		}
	}

	@Override
	protected IRhombus createModel() {
		return ShapeSupplier.createRhombus();
	}

	@Test
	void testShadowNotEmpty() {
		assertFalse(view.shadow.getElements().isEmpty());
	}

	@Test
	void testDbleBorderNotEmpty() {
		assertFalse(view.dblBorder.getElements().isEmpty());
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
	void testShadowPositionSameThanBorder() {
		assertEquals(view.getBorder().getElements(), view.getShadow().orElseThrow().getElements());
	}
}
