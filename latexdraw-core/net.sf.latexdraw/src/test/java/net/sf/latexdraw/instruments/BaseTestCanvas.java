package net.sf.latexdraw.instruments;

import java.util.Arrays;
import java.util.concurrent.TimeoutException;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.After;
import org.junit.Before;
import org.malai.action.ActionsRegistry;
import org.testfx.util.WaitForAsyncUtils;

abstract class BaseTestCanvas extends TestLatexdrawGUI {
	static final long SLEEP = 400L;

	Pencil pencil;
	Hand hand;
	Canvas canvas;

	IRectangle addedRec;
	IGrid addedGrid;
	IPolyline addedPolyline;
	IPlot addedPlot;
	IGroup addedGroup;

	final GUIVoidCommand addText = () -> Platform.runLater(() -> {
		canvas.getDrawing().addShape(ShapeFactory.INST.createText(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX()+300,-Canvas.ORIGIN.getY()+300), "$foo bar"));
	});

	final GUIVoidCommand addGroup = () -> Platform.runLater(() -> {
		IRectangle r1 = ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX()+50, -Canvas.ORIGIN.getY()+50), 200, 100);
		IRectangle r2 = ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX()+300, -Canvas.ORIGIN.getY()+300), 100, 100);
		addedGroup = ShapeFactory.INST.createGroup();
		addedGroup.addShape(r1);
		addedGroup.addShape(r2);
		canvas.getDrawing().addShape(addedGroup);
	});

	final GUIVoidCommand addGrid = () -> Platform.runLater(() -> {
		addedGrid = ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX()+200, -Canvas.ORIGIN.getY()+200));
		canvas.getDrawing().addShape(addedGrid);
	});

	final GUIVoidCommand addLines = () -> Platform.runLater(() -> {
		addedPolyline = ShapeFactory.INST.createPolyline(Arrays.asList(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX()+50, -Canvas.ORIGIN.getY()+250),
			ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX()+300, -Canvas.ORIGIN.getY()+350),
			ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX()+120, -Canvas.ORIGIN.getY()+500)));
		addedPolyline.setArrowStyle(ArrowStyle.LEFT_ARROW, 0);
		addedPolyline.setThickness(20d);
		canvas.getDrawing().addShape(addedPolyline);
	});

	final GUIVoidCommand addPlot = () -> Platform.runLater(() -> {
		addedPlot = ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX()+50, -Canvas.ORIGIN.getY()+400),
			0d, 5d, "x", false);
		addedPlot.setThickness(10d);
		canvas.getDrawing().addShape(addedPlot);
	});

	final GUIVoidCommand selectAllShapes = () -> {
		Platform.runLater(() -> canvas.requestFocus());
		WaitForAsyncUtils.waitForFxEvents();
		press(KeyCode.CONTROL, KeyCode.A).release(KeyCode.A, KeyCode.CONTROL);
	};

	final GUIVoidCommand addRec = () -> Platform.runLater(() -> {
		addedRec = ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX()+50, -Canvas.ORIGIN.getY()+50), 200, 100);
		addedRec.setFilled(true);
		addedRec.setFillingCol(DviPsColors.APRICOT);
		canvas.getDrawing().addShape(addedRec);
	});

	final GUIVoidCommand addRec2 = () -> Platform.runLater(() -> {
		IRectangle rec = ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX()+300,
			-Canvas.ORIGIN.getY()+300), 100, 100);
		rec.setFilled(true);
		rec.setFillingCol(DviPsColors.APRICOT);
		canvas.getDrawing().addShape(rec);
	});

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "/fxml/Canvas.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		pencil = (Pencil) injectorFactory.call(Pencil.class);
		hand = (Hand) injectorFactory.call(Hand.class);
		canvas = (Canvas) injectorFactory.call(Canvas.class);

		Platform.runLater(() -> {
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
		});
	}

	@Override
	@After
	public void tearDown() throws TimeoutException {
		super.tearDown();
		ActionsRegistry.INSTANCE.removeHandler(canvas);
	}

	Group getPane() {
		return (Group) canvas.getChildren().get(2);
	}
}
