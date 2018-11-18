package net.sf.latexdraw.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.paint.Color;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.ArcStyle;
import net.sf.latexdraw.model.api.shape.ArrowStyle;
import net.sf.latexdraw.model.api.shape.AxesStyle;
import net.sf.latexdraw.model.api.shape.BorderPos;
import net.sf.latexdraw.model.api.shape.Closable;
import net.sf.latexdraw.model.api.shape.DotStyle;
import net.sf.latexdraw.model.api.shape.FillingStyle;
import net.sf.latexdraw.model.api.shape.Arc;
import net.sf.latexdraw.model.api.shape.ArrowableSingleShape;
import net.sf.latexdraw.model.api.shape.Axes;
import net.sf.latexdraw.model.api.shape.Dot;
import net.sf.latexdraw.model.api.shape.FreeHandStyle;
import net.sf.latexdraw.model.api.shape.Freehand;
import net.sf.latexdraw.model.api.shape.Grid;
import net.sf.latexdraw.model.api.shape.Picture;
import net.sf.latexdraw.model.api.shape.Plot;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.model.api.shape.Square;
import net.sf.latexdraw.model.api.shape.Text;
import net.sf.latexdraw.model.api.shape.LineStyle;
import net.sf.latexdraw.model.api.shape.PlotStyle;
import net.sf.latexdraw.model.api.shape.PlottingStyle;
import net.sf.latexdraw.model.api.shape.TextPosition;
import net.sf.latexdraw.model.api.shape.TicksStyle;
import org.junit.rules.TemporaryFolder;

public final class ParameteriseShapeData implements HelperTest {
	public static ParameteriseShapeData INST = new ParameteriseShapeData();
	private final Set<TemporaryFolder> tempFolders = new HashSet<>();

	private ParameteriseShapeData() {
		super();
	}

	public Path getTestPNG(final TemporaryFolder folder) throws IOException {
		final Path path = Paths.get(folder.getRoot().toPath().toString(), "LaTeXDrawSmall.png");
		Files.copy(Paths.get("src/test/resources/LaTeXDrawSmall.png"), path);
		return path;
	}

	public Path getTestPNG(final Path folder) throws IOException {
		final Path path = Paths.get(folder.toString(), "LaTeXDrawSmall.png");
		Files.copy(Paths.get("src/test/resources/LaTeXDrawSmall.png"), path);
		return path;
	}

	public void clearTempFolders() {
		tempFolders.forEach(folder -> folder.delete());
		tempFolders.clear();
	}

	/**
	 * Filled = true
	 * fill color = 0.3, 0.85, 0.15, 0.65
	 * border po = into
	 * dble bord = true
	 * dble sep = 13
	 * dble col = 0.6, 0.8, 0.45, 0.24
	 * shadow = true
	 * shad col = 0.1, 0.3, 0.6, 0.4
	 * shad angle = 2.23
	 * shad size = 65
	 * line style = DOTTED
	 * thickness = 22
	 * line col = Color.CYAN
	 * rotation angle = 3.05
	 * show pts = true
	 */
	public Shape setShapeData1(final Shape sh) {
		if(sh.isFillable()) {
			sh.setFilled(true);
			sh.setFillingCol(ShapeFactory.INST.createColor(0.3, 0.85, 0.15, 0.65));
		}

		if(sh.isBordersMovable()) {
			sh.setBordersPosition(BorderPos.INTO);
		}

		if(sh.isDbleBorderable()) {
			sh.setHasDbleBord(true);
			sh.setDbleBordSep(13);
			sh.setDbleBordCol(ShapeFactory.INST.createColor(0.6, 0.8, 0.45, 0.24));
		}

		if(sh.isShadowable()) {
			sh.setHasShadow(true);
			sh.setShadowCol(ShapeFactory.INST.createColor(0.1, 0.3, 0.6, 0.4));
			sh.setShadowAngle(2.23);
			sh.setShadowSize(65);
		}

		if(sh.isThicknessable()) {
			sh.setThickness(22);
		}

		sh.setRotationAngle(3.05);

		sh.setLineColour(ShapeFactory.INST.createColorFX(Color.CYAN));

		if(sh.isLineStylable()) {
			sh.setLineStyle(LineStyle.DOTTED);
		}

		if(sh.isShowPtsable()) {
			sh.setShowPts(true);
		}

		return sh;
	}


	/**
	 * fill = VLINES
	 * hatch color = 0.1, 0.3, 0.2, 0.6
	 * hatch angle = -1.14
	 * hatch sep = 0.8
	 * hatch width = 11
	 * border po = INTO
	 * dble bord = default
	 * shadow = default
	 * line style = default
	 * thickness = 22
	 * line col = Color.CYAN
	 * rotation angle = -1.36
	 * show pts = false
	 */
	public Shape setShapeData2(final Shape sh) {
		if(sh.isInteriorStylable()) {
			sh.setFillingStyle(FillingStyle.VLINES);
			sh.setHatchingsCol(ShapeFactory.INST.createColor(0.1, 0.3, 0.2, 0.6));
			sh.setHatchingsAngle(-1.14);
			sh.setHatchingsSep(0.8);
			sh.setHatchingsWidth(11);
		}

		if(sh.isBordersMovable()) {
			sh.setBordersPosition(BorderPos.INTO);
		}

		if(sh.isThicknessable()) {
			sh.setThickness(22);
		}

		sh.setRotationAngle(-1.36);

		sh.setLineColour(ShapeFactory.INST.createColorFX(Color.CYAN));

		if(sh.isShowPtsable()) {
			sh.setShowPts(false);
		}

		return sh;
	}


	/**
	 * fill = grad
	 * grad1 color = Color.RED
	 * grad2 color = Color.BLUE
	 * grad angle = 2.21
	 * grad sep = 0.4
	 * border po = mid
	 * dble bord = true
	 * dble sep = 2
	 * dble col = Color.GREEN
	 * shadow = true
	 * shad col = Color.AQUAMARINE
	 * shad angle = -2.23
	 * shad size = 65
	 * line style = dash
	 * thickness = default
	 * line col = default
	 * rotation angle = default
	 * show pts = default
	 */
	public Shape setShapeData3(final Shape sh) {
		if(sh.isInteriorStylable()) {
			sh.setFillingStyle(FillingStyle.GRAD);
			sh.setGradColStart(ShapeFactory.INST.createColorFX(Color.RED));
			sh.setGradColEnd(ShapeFactory.INST.createColorFX(Color.BLUE));
			sh.setGradAngle(2.21);
			sh.setGradMidPt(0.4);
		}

		if(sh.isBordersMovable()) {
			sh.setBordersPosition(BorderPos.MID);
		}

		if(sh.isDbleBorderable()) {
			sh.setHasDbleBord(true);
			sh.setDbleBordSep(2);
			sh.setDbleBordCol(ShapeFactory.INST.createColorFX(Color.GREEN));
		}

		if(sh.isShadowable()) {
			sh.setHasShadow(true);
			sh.setShadowCol(ShapeFactory.INST.createColorFX(Color.AQUAMARINE));
			sh.setShadowAngle(-2.23);
			sh.setShadowSize(65);
		}

		if(sh.isLineStylable()) {
			sh.setLineStyle(LineStyle.DASHED);
		}

		return sh;
	}


	/**
	 * fill = hline plain
	 * border po = default
	 * dble bord = default
	 * shadow = true
	 * fill col = green
	 * shad col = default
	 * shad angle = default
	 * shad size = default
	 * line style = dash
	 * thickness = default
	 * line col = 0.1, 0.2, 0.3, 0.6
	 * rotation angle = default
	 * dash sep black = default
	 * dash sep white = default
	 * show pts = default
	 */
	public Shape setShapeData4(final Shape sh) {
		if(sh.isShadowable()) {
			sh.setHasShadow(true);
		}

		if(sh.isInteriorStylable()) {
			sh.setFillingStyle(FillingStyle.HLINES_PLAIN);
			sh.setHatchingsCol(ShapeFactory.INST.createColorFX(Color.BLUE));
			sh.setFillingCol(ShapeFactory.INST.createColorFX(Color.GREEN));
		}

		if(sh.isLineStylable()) {
			sh.setLineStyle(LineStyle.DASHED);
		}

		sh.setLineColour(ShapeFactory.INST.createColor(0.1, 0.2, 0.3, 0.6));

		return sh;
	}

	public Picture setPictureData1(final Picture pic) throws IOException {
		final TemporaryFolder folder = new TemporaryFolder();
		folder.create();
		tempFolders.add(folder);
		pic.setPathSource(getTestPNG(folder).toString());
		return pic;
	}

	public Rectangle setRectangleData1(final Rectangle rec) {
		rec.setLineArc(0.55);
		return rec;
	}

	public Square setSquareData1(final Square sq) {
		sq.setLineArc(0.33);
		return sq;
	}

	public Text setTextData1(final Text text) {
		text.setTextPosition(TextPosition.CENTER);
		return text;
	}

	public Arc setArcData1(final Arc arc) {
		arc.setAngleStart(1.23);
		arc.setAngleEnd(2.23);
		arc.setArcStyle(ArcStyle.CHORD);
		return arc;
	}

	public Arc setArcData2(final Arc arc) {
		arc.setAngleStart(-1.23);
		arc.setAngleEnd(2.23);
		return arc;
	}

	public Arc setArcData3(final Arc arc) {
		arc.setAngleStart(2.23);
		arc.setAngleEnd(1.23);
		arc.setArcStyle(ArcStyle.WEDGE);
		return arc;
	}

	public Dot setDotData1(final Dot dot) {
		dot.setDotStyle(DotStyle.ASTERISK);
		return dot;
	}

	public Dot setDotData2(final Dot dot) {
		dot.setDotStyle(DotStyle.FDIAMOND);
		return dot;
	}

	public Dot setDotData3(final Dot dot) {
		dot.setDotStyle(DotStyle.PENTAGON);
		return dot;
	}

	public Plot setPlotData1(final Plot plot) {
		plot.setPlotMaxX(100d);
		plot.setPlotMinX(85d);
		plot.setNbPlottedPoints(10);
		return plot;
	}

	public Plot setPlotData2(final Plot plot) {
		plot.setPlotMaxX(100d);
		plot.setPlotMinX(1d);
		plot.setPlotEquation("x log");
		plot.setNbPlottedPoints(50);
		plot.setPlotStyle(PlotStyle.POLYGON);
		plot.setPolar(true);
		return plot;
	}

	public Plot setPlotData3(final Plot plot) {
		plot.setPlotMaxX(1d);
		plot.setPlotMinX(-100d);
		plot.setNbPlottedPoints(10);
		plot.setPlotStyle(PlotStyle.DOTS);
		return plot;
	}

	public Plot setPlotData4(final Plot plot) {
		plot.setPlotStyle(PlotStyle.LINE);
		plot.setPlotEquation("x sin");
		plot.setPolar(true);
		return plot;
	}

	public Grid setGridData1(final Grid sh) {
		sh.setGridLabelsColour(ShapeFactory.INST.createColorFX(Color.AQUA));
		sh.setGridDots(3);
		sh.setUnit(1.1);
		sh.setGridWidth(5.1d);
		sh.setXLabelSouth(true);
		return sh;
	}

	public Grid setGridData2(final Grid sh) {
		sh.setSubGridWidth(2.13);
		sh.setSubGridDiv(11);
		sh.setSubGridColour(ShapeFactory.INST.createColor(0.15, 0.2, 0.3, 0.6));
		sh.setXLabelSouth(false);
		sh.setYLabelWest(true);
		return sh;
	}

	public Grid setGridData3(final Grid sh) {
		sh.setGridLabelsColour(ShapeFactory.INST.createColor(0.1, 0.2, 0.3, 0.4));
		sh.setSubGridDots(6);
		sh.setUnit(0.4);
		sh.setYLabelWest(false);
		return sh;
	}

	public Axes setAxesData1(final Axes sh) {
		sh.setIncrementX(1.2);
		sh.setDistLabelsY(0.85);
		sh.setLabelsDisplayed(PlottingStyle.ALL);
		sh.setShowOrigin(true);
		sh.setTicksDisplayed(PlottingStyle.X);
		sh.setTicksStyle(TicksStyle.BOTTOM);
		sh.setTicksSize(2.35);
		sh.setAxesStyle(AxesStyle.FRAME);
		return sh;
	}

	public Axes setAxesData2(final Axes sh) {
		sh.setIncrementY(0.8);
		sh.setDistLabelsX(1.35);
		sh.setLabelsDisplayed(PlottingStyle.NONE);
		sh.setShowOrigin(false);
		sh.setTicksDisplayed(PlottingStyle.Y);
		sh.setTicksStyle(TicksStyle.TOP);
		sh.setTicksSize(0.22);
		sh.setAxesStyle(AxesStyle.NONE);
		return sh;
	}

	public ArrowableSingleShape setArrowableData1(final ArrowableSingleShape sh) {
		sh.setArrowStyle(ArrowStyle.LEFT_ARROW, 0);
		sh.setArrowStyle(ArrowStyle.BAR_IN, -1);
		sh.setArrowInset(1.1);
		sh.setArrowLength(2.1);
		sh.setArrowSizeDim(0.7);
		sh.setArrowSizeNum(3.8);
		sh.setTBarSizeDim(4.5);
		sh.setTBarSizeNum(6.2);
		return sh;
	}

	public ArrowableSingleShape setArrowableData2(final ArrowableSingleShape sh) {
		sh.setArrowStyle(ArrowStyle.BAR_END, 0);
		sh.setArrowStyle(ArrowStyle.ROUND_IN, -1);
		sh.setTBarSizeDim(2.5);
		sh.setTBarSizeNum(3.2);
		sh.setDotSizeDim(1.1);
		sh.setDotSizeNum(3.3);
		return sh;
	}

	public ArrowableSingleShape setArrowableData3(final ArrowableSingleShape sh) {
		sh.setArrowStyle(ArrowStyle.RIGHT_ROUND_BRACKET, 0);
		sh.setArrowStyle(ArrowStyle.RIGHT_DBLE_ARROW, -1);
		sh.setArrowInset(2.1);
		sh.setArrowLength(3.1);
		sh.setArrowSizeDim(2.7);
		sh.setArrowSizeNum(1.8);
		sh.setRBracketNum(1.2);
		sh.setTBarSizeNum(2d);
		sh.setTBarSizeDim(3d);
		return sh;
	}

	public ArrowableSingleShape setArrowableData4(final ArrowableSingleShape sh) {
		sh.setArrowStyle(ArrowStyle.NONE, 0);
		sh.setArrowStyle(ArrowStyle.LEFT_DBLE_ARROW, -1);
		sh.setArrowInset(0.2);
		sh.setArrowLength(3.6);
		sh.setArrowSizeDim(2.7);
		sh.setArrowSizeNum(0.1);
		return sh;
	}

	public ArrowableSingleShape setArrowableData5(final ArrowableSingleShape sh) {
		sh.setArrowStyle(ArrowStyle.LEFT_SQUARE_BRACKET, 0);
		sh.setArrowStyle(ArrowStyle.NONE, -1);
		sh.setBracketNum(0.1);
		sh.setTBarSizeNum(2.5);
		sh.setTBarSizeDim(0.5);
		return sh;
	}

	public <T extends Shape & Closable> T setClosableOpened(final T sh) {
		sh.setOpened(true);
		return sh;
	}

	public <T extends Shape & Closable> T setClosableClosed(final T sh) {
		sh.setOpened(false);
		return sh;
	}

	public Freehand setFreeHand1(final Freehand sh) {
		sh.setInterval(10);
		sh.setType(FreeHandStyle.LINES);
		return sh;
	}

	public Freehand setFreeHand2(final Freehand sh) {
		sh.setInterval(2);
		sh.setType(FreeHandStyle.CURVES);
		return sh;
	}
}
