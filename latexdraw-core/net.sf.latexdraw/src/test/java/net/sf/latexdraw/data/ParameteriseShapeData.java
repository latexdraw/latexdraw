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
import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.models.interfaces.shape.IPicture;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.LineStyle;
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
	 * fill color = Color.RED
	 * border po = into
	 * dble bord = true
	 * dble sep = 13
	 * dble col = Color.GREEN
	 * shadow = true
	 * shad col = Color.AQUAMARINE
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
			sh.setFillingCol(ShapeFactory.INST.createColorFX(Color.RED));
		}

		if(sh.isBordersMovable()) {
			sh.setBordersPosition(BorderPos.INTO);
		}

		if(sh.isDbleBorderable()) {
			sh.setHasDbleBord(true);
			sh.setDbleBordSep(13);
			sh.setDbleBordCol(ShapeFactory.INST.createColorFX(Color.GREEN));
		}

		if(sh.isShadowable()) {
			sh.setHasShadow(true);
			sh.setShadowCol(ShapeFactory.INST.createColorFX(Color.AQUAMARINE));
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
	 * hatch color = Color.BLUE
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
			sh.setHatchingsCol(ShapeFactory.INST.createColorFX(Color.BLUE));
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
}
