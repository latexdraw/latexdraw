package net.sf.latexdraw.instrument;

import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import org.junit.Before;

public abstract class TestArcStyleGUI extends TestShapePropGUI<ShapeArcCustomiser> {
	protected ToggleButton arcB;
	protected ToggleButton wedgeB;
	protected ToggleButton chordB;
	protected Spinner<Double> startAngleS;
	protected Spinner<Double> endAngleS;

	protected final CmdVoid selectArc = () -> clickOn(arcB);
	protected final CmdVoid selectWedge = () -> clickOn(wedgeB);
	protected final CmdVoid selectChord = () -> clickOn(chordB);
	protected final CmdVoid incrementStartAngle = () -> incrementSpinner(startAngleS);
	protected final CmdVoid incrementEndAngle = () -> incrementSpinner(endAngleS);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "/fxml/ArcStyle.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		arcB = find("#arcB");
		wedgeB = find("#wedgeB");
		chordB = find("#chordB");
		endAngleS = find("#endAngleS");
		startAngleS = find("#startAngleS");
		ins = injector.getInstance(ShapeArcCustomiser.class);
		ins.setActivated(true);
	}
}
