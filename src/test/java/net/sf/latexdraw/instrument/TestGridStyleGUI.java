package net.sf.latexdraw.instrument;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
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

	protected final CmdVoid pickcolourLabels = () -> pickColour(colourLabels);
	protected final CmdVoid pickcolourSubGrid = () -> pickColour(colourSubGrid);
	protected final CmdVoid incrementgridWidth = () -> incrementSpinner(gridWidth);
	protected final CmdVoid incrementsubGridWidth = () -> incrementSpinner(subGridWidth);
	protected final CmdVoid incrementgridDots = () -> incrementSpinner(gridDots);
	protected final CmdVoid incrementsubGridDots = () -> incrementSpinner(subGridDots);
	protected final CmdVoid incrementsubGridDiv = () -> incrementSpinner(subGridDiv);
	protected final CmdVoid clicklabelsYInvertedCB = () -> clickOn(labelsYInvertedCB);
	protected final CmdVoid clicklabelsXInvertedCB = () -> clickOn(labelsXInvertedCB);

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
		ins = injector.getInstance(ShapeGridCustomiser.class);
		ins.setActivated(true);
	}
}
