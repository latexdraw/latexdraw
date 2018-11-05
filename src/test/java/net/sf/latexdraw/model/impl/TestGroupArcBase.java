package net.sf.latexdraw.model.impl;

import java.util.Optional;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.ArcStyle;
import net.sf.latexdraw.model.api.shape.Arc;
import net.sf.latexdraw.model.api.shape.Group;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TestGroupArcBase {
	Group group;
	Arc arc1;
	Arc arc2;

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
		assertThat(group.getArcStyleList(), contains(Optional.empty(), Optional.of(ArcStyle.CHORD), Optional.empty(), Optional.of(ArcStyle.WEDGE)));
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
		assertThat(group.getAngleStartList(), contains(Optional.empty(), Optional.of(1.1), Optional.empty(), Optional.of(1.6)));
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
		assertThat(group.getAngleEndList(), contains(Optional.empty(), Optional.of(2.6), Optional.empty(), Optional.of(3.1)));
	}
}
