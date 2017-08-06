package net.sf.latexdraw.view.jfx;

import java.util.Arrays;
import java.util.concurrent.TimeoutException;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import org.junit.BeforeClass;
import org.testfx.api.FxToolkit;

public class TestViewPolygon extends TestViewPolyPoint<ViewPolygon, IPolygon> {
	@BeforeClass
	public static void beforeClass() throws TimeoutException {
		FxToolkit.registerPrimaryStage();
	}

	@Override
	protected IPolygon createModel() {
		return ShapeFactory.INST.createPolygon(Arrays.asList(
			ShapeFactory.INST.createPoint(9, 23),
			ShapeFactory.INST.createPoint(21, 11),
			ShapeFactory.INST.createPoint(45, 3),
			ShapeFactory.INST.createPoint(87, 125),
			ShapeFactory.INST.createPoint(187, 25),
			ShapeFactory.INST.createPoint(287, 425),
			ShapeFactory.INST.createPoint(387, 325),
			ShapeFactory.INST.createPoint(19, 233),
			ShapeFactory.INST.createPoint(121, 114),
			ShapeFactory.INST.createPoint(445, 33)));
	}
}
