package test.svg.loadSave;

import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IModifiablePointsShape;

public abstract class TestLoadSaveSVGModifiablePointsShape<T extends IModifiablePointsShape> extends TestLoadSaveSVG<IModifiablePointsShape> {


	@Override
	protected void compareShapes(final IModifiablePointsShape r2) {
		super.compareShapes(r2);
		assertEquals(shape.getNbPoints(), r2.getNbPoints());

		for(int i=0, size=shape.getNbPoints(); i<size; i++) {
			assertEquals(shape.getPtAt(i).getX(), r2.getPtAt(i).getX());
			assertEquals(shape.getPtAt(i).getY(), r2.getPtAt(i).getY());
		}
	}


	@Override
	protected void setDefaultDimensions() {
		shape.addPoint(ShapeFactory.createPoint(10, 20));
		shape.addPoint(ShapeFactory.createPoint(30, 50));
		shape.addPoint(ShapeFactory.createPoint(60, 78));
		shape.addPoint(ShapeFactory.createPoint(-60, -10));
	}
}
