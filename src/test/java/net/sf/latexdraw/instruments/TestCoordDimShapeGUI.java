package net.sf.latexdraw.instruments;

import javafx.scene.control.Spinner;
import org.junit.Before;

public abstract class TestCoordDimShapeGUI extends TestShapePropGUI<ShapeCoordDimCustomiser> {
	protected Spinner<Double> tlxS;
	protected Spinner<Double> tlyS;

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
		ins = (ShapeCoordDimCustomiser) injectorFactory.call(ShapeCoordDimCustomiser.class);
		ins.setActivated(true);
	}
}
