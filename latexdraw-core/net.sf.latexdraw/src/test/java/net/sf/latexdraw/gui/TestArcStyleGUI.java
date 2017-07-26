package net.sf.latexdraw.gui;

import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import net.sf.latexdraw.instruments.ShapeArcCustomiser;
import org.junit.Before;

public abstract class TestArcStyleGUI extends TestShapePropGUI<ShapeArcCustomiser> {
	protected ToggleButton arcB;
	protected ToggleButton wedgeB;
	protected ToggleButton chordB;
	protected Spinner<Double> startAngleS;
	protected Spinner<Double> endAngleS;

	protected final GUIVoidCommand selectArc = () -> clickOn(arcB);
	protected final GUIVoidCommand selectWedge = () -> clickOn(wedgeB);
	protected final GUIVoidCommand selectChord = () -> clickOn(chordB);
	protected final GUIVoidCommand incrementStartAngle = () -> incrementSpinner(startAngleS);
	protected final GUIVoidCommand incrementEndAngle = () -> incrementSpinner(endAngleS);

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
		ins = (ShapeArcCustomiser) guiceFactory.call(ShapeArcCustomiser.class);
		ins.setActivated(true);
	}
}
