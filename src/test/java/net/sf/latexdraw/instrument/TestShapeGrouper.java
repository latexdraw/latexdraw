package net.sf.latexdraw.instrument;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.sf.latexdraw.command.shape.SelectShapes;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.util.Injector;
import org.junit.Before;
import org.junit.Test;
import org.malai.command.CommandsRegistry;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestShapeGrouper extends SelectionBasedTesting<ShapeGrouper> {
	Button groupB;
	Button sepB;
	AnchorPane mainPane;

	final GUIVoidCommand clickGroup = () -> clickOn(groupB);
	final GUIVoidCommand clickSep = () -> clickOn(sepB);

	final GUIVoidCommand selectOneGroup = () -> {
		final Group group = ShapeFactory.INST.createGroup();
		group.addShape(ShapeFactory.INST.createCircle());
		group.addShape(ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(20, 30), 10));
		drawing.addShape(group);
		drawing.setSelection(Collections.singletonList(drawing.getShapeAt(0).orElseThrow()));
		final SelectShapes cmd = new SelectShapes(drawing);
		cmd.addShape(drawing.getShapeAt(0).orElseThrow());
		CommandsRegistry.INSTANCE.addCommand(cmd, handler);
		ins.update();
	};

	@Override
	protected String getFXMLPathFromLatexdraw() {
		return "/fxml/Group.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		groupB = find("#groupB");
		sepB = find("#sepB");
		mainPane = find("#mainPane");
		ins = injector.getInstance(ShapeGrouper.class);
		ins.setActivated(true);
		ins.update();
	}

	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				pencil = mock(Pencil.class);
				hand = mock(Hand.class);
				bindAsEagerSingleton(ShapeGrouper.class);
				bindToInstance(TextSetter.class, Mockito.mock(TextSetter.class));
				bindToInstance(Hand.class, hand);
				bindToInstance(Pencil.class, pencil);
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Test
	public void testDeactivatedEmpty() {
		assertFalse(ins.isActivated());
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testActivateOnGroupSelected() {
		selectOneGroup.execute();
		waitFXEvents.execute();
		assertTrue(ins.isActivated());
	}

	@Test
	public void testActivateOnTwoShapesSelected() {
		selectTwoShapes.execute();
		waitFXEvents.execute();
		assertTrue(ins.isActivated());
	}

	@Test
	public void testNotActivateOnOneShapeNotGroupSelected() {
		selectOneShape.execute();
		waitFXEvents.execute();
		assertFalse(ins.isActivated());
	}

	@Test
	public void testNotVisibleOnOneShapeNotGroupSelected() {
		selectOneShape.execute();
		waitFXEvents.execute();
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testNotVisiblePencil() {
		when(pencil.isActivated()).thenReturn(true);
		when(hand.isActivated()).thenReturn(false);
		selectTwoShapes.execute();
		waitFXEvents.execute();
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testNotActivatedPencil() {
		when(pencil.isActivated()).thenReturn(true);
		when(hand.isActivated()).thenReturn(false);
		selectTwoShapes.execute();
		waitFXEvents.execute();
		assertFalse(ins.isActivated());
	}

	@Test
	public void testGroupTwoShapes() {
		new CompositeGUIVoidCommand(selectTwoShapes, clickGroup).execute();
		assertEquals(1, drawing.size());
		waitFXEvents.execute();
		assertTrue(drawing.getShapeAt(0).orElseThrow() instanceof Group);
	}

	@Test
	public void testUnGroup() {
		selectOneGroup.execute();
		final Group group = (Group) drawing.getShapeAt(0).orElseThrow();
		clickSep.execute();
		waitFXEvents.execute();
		assertEquals(2, drawing.size());
		assertEquals(group.getShapeAt(0).orElseThrow(), drawing.getShapeAt(0).orElseThrow());
		assertEquals(group.getShapeAt(1).orElseThrow(), drawing.getShapeAt(1).orElseThrow());
	}
}
