package test.svg.loadSave;

import static org.junit.Assert.assertEquals;
import net.sf.latexdraw.glib.models.interfaces.shape.IPositionShape;

public abstract class TestLoadSaveSVGPositionShape<T extends IPositionShape> extends TestLoadSaveSVG<T> {
	@Override
	protected void compareShapes(final T sh2) {
		super.compareShapes(sh2);
		assertEquals(sh2.getPosition().getX(), shape.getPosition().getX(), 0.00001);
		assertEquals(sh2.getPosition().getY(), shape.getPosition().getY(), 0.00001);
	}


	@Override
	protected void setDefaultDimensions() {
		shape.setPosition(200.0, 300.0);
	}
}
