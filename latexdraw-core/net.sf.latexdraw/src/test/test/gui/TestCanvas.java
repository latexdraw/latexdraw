package test.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.glib.views.latex.DviPsColors;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeBorderCustomiser;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.PageView;
import net.sf.latexdraw.view.jfx.ViewRectangle;

public class TestCanvas extends TestLatexdrawGUI {
	Pencil pencil;
	Hand hand;
	Canvas canvas;
	IRectangle addedRec;

	final GUIVoidCommand addRec = () -> {
		Platform.runLater(() -> {
			addedRec = ShapeFactory.createRectangle(ShapeFactory.createPoint(-Canvas.ORIGIN.getX(), -Canvas.ORIGIN.getY()), 100, 100);
			addedRec.setFilled(true);
			addedRec.setFillingCol(DviPsColors.APRICOT);
			canvas.getDrawing().addShape(addedRec);
		});
	};
	
	final GUIVoidCommand addRec2 = () -> {
		Platform.runLater(() -> {
			IRectangle rec = ShapeFactory.createRectangle(ShapeFactory.createPoint(-Canvas.ORIGIN.getX()+300, -Canvas.ORIGIN.getY()+300), 100, 100);
			rec.setFilled(true);
			rec.setFillingCol(DviPsColors.APRICOT);
			canvas.getDrawing().addShape(rec);
		});
	};

	final GUIVoidCommand clickOnAddedRec = () -> {
		rightClickOn(new Point2D(50, 50));
	};
	
	final GUIVoidCommand ctrlClickOnAddedRec2 = () -> {
		press(KeyCode.CONTROL).rightClickOn(new Point2D(330, 350)).release(KeyCode.CONTROL);
	};

	final GUIVoidCommand shiftClickOnAddedRec = () -> {
		press(KeyCode.SHIFT).rightClickOn(new Point2D(55, 55)).release(KeyCode.SHIFT);
	};

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "view/jfx/ui/Canvas.fxml";
	}

	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				this.pencil = mock(Pencil.class);
				bind(ShapeBorderCustomiser.class).asEagerSingleton();
				bind(Hand.class).asEagerSingleton();
				bind(Canvas.class).asEagerSingleton();
				bind(Pencil.class).toInstance(this.pencil);
			}
		};
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		pencil = (Pencil)guiceFactory.call(Pencil.class);
		hand = (Hand)guiceFactory.call(Hand.class);

		hand.setActivated(true);
		when(pencil.isActivated()).thenReturn(false);

		canvas = lookup("#canvas").queryFirst();
	}

	Pane getPane() {
		return (Pane)canvas.getChildren().get(1);
	}

	@Test
	public void testPageViewExits() {
		assertTrue(canvas.getChildren().get(0) instanceof PageView);
	}

	@Test
	public void testViewsPaneExists() {
		assertTrue(canvas.getChildren().get(1) instanceof Pane);
	}

	@Test
	public void testViewsPanePositionORIGIN() {
		Pane vpane = getPane();
		assertEquals(Canvas.ORIGIN.getX(), vpane.getLayoutX(), 0.000001);
		assertEquals(Canvas.ORIGIN.getY(), vpane.getLayoutY(), 0.000001);
	}

	@Test
	public void testShapeAddedViewCreated() {
		new CompositeGUIVoidCommand(addRec, waitFXEvents).execute();
		Pane vpane = getPane();
		assertEquals(1, vpane.getChildren().size());
	}

	@Test
	public void testShapeAddedViewRecCreated() {
		new CompositeGUIVoidCommand(addRec, waitFXEvents).execute();
		Pane vpane = (Pane)canvas.getChildren().get(1);
		assertTrue(vpane.getChildren().get(0) instanceof ViewRectangle);
	}

	@Test
	public void testOneClickOnShapeSelectsIt() {
		new CompositeGUIVoidCommand(addRec, waitFXEvents, clickOnAddedRec, waitFXEvents).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertSame(addedRec, canvas.getDrawing().getSelection().getShapeAt(0));
	}

	@Test
	public void testTwoClicksOnShapeSelectsItOneTime() {
		new CompositeGUIVoidCommand(addRec, waitFXEvents, clickOnAddedRec, waitFXEvents, clickOnAddedRec, waitFXEvents).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
	}

	@Test
	public void testShiftClickOnShapeDeselectsIt() {
		new CompositeGUIVoidCommand(addRec, waitFXEvents, clickOnAddedRec, waitFXEvents, shiftClickOnAddedRec, waitFXEvents).execute();
		assertTrue(canvas.getDrawing().getSelection().isEmpty());
	}
	
	@Test
	public void testCtrlClickOnShapeAddsSelection() {
		new CompositeGUIVoidCommand(addRec, addRec2, waitFXEvents, clickOnAddedRec, waitFXEvents, ctrlClickOnAddedRec2, waitFXEvents).execute();
		assertEquals(2, canvas.getDrawing().getSelection().size());
		assertNotSame(canvas.getDrawing().getSelection().getShapeAt(0), canvas.getDrawing().getSelection().getShapeAt(1));
	}
	
	@Test
	public void testTwoAddsAndShiftClickSelectsOneShape() {
		new CompositeGUIVoidCommand(addRec, addRec2, waitFXEvents, clickOnAddedRec, waitFXEvents, ctrlClickOnAddedRec2, waitFXEvents,
				shiftClickOnAddedRec, waitFXEvents).execute();
		assertEquals(1, canvas.getDrawing().getSelection().size());
		assertNotSame(addedRec, canvas.getDrawing().getSelection().getShapeAt(0));
	}
}
