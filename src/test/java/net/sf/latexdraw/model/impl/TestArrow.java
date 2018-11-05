package net.sf.latexdraw.model.impl;

import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.ArrowStyle;
import net.sf.latexdraw.model.api.shape.Arrow;
import net.sf.latexdraw.model.api.shape.ArrowableSingleShape;
import net.sf.latexdraw.model.api.shape.Line;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestArrow {
	private Arrow arrow;
	@Mock private ArrowableSingleShape arrowable;

	@Before
	public void setUp() {
		arrow = new ArrowImpl(arrowable);
		when(arrowable.getFullThickness()).thenReturn(2d);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructor1NULL() {
		new ArrowImpl(null);
	}

	private void setArrowParams() {
		arrow.setArrowLength(11.123);
		arrow.setArrowStyle(ArrowStyle.BAR_IN);
		arrow.setArrowSizeDim(17.1234);
		arrow.setArrowSizeNum(21.12);
		arrow.setArrowInset(22.92);
		arrow.setDotSizeDim(29.12);
		arrow.setDotSizeNum(23.12);
		arrow.setTBarSizeDim(3.65);
		arrow.setTBarSizeNum(54.12);
		arrow.setBracketNum(2.12);
		arrow.setRBracketNum(73.82);
	}

	private void assertArrowEquals(final Arrow arrow2) {
		assertEquals(arrow.getArrowLength(), arrow2.getArrowLength(), 0.0001);
		assertEquals(arrow.getArrowStyle(), arrow2.getArrowStyle());
		assertEquals(arrow.getArrowSizeDim(), arrow2.getArrowSizeDim(), 0.0001);
		assertEquals(arrow.getArrowSizeNum(), arrow2.getArrowSizeNum(), 0.0001);
		assertEquals(arrow.getArrowInset(), arrow2.getArrowInset(), 0.0001);
		assertEquals(arrow.getDotSizeDim(), arrow2.getDotSizeDim(), 0.0001);
		assertEquals(arrow.getDotSizeNum(), arrow2.getDotSizeNum(), 0.0001);
		assertEquals(arrow.getTBarSizeDim(), arrow2.getTBarSizeDim(), 0.0001);
		assertEquals(arrow.getTBarSizeNum(), arrow2.getTBarSizeNum(), 0.0001);
		assertEquals(arrow.getBracketNum(), arrow2.getBracketNum(), 0.0001);
		assertEquals(arrow.getRBracketNum(), arrow2.getRBracketNum(), 0.0001);
	}

	@Test
	public void testCopyArrow() {
		setArrowParams();
		final Arrow arrow2 = new ArrowImpl(arrowable);
		arrow2.copy(arrow);
		assertArrowEquals(arrow2);
	}

	@Test
	public void testCopyNULL() {
		arrow.copy(null);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructor2NULLArrow() {
		new ArrowImpl(null, arrowable);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructor2NULLArrowable() {
		new ArrowImpl(new ArrowImpl(arrowable), null);
	}

	@Test
	public void testConstructor2CopyOK() {
		setArrowParams();
		assertArrowEquals(new ArrowImpl(arrow, arrowable));
	}

	@Test
	public void testGetLineThickness() {
		assertEquals(2d, arrow.getLineThickness(), 0.0001);
	}

	@Test
	public void testGetSetArrowStyle() {
		arrow.setArrowStyle(ArrowStyle.RIGHT_ARROW);
		assertEquals(ArrowStyle.RIGHT_ARROW, arrow.getArrowStyle());
	}

	@Test
	public void testGetSetArrowSizeDim() {
		arrow.setArrowSizeDim(11.2);
		assertEquals(11.2, arrow.getArrowSizeDim(), 0.00001);
	}

	@Test
	public void testGetSetArrowSizeNum() {
		arrow.setArrowSizeNum(11.2);
		assertEquals(11.2, arrow.getArrowSizeNum(), 0.00001);
	}

	@Test
	public void testGetSetArrowLength() {
		arrow.setArrowLength(11.2);
		assertEquals(11.2, arrow.getArrowLength(), 0.00001);
	}

	@Test
	public void testGetSetArrowInset() {
		arrow.setArrowInset(11.2);
		assertEquals(11.2, arrow.getArrowInset(), 0.00001);
	}

	@Test
	public void testGetSetDotSizeDim() {
		arrow.setDotSizeDim(11.2);
		assertEquals(11.2, arrow.getDotSizeDim(), 0.00001);
	}

	@Test
	public void testGetSetDotSizeNum() {
		arrow.setDotSizeNum(11.2);
		assertEquals(11.2, arrow.getDotSizeNum(), 0.00001);
	}

	@Test
	public void testGetSettBarSizeDim() {
		arrow.setTBarSizeDim(11.2);
		assertEquals(11.2, arrow.getTBarSizeDim(), 0.00001);
	}

	@Test
	public void testGetSettBarSizeNum() {
		arrow.setTBarSizeNum(11.2);
		assertEquals(11.2, arrow.getTBarSizeNum(), 0.00001);
	}

	@Test
	public void testGetSetbracketNum() {
		arrow.setBracketNum(11.2);
		assertEquals(11.2, arrow.getBracketNum(), 0.00001);
	}

	@Test
	public void testGetSetrBracketNum() {
		arrow.setRBracketNum(11.2);
		assertEquals(11.2, arrow.getRBracketNum(), 0.00001);
	}

	@Test
	public void testGetSetArrowStyleKO() {
		arrow.setArrowStyle(ArrowStyle.RIGHT_ARROW);
		arrow.setArrowStyle(null);
		assertEquals(ArrowStyle.RIGHT_ARROW, arrow.getArrowStyle());
	}

	@Test
	public void testGetSetArrowSizeDimKO() {
		arrow.setArrowSizeDim(11.2);
		arrow.setArrowSizeDim(0d);
		assertEquals(11.2, arrow.getArrowSizeDim(), 0.00001);
	}

	@Test
	public void testGetSetArrowSizeNumKO() {
		arrow.setArrowSizeNum(11.2);
		arrow.setArrowSizeNum(-2d);
		assertEquals(11.2, arrow.getArrowSizeNum(), 0.00001);
	}

	@Test
	public void testGetSetArrowLengthKO() {
		arrow.setArrowLength(11.2);
		arrow.setArrowLength(-1d);
		assertEquals(11.2, arrow.getArrowLength(), 0.00001);
	}

	@Test
	public void testGetSetArrowInsetKO() {
		arrow.setArrowInset(11.2);
		arrow.setArrowInset(-3d);
		assertEquals(11.2, arrow.getArrowInset(), 0.00001);
	}

	@Test
	public void testGetSetDotSizeDimKO() {
		arrow.setDotSizeDim(11.2);
		arrow.setDotSizeDim(0d);
		assertEquals(11.2, arrow.getDotSizeDim(), 0.00001);
	}

	@Test
	public void testGetSetDotSizeNumKO() {
		arrow.setDotSizeNum(11.2);
		arrow.setDotSizeNum(-5d);
		assertEquals(11.2, arrow.getDotSizeNum(), 0.00001);
	}

	@Test
	public void testGetSettBarSizeDimKO() {
		arrow.setTBarSizeDim(11.2);
		arrow.setTBarSizeDim(0d);
		assertEquals(11.2, arrow.getTBarSizeDim(), 0.00001);
	}

	@Test
	public void testGetSettBarSizeNumKO() {
		arrow.setTBarSizeNum(11.2);
		arrow.setTBarSizeNum(-6d);
		assertEquals(11.2, arrow.getTBarSizeNum(), 0.00001);
	}

	@Test
	public void testGetSetbracketNumKO() {
		arrow.setBracketNum(11.2);
		arrow.setBracketNum(-5d);
		assertEquals(11.2, arrow.getBracketNum(), 0.00001);
	}

	@Test
	public void testGetSetrBracketNumKO() {
		arrow.setRBracketNum(11.2);
		arrow.setRBracketNum(-1d);
		assertEquals(11.2, arrow.getRBracketNum(), 0.00001);
	}

	@Test
	public void testGetRoundShapedArrowRadius() {
		arrow.setDotSizeNum(3d);
		arrow.setDotSizeDim(4d);
		assertEquals(5d, arrow.getRoundShapedArrowRadius(), 0.0001);
	}

	@Test
	public void testGetBarShapedArrowWidth() {
		arrow.setTBarSizeNum(3d);
		arrow.setTBarSizeDim(4d);
		assertEquals(10d, arrow.getBarShapedArrowWidth(), 0.0001);
	}

	@Test
	public void testGetBracketShapedArrowLength() {
		arrow.setBracketNum(5d);
		arrow.setTBarSizeNum(3d);
		arrow.setTBarSizeDim(4d);
		assertEquals(50d, arrow.getBracketShapedArrowLength(), 0.0001);
	}

	@Test
	public void testGetArrowShapeLengthRightArrow() {
		arrow.setArrowStyle(ArrowStyle.RIGHT_ARROW);
		assertEquals(arrow.getArrowShapedWidth() * arrow.getArrowLength(), arrow.getArrowShapeLength(), 0.001);
	}

	@Test
	public void testGetArrowShapeLengthLeftArrow() {
		arrow.setArrowStyle(ArrowStyle.LEFT_ARROW);
		assertEquals(arrow.getArrowShapedWidth() * arrow.getArrowLength(), arrow.getArrowShapeLength(), 0.001);
	}

	@Test
	public void testGetArrowShapeLengthDbleRightArrow() {
		arrow.setArrowStyle(ArrowStyle.RIGHT_DBLE_ARROW);
		assertEquals(arrow.getArrowShapedWidth() * arrow.getArrowLength() * 2d, arrow.getArrowShapeLength(), 0.001);
	}

	@Test
	public void testGetArrowShapeLengthDbleLeftArrow() {
		arrow.setArrowStyle(ArrowStyle.LEFT_DBLE_ARROW);
		assertEquals(arrow.getArrowShapedWidth() * arrow.getArrowLength() * 2d, arrow.getArrowShapeLength(), 0.001);
	}

	@Test
	public void testGetArrowShapeLengthRoundIn() {
		arrow.setArrowStyle(ArrowStyle.ROUND_IN);
		assertEquals(arrow.getLineThickness() / 2d, arrow.getArrowShapeLength(), 0.001);
	}

	@Test
	public void testGetArrowShapeLengthLeftSquareBracket() {
		arrow.setArrowStyle(ArrowStyle.LEFT_SQUARE_BRACKET);
		assertEquals(arrow.getLineThickness() / 2d, arrow.getArrowShapeLength(), 0.001);
	}

	@Test
	public void testGetArrowShapeLengthRightSquareBracket() {
		arrow.setArrowStyle(ArrowStyle.LEFT_SQUARE_BRACKET);
		assertEquals(arrow.getLineThickness() / 2d, arrow.getArrowShapeLength(), 0.001);
	}

	@Test
	public void testGetArrowShapeLengthCircleIn() {
		arrow.setArrowStyle(ArrowStyle.CIRCLE_IN);
		assertEquals(arrow.getRoundShapedArrowRadius(), arrow.getArrowShapeLength(), 0.001);
	}

	@Test
	public void testGetArrowShapeLengthDiskIn() {
		arrow.setArrowStyle(ArrowStyle.DISK_IN);
		assertEquals(arrow.getRoundShapedArrowRadius(), arrow.getArrowShapeLength(), 0.001);
	}

	@Test
	public void testGetArrowShapeLengthNone() {
		arrow.setArrowStyle(ArrowStyle.NONE);
		assertEquals(0d, arrow.getArrowShapeLength(), 0.001);
	}

	@Test
	public void testGetArrowShapedWidth() {
		assertEquals(arrow.getArrowSizeNum() * arrow.getLineThickness() + arrow.getArrowSizeDim(), arrow.getArrowShapedWidth(), 0.001);
	}

	@Test
	public void testSetOnChanged() {
		final Runnable onChanged = Mockito.mock(Runnable.class);
		arrow.onChanges(onChanged);
		setArrowParams();
		verify(onChanged, times(11)).run();
	}

	@Test
	public void testGetShape() {
		assertEquals(arrowable, arrow.getShape());
	}

	@Test
	public void testGetArrowLine() {
		final Line line = ShapeFactory.INST.createLine(1d, 2d, 3d, 4d);
		when(arrowable.getArrowLine(0)).thenReturn(line);
		when(arrowable.getArrowIndex(arrow)).thenReturn(0);
		assertEquals(line, arrow.getArrowLine());
	}

	@Test
	public void testIsLeftArrowKO() {
		when(arrowable.getArrowIndex(arrow)).thenReturn(1);
		assertFalse(arrow.isLeftArrow());
	}

	@Test
	public void testIsLeftArrowOK() {
		when(arrowable.getArrowIndex(arrow)).thenReturn(0);
		assertTrue(arrow.isLeftArrow());
	}

	@Test
	public void testIsInvertedKOOnRight() {
		arrow.setArrowStyle(ArrowStyle.RIGHT_ARROW);
		when(arrowable.getArrowIndex(arrow)).thenReturn(1);
		assertFalse(arrow.isInverted());
	}

	@Test
	public void testIsInvertedOKOnRight() {
		arrow.setArrowStyle(ArrowStyle.LEFT_DBLE_ARROW);
		when(arrowable.getArrowIndex(arrow)).thenReturn(1);
		assertTrue(arrow.isInverted());
	}

	@Test
	public void testIsInvertedOKOnLeft() {
		arrow.setArrowStyle(ArrowStyle.RIGHT_SQUARE_BRACKET);
		when(arrowable.getArrowIndex(arrow)).thenReturn(0);
		assertTrue(arrow.isInverted());
	}

	@Test
	public void testIsInvertedKOOnLeft() {
		arrow.setArrowStyle(ArrowStyle.LEFT_ROUND_BRACKET);
		when(arrowable.getArrowIndex(arrow)).thenReturn(0);
		assertFalse(arrow.isInverted());
	}

	@Test
	public void testHasStyleOK() {
		arrow.setArrowStyle(ArrowStyle.RIGHT_ARROW);
		assertTrue(arrow.hasStyle());
	}

	@Test
	public void testHasStyleKO() {
		arrow.setArrowStyle(ArrowStyle.NONE);
		assertFalse(arrow.hasStyle());
	}
}
