package net.sf.latexdraw.instrument;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.sf.latexdraw.model.api.shape.Shape;
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

	final CmdVoid clickAlignBot = () -> clickOn(alignBot);
	final CmdVoid clickAlignTop = () -> clickOn(alignTop);
	final CmdVoid clickAlignLeft = () -> clickOn(alignLeft);
	final CmdVoid clickAlignRight = () -> clickOn(alignRight);
	final CmdVoid clickAlignMidHoriz = () -> clickOn(alignMidHoriz);
	final CmdVoid clickAlignMidVert = () -> clickOn(alignMidVert);
	final CmdVoid clickMirrorH = () -> clickOn(mirrorH);
	final CmdVoid clickMirrorV = () -> clickOn(mirrorV);
	final CmdVoid clickDistribVertBot = () -> clickOn(distribVertBot);
	final CmdVoid clickDistribVertEq = () -> clickOn(distribVertEq);
	final CmdVoid clickDistribVertMid = () -> clickOn(distribVertMid);
	final CmdVoid clickDistribVertTop = () -> clickOn(distribVertTop);
	final CmdVoid clickDistribHorizEq = () -> clickOn(distribHorizEq);
	final CmdVoid clickDistribHorizLeft = () -> clickOn(distribHorizLeft);
	final CmdVoid clickDistribHorizMid = () -> clickOn(distribHorizMid);
	final CmdVoid clickDistribHorizRight = () -> clickOn(distribHorizRight);

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
		ins = injector.getInstance(ShapeTransformer.class);
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
				bindToInstance(TextSetter.class, Mockito.mock(TextSetter.class));
				bindToInstance(Hand.class, hand);
				bindToInstance(Pencil.class, pencil);
				bindAsEagerSingleton(ShapeTransformer.class);
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
		waitFXEvents.execute();
		assertTrue(ins.isActivated());
	}

	@Test
	public void testNotActivateOnOneShapeSelected() {
		Cmds.of(selectOneShape).execute();
		assertFalse(ins.isActivated());
	}

	@Test
	public void testNotVisibleOnOneShapeSelected() {
		Cmds.of(selectOneShape).execute();
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testNotVisiblePencil() {
		when(pencil.isActivated()).thenReturn(true);
		when(hand.isActivated()).thenReturn(false);
		Cmds.of(selectTwoShapes).execute();
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testNotActivatedPencil() {
		when(pencil.isActivated()).thenReturn(true);
		when(hand.isActivated()).thenReturn(false);
		Cmds.of(selectTwoShapes).execute();
		assertFalse(ins.isActivated());
	}

	@Test
	public void testAlignBot() {
		Cmds.of(selectTwoShapes).execute();
		final List<Shape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		Cmds.of(clickAlignBot).execute();
		assertNotEquals(dups.get(0).getPoints(), drawing.getShapes().get(0).getPoints());
	}

	@Test
	public void testAlignTop() {
		Cmds.of(selectTwoShapes).execute();
		final List<Shape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		Cmds.of(clickAlignTop).execute();
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testAlignLeft() {
		Cmds.of(selectTwoShapes).execute();
		final List<Shape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		Cmds.of(clickAlignLeft).execute();
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testAlignRight() {
		Cmds.of(selectTwoShapes).execute();
		final List<Shape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		Cmds.of(clickAlignRight).execute();
		assertNotEquals(dups.get(0).getPoints(), drawing.getShapes().get(0).getPoints());
	}

	@Test
	public void testAlignMidHoriz() {
		Cmds.of(selectTwoShapes).execute();
		final List<Shape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		Cmds.of(clickAlignMidHoriz).execute();
		assertNotEquals(dups.get(0).getPoints(), drawing.getShapes().get(0).getPoints());
	}

	@Test
	public void testAlignMidVert() {
		Cmds.of(selectTwoShapes).execute();
		final List<Shape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		Cmds.of(clickAlignMidVert).execute();
		assertNotEquals(dups.get(0).getPoints(), drawing.getShapes().get(0).getPoints());
	}

	@Test
	public void testMirrorH() {
		Cmds.of(selectTwoShapes).execute();
		final List<Shape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		Cmds.of(clickMirrorH).execute();
		assertNotEquals(dups.get(0).getPoints(), drawing.getShapes().get(0).getPoints());
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testMirrorV() {
		Cmds.of(selectTwoShapes).execute();
		final List<Shape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		Cmds.of(clickMirrorV).execute();
		assertNotEquals(dups.get(0).getPoints(), drawing.getShapes().get(0).getPoints());
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testDistribVertBot() {
		Cmds.of(selectThreeShapes).execute();
		final List<Shape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		Cmds.of(clickDistribVertBot).execute();
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testDistribVertEq() {
		Cmds.of(selectThreeShapes).execute();
		final List<Shape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		Cmds.of(clickDistribVertEq).execute();
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testDistribVertMid() {
		Cmds.of(selectThreeShapes).execute();
		final List<Shape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		Cmds.of(clickDistribVertMid).execute();
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testDistribVertTop() {
		Cmds.of(selectThreeShapes).execute();
		final List<Shape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		Cmds.of(clickDistribVertTop).execute();
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testDistribHorizEq() {
		Cmds.of(selectThreeShapes).execute();
		final List<Shape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		Cmds.of(clickDistribHorizEq).execute();
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testDistribHorizLeft() {
		Cmds.of(selectThreeShapes).execute();
		final List<Shape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		Cmds.of(clickDistribHorizLeft).execute();
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testDistribHorizMid() {
		Cmds.of(selectThreeShapes).execute();
		final List<Shape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		Cmds.of(clickDistribHorizMid).execute();
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}

	@Test
	public void testDistribHorizRight() {
		Cmds.of(selectThreeShapes).execute();
		final List<Shape> dups = drawing.getShapes().stream().map(sh -> sh.duplicate()).collect(Collectors.toList());
		Cmds.of(clickDistribHorizRight).execute();
		assertNotEquals(dups.get(1).getPoints(), drawing.getShapes().get(1).getPoints());
	}
}
