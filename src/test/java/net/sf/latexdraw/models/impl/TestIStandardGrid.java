package net.sf.latexdraw.models.impl;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.data.DoubleData;
import net.sf.latexdraw.data.StdGridData;
import net.sf.latexdraw.models.interfaces.shape.IStandardGrid;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeThat;

@RunWith(Theories.class)
public class TestIStandardGrid implements HelperTest {
	@Theory
	public void testGetSetLabelsSize(@StdGridData IStandardGrid shape, @TestedOn(ints = {0, 1, 10}) final int value) {
		shape.setLabelsSize(value);
		assertEquals(value, shape.getLabelsSize());
	}

	@Theory
	public void testGetSetLabelsSizeKO(@StdGridData IStandardGrid shape) {
		shape.setLabelsSize(11);
		shape.setLabelsSize(-1);
		assertEquals(11, shape.getLabelsSize());
	}

	@Theory
	public void testGetSetGridEndX(@StdGridData IStandardGrid shape, @DoubleData final double value) {
		assumeThat(value, greaterThan(-10d));

		shape.setGridStartX(-10d);
		shape.setGridEndX(value);
		assertEqualsDouble(value, shape.getGridEndX());
	}

	@Theory
	public void testGetSetGridEndXKO(@StdGridData IStandardGrid shape, @DoubleData(bads = true, vals = {-20d}) final double value) {
		shape.setGridStartX(-10d);
		shape.setGridEndX(10d);
		shape.setGridEndX(value);
		assertEqualsDouble(10d, shape.getGridEndX());
	}

	@Theory
	public void testGetSetGridEndY(@StdGridData IStandardGrid shape, @DoubleData final double value) {
		assumeThat(value, greaterThan(-10d));

		shape.setGridStartY(-10d);
		shape.setGridEndY(value);
		assertEqualsDouble(value, shape.getGridEndY());
	}

	@Theory
	public void testGetSetGridEndYKO(@StdGridData IStandardGrid shape, @DoubleData(bads = true, vals = {-20d}) final double value) {
		shape.setGridStartY(-10d);
		shape.setGridEndY(10d);
		shape.setGridEndY(value);
		assertEqualsDouble(10d, shape.getGridEndY());
	}

	@Theory
	public void testGetSetGridStartY(@StdGridData IStandardGrid shape, @DoubleData final double value) {
		assumeThat(value, lessThan(10d));

		shape.setGridEndY(10d);
		shape.setGridStartY(value);
		assertEqualsDouble(value, shape.getGridStartY());
	}

	@Theory
	public void testGetSetGridStartYKO(@StdGridData IStandardGrid shape, @DoubleData(bads = true, vals = {20d}) final double value) {
		shape.setGridEndY(10d);
		shape.setGridStartY(-10d);
		shape.setGridStartY(value);
		assertEqualsDouble(-10d, shape.getGridStartY());
	}

	@Theory
	public void testGetSetGridStartX(@StdGridData IStandardGrid shape, @DoubleData final double value) {
		assumeThat(value, lessThan(10d));

		shape.setGridEndX(10d);
		shape.setGridStartX(value);
		assertEqualsDouble(value, shape.getGridStartX());
	}

	@Theory
	public void testGetSetGridStartXKO(@StdGridData IStandardGrid shape, @DoubleData(bads = true, vals = {20d}) final double value) {
		shape.setGridEndX(10d);
		shape.setGridStartX(-10d);
		shape.setGridStartX(value);
		assertEqualsDouble(-10d, shape.getGridStartX());
	}

	@Theory
	public void testSetGridStart(@StdGridData IStandardGrid shape, @DoubleData final double x, @DoubleData final double y) {
		assumeThat(x, lessThan(20d));
		assumeThat(y, lessThan(20d));

		shape.setGridEnd(20, 20);
		shape.setGridStart(x, y);
		assertEqualsDouble(x, shape.getGridStartX());
		assertEqualsDouble(y, shape.getGridStartY());
	}

	@Theory
	public void testSetGridEnd(@StdGridData IStandardGrid shape, @DoubleData final double x, @DoubleData final double y) {
		assumeThat(x, greaterThan(-20d));
		assumeThat(y, greaterThan(-20d));

		shape.setGridStart(-20, -20);
		shape.setGridEnd(x, y);
		assertEqualsDouble(x, shape.getGridEndX());
		assertEqualsDouble(y, shape.getGridEndY());
	}

	@Theory
	public void testSetOrigin(@StdGridData IStandardGrid shape, @DoubleData final double x, @DoubleData final double y) {
		shape.setOrigin(x, y);
		assertEqualsDouble(x, shape.getOriginX());
		assertEqualsDouble(y, shape.getOriginY());
	}

	@Theory
	public void testSetOriginKO(@StdGridData IStandardGrid shape, @DoubleData(vals = {}, bads = true) final double x,
								@DoubleData(vals ={}, bads = true) final double y) {
		shape.setOrigin(11d, 13d);
		shape.setOrigin(x, y);
		assertEqualsDouble(11d, shape.getOriginX());
		assertEqualsDouble(13d, shape.getOriginY());
	}

	@Theory
	public void testGetSetOriginX(@StdGridData IStandardGrid shape, @DoubleData final double value) {
		shape.setOriginX(value);
		assertEqualsDouble(value, shape.getOriginX());
	}

	@Theory
	public void testGetSetOriginXKO(@StdGridData IStandardGrid shape, @DoubleData(bads = true, vals = {}) final double value) {
		shape.setOriginX(-10d);
		shape.setOriginX(value);
		assertEqualsDouble(-10d, shape.getOriginX());
	}

	@Theory
	public void testGetSetOriginY(@StdGridData IStandardGrid shape, @DoubleData final double value) {
		shape.setOriginY(value);
		assertEqualsDouble(value, shape.getOriginY());
	}

	@Theory
	public void testGetSetOriginYKO(@StdGridData IStandardGrid shape, @DoubleData(bads = true, vals = {}) final double value) {
		shape.setOriginY(-10d);
		shape.setOriginY(value);
		assertEqualsDouble(-10d, shape.getOriginY());
	}

	@Theory
	public void testDuplicate(@StdGridData IStandardGrid shape) {
		shape.setOrigin(20, 30);
		shape.setGridStart(-100, -40);
		shape.setGridEnd(200, 300);
		shape.setLabelsSize(12);

		final IStandardGrid dup = shape.duplicate();

		assertEqualsDouble(20., dup.getOriginX());
		assertEqualsDouble(30., dup.getOriginY());
		assertEqualsDouble(-100., dup.getGridStartX());
		assertEqualsDouble(-40., dup.getGridStartY());
		assertEqualsDouble(200., dup.getGridEndX());
		assertEqualsDouble(300., dup.getGridEndY());
		assertEquals(12, dup.getLabelsSize());
	}

	@Theory
	public void testCopy(@StdGridData IStandardGrid shape, @StdGridData IStandardGrid shape2) {
		shape2.setOrigin(20, 30);
		shape2.setGridEnd(100, -40);
		shape2.setGridStart(200, 300);
		shape2.setLabelsSize(20);

		shape.copy(shape2);

		assertEqualsDouble(shape2.getOriginX(), shape.getOriginX());
		assertEqualsDouble(shape2.getOriginY(), shape.getOriginY());
		assertEqualsDouble(shape2.getGridEndX(), shape.getGridEndX());
		assertEqualsDouble(shape2.getGridEndY(), shape.getGridEndY());
		assertEqualsDouble(shape2.getGridStartX(), shape.getGridStartX());
		assertEqualsDouble(shape2.getGridStartY(), shape.getGridStartY());
		assertEquals(shape2.getLabelsSize(), shape.getLabelsSize());
	}
}
