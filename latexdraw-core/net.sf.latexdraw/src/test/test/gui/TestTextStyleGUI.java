package test.gui;

import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import net.sf.latexdraw.view.latex.LaTeXGenerator;
import net.sf.latexdraw.instruments.ShapeTextCustomiser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class TestTextStyleGUI extends TestShapePropGUI<ShapeTextCustomiser> {
	protected ToggleButton blButton;
	protected ToggleButton bButton;
	protected ToggleButton brButton;
	protected ToggleButton tlButton;
	protected ToggleButton tButton;
	protected ToggleButton trButton;
	protected ToggleButton lButton;
	protected ToggleButton rButton;
	protected ToggleButton centreButton;
	protected TextArea packagesField;
	protected TextArea logField;
	protected TitledPane mainPane;

	protected final GUIVoidCommand clickOnblButton = () -> clickOn(blButton);
	protected final GUIVoidCommand clickOnbButton = () -> clickOn(bButton);
	protected final GUIVoidCommand clickOnbrButton = () -> clickOn(brButton);
	protected final GUIVoidCommand clickOntlButton = () -> clickOn(tlButton);
	protected final GUIVoidCommand clickOntButton = () -> clickOn(tButton);
	protected final GUIVoidCommand clickOntrButton = () -> clickOn(trButton);
	protected final GUIVoidCommand clickOnlButton = () -> clickOn(lButton);
	protected final GUIVoidCommand clickOnrButton = () -> clickOn(rButton);
	protected final GUIVoidCommand clickOncentreButton = () -> clickOn(centreButton);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "/fxml/TextStyle.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		blButton = find("#blButton");
		bButton = find("#bButton");
		brButton = find("#brButton");
		tlButton = find("#tlButton");
		tButton = find("#tButton");
		trButton = find("#trButton");
		lButton = find("#lButton");
		rButton = find("#rButton");
		centreButton = find("#centreButton");
		packagesField = find("#packagesField");
		logField = find("#logField");
		mainPane = find("#mainPane");
		ins = (ShapeTextCustomiser)guiceFactory.call(ShapeTextCustomiser.class);
		ins.setActivated(true);
	}

	@Test
	public void testEditPackagesField() {
		clickOn(packagesField).type(KeyCode.A).sleep(1200);
		assertEquals(LaTeXGenerator.getPackages(), "a");
	}
}
