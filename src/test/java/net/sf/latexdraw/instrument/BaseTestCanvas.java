package net.sf.latexdraw.instrument;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeoutException;
import javafx.application.Platform;
import net.sf.latexdraw.command.shape.SelectShapes;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.ArrowStyle;
import net.sf.latexdraw.model.api.shape.Arc;
import net.sf.latexdraw.model.api.shape.BezierCurve;
import net.sf.latexdraw.model.api.shape.Grid;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Plot;
import net.sf.latexdraw.model.api.shape.Polyline;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.service.EditingService;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.After;
import org.junit.Before;
import io.github.interacto.command.CommandsRegistry;
import org.testfx.util.WaitForAsyncUtils;

abstract class BaseTestCanvas extends TestLatexdrawGUI {
	Pencil pencil;
	EditingService editing;
	Hand hand;
	Canvas canvas;

	Rectangle addedRec;
	Grid addedGrid;
	Arc addedArc;
	Polyline addedPolyline;
	Plot addedPlot;
	BezierCurve addedBezier;
	Group addedGroup;

	final CmdFXVoid requestFocusCanvas = () -> canvas.requestFocus();

	final CmdFXVoid addText = () -> canvas.getDrawing().addShape(ShapeFactory.INST.createText(
			ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX() + 300, -Canvas.ORIGIN.getY() + 300), "$gridGapProp bar"));

	final CmdFXVoid addGroup = () -> {
		final Rectangle r1 = ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX() + 50, -Canvas.ORIGIN.getY() + 50), 200, 100);
		final Rectangle r2 = ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX() + 300, -Canvas.ORIGIN.getY() + 300), 100, 100);
		r1.setFilled(true);
		r2.setFilled(true);
		addedGroup = ShapeFactory.INST.createGroup();
		addedGroup.addShape(r1);
		addedGroup.addShape(r2);
		canvas.getDrawing().addShape(addedGroup);
	};

	final CmdFXVoid addGrid = () -> {
		addedGrid = ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX() + 200, -Canvas.ORIGIN.getY() + 200));
		canvas.getDrawing().addShape(addedGrid);
	};

	final CmdFXVoid addLines = () -> {
		addedPolyline = ShapeFactory.INST.createPolyline(Arrays.asList(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX() + 50, -Canvas.ORIGIN.getY() + 250),
			ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX() + 300, -Canvas.ORIGIN.getY() + 350),
			ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX() + 120, -Canvas.ORIGIN.getY() + 500)));
		addedPolyline.setArrowStyle(ArrowStyle.LEFT_ARROW, 0);
		addedPolyline.setThickness(20d);
		canvas.getDrawing().addShape(addedPolyline);
	};

	final CmdFXVoid addPlot = () -> {
		addedPlot = ShapeFactory.INST.createPlot(
			ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX() + 50, -Canvas.ORIGIN.getY() + 400), 0d, 5d, "x", false);
		addedPlot.setThickness(10d);
		canvas.getDrawing().addShape(addedPlot);
	};

	final CmdFXVoid addBezier = () -> {
		addedBezier = ShapeFactory.INST.createBezierCurve(
			Arrays.asList(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX() + 50, -Canvas.ORIGIN.getY() + 250),
			ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX() + 300, -Canvas.ORIGIN.getY() + 350),
			ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX() + 120, -Canvas.ORIGIN.getY() + 500)));
		addedBezier.setThickness(5d);
		canvas.getDrawing().addShape(addedBezier);
	};

	final CmdFXVoid selectAllShapes = () -> {
		final SelectShapes cmd = new SelectShapes(canvas.getDrawing());
		canvas.getDrawing().getShapes().forEach(sh -> cmd.addShape(sh));
		cmd.doIt();
		CommandsRegistry.getInstance().addCommand(cmd);
	};

	final CmdFX<Integer> selectShape = index -> {
		canvas.getDrawing().setSelection(Collections.emptyList());
		canvas.getDrawing().getSelection().addShape(canvas.getDrawing().getShapeAt(index).orElseThrow());
	};

	final CmdFXVoid addRec = () -> {
		addedRec = ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX() + 50, -Canvas.ORIGIN.getY() + 50), 200, 100);
		addedRec.setFilled(true);
		addedRec.setFillingCol(DviPsColors.APRICOT);
		canvas.getDrawing().addShape(addedRec);
	};

	final CmdFXVoid addRec2 = () -> {
		final Rectangle rec = ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX() + 300, -Canvas.ORIGIN.getY() + 300), 100, 100);
		rec.setFilled(true);
		rec.setFillingCol(DviPsColors.APRICOT);
		canvas.getDrawing().addShape(rec);
	};

	final CmdFXVoid addArc = () -> {
		addedArc = ShapeFactory.INST.createCircleArc(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX() + 200, -Canvas.ORIGIN.getY() + 500), 450);
		addedArc.setFilled(true);
		canvas.getDrawing().addShape(addedArc);
	};

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "/fxml/Canvas.fxml";
	}

	@Before
	public void setUp() throws Exception {
		editing = injector.getInstance(EditingService.class);
		pencil = injector.getInstance(Pencil.class);
		hand = injector.getInstance(Hand.class);
		canvas = injector.getInstance(Canvas.class);
		injector.getInstance(PreferencesService.class).magneticGridProperty().set(false);

		Cmds.of(CmdFXVoid.of(() -> {
			final int width = 800;
			final int height = 600;
			stage.minHeightProperty().unbind();
			stage.minWidthProperty().unbind();
			canvas.setMaxWidth(width);
			canvas.setMaxHeight(height);
			canvas.getScene().getWindow().setWidth(width);
			canvas.getScene().getWindow().setHeight(height);
			stage.setMaxWidth(width);
			stage.setMaxHeight(height);
			stage.setMinWidth(width);
			stage.setMinHeight(height);
			stage.centerOnScreen();
			stage.toFront();
		})).execute();
	}

	@Override
	@After
	public void tearDown() throws TimeoutException {
		Platform.runLater(() -> canvas.getDrawing().clear());
		WaitForAsyncUtils.waitForFxEvents();
		super.tearDown();
	}

	javafx.scene.Group getPane() {
		return (javafx.scene.Group) canvas.getChildren().get(2);
	}
}
