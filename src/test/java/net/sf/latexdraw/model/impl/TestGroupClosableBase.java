package net.sf.latexdraw.model.impl;

import java.util.Arrays;
import java.util.Optional;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.BezierCurve;
import net.sf.latexdraw.model.api.shape.Group;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestGroupClosableBase {
	Group group;
	BezierCurve open1;
	BezierCurve open2;

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
	public void testSetOpenListKONotSameSize() {
		open1.setOpened(true);
		open2.setOpened(true);
		group.setOpenList(Arrays.asList(Optional.of(false), Optional.of(true)));
		assertEquals(Arrays.asList(Optional.empty(), Optional.of(true), Optional.empty(), Optional.of(true)), group.getOpenList());
	}

	@Test
	public void testSetOpenListOK() {
		open1.setOpened(false);
		open2.setOpened(true);
		group.setOpenList(Arrays.asList(Optional.of(false), Optional.of(true), Optional.of(false), Optional.of(false)));
		assertEquals(Arrays.asList(Optional.empty(), Optional.of(true), Optional.empty(), Optional.of(false)), group.getOpenList());
	}
}
