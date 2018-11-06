package net.sf.latexdraw.view.jfx;

import java.util.Arrays;
import net.sf.latexdraw.data.InjectionExtension;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Polygon;
import org.junit.jupiter.api.extension.ExtendWith;

// To remove with vintage will not be used anymore. Otherwise, junit thinks this class is junit4
@ExtendWith(InjectionExtension.class)
public class TestViewPolygon extends TestViewPolyPoint<ViewPolygon, Polygon> {
	@Override
	protected Polygon createModel() {
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
