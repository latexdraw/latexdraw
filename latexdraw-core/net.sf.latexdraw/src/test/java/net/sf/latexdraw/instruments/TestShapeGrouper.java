package net.sf.latexdraw.instruments;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import net.sf.latexdraw.actions.shape.SelectShapes;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.util.Injector;
import org.junit.Before;
import org.junit.Test;
import org.malai.action.ActionsRegistry;
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
		IGroup group = ShapeFactory.INST.createGroup();
		group.addShape(ShapeFactory.INST.createCircle());
		group.addShape(ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(20, 30), 10));
		drawing.addShape(group);
		drawing.setSelection(Collections.singletonList(drawing.getShapeAt(0)));
		SelectShapes action = new SelectShapes();
		action.addShape(drawing.getShapeAt(0));
		ActionsRegistry.INSTANCE.addAction(action, handler);
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
		ins = (ShapeGrouper) injectorFactory.call(ShapeGrouper.class);
		ins.setActivated(true);
		ins.update();
	}

	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				pencil = mock(Pencil.class);
				hand = mock(Hand.class);
				bindAsEagerSingleton(ShapeGrouper.class);
				bindToInstance(Hand.class, hand);
				bindToInstance(Pencil.class, pencil);
				bindToInstance(TextSetter.class, Mockito.mock(TextSetter.class));
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Test
	public void testDeactivatedEmpty() {
		assertFalse(ins.isActivated());
	}

	@Test
	public void testNotVisibleEmpty() {
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testActivateOnGroupSelected() {
		selectOneGroup.execute();
		assertTrue(ins.isActivated());
	}

	@Test
	public void testActivateOnTwoShapesSelected() {
		selectTwoShapes.execute();
		assertTrue(ins.isActivated());
	}

	@Test
	public void testNotActivateOnOneShapeNotGroupSelected() {
		selectOneShape.execute();
		assertFalse(ins.isActivated());
	}

	@Test
	public void testNotVisibleOnOneShapeNotGroupSelected() {
		selectOneShape.execute();
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testNotVisiblePencil() {
		when(pencil.isActivated()).thenReturn(true);
		when(hand.isActivated()).thenReturn(false);
		selectTwoShapes.execute();
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testNotActivatedPencil() {
		when(pencil.isActivated()).thenReturn(true);
		when(hand.isActivated()).thenReturn(false);
		selectTwoShapes.execute();
		assertFalse(ins.isActivated());
	}

	@Test
	public void testGroupTwoShapes() {
		new CompositeGUIVoidCommand(selectTwoShapes, clickGroup).execute();
		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IGroup);
	}

	@Test
	public void testUnGroup() {
		selectOneGroup.execute();
		IGroup group = (IGroup) drawing.getShapeAt(0);
		clickSep.execute();
		assertEquals(2, drawing.size());
		assertEquals(group.getShapeAt(0), drawing.getShapeAt(0));
		assertEquals(group.getShapeAt(1), drawing.getShapeAt(1));
	}
}
