package net.sf.latexdraw.view.jfx;

import java.util.concurrent.TimeoutException;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.view.jfx.ViewPolyline;
import org.junit.BeforeClass;
import org.testfx.api.FxToolkit;

public class TestViewPolyline extends TestViewPolyPoint<ViewPolyline, IPolyline> {
	@BeforeClass
	public static void beforeClass() throws TimeoutException {
		FxToolkit.registerPrimaryStage();
	}

	@Override
	protected IPolyline createModel() {
		final IPolyline sh = ShapeFactory.INST.createPolyline();

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
}
