package net.sf.latexdraw.instruments;

import javafx.scene.Group;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.view.MagneticGrid;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.PageView;
import net.sf.latexdraw.view.jfx.ViewRectangle;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class TestCanvas extends BaseTestCanvas {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException {
				super.configure();
				bindToInstance(Border.class, Mockito.mock(Border.class));
				bindToInstance(CanvasController.class, Mockito.mock(CanvasController.class));
				bindAsEagerSingleton(FacadeCanvasController.class);
				bindToInstance(Pencil.class, Mockito.mock(Pencil.class));
				bindToInstance(Hand.class, Mockito.mock(Hand.class));
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
				bindToInstance(TextSetter.class, Mockito.mock(TextSetter.class));
			}
		};
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		hand.setActivated(true);
		when(pencil.isActivated()).thenReturn(false);
	}

	@Test
	public void testPageViewExits() {
		assertTrue(canvas.getChildren().get(0) instanceof PageView);
	}

	@Test
	public void testMagneticGridExists() {
		assertTrue(canvas.getChildren().get(1) instanceof MagneticGrid);
	}

	@Test
	public void testViewsPaneExists() {
		assertTrue(canvas.getChildren().get(2) instanceof Group);
	}

	@Test
	public void testViewsPanePositionORIGIN() {
		Group group = getPane();
		assertEquals(Canvas.ORIGIN.getX(), group.getLayoutX(), 0.000001);
		assertEquals(Canvas.ORIGIN.getY(), group.getLayoutY(), 0.000001);
	}

	@Test
	public void testShapeAddedViewCreated() {
		new CompositeGUIVoidCommand(addRec, waitFXEvents).execute();
		assertEquals(1, getPane().getChildren().size());
	}

	@Test
	public void testShapeAddedViewRecCreated() {
		new CompositeGUIVoidCommand(addRec, waitFXEvents).execute();
		assertTrue(getPane().getChildren().get(0) instanceof ViewRectangle);
	}
}
