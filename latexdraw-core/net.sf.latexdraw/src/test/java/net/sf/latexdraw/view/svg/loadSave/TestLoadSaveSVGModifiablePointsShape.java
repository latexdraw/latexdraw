package net.sf.latexdraw.view.svg.loadSave;

import static org.junit.Assert.assertEquals;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;

public abstract class TestLoadSaveSVGModifiablePointsShape<T extends IModifiablePointsShape> extends TestLoadSaveSVG<T> {
	@Override
	protected void compareShapes(final T r2) {
		super.compareShapes(r2);
		assertEquals(shape.getNbPoints(), r2.getNbPoints());

		for(int i = 0, size = shape.getNbPoints(); i < size; i++) {
			assertEquals(shape.getPtAt(i).getX(), r2.getPtAt(i).getX(), 0.0001);
			assertEquals(shape.getPtAt(i).getY(), r2.getPtAt(i).getY(), 0.0001);
		}
	}

	@Override
	protected void setDefaultDimensions() {
		shape.addPoint(ShapeFactory.INST.createPoint(10, 20));
		shape.addPoint(ShapeFactory.INST.createPoint(30, 50));
		shape.addPoint(ShapeFactory.INST.createPoint(60, 78));
		shape.addPoint(ShapeFactory.INST.createPoint(-60, -10));
	}
}
