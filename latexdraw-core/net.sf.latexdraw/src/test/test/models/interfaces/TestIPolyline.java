package test.models.interfaces;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class TestIPolyline<T extends IPolyline> extends TestIPolygon<T> {
	//TODO to move in an interface when will use junit 5 to test all the shapes that support arrows
	@Test
	public void testCopyArrowableLines() {
		shape2.setArrowStyle(ArrowStyle.BAR_IN, 0);
		shape2.setArrowStyle(ArrowStyle.LEFT_ARROW, 1);
		shape.copy(shape2);
		assertEquals(ArrowStyle.BAR_IN, shape.getArrowStyle(0));
		assertEquals(ArrowStyle.LEFT_ARROW, shape.getArrowStyle(1));
	}

	@Test
	public void testCopyArrowableGroup() {
		IGroup gp = ShapeFactory.INST.createGroup();
		shape2.setArrowStyle(ArrowStyle.BAR_IN, 0);
		shape2.setArrowStyle(ArrowStyle.LEFT_ARROW, 1);
		gp.addShape(shape2);
		shape.copy(gp);
		assertEquals(ArrowStyle.BAR_IN, shape.getArrowStyle(0));
		assertEquals(ArrowStyle.LEFT_ARROW, shape.getArrowStyle(1));
	}
}
