package net.sf.latexdraw.gui;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
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
	protected Pane mainPane;

	protected final GUIVoidCommand pickcolourLabels = () -> pickColour(colourLabels);
	protected final GUIVoidCommand pickcolourSubGrid = () -> pickColour(colourSubGrid);
	protected final GUIVoidCommand incrementgridWidth = () -> incrementSpinner(gridWidth);
	protected final GUIVoidCommand incrementsubGridWidth = () -> incrementSpinner(subGridWidth);
	protected final GUIVoidCommand incrementgridDots = () -> incrementSpinner(gridDots);
	protected final GUIVoidCommand incrementsubGridDots = () -> incrementSpinner(subGridDots);
	protected final GUIVoidCommand incrementsubGridDiv = () -> incrementSpinner(subGridDiv);
	protected final GUIVoidCommand clicklabelsYInvertedCB = () -> clickOn(labelsYInvertedCB);
	protected final GUIVoidCommand clicklabelsXInvertedCB = () -> clickOn(labelsXInvertedCB);

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
		ins = (ShapeGridCustomiser) injectorFactory.call(ShapeGridCustomiser.class);
		ins.setActivated(true);
	}
}
