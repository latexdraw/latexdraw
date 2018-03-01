package net.sf.latexdraw.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.paint.Color;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ArcStyle;
import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.models.interfaces.shape.IArc;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IPicture;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.ISquare;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.models.interfaces.shape.LineStyle;
import net.sf.latexdraw.models.interfaces.shape.PlotStyle;
import net.sf.latexdraw.models.interfaces.shape.TextPosition;
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
	public IShape setShapeData1(final IShape sh) {
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
	public IShape setShapeData2(final IShape sh) {
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
	public IShape setShapeData3(final IShape sh) {
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
	public IShape setShapeData4(final IShape sh) {
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

	public IPicture setPictureData1(final IPicture pic) throws IOException {
		final TemporaryFolder folder = new TemporaryFolder();
		folder.create();
		tempFolders.add(folder);
		pic.setPathSource(getTestPNG(folder).toString());
		return pic;
	}

	public IRectangle setRectangleData1(final IRectangle rec) {
		rec.setLineArc(0.55);
		return rec;
	}

	public ISquare setSquareData1(final ISquare sq) {
		sq.setLineArc(0.33);
		return sq;
	}

	public IText setTextData1(final IText text) {
		text.setTextPosition(TextPosition.CENTER);
		return text;
	}

	public IArc setArcData1(final IArc arc) {
		arc.setAngleStart(1.23);
		arc.setAngleEnd(2.23);
		arc.setArcStyle(ArcStyle.CHORD);
		return arc;
	}

	public IArc setArcData2(final IArc arc) {
		arc.setAngleStart(-1.23);
		arc.setAngleEnd(2.23);
		return arc;
	}

	public IArc setArcData3(final IArc arc) {
		arc.setAngleStart(2.23);
		arc.setAngleEnd(1.23);
		arc.setArcStyle(ArcStyle.WEDGE);
		return arc;
	}

	public IDot setDotData1(final IDot dot) {
		dot.setDotStyle(DotStyle.ASTERISK);
		return dot;
	}

	public IDot setDotData2(final IDot dot) {
		dot.setDotStyle(DotStyle.FDIAMOND);
		return dot;
	}

	public IDot setDotData3(final IDot dot) {
		dot.setDotStyle(DotStyle.PENTAGON);
		return dot;
	}

	public IPlot setPlotData1(final IPlot plot) {
		plot.setPlotMaxX(100d);
		plot.setPlotMinX(85d);
		plot.setNbPlottedPoints(10);
		return plot;
	}

	public IPlot setPlotData2(final IPlot plot) {
		plot.setPlotMaxX(100d);
		plot.setPlotMinX(1d);
		plot.setPlotEquation("x log");
		plot.setNbPlottedPoints(50);
		plot.setPlotStyle(PlotStyle.POLYGON);
		plot.setPolar(true);
		return plot;
	}

	public IPlot setPlotData3(final IPlot plot) {
		plot.setPlotMaxX(1d);
		plot.setPlotMinX(-100d);
		plot.setNbPlottedPoints(10);
		plot.setPlotStyle(PlotStyle.DOTS);
		return plot;
	}

	public IPlot setPlotData4(final IPlot plot) {
		plot.setPlotStyle(PlotStyle.LINE);
		plot.setPlotEquation("x sin");
		plot.setPolar(true);
		return plot;
	}

	public IGrid setGridData1(final IGrid sh) {
		sh.setGridLabelsColour(ShapeFactory.INST.createColorFX(Color.AQUA));
		sh.setGridDots(3);
		sh.setUnit(1.1);
		sh.setGridWidth(5.1d);
		sh.setXLabelSouth(true);
		return sh;
	}

	public IGrid setGridData2(final IGrid sh) {
		sh.setSubGridWidth(2.13);
		sh.setSubGridDiv(11);
		sh.setSubGridColour(ShapeFactory.INST.createColor(0.15, 0.2, 0.3, 0.6));
		sh.setXLabelSouth(false);
		sh.setYLabelWest(true);
		return sh;
	}

	public IGrid setGridData3(final IGrid sh) {
		sh.setGridLabelsColour(ShapeFactory.INST.createColor(0.1, 0.2, 0.3, 0.4));
		sh.setSubGridDots(6);
		sh.setUnit(0.4);
		sh.setYLabelWest(false);
		return sh;
	}
}
