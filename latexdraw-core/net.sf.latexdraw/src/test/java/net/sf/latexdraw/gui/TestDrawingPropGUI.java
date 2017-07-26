package net.sf.latexdraw.gui;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import net.sf.latexdraw.gui.robot.FxRobotListSelection;
import net.sf.latexdraw.gui.robot.FxRobotSpinner;
import net.sf.latexdraw.instruments.DrawingPropertiesCustomiser;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.view.latex.LaTeXGenerator;
import net.sf.latexdraw.view.latex.VerticalPosition;
import net.sf.latexdraw.view.pst.PSTCodeGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class TestDrawingPropGUI extends TestLatexdrawGUI implements FxRobotSpinner, FxRobotListSelection {
	TextField titleField;
	TextField labelField;
	CheckBox middleHorizPosCB;
	ComboBox<VerticalPosition> positionCB;
	Spinner<Double> scaleField;
	DrawingPropertiesCustomiser ins;
	LaTeXGenerator gen;

	final GUIVoidCommand typeLabel = () -> clickOn(labelField).write("newLabel");
	final GUIVoidCommand typeTitle = () -> clickOn(titleField).write("new Title");
	final GUIVoidCommand changeScale = () -> incrementSpinner(scaleField);
	final GUIVoidCommand checkMiddleHoriz = () -> clickOn(middleHorizPosCB);
	final GUICommand<VerticalPosition> changePosition = pos -> selectGivenComboBoxItem(positionCB, pos);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "/fxml/DrawingProps.fxml";
	}

	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				hand = mock(Hand.class);
				bind(PSTCodeGenerator.class).asEagerSingleton();
				bind(DrawingPropertiesCustomiser.class).asEagerSingleton();
			}

			@Provides
			LaTeXGenerator provideLaTeXGenerator(final PSTCodeGenerator gen) {
				return gen;
			}
		};
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		titleField = find("#titleField");
		labelField = find("#labelField");
		middleHorizPosCB = find("#middleHorizPosCB");
		positionCB = find("#positionCB");
		scaleField = find("#scaleField");
		((IDrawing) guiceFactory.call(IDrawing.class)).addShape(ShapeFactory.INST.createCircle());
		ins = (DrawingPropertiesCustomiser) guiceFactory.call(DrawingPropertiesCustomiser.class);
		gen = (LaTeXGenerator) guiceFactory.call(LaTeXGenerator.class);
		ins.setActivated(true);
	}

	@Test
	public void testSetCaption() throws Exception {
		new CompositeGUIVoidCommand(typeTitle, waitFX1Second).execute();
		assertEquals("new Title", gen.getCaption());
	}

	@Test
	public void testSetLabel() throws Exception {
		new CompositeGUIVoidCommand(typeLabel, waitFX1Second).execute();
		assertEquals("newLabel", gen.getLabel());
	}

	@Test
	public void testSetScale() throws Exception {
		changeScale.execute();
		assertEquals(scaleField.getValueFactory().getValue(), gen.getScale(), 0.0000001);
	}

	@Test
	public void testSetMiddleHoriz() throws Exception {
		checkMiddleHoriz.execute();
		assertEquals(middleHorizPosCB.isSelected(), gen.isPositionHoriCentre());
	}

	@Test
	public void testSePositionBOTTOM() throws Exception {
		changePosition.execute(VerticalPosition.BOTTOM);
		assertEquals(VerticalPosition.BOTTOM, gen.getPositionVertToken());
	}

	@Test
	public void testSePositionFLOAT() throws Exception {
		changePosition.execute(VerticalPosition.FLOATS_PAGE);
		assertEquals(VerticalPosition.FLOATS_PAGE, gen.getPositionVertToken());
	}

	@Test
	public void testSePositionHERE() throws Exception {
		changePosition.execute(VerticalPosition.HERE);
		assertEquals(VerticalPosition.HERE, gen.getPositionVertToken());
	}

	@Test
	public void testSePositionHEREHERE() throws Exception {
		changePosition.execute(VerticalPosition.HERE_HERE);
		assertEquals(VerticalPosition.HERE_HERE, gen.getPositionVertToken());
	}

	@Test
	public void testSePositionTOP() throws Exception {
		changePosition.execute(VerticalPosition.TOP);
		assertEquals(VerticalPosition.TOP, gen.getPositionVertToken());
	}

	@Test
	public void testSePositionNONE() throws Exception {
		changePosition.execute(VerticalPosition.NONE);
		assertEquals(VerticalPosition.NONE, gen.getPositionVertToken());
	}

	@Test
	public void testTitledPaneVisible() {
		assertTrue(titledPane.isVisible());
	}
}
