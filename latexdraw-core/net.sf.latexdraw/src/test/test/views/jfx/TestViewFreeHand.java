package test.views.jfx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.FreeHandStyle;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.view.jfx.ViewFreeHand;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestViewFreeHand extends TestViewSingleShape<ViewFreeHand, IFreehand, Path> {
	List<PathElement> before;


	@BeforeClass
	public static void beforeClass() throws TimeoutException {
		FxToolkit.registerPrimaryStage();
	}

	@Override
	protected IFreehand createModel() {
		final IFreehand sh = ShapeFactory.INST.createFreeHand();

		sh.addPoint(ShapeFactory.INST.createPoint(9, 23));
		sh.addPoint(ShapeFactory.INST.createPoint(21, 11));
		sh.addPoint(ShapeFactory.INST.createPoint(45, 3));
		sh.addPoint(ShapeFactory.INST.createPoint(87, 125));
		sh.addPoint(ShapeFactory.INST.createPoint(187, 25));
		sh.addPoint(ShapeFactory.INST.createPoint(287, 425));
		sh.addPoint(ShapeFactory.INST.createPoint(387, 325));
		sh.addPoint(ShapeFactory.INST.createPoint(19, 233));
		sh.addPoint(ShapeFactory.INST.createPoint(121, 114));
		sh.addPoint(ShapeFactory.INST.createPoint(445, 33));

		return sh;
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		before = new ArrayList<>(border.getElements());
	}

	@Test
	public void testChangeInterval() {
		model.setInterval(model.getInterval() * 2);
		assertNotEquals(border.getElements(), before);
	}

	@Test
	public void testChangeType() {
		model.setType(model.getType() == FreeHandStyle.CURVES ? FreeHandStyle.LINES : FreeHandStyle.CURVES);
		assertNotEquals(border.getElements(), before);
	}

	@Test
	public void testChangeOpen() {
		model.setOpen(!model.isOpen());
		assertNotEquals(border.getElements(), before);
	}

	@Override
	@Test
	public void testShadowPositionSameThanBorder() {
		assertEquals(border.getElements(), view.getShadow().get().getElements());
	}
}
