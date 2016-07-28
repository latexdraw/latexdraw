package test.gui;

import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import net.sf.latexdraw.instruments.ShapeCoordDimCustomiser;

import org.junit.Before;

public abstract class TestCoordDimShapeGUI extends TestShapePropGUI<ShapeCoordDimCustomiser> {
	protected Spinner<Double> tlxS;
	protected Spinner<Double> tlyS;
	protected TitledPane mainPane;

	protected final GUIVoidCommand incrementX = () -> incrementSpinner(tlxS);
	protected final GUIVoidCommand incrementY = () -> incrementSpinner(tlyS);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "/fxml/Dimension.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		tlxS = find("#tlxS");
		tlyS = find("#tlyS");
		mainPane = find("#mainPane");
		ins = (ShapeCoordDimCustomiser)guiceFactory.call(ShapeCoordDimCustomiser.class);
		ins.setActivated(true);
	}
}
