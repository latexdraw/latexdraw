package net.sf.latexdraw.instruments;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.util.Injector;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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
	Button distribVertBot;
	Button distribVertEq;
	Button distribVertMid;
	Button distribVertTop;
	Button distribHorizEq;
	Button distribHorizLeft;
	Button distribHorizMid;
	Button distribHorizRight;
	VBox mainPane;

	final GUIVoidCommand clickAlignBot = () -> clickOn(alignBot);
	final GUIVoidCommand clickAlignTop = () -> clickOn(alignTop);
	final GUIVoidCommand clickAlignLeft = () -> clickOn(alignLeft);
	final GUIVoidCommand clickAlignRight = () -> clickOn(alignRight);
	final GUIVoidCommand clickAlignMidHoriz = () -> clickOn(alignMidHoriz);
	final GUIVoidCommand clickAlignMidVert = () -> clickOn(alignMidVert);
	final GUIVoidCommand clickMirrorH = () -> clickOn(mirrorH);
	final GUIVoidCommand clickMirrorV = () -> clickOn(mirrorV);
	final GUIVoidCommand clickDistribVertBot = () -> clickOn(distribVertBot);
	final GUIVoidCommand clickDistribVertEq = () -> clickOn(distribVertEq);
	final GUIVoidCommand clickDistribVertMid = () -> clickOn(distribVertMid);
	final GUIVoidCommand clickDistribVertTop = () -> clickOn(distribVertTop);
	final GUIVoidCommand clickDistribHorizEq = () -> clickOn(distribHorizEq);
	final GUIVoidCommand clickDistribHorizLeft = () -> clickOn(distribHorizLeft);
	final GUIVoidCommand clickDistribHorizMid = () -> clickOn(distribHorizMid);
	final GUIVoidCommand clickDistribHorizRight = () -> clickOn(distribHorizRight);

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
		distribVertBot = find("#distribVertBot");
		distribVertEq = find("#distribVertEq");
		distribVertMid = find("#distribVertMid");
		distribVertTop = find("#distribVertTop");
		distribHorizEq = find("#distribHorizEq");
		distribHorizMid = find("#distribHorizMid");
		distribHorizRight = find("#distribHorizRight");
		distribHorizLeft = find("#distribHorizLeft");
		mainPane = find("#mainPane");
		ins = (ShapeTransformer) injectorFactory.call(ShapeTransformer.class);
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
				bindAsEagerSingleton(ShapeTransformer.class);
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
	public void testActivateOnTwoShapesSelected() {
		selectTwoShapes.execute();
		assertTrue(ins.isActivated());
	}

	@Test
	public void testNotActivateOnOneShapeSelected() {
		selectOneShape.execute();
		assertFalse(ins.isActivated());
	}

	@Test
	public void testNotVisibleOnOneShapeSelected() {
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
	public void testAlignBot() {
		selectTwoShapes.execute();
		final List<IShape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		clickAlignBot.execute();
		assertNotEquals(dups.get(0).getPoints(), drawing.getShapes().get(0).getPoints());
	}

	@Test
	public void testAlignTop() {
		selectTwoShapes.execute();
		final List<IShape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		clickAlignTop.execute();
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testAlignLeft() {
		selectTwoShapes.execute();
		final List<IShape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		clickAlignLeft.execute();
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testAlignRight() {
		selectTwoShapes.execute();
		final List<IShape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		clickAlignRight.execute();
		assertNotEquals(dups.get(0).getPoints(), drawing.getShapes().get(0).getPoints());
	}

	@Test
	public void testAlignMidHoriz() {
		selectTwoShapes.execute();
		final List<IShape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		clickAlignMidHoriz.execute();
		assertNotEquals(dups.get(0).getPoints(), drawing.getShapes().get(0).getPoints());
	}

	@Test
	public void testAlignMidVert() {
		selectTwoShapes.execute();
		final List<IShape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		clickAlignMidVert.execute();
		assertNotEquals(dups.get(0).getPoints(), drawing.getShapes().get(0).getPoints());
	}

	@Test
	public void testMirrorH() {
		selectTwoShapes.execute();
		final List<IShape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		clickMirrorH.execute();
		assertNotEquals(dups.get(0).getPoints(), drawing.getShapes().get(0).getPoints());
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testMirrorV() {
		selectTwoShapes.execute();
		final List<IShape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		clickMirrorV.execute();
		assertNotEquals(dups.get(0).getPoints(), drawing.getShapes().get(0).getPoints());
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testDistribVertBot() {
		selectThreeShapes.execute();
		final List<IShape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		clickDistribVertBot.execute();
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testDistribVertEq() {
		selectThreeShapes.execute();
		final List<IShape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		clickDistribVertEq.execute();
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testDistribVertMid() {
		selectThreeShapes.execute();
		final List<IShape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		clickDistribVertMid.execute();
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testDistribVertTop() {
		selectThreeShapes.execute();
		final List<IShape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		clickDistribVertTop.execute();
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testDistribHorizEq() {
		selectThreeShapes.execute();
		final List<IShape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		clickDistribHorizEq.execute();
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testDistribHorizLeft() {
		selectThreeShapes.execute();
		final List<IShape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		clickDistribHorizLeft.execute();
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testDistribHorizMid() {
		selectThreeShapes.execute();
		final List<IShape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		clickDistribHorizMid.execute();
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testDistribHorizRight() {
		selectThreeShapes.execute();
		final List<IShape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		clickDistribHorizRight.execute();
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}
}
