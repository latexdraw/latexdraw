package test.gui.pencil;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeCoordDimCustomiser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import test.gui.CompositeGUICommand;
import test.gui.ShapePropModule;
import test.gui.TestCoordDimShapeGUI;

import com.google.inject.AbstractModule;

@RunWith(MockitoJUnitRunner.class)
public class TestPencilCoordDimStyle extends TestCoordDimShapeGUI {
	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				hand = mock(Hand.class);
				bind(ShapeCoordDimCustomiser.class).asEagerSingleton();
				bind(Pencil.class).asEagerSingleton();
				bind(Hand.class).toInstance(hand);
			}
		};
	}
	
	@Test
	public void testControllerNotActivatedWithPencil() {
		new CompositeGUICommand(activatePencil, pencilCreatesRec, updateIns, checkInsDeactivated).execute();
	}
	
	@Test
	public void testControllerNotVisibleWithPencil() {
		new CompositeGUICommand(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(mainPane.isVisible());
	}
}
