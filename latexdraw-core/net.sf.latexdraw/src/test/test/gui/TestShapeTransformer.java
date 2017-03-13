package test.gui;

import com.google.inject.AbstractModule;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeTransformer;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestShapeTransformer extends SelectionBasedTesting<ShapeTransformer> {
	Button mirrorH;
	Button mirrorV;
	Button alignBot;
	Button alignLeft;
	Button alignRight;
	Button alignTop;
	Button alignMidHoriz;
	Button alignMidVert;
	VBox mainPane;

	final GUIVoidCommand clickAlignBot = () -> clickOn(alignBot);
	final GUIVoidCommand clickAlignTop = () -> clickOn(alignTop);
	final GUIVoidCommand clickAlignLeft = () -> clickOn(alignLeft);
	final GUIVoidCommand clickAlignRight = () -> clickOn(alignRight);
	final GUIVoidCommand clickAlignMidHoriz = () -> clickOn(alignMidHoriz);
	final GUIVoidCommand clickAlignMidVert = () -> clickOn(alignMidVert);
	final GUIVoidCommand clickMirrorH = () -> clickOn(mirrorH);
	final GUIVoidCommand clickMirrorV = () -> clickOn(mirrorV);

	@Override
	protected String getFXMLPathFromLatexdraw() {
		return "/fxml/Transformation.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		mirrorH = find("#mirrorH");
		mirrorV = find("#mirrorV");
		alignBot = find("#alignBot");
		alignLeft = find("#alignLeft");
		alignRight = find("#alignRight");
		alignTop = find("#alignTop");
		alignMidHoriz = find("#alignMidHoriz");
		alignMidVert = find("#alignMidVert");
		mainPane = find("#mainPane");
		ins = (ShapeTransformer) guiceFactory.call(ShapeTransformer.class);
		ins.setActivated(true);
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
				bind(ShapeTransformer.class).asEagerSingleton();
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
	public void testActivateOnTwoShapesSelected() throws Exception {
		selectTwoShapes.execute();
		assertTrue(ins.isActivated());
	}

	@Test
	public void testNotActivateOnOneShapeSelected() throws Exception {
		selectOneShape.execute();
		assertTrue(ins.isActivated());
	}

	@Test
	public void testNotVisibleOnOneShapeSelected() throws Exception {
		selectOneShape.execute();
		assertTrue(mainPane.isVisible());
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
	public void testAlignBot() {
		selectTwoShapes.execute();
		List<IShape> dups = drawing.getShapes().stream().map(sh -> (IShape)sh.duplicate()).collect(Collectors.toList());
		clickAlignBot.execute();
		assertNotEquals(dups.get(0).getPoints(), drawing.getShapes().get(0).getPoints());
	}

	@Test
	public void testAlignTop() {
		selectTwoShapes.execute();
		List<IShape> dups = drawing.getShapes().stream().map(sh -> (IShape)sh.duplicate()).collect(Collectors.toList());
		clickAlignTop.execute();
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testAlignLeft() {
		selectTwoShapes.execute();
		List<IShape> dups = drawing.getShapes().stream().map(sh -> (IShape)sh.duplicate()).collect(Collectors.toList());
		clickAlignLeft.execute();
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testAlignRight() {
		selectTwoShapes.execute();
		List<IShape> dups = drawing.getShapes().stream().map(sh -> (IShape)sh.duplicate()).collect(Collectors.toList());
		clickAlignRight.execute();
		assertNotEquals(dups.get(0).getPoints(), drawing.getShapes().get(0).getPoints());
	}

	@Test
	public void testAlignMidHoriz() {
		selectTwoShapes.execute();
		List<IShape> dups = drawing.getShapes().stream().map(sh -> (IShape)sh.duplicate()).collect(Collectors.toList());
		clickAlignMidHoriz.execute();
		assertNotEquals(dups.get(0).getPoints(), drawing.getShapes().get(0).getPoints());
	}

	@Test
	public void testAlignMidVert() {
		selectTwoShapes.execute();
		List<IShape> dups = drawing.getShapes().stream().map(sh -> (IShape)sh.duplicate()).collect(Collectors.toList());
		clickAlignMidVert.execute();
		assertNotEquals(dups.get(0).getPoints(), drawing.getShapes().get(0).getPoints());
	}

	@Test
	public void testMirrorH() {
		selectTwoShapes.execute();
		List<IShape> dups = drawing.getShapes().stream().map(sh -> (IShape)sh.duplicate()).collect(Collectors.toList());
		clickMirrorH.execute();
		assertNotEquals(dups.get(0).getPoints(), drawing.getShapes().get(0).getPoints());
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testMirrorV() {
		selectTwoShapes.execute();
		List<IShape> dups = drawing.getShapes().stream().map(sh -> (IShape)sh.duplicate()).collect(Collectors.toList());
		clickMirrorV.execute();
		assertNotEquals(dups.get(0).getPoints(), drawing.getShapes().get(0).getPoints());
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}
}
