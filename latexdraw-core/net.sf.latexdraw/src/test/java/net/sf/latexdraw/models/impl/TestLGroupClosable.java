package net.sf.latexdraw.models.impl;

import java.util.Arrays;
import java.util.List;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestLGroupClosable {
	IGroup group;
	IBezierCurve open1;
	IBezierCurve open2;

	@Before
	public void setUp() {
		group = ShapeFactory.INST.createGroup();
		open1 = ShapeFactory.INST.createBezierCurve(Arrays.asList(ShapeFactory.INST.createPoint(10, 20),
			ShapeFactory.INST.createPoint(30, 40)));
		open2 = ShapeFactory.INST.createBezierCurve(Arrays.asList(ShapeFactory.INST.createPoint(-10, -20),
			ShapeFactory.INST.createPoint(50, 20)));
		group.addShape(ShapeFactory.INST.createCircle());
		group.addShape(open1);
		group.addShape(ShapeFactory.INST.createText());
		group.addShape(open2);
	}

	@Test
	public void testIsOpenedOK() {
		open2.setOpened(true);
		assertTrue(group.isOpened());
	}

	@Test
	public void testIsOpenedOKFalse() {
		open1.setOpened(false);
		open2.setOpened(true);
		assertFalse(group.isOpened());
	}

	@Test
	public void testIsOpenedKO() {
		group.clear();
		assertFalse(group.isOpened());
	}

	@Test
	public void testSetOpen() {
		open1.setOpened(false);
		open2.setOpened(false);
		group.setOpened(true);
		assertTrue(open1.isOpened());
		assertTrue(open2.isOpened());
	}

	@Test
	public void testSetOpenListKO() {
		open1.setOpened(true);
		open2.setOpened(true);
		final List<Boolean> vals = Arrays.asList(open1.isOpened(), open2.isOpened());
		group.setOpenList(null);
		assertEquals(vals, Arrays.asList(open1.isOpened(), open2.isOpened()));
	}

	@Test
	public void testSetOpenListKONotSameSize() {
		open1.setOpened(true);
		open2.setOpened(true);
		final List<Boolean> vals = Arrays.asList(open1.isOpened(), open2.isOpened());
		group.setOpenList(Arrays.asList(false, true));
		assertEquals(vals, Arrays.asList(open1.isOpened(), open2.isOpened()));
	}

	@Test
	public void testSetOpenListOK() {
		open1.setOpened(false);
		open2.setOpened(true);
		group.setOpenList(Arrays.asList(false, true, false, false));
		assertEquals(Arrays.asList(true, false), Arrays.asList(open1.isOpened(), open2.isOpened()));
	}
}
