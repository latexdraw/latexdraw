package net.sf.latexdraw.models.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.IArrowableSingleShape;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TestLGroupArrowable {
	IGroup group;
	IArrowableSingleShape arr1;
	IArrowableSingleShape arr2;

	@Before
	public void setUp() {
		group = ShapeFactory.INST.createGroup();
		arr1 = ShapeFactory.INST.createPolyline(Arrays.asList(ShapeFactory.INST.createPoint(10, 20),
			ShapeFactory.INST.createPoint(30, 40)));
		arr2 = ShapeFactory.INST.createPolyline(Arrays.asList(ShapeFactory.INST.createPoint(-10, -20),
			ShapeFactory.INST.createPoint(50, 20)));
		group.addShape(ShapeFactory.INST.createRectangle());
		group.addShape(arr1);
		group.addShape(ShapeFactory.INST.createCircle());
		group.addShape(arr2);
	}

	@Test
	public void testGetArrowIndexKONull() {
		assertEquals(-1, group.getArrowIndex(null));
	}

	@Test
	public void testGetArrowIndexKO() {
		assertEquals(-1, group.getArrowIndex(ShapeFactory.INST.createPolyline(Arrays.asList(ShapeFactory.INST.createPoint(10, 20),
			ShapeFactory.INST.createPoint(30, 40))).getArrowAt(0)));
	}

	@Test
	public void testGetArrowIndexKOOnlyFirstShape() {
		assertEquals(-1, group.getArrowIndex(arr2.getArrowAt(1)));
	}

	@Test
	public void testGetArrowIndexKOEmpty() {
		group.clear();
		assertEquals(-1, group.getArrowIndex(arr1.getArrowAt(0)));
	}

	@Test
	public void testGetArrowIndexOK() {
		assertEquals(0, group.getArrowIndex(arr1.getArrowAt(0)));
	}

	@Test
	public void testGetNbArrowsOK() {
		assertEquals(2, group.getNbArrows());
	}

	@Test
	public void testGetNbArrowsEmpty() {
		group.clear();
		assertEquals(0, group.getNbArrows());
	}

	@Test
	public void testSetTBarSizeDimListKO() {
		final List<Optional<Double>> vals = Arrays.asList(Optional.of(arr1.getTBarSizeDim()), Optional.of(arr2.getTBarSizeDim()));
		group.setTBarSizeDimList(null);
		assertEquals(vals, Arrays.asList(Optional.of(arr1.getTBarSizeDim()), Optional.of(arr2.getTBarSizeDim())));
	}

	@Test
	public void testSetTBarSizeDimListKONotSameSize() {
		final List<Optional<Double>> vals = Arrays.asList(Optional.of(arr1.getTBarSizeDim()), Optional.of(arr2.getTBarSizeDim()));
		group.setTBarSizeDimList(Arrays.asList(Optional.of(34d), Optional.of(22d)));
		assertEquals(vals, Arrays.asList(Optional.of(arr1.getTBarSizeDim()), Optional.of(arr2.getTBarSizeDim())));
	}

	@Test
	public void testSetTBarSizeDimListOK() {
		group.setTBarSizeDimList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(22d)));
		assertEquals(Arrays.asList(Optional.of(34d), Optional.of(22d)), Arrays.asList(Optional.of(arr1.getTBarSizeDim()), Optional.of(arr2.getTBarSizeDim())));
	}

	@Test
	public void testGetTBarSizeDimList() {
		assertEquals(Arrays.asList(Optional.empty(), Optional.of(arr1.getTBarSizeDim()), Optional.empty(),
			Optional.of(arr2.getTBarSizeDim())), group.getTBarSizeDimList());
	}

	@Test
	public void testSetTBarSizeNumListKO() {
		final List<Optional<Double>> vals = Arrays.asList(Optional.of(arr1.getTBarSizeNum()), Optional.of(arr2.getTBarSizeNum()));
		group.setTBarSizeNumList(null);
		assertEquals(vals, Arrays.asList(Optional.of(arr1.getTBarSizeNum()), Optional.of(arr2.getTBarSizeNum())));
	}

	@Test
	public void testSetTBarSizeNumListKONotSameSize() {
		final List<Optional<Double>> vals = Arrays.asList(Optional.of(arr1.getTBarSizeNum()), Optional.of(arr2.getTBarSizeNum()));
		group.setTBarSizeNumList(Arrays.asList(Optional.of(34d), Optional.of(22d)));
		assertEquals(vals, Arrays.asList(Optional.of(arr1.getTBarSizeNum()), Optional.of(arr2.getTBarSizeNum())));
	}

	@Test
	public void testSetTBarSizeNumListOK() {
		group.setTBarSizeNumList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(22d)));
		assertEquals(Arrays.asList(Optional.of(34d), Optional.of(22d)), Arrays.asList(Optional.of(arr1.getTBarSizeNum()), Optional.of(arr2.getTBarSizeNum())));
	}

	@Test
	public void testGetTBarSizeNumList() {
		assertEquals(Arrays.asList(Optional.empty(), Optional.of(arr1.getTBarSizeNum()), Optional.empty(),
			Optional.of(arr2.getTBarSizeNum())), group.getTBarSizeNumList());
	}

	@Test
	public void testSetDotSizeNumListKO() {
		final List<Optional<Double>> vals = Arrays.asList(Optional.of(arr1.getDotSizeNum()), Optional.of(arr2.getDotSizeNum()));
		group.setDotSizeNumList(null);
		assertEquals(vals, Arrays.asList(Optional.of(arr1.getDotSizeNum()), Optional.of(arr2.getDotSizeNum())));
	}

	@Test
	public void testSetDotSizeNumListKONotSameSize() {
		final List<Optional<Double>> vals = Arrays.asList(Optional.of(arr1.getDotSizeNum()), Optional.of(arr2.getDotSizeNum()));
		group.setDotSizeNumList(Arrays.asList(Optional.of(34d), Optional.of(22d)));
		assertEquals(vals, Arrays.asList(Optional.of(arr1.getDotSizeNum()), Optional.of(arr2.getDotSizeNum())));
	}

	@Test
	public void testSetDotSizeNumListOK() {
		group.setDotSizeNumList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(22d)));
		assertEquals(Arrays.asList(Optional.of(34d), Optional.of(22d)), Arrays.asList(Optional.of(arr1.getDotSizeNum()), Optional.of(arr2.getDotSizeNum())));
	}

	@Test
	public void testGetDotSizeNumList() {
		assertEquals(Arrays.asList(Optional.empty(), Optional.of(arr1.getDotSizeNum()), Optional.empty(), Optional.of(arr2.getDotSizeNum())),
			group.getDotSizeNumList());
	}

	@Test
	public void testSetDotSizeDimListKO() {
		final List<Optional<Double>> vals = Arrays.asList(Optional.of(arr1.getDotSizeDim()), Optional.of(arr2.getDotSizeDim()));
		group.setDotSizeDimList(null);
		assertEquals(vals, Arrays.asList(Optional.of(arr1.getDotSizeDim()), Optional.of(arr2.getDotSizeDim())));
	}

	@Test
	public void testSetDotSizeDimListKONotSameSize() {
		final List<Optional<Double>> vals = Arrays.asList(Optional.of(arr1.getDotSizeDim()), Optional.of(arr2.getDotSizeDim()));
		group.setDotSizeDimList(Arrays.asList(Optional.of(34d), Optional.of(22d)));
		assertEquals(vals, Arrays.asList(Optional.of(arr1.getDotSizeDim()), Optional.of(arr2.getDotSizeDim())));
	}

	@Test
	public void testSetDotSizeDimListOK() {
		group.setDotSizeDimList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(22d)));
		assertEquals(Arrays.asList(Optional.of(34d), Optional.of(22d)), Arrays.asList(Optional.of(arr1.getDotSizeDim()), Optional.of(arr2.getDotSizeDim())));
	}

	@Test
	public void testGetDotSizeDimList() {
		assertEquals(Arrays.asList(Optional.empty(), Optional.of(arr1.getDotSizeDim()), Optional.empty(), Optional.of(arr2.getDotSizeDim())),
			group.getDotSizeDimList());
	}

	@Test
	public void testSetBracketNumListKO() {
		final List<Optional<Double>> vals = Arrays.asList(Optional.of(arr1.getBracketNum()), Optional.of(arr2.getBracketNum()));
		group.setBracketNumList(null);
		assertEquals(vals, Arrays.asList(Optional.of(arr1.getBracketNum()), Optional.of(arr2.getBracketNum())));
	}

	@Test
	public void testSetBracketNumListKONotSameSize() {
		final List<Optional<Double>> vals = Arrays.asList(Optional.of(arr1.getBracketNum()), Optional.of(arr2.getBracketNum()));
		group.setBracketNumList(Arrays.asList(Optional.of(34d), Optional.of(22d)));
		assertEquals(vals, Arrays.asList(Optional.of(arr1.getBracketNum()), Optional.of(arr2.getBracketNum())));
	}

	@Test
	public void testSetBracketNumListOK() {
		group.setBracketNumList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(22d)));
		assertEquals(Arrays.asList(Optional.of(34d), Optional.of(22d)), Arrays.asList(Optional.of(arr1.getBracketNum()), Optional.of(arr2.getBracketNum())));
	}

	@Test
	public void testGetBracketNumList() {
		assertEquals(Arrays.asList(Optional.empty(), Optional.of(arr1.getBracketNum()), Optional.empty(), Optional.of(arr2.getBracketNum())),
			group.getBracketNumList());
	}

	@Test
	public void testSetRBracketNumListKO() {
		final List<Optional<Double>> vals = Arrays.asList(Optional.of(arr1.getRBracketNum()), Optional.of(arr2.getRBracketNum()));
		group.setRBracketNumList(null);
		assertEquals(vals, Arrays.asList(Optional.of(arr1.getRBracketNum()), Optional.of(arr2.getRBracketNum())));
	}

	@Test
	public void testSetRBracketNumListKONotSameSize() {
		final List<Optional<Double>> vals = Arrays.asList(Optional.of(arr1.getRBracketNum()), Optional.of(arr2.getRBracketNum()));
		group.setRBracketNumList(Arrays.asList(Optional.of(34d), Optional.of(22d)));
		assertEquals(vals, Arrays.asList(Optional.of(arr1.getRBracketNum()), Optional.of(arr2.getRBracketNum())));
	}

	@Test
	public void testSetRBracketNumListOK() {
		group.setRBracketNumList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(22d)));
		assertEquals(Arrays.asList(Optional.of(34d), Optional.of(22d)), Arrays.asList(Optional.of(arr1.getRBracketNum()), Optional.of(arr2.getRBracketNum())));
	}

	@Test
	public void testGetRBracketNumList() {
		assertEquals(Arrays.asList(Optional.empty(), Optional.of(arr1.getRBracketNum()), Optional.empty(), Optional.of(arr2.getRBracketNum())),
			group.getRBracketNumList());
	}

	@Test
	public void testSetArrowSizeNumListKO() {
		final List<Optional<Double>> vals = Arrays.asList(Optional.of(arr1.getArrowSizeNum()), Optional.of(arr2.getArrowSizeNum()));
		group.setArrowSizeNumList(null);
		assertEquals(vals, Arrays.asList(Optional.of(arr1.getArrowSizeNum()), Optional.of(arr2.getArrowSizeNum())));
	}

	@Test
	public void testSetArrowSizeNumListKONotSameSize() {
		final List<Optional<Double>> vals = Arrays.asList(Optional.of(arr1.getArrowSizeNum()), Optional.of(arr2.getArrowSizeNum()));
		group.setArrowSizeNumList(Arrays.asList(Optional.of(34d), Optional.of(22d)));
		assertEquals(vals, Arrays.asList(Optional.of(arr1.getArrowSizeNum()), Optional.of(arr2.getArrowSizeNum())));
	}

	@Test
	public void testSetArrowSizeNumListOK() {
		group.setArrowSizeNumList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(22d)));
		assertEquals(Arrays.asList(Optional.of(34d), Optional.of(22d)), Arrays.asList(Optional.of(arr1.getArrowSizeNum()), Optional.of(arr2.getArrowSizeNum())));
	}

	@Test
	public void testGetArrowSizeNumList() {
		assertEquals(Arrays.asList(Optional.empty(), Optional.of(arr1.getArrowSizeNum()), Optional.empty(), Optional.of(arr2.getArrowSizeNum())),
			group.getArrowSizeNumList());
	}

	@Test
	public void testSetArrowSizeDimListKO() {
		final List<Optional<Double>> vals = Arrays.asList(Optional.of(arr1.getArrowSizeDim()), Optional.of(arr2.getArrowSizeDim()));
		group.setArrowSizeDimList(null);
		assertEquals(vals, Arrays.asList(Optional.of(arr1.getArrowSizeDim()), Optional.of(arr2.getArrowSizeDim())));
	}

	@Test
	public void testSetArrowSizeDimListKONotSameSize() {
		final List<Optional<Double>> vals = Arrays.asList(Optional.of(arr1.getArrowSizeDim()), Optional.of(arr2.getArrowSizeDim()));
		group.setArrowSizeDimList(Arrays.asList(Optional.of(34d), Optional.of(22d)));
		assertEquals(vals, Arrays.asList(Optional.of(arr1.getArrowSizeDim()), Optional.of(arr2.getArrowSizeDim())));
	}

	@Test
	public void testSetArrowSizeDimListOK() {
		group.setArrowSizeDimList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(22d)));
		assertEquals(Arrays.asList(Optional.of(34d), Optional.of(22d)), Arrays.asList(Optional.of(arr1.getArrowSizeDim()), Optional.of(arr2.getArrowSizeDim())));
	}

	@Test
	public void testGetArrowSizeDimList() {
		assertEquals(Arrays.asList(Optional.empty(), Optional.of(arr1.getArrowSizeDim()), Optional.empty(), Optional.of(arr2.getArrowSizeDim())),
			group.getArrowSizeDimList());
	}

	@Test
	public void testSetArrowLengthListKO() {
		final List<Optional<Double>> vals = Arrays.asList(Optional.of(arr1.getArrowLength()), Optional.of(arr2.getArrowLength()));
		group.setArrowLengthList(null);
		assertEquals(vals, Arrays.asList(Optional.of(arr1.getArrowLength()), Optional.of(arr2.getArrowLength())));
	}

	@Test
	public void testSetArrowLengthListKONotSameSize() {
		final List<Optional<Double>> vals = Arrays.asList(Optional.of(arr1.getArrowLength()), Optional.of(arr2.getArrowLength()));
		group.setArrowLengthList(Arrays.asList(Optional.of(34d), Optional.of(22d)));
		assertEquals(vals, Arrays.asList(Optional.of(arr1.getArrowLength()), Optional.of(arr2.getArrowLength())));
	}

	@Test
	public void testSetArrowLengthListOK() {
		group.setArrowLengthList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(22d)));
		assertEquals(Arrays.asList(Optional.of(34d), Optional.of(22d)), Arrays.asList(Optional.of(arr1.getArrowLength()), Optional.of(arr2.getArrowLength())));
	}

	@Test
	public void testGetArrowLengthList() {
		assertEquals(Arrays.asList(Optional.empty(), Optional.of(arr1.getArrowLength()), Optional.empty(), Optional.of(arr2.getArrowLength())),
			group.getArrowLengthList());
	}

	@Test
	public void testSetArrowInsetListKO() {
		final List<Optional<Double>> vals = Arrays.asList(Optional.of(arr1.getArrowInset()), Optional.of(arr2.getArrowInset()));
		group.setArrowInsetList(null);
		assertEquals(vals, Arrays.asList(Optional.of(arr1.getArrowInset()), Optional.of(arr2.getArrowInset())));
	}

	@Test
	public void testSetArrowInsetListKONotSameSize() {
		final List<Optional<Double>> vals = Arrays.asList(Optional.of(arr1.getArrowInset()), Optional.of(arr2.getArrowInset()));
		group.setArrowInsetList(Arrays.asList(Optional.of(34d), Optional.of(22d)));
		assertEquals(vals, Arrays.asList(Optional.of(arr1.getArrowInset()), Optional.of(arr2.getArrowInset())));
	}

	@Test
	public void testSetArrowInsetListOK() {
		group.setArrowInsetList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(22d)));
		assertEquals(Arrays.asList(Optional.of(34d), Optional.of(22d)), Arrays.asList(Optional.of(arr1.getArrowInset()), Optional.of(arr2.getArrowInset())));
	}

	@Test
	public void testGetArrowInsetList() {
		assertEquals(Arrays.asList(Optional.empty(), Optional.of(arr1.getArrowInset()), Optional.empty(), Optional.of(arr2.getArrowInset())),
			group.getArrowInsetList());
	}

	@Test
	public void testSetArrowStyleListKO() {
		final List<ArrowStyle> vals = Arrays.asList(arr1.getArrowStyle(0), arr2.getArrowStyle(1));
		group.setArrowInsetList(null);
		assertEquals(vals, Arrays.asList(arr1.getArrowStyle(0), arr2.getArrowStyle(1)));
	}

	@Test
	public void testSetArrowStyleListKONotSameSize() {
		final List<ArrowStyle> vals = Arrays.asList(arr1.getArrowStyle(0), arr2.getArrowStyle(0));
		group.setArrowStyleList(Arrays.asList(Optional.of(ArrowStyle.LEFT_ARROW), Optional.of(ArrowStyle.RIGHT_ARROW)), 0);
		assertEquals(vals, Arrays.asList(arr1.getArrowStyle(0), arr2.getArrowStyle(0)));
	}

	@Test
	public void testSetArrowStyleListOK() {
		group.setArrowStyleList(Arrays.asList(Optional.empty(), Optional.of(ArrowStyle.LEFT_ARROW), Optional.empty(), Optional.of(ArrowStyle.RIGHT_ARROW)), 0);
		assertEquals(Arrays.asList(Optional.of(ArrowStyle.LEFT_ARROW), Optional.of(ArrowStyle.RIGHT_ARROW)),
			Arrays.asList(Optional.of(arr1.getArrowStyle(0)), Optional.of(arr2.getArrowStyle(0))));
	}

	@Test
	public void testSetArrowStyleListKOIndex() {
		final List<ArrowStyle> vals = Arrays.asList(arr1.getArrowStyle(0), arr2.getArrowStyle(0));
		group.setArrowStyleList(Arrays.asList(Optional.empty(), Optional.of(ArrowStyle.LEFT_ARROW), Optional.empty(), Optional.of(ArrowStyle.RIGHT_ARROW)), 5);
		assertEquals(vals, Arrays.asList(arr1.getArrowStyle(0), arr2.getArrowStyle(0)));
	}

	@Test
	public void testGetArrowStyleList() {
		group.setArrowStyleList(Arrays.asList(Optional.empty(), Optional.of(ArrowStyle.LEFT_ARROW), Optional.empty(), Optional.of(ArrowStyle.RIGHT_ARROW)), 0);
		assertEquals(Arrays.asList(Optional.empty(), Optional.of(ArrowStyle.LEFT_ARROW), Optional.empty(), Optional.of(ArrowStyle.RIGHT_ARROW)), group.getArrowStyleList(0));
	}

	@Test
	public void testGetArrowAtOK() {
		assertEquals(arr1.getArrowAt(0), group.getArrowAt(0));
		assertEquals(arr1.getArrowAt(1), group.getArrowAt(1));
	}

	@Test
	public void testGetArrowAtKOEmpty() {
		group.clear();
		assertNull(group.getArrowAt(0));
	}

	@Test
	public void testSetDotSizeDim() {
		group.setDotSizeDimList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(11d)));
		group.setDotSizeDim(22.2);
		assertEquals(Arrays.asList(Optional.of(22.2), Optional.of(22.2)), Arrays.asList(Optional.of(arr1.getDotSizeDim()), Optional.of(arr2.getDotSizeDim())));
	}

	@Test
	public void testGetDotSizeDim() {
		group.setDotSizeDimList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(11d)));
		assertEquals(34d, group.getDotSizeDim(), 0.0001);
	}

	@Test
	public void testGetDotSizeDimEmpty() {
		group.clear();
		assertTrue(Double.isNaN(group.getDotSizeDim()));
	}

	@Test
	public void testSetDotSizeNum() {
		group.setDotSizeNumList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(11d)));
		group.setDotSizeNum(22.2);
		assertEquals(Arrays.asList(Optional.of(22.2), Optional.of(22.2)), Arrays.asList(Optional.of(arr1.getDotSizeNum()), Optional.of(arr2.getDotSizeNum())));
	}

	@Test
	public void testGetDotSizeNum() {
		group.setDotSizeNumList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(11d)));
		assertEquals(34d, group.getDotSizeNum(), 0.0001);
	}

	@Test
	public void testGetDotSizeNumEmpty() {
		group.clear();
		assertTrue(Double.isNaN(group.getDotSizeNum()));
	}

	@Test
	public void testSetTBarSizeNum() {
		group.setTBarSizeNumList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(11d)));
		group.setTBarSizeNum(22.2);
		assertEquals(Arrays.asList(Optional.of(22.2), Optional.of(22.2)), Arrays.asList(Optional.of(arr1.getTBarSizeNum()), Optional.of(arr2.getTBarSizeNum())));
	}

	@Test
	public void testGetTBarSizeNum() {
		group.setTBarSizeNumList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(11d)));
		assertEquals(34d, group.getTBarSizeNum(), 0.0001);
	}

	@Test
	public void testGetTBarSizeNumEmpty() {
		group.clear();
		assertTrue(Double.isNaN(group.getTBarSizeNum()));
	}

	@Test
	public void testSetTBarSizeDim() {
		group.setTBarSizeDimList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(11d)));
		group.setTBarSizeDim(22.2);
		assertEquals(Arrays.asList(Optional.of(22.2), Optional.of(22.2)), Arrays.asList(Optional.of(arr1.getTBarSizeDim()), Optional.of(arr2.getTBarSizeDim())));
	}

	@Test
	public void testGetTBarSizeDim() {
		group.setTBarSizeDimList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(11d)));
		assertEquals(34d, group.getTBarSizeDim(), 0.0001);
	}

	@Test
	public void testGetTBarSizeDimEmpty() {
		group.clear();
		assertTrue(Double.isNaN(group.getTBarSizeDim()));
	}

	@Test
	public void testSetRBracketNum() {
		group.setRBracketNumList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(11d)));
		group.setRBracketNum(22.2);
		assertEquals(Arrays.asList(Optional.of(22.2), Optional.of(22.2)), Arrays.asList(Optional.of(arr1.getRBracketNum()), Optional.of(arr2.getRBracketNum())));
	}

	@Test
	public void testGetRBracketNum() {
		group.setRBracketNumList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(11d)));
		assertEquals(34d, group.getRBracketNum(), 0.0001);
	}

	@Test
	public void testGetRBracketNumEmpty() {
		group.clear();
		assertTrue(Double.isNaN(group.getRBracketNum()));
	}

	@Test
	public void testSetBracketNum() {
		group.setBracketNumList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(11d)));
		group.setBracketNum(22.2);
		assertEquals(Arrays.asList(Optional.of(22.2), Optional.of(22.2)), Arrays.asList(Optional.of(arr1.getBracketNum()), Optional.of(arr2.getBracketNum())));
	}

	@Test
	public void testGetBracketNum() {
		group.setBracketNumList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(11d)));
		assertEquals(34d, group.getBracketNum(), 0.0001);
	}

	@Test
	public void testGetBracketNumEmpty() {
		group.clear();
		assertTrue(Double.isNaN(group.getBracketNum()));
	}

	@Test
	public void testSetArrowLength() {
		group.setArrowLengthList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(11d)));
		group.setArrowLength(22.2);
		assertEquals(Arrays.asList(Optional.of(22.2), Optional.of(22.2)), Arrays.asList(Optional.of(arr1.getArrowLength()), Optional.of(arr2.getArrowLength())));
	}

	@Test
	public void testGetArrowLength() {
		group.setArrowLengthList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(11d)));
		assertEquals(34d, group.getArrowLength(), 0.0001);
	}

	@Test
	public void testSetArrowLengthEmpty() {
		group.clear();
		assertTrue(Double.isNaN(group.getArrowLength()));
	}

	@Test
	public void testSetArrowSizeDim() {
		group.setArrowSizeDimList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(11d)));
		group.setArrowSizeDim(22.2);
		assertEquals(Arrays.asList(Optional.of(22.2), Optional.of(22.2)), Arrays.asList(Optional.of(arr1.getArrowSizeDim()), Optional.of(arr2.getArrowSizeDim())));
	}

	@Test
	public void testGetArrowSizeDim() {
		group.setArrowSizeDimList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(11d)));
		assertEquals(34d, group.getArrowSizeDim(), 0.0001);
	}

	@Test
	public void testSetArrowSizeDimEmpty() {
		group.clear();
		assertTrue(Double.isNaN(group.getArrowSizeDim()));
	}

	@Test
	public void testSetArrowSizeNum() {
		group.setArrowSizeNumList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(11d)));
		group.setArrowSizeNum(22.2);
		assertEquals(Arrays.asList(Optional.of(22.2), Optional.of(22.2)), Arrays.asList(Optional.of(arr1.getArrowSizeNum()), Optional.of(arr2.getArrowSizeNum())));
	}

	@Test
	public void testGetArrowSizeNum() {
		group.setArrowSizeNumList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(11d)));
		assertEquals(34d, group.getArrowSizeNum(), 0.0001);
	}

	@Test
	public void testSetArrowSizeNumEmpty() {
		group.clear();
		assertTrue(Double.isNaN(group.getArrowSizeNum()));
	}

	@Test
	public void testSetArrowInset() {
		group.setArrowInsetList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(11d)));
		group.setArrowInset(22.2);
		assertEquals(Arrays.asList(Optional.of(22.2), Optional.of(22.2)), Arrays.asList(Optional.of(arr1.getArrowInset()), Optional.of(arr2.getArrowInset())));
	}

	@Test
	public void testGetArrowInset() {
		group.setArrowInsetList(Arrays.asList(Optional.empty(), Optional.of(34d), Optional.empty(), Optional.of(11d)));
		assertEquals(34d, group.getArrowInset(), 0.0001);
	}

	@Test
	public void testSetArrowInsetEmpty() {
		group.clear();
		assertTrue(Double.isNaN(group.getArrowInset()));
	}
}
