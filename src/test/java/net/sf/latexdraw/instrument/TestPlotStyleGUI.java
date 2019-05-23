package net.sf.latexdraw.instrument;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import net.sf.latexdraw.model.api.shape.PlotStyle;
import org.junit.Before;

public abstract class TestPlotStyleGUI extends TestShapePropGUI<ShapePlotCustomiser> {
	protected Spinner<Integer> nbPtsSpinner;
	protected Spinner<Double> minXSpinner;
	protected Spinner<Double> maxXSpinner;
	protected Spinner<Double> xScaleSpinner;
	protected Spinner<Double> yScaleSpinner;
	protected CheckBox polarCB;
	protected ComboBox<PlotStyle> plotStyleCB;

	protected final Cmd<PlotStyle> selectplotStyleCB = style -> selectGivenComboBoxItem(plotStyleCB, style);
	protected final CmdVoid incrementnbPtsSpinner = () -> incrementSpinner(nbPtsSpinner);
	protected final CmdVoid incrementminXSpinner = () -> incrementSpinner(minXSpinner);
	protected final CmdVoid incrementmaxXSpinner = () -> incrementSpinner(maxXSpinner);
	protected final CmdVoid incrementxScaleSpinner = () -> incrementSpinner(xScaleSpinner);
	protected final CmdVoid incrementyScaleSpinner = () -> incrementSpinner(yScaleSpinner);
	protected final CmdVoid clickpolarCB = () -> clickOn(polarCB);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "/fxml/PlotStyle.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		polarCB = find("#polarCB");
		yScaleSpinner = find("#yScaleSpinner");
		xScaleSpinner = find("#xScaleSpinner");
		maxXSpinner = find("#maxXSpinner");
		minXSpinner = find("#minXSpinner");
		nbPtsSpinner = find("#nbPtsSpinner");
		plotStyleCB = find("#plotStyleCB");
		ins = injector.getInstance(ShapePlotCustomiser.class);
		ins.setActivated(true);
	}
}
