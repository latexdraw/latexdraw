package net.sf.latexdraw.models.impl;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ArcStyle;
import net.sf.latexdraw.models.interfaces.shape.IArc;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TestLGroupArc {
	IGroup group;
	IArc arc1;
	IArc arc2;

	@Before
	public void setUp() {
		group = ShapeFactory.INST.createGroup();
		arc1 = ShapeFactory.INST.createCircleArc();
		arc2 = ShapeFactory.INST.createCircleArc();
		group.addShape(ShapeFactory.INST.createRectangle());
		group.addShape(arc1);
		group.addShape(ShapeFactory.INST.createCircle());
		group.addShape(arc2);

		arc1.setArcStyle(ArcStyle.CHORD);
		arc2.setArcStyle(ArcStyle.WEDGE);
		arc1.setAngleStart(1.1);
		arc2.setAngleStart(1.6);
		arc1.setAngleEnd(2.6);
		arc2.setAngleEnd(3.1);
	}

	@Test
	public void testGetArcStyle() {
		assertEquals(ArcStyle.CHORD, group.getArcStyle());
	}

	@Test
	public void testSetArcStyle() {
		group.setArcStyle(ArcStyle.ARC);
		assertEquals(ArcStyle.ARC, arc1.getArcStyle());
		assertEquals(ArcStyle.ARC, arc2.getArcStyle());
	}

	@Test
	public void testGetArcStyleAll() {
		assertThat(group.getArcStyleList(), contains(null, ArcStyle.CHORD, null, ArcStyle.WEDGE));
	}

	@Test
	public void testGetAngleStart() {
		assertEquals(1.1, group.getAngleStart(), 0.0001);
	}

	@Test
	public void testSetAngleStart() {
		group.setAngleStart(0.5);
		assertEquals(0.5, arc1.getAngleStart(), 0.0001);
		assertEquals(0.5, arc2.getAngleStart(), 0.0001);
	}

	@Test
	public void testGetAngleStartAll() {
		assertThat(group.getAngleStartList(), contains(null, 1.1, null, 1.6));
	}

	@Test
	public void testGetAngleEnd() {
		assertEquals(2.6, group.getAngleEnd(), 0.0001);
	}

	@Test
	public void testSetAngleEnd() {
		group.setAngleEnd(0.4);
		assertEquals(0.4, arc1.getAngleEnd(), 0.0001);
		assertEquals(0.4, arc2.getAngleEnd(), 0.0001);
	}

	@Test
	public void testGetAngleEndAll() {
		assertThat(group.getAngleEndList(), contains(null, 2.6, null, 3.1));
	}
}
