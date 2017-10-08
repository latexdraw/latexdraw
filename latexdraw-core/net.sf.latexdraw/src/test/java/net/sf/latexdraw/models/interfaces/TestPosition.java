package net.sf.latexdraw.models.interfaces;

import net.sf.latexdraw.models.interfaces.shape.Position;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestPosition {
	@Test
	public void testIsSouth() {
		assertTrue(Position.SE.isSouth());
		assertTrue(Position.SOUTH.isSouth());
		assertTrue(Position.SW.isSouth());
		assertFalse(Position.NE.isSouth());
		assertFalse(Position.NW.isSouth());
		assertFalse(Position.NORTH.isSouth());
		assertFalse(Position.WEST.isSouth());
		assertFalse(Position.EAST.isSouth());
	}

	@Test
	public void testIsNorth() {
		assertTrue(Position.NE.isNorth());
		assertTrue(Position.NW.isNorth());
		assertTrue(Position.NORTH.isNorth());
		assertFalse(Position.SE.isNorth());
		assertFalse(Position.SW.isNorth());
		assertFalse(Position.SOUTH.isNorth());
		assertFalse(Position.WEST.isNorth());
		assertFalse(Position.EAST.isNorth());
	}

	@Test
	public void testIsWest() {
		assertTrue(Position.WEST.isWest());
		assertTrue(Position.NW.isWest());
		assertTrue(Position.SW.isWest());
		assertFalse(Position.EAST.isWest());
		assertFalse(Position.SE.isWest());
		assertFalse(Position.NE.isWest());
		assertFalse(Position.NORTH.isWest());
		assertFalse(Position.SOUTH.isWest());
	}

	@Test
	public void testIsEast() {
		assertTrue(Position.EAST.isEast());
		assertTrue(Position.NE.isEast());
		assertTrue(Position.SE.isEast());
		assertFalse(Position.SOUTH.isEast());
		assertFalse(Position.NORTH.isEast());
		assertFalse(Position.NW.isEast());
		assertFalse(Position.SW.isEast());
		assertFalse(Position.WEST.isEast());
	}
}
