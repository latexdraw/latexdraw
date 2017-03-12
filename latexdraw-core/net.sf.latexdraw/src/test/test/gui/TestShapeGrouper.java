package test.gui;

import com.google.inject.AbstractModule;
import java.util.Arrays;
import java.util.Collections;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import net.sf.latexdraw.actions.shape.SelectShapes;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeGrouper;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.malai.action.ActionHandler;
import org.malai.action.ActionsRegistry;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestShapeGrouper extends TestShapePropGUI<ShapeGrouper> {
	Button groupB;
	Button sepB;
	AnchorPane mainPane;
	@Mock ActionHandler handler;

	final GUIVoidCommand clickGroup = () -> clickOn(groupB);
	final GUIVoidCommand clickSep = () -> clickOn(sepB);

	final GUIVoidCommand selectTwoShapes = () -> {
		drawing.addShape(ShapeFactory.INST.createCircle());
		drawing.addShape(ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(20, 30), 10));
		drawing.setSelection(Arrays.asList(drawing.getShapeAt(0), drawing.getShapeAt(1)));
		SelectShapes action = new SelectShapes();
		action.addShape(drawing.getShapeAt(0));
		action.addShape(drawing.getShapeAt(1));
		ActionsRegistry.INSTANCE.addAction(action, handler);
		ins.update();
	};

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

	final GUIVoidCommand selectOneShape = () -> {
		drawing.addShape(ShapeFactory.INST.createCircle());
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
		ins = (ShapeGrouper) guiceFactory.call(ShapeGrouper.class);
		ins.setActivated(true);
		when(pencil.isActivated()).thenReturn(false);
		when(hand.isActivated()).thenReturn(true);
		ins.update();
	}

	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				pencil = mock(Pencil.class);
				hand = mock(Hand.class);
				bind(ShapeGrouper.class).asEagerSingleton();
				bind(Hand.class).toInstance(hand);
				bind(Pencil.class).toInstance(pencil);
			}
		};
	}

	@Test
	public void testDeactivatedEmpty() throws Exception {
		assertFalse(ins.isActivated());
	}

	@Test
	public void testNotVisibleEmpty() throws Exception {
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testActivateOnGroupSelected() throws Exception {
		selectOneGroup.execute();
		assertTrue(ins.isActivated());
	}

	@Test
	public void testActivateOnTwoShapesSelected() throws Exception {
		selectTwoShapes.execute();
		assertTrue(ins.isActivated());
	}

	@Test
	public void testNotActivateOnOneShapeNotGroupSelected() throws Exception {
		selectOneShape.execute();
		assertFalse(ins.isActivated());
	}

	@Test
	public void testNotVisibleOnOneShapeNotGroupSelected() throws Exception {
		selectOneShape.execute();
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testNotVisiblePencil() throws Exception {
		when(pencil.isActivated()).thenReturn(true);
		when(hand.isActivated()).thenReturn(false);
		selectTwoShapes.execute();
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testNotActivatedPencil() throws Exception {
		when(pencil.isActivated()).thenReturn(true);
		when(hand.isActivated()).thenReturn(false);
		selectTwoShapes.execute();
		assertFalse(ins.isActivated());
	}

	@Test
	public void testGroupTwoShapes() throws Exception {
		new CompositeGUIVoidCommand(selectTwoShapes, clickGroup).execute();
		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IGroup);
	}

	@Test
	public void testUnGroup() throws Exception {
		selectOneGroup.execute();
		IGroup group = (IGroup) drawing.getShapeAt(0);
		clickSep.execute();
		assertEquals(2, drawing.size());
		assertEquals(group.getShapeAt(0), drawing.getShapeAt(0));
		assertEquals(group.getShapeAt(1), drawing.getShapeAt(1));
	}
}
