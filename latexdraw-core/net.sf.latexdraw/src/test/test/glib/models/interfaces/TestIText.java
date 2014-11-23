package test.glib.models.interfaces;


import static org.junit.Assert.*;
import net.sf.latexdraw.glib.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IText;

import org.junit.Test;

public abstract class TestIText<T extends IText> extends TestIPositionShape<T> {

	@Override
	@Test public void testGetBottomLeftPoint() {
		assertEquals(shape.getBottomLeftPoint(), ((IPositionShape)shape).getPosition());
	}

	@Override
	@Test public void testGetBottomRightPoint() {
		//TODO
	}

	@Override
	@Test public void testGetTopLeftPoint() {
		//TODO
	}

	@Override
	@Test public void testGetTopRightPoint() {
		//TODO
	}

	@Override
	@Test public void testMirrorHorizontal() {
		//TODO
	}

	@Override
	@Test public void testMirrorVertical() {
		//TODO
	}


	@Override
	@Test
	public void testCopy() {
		//TODO
	}


	@Override
	@Test
	public void testDuplicate() {
		super.testDuplicate();
		//TODO
	}
}
