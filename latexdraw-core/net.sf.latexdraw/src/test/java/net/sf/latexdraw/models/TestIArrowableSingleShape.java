package net.sf.latexdraw.models;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.data.ArrowableData;
import net.sf.latexdraw.data.DoubleData;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.IArrowableSingleShape;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeThat;

@RunWith(Theories.class)
public class TestIArrowableSingleShape implements HelperTest {
	@Theory
	public void testCopyArrowableLines(@ArrowableData final IArrowableSingleShape s1, @ArrowableData final IArrowableSingleShape s2) {
		s2.setArrowStyle(ArrowStyle.BAR_IN, 0);
		s2.setArrowStyle(ArrowStyle.LEFT_ARROW, 1);
		s1.copy(s2);
		assertEquals(ArrowStyle.BAR_IN, s1.getArrowStyle(0));
		assertEquals(ArrowStyle.LEFT_ARROW, s1.getArrowStyle(1));
	}

	@Theory
	public void setTBarSizeDim(@ArrowableData final IArrowableSingleShape sh, @DoubleData final double value) {
		assumeThat(value, greaterThan(0d));

		sh.setTBarSizeDim(value);
		assertEqualsDouble(value, sh.getTBarSizeDim());
	}

	@Theory
	public void setTBarSizeNum(@ArrowableData final IArrowableSingleShape sh, @DoubleData final double value) {
		assumeThat(value, greaterThanOrEqualTo(0d));

		sh.setTBarSizeNum(value);
		assertEqualsDouble(value, sh.getTBarSizeNum());
	}

	@Theory
	public void setDotSizeDim(@ArrowableData final IArrowableSingleShape sh, @DoubleData final double value) {
		assumeThat(value, greaterThan(0d));

		sh.setDotSizeDim(value);
		assertEqualsDouble(value, sh.getDotSizeDim());
	}

	@Theory
	public void setDotSizeNum(@ArrowableData final IArrowableSingleShape sh, @DoubleData final double value) {
		assumeThat(value, greaterThanOrEqualTo(0.1));

		sh.setDotSizeNum(value);
		assertEqualsDouble(value, sh.getDotSizeNum());
	}

	@Theory
	public void setBracketNum(@ArrowableData final IArrowableSingleShape sh, @DoubleData final double value) {
		assumeThat(value, greaterThanOrEqualTo(0d));

		sh.setBracketNum(value);
		assertEqualsDouble(value, sh.getBracketNum());
	}

	@Theory
	public void setArrowSizeNum(@ArrowableData final IArrowableSingleShape sh, @DoubleData final double value) {
		assumeThat(value, greaterThanOrEqualTo(0d));

		sh.setArrowSizeNum(value);
		assertEqualsDouble(value, sh.getArrowSizeNum());
	}

	@Theory
	public void setArrowSizeDim(@ArrowableData final IArrowableSingleShape sh, @DoubleData final double value) {
		assumeThat(value, greaterThan(0d));

		sh.setArrowSizeDim(value);
		assertEqualsDouble(value, sh.getArrowSizeDim());
	}

	@Theory
	public void setArrowInset(@ArrowableData final IArrowableSingleShape sh, @DoubleData final double value) {
		assumeThat(value, greaterThanOrEqualTo(0d));

		sh.setArrowInset(value);
		assertEqualsDouble(value, sh.getArrowInset());
	}

	@Theory
	public void setArrowLength(@ArrowableData final IArrowableSingleShape sh, @DoubleData final double value) {
		assumeThat(value, greaterThanOrEqualTo(0d));

		sh.setArrowLength(value);
		assertEqualsDouble(value, sh.getArrowLength());
	}

	@Theory
	public void setRBracketNum(@ArrowableData final IArrowableSingleShape sh, @DoubleData final double value) {
		assumeThat(value, greaterThanOrEqualTo(0d));

		sh.setRBracketNum(value);
		assertEqualsDouble(value, sh.getRBracketNum());
	}
}
