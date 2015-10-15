package test.gui;

import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import net.sf.latexdraw.glib.views.latex.LaTeXGenerator;
import net.sf.latexdraw.instruments.ShapeTextCustomiser;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

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

	final protected GUIVoidCommand clickOnblButton = () -> clickOn(blButton);
	final protected GUIVoidCommand clickOnbButton = () -> clickOn(bButton);
	final protected GUIVoidCommand clickOnbrButton = () -> clickOn(brButton);
	final protected GUIVoidCommand clickOntlButton = () -> clickOn(tlButton);
	final protected GUIVoidCommand clickOntButton = () -> clickOn(tButton);
	final protected GUIVoidCommand clickOntrButton = () -> clickOn(trButton);
	final protected GUIVoidCommand clickOnlButton = () -> clickOn(lButton);
	final protected GUIVoidCommand clickOnrButton = () -> clickOn(rButton);
	final protected GUIVoidCommand clickOncentreButton = () -> clickOn(centreButton);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "glib/views/jfx/ui/TextStyle.fxml";
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
