package net.sf.latexdraw.view.jfx;

import java.util.concurrent.TimeoutException;
import javafx.geometry.Bounds;
import javafx.scene.shape.Path;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ITriangle;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import static org.junit.Assert.assertEquals;

public class TestViewTriangle extends TestViewBorderedShape<ViewTriangle, ITriangle, Path> {
	@BeforeClass
	public static void beforeClass() throws TimeoutException {
		FxToolkit.registerPrimaryStage();
	}

	@Override
	protected ITriangle createModel() {
		final ITriangle sh = ShapeFactory.INST.createTriangle();
		sh.setWidth(11d);
		sh.setHeight(21d);
		sh.setX(103d);
		sh.setY(207d);
		return sh;
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
		assertEquals(view.getBorder().getElements(), view.getShadow().get().getElements());
	}
}
