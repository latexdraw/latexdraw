package test.gui;

import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import net.sf.latexdraw.instruments.ShapeArcCustomiser;

import org.junit.Before;

public abstract class TestArcStyleGUI extends TestShapePropGUI<ShapeArcCustomiser> {
	protected ToggleButton arcB;
	protected ToggleButton wedgeB;
	protected ToggleButton chordB;
	protected Spinner<Double> startAngleS;
	protected Spinner<Double> endAngleS;
	protected TitledPane mainPane;
	
	final protected GUIVoidCommand selectArc = () -> clickOn(arcB);
	final protected GUIVoidCommand selectWedge = () -> clickOn(wedgeB);
	final protected GUIVoidCommand selectChord = () -> clickOn(chordB);
	final protected GUIVoidCommand incrementStartAngle = () -> incrementSpinner(startAngleS);
	final protected GUIVoidCommand incrementEndAngle = () -> incrementSpinner(endAngleS);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "glib/views/jfx/ui/ArcStyle.fxml";
	}
	 
	@Override
	@Before
	public void setUp() {
		super.setUp();
		mainPane	= find("#mainPane");
		arcB 		= find("#arcB");
		wedgeB 		= find("#wedgeB");
		chordB 		= find("#chordB");
		endAngleS 	= find("#endAngleS");
		startAngleS = find("#startAngleS");
		ins			= (ShapeArcCustomiser)guiceFactory.call(ShapeArcCustomiser.class);
		ins.setActivated(true);
	}
}
