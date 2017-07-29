package net.sf.latexdraw.view.jfx;

import java.util.concurrent.TimeoutException;
import javafx.geometry.Bounds;
import javafx.scene.shape.Path;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IRhombus;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import static org.junit.Assert.assertEquals;

public class TestViewRhombus extends TestViewBorderedShape<ViewRhombus, IRhombus, Path> {
	@BeforeClass
	public static void beforeClass() throws TimeoutException {
		FxToolkit.registerPrimaryStage();
	}

	@Override
	protected IRhombus createModel() {
		final IRhombus rec = ShapeFactory.INST.createRhombus();
		rec.setWidth(11d);
		rec.setHeight(21d);
		rec.setX(103d);
		rec.setY(207d);
		return rec;
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
