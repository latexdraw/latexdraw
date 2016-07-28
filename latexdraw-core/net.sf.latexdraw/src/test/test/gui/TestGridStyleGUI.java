package test.gui;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import net.sf.latexdraw.instruments.ShapeGridCustomiser;

import org.junit.Before;

public abstract class TestGridStyleGUI extends TestShapePropGUI<ShapeGridCustomiser> {
	protected ColorPicker colourLabels;
	protected ColorPicker colourSubGrid;
	protected Spinner<Double> gridWidth;
	protected Spinner<Double> subGridWidth;
	protected Spinner<Integer> gridDots;
	protected Spinner<Integer> subGridDots;
	protected Spinner<Integer> subGridDiv;
	protected ToggleButton labelsYInvertedCB;
	protected ToggleButton labelsXInvertedCB;
	protected AnchorPane mainPane;

	final protected GUIVoidCommand pickcolourLabels = () -> pickColour(colourLabels);
	final protected GUIVoidCommand pickcolourSubGrid = () -> pickColour(colourSubGrid);
	final protected GUIVoidCommand incrementgridWidth = () -> incrementSpinner(gridWidth);
	final protected GUIVoidCommand incrementsubGridWidth = () -> incrementSpinner(subGridWidth);
	final protected GUIVoidCommand incrementgridDots = () -> incrementSpinner(gridDots);
	final protected GUIVoidCommand incrementsubGridDots = () -> incrementSpinner(subGridDots);
	final protected GUIVoidCommand incrementsubGridDiv = () -> incrementSpinner(subGridDiv);
	final protected GUIVoidCommand clicklabelsYInvertedCB = () -> clickOn(labelsYInvertedCB);
	final protected GUIVoidCommand clicklabelsXInvertedCB = () -> clickOn(labelsXInvertedCB);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "/fxml/GridStyle.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		colourLabels = find("#colourLabels");
		colourSubGrid = find("#colourSubGrid");
		gridWidth = find("#gridWidth");
		subGridWidth = find("#subGridWidth");
		gridDots = find("#gridDots");
		subGridDots = find("#subGridDots");
		subGridDiv = find("#subGridDiv");
		labelsYInvertedCB = find("#labelsYInvertedCB");
		labelsXInvertedCB = find("#labelsXInvertedCB");
		mainPane = find("#mainPane");
		ins = (ShapeGridCustomiser)guiceFactory.call(ShapeGridCustomiser.class);
		ins.setActivated(true);
	}
}
