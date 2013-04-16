package test.glib.models.interfaces;


import net.sf.latexdraw.glib.models.interfaces.IPositionShape;
import static junit.framework.Assert.*;
import net.sf.latexdraw.glib.models.interfaces.IText;

import org.junit.Test;

public abstract class TestIText<T extends IText> extends TestIPositionShape<T> {

	@Override
	public void testGetBottomLeftPoint() {
		assertEquals(shape.getBottomLeftPoint(), ((IPositionShape)shape).getPosition());
	}

	@Override
	public void testGetBottomRightPoint() {
		//TODO
	}

	@Override
	public void testGetTopLeftPoint() {
		//TODO
	}

	@Override
	public void testGetTopRightPoint() {
		//TODO
	}

	@Override
	public void testMirrorHorizontal() {
		//TODO
	}

	@Override
	public void testMirrorVertical() {
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
