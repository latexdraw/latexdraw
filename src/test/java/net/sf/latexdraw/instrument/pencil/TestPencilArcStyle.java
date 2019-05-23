package net.sf.latexdraw.instrument.pencil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import javafx.stage.Stage;
import net.sf.latexdraw.instrument.Cmds;
import net.sf.latexdraw.instrument.Hand;
import net.sf.latexdraw.instrument.MetaShapeCustomiser;
import net.sf.latexdraw.instrument.Pencil;
import net.sf.latexdraw.instrument.ShapeArcCustomiser;
import net.sf.latexdraw.instrument.ShapePropInjector;
import net.sf.latexdraw.instrument.TestArcStyleGUI;
import net.sf.latexdraw.instrument.TextSetter;
import net.sf.latexdraw.model.api.shape.Arc;
import net.sf.latexdraw.model.api.shape.ArcStyle;
import net.sf.latexdraw.util.Injector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TestPencilArcStyle extends TestArcStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				hand = mock(Hand.class);
				bindToInstance(Hand.class, hand);
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindAsEagerSingleton(Pencil.class);
				bindAsEagerSingleton(ShapeArcCustomiser.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesArc, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testControllerNotActivatedWhenBadPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesRec, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesArc, updateIns).execute();
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testClickChordUnselectOthersPencil() {
		Cmds.of(activatePencil, pencilCreatesArc, updateIns, selectWedge, selectChord).execute();
		assertFalse(arcB.isSelected());
		assertFalse(wedgeB.isSelected());
		assertTrue(chordB.isSelected());
	}

	@Test
	public void testClickWedgeUnselectOthersPencil() {
		Cmds.of(activatePencil, pencilCreatesArc, updateIns, selectChord, selectWedge).execute();
		assertFalse(arcB.isSelected());
		assertTrue(wedgeB.isSelected());
		assertFalse(chordB.isSelected());
	}

	@Test
	public void testClickArcUnselectOthersPencil() {
		Cmds.of(activatePencil, pencilCreatesArc, updateIns, selectChord, selectArc).execute();
		assertTrue(arcB.isSelected());
		assertFalse(wedgeB.isSelected());
		assertFalse(chordB.isSelected());
	}

	@Test
	public void testArcEndAnglePencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesArc, updateIns), endAngleS, incrementEndAngle,
			Collections.singletonList(() -> Math.toDegrees(((Arc) editing.createShapeInstance()).getAngleEnd())));
	}

	@Test
	public void testArcStartAnglePencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesArc, updateIns), startAngleS, incrementStartAngle,
			Collections.singletonList(() -> Math.toDegrees(((Arc) editing.createShapeInstance()).getAngleStart())));
	}

	@Test
	public void testArcTypeWedgePencil() {
		Cmds.of(activatePencil, pencilCreatesArc, updateIns, selectChord, selectWedge).execute();
		assertEquals(ArcStyle.WEDGE, ((Arc) editing.createShapeInstance()).getArcStyle());
	}

	@Test
	public void testArcTypeArcPencil() {
		Cmds.of(activatePencil, pencilCreatesArc, updateIns, selectChord, selectArc).execute();
		assertEquals(ArcStyle.ARC, ((Arc) editing.createShapeInstance()).getArcStyle());
	}

	@Test
	public void testArcTypeChordPencil() {
		Cmds.of(activatePencil, pencilCreatesArc, updateIns, selectArc, selectChord).execute();
		assertEquals(ArcStyle.CHORD, ((Arc) editing.createShapeInstance()).getArcStyle());
	}
}
