package net.sf.latexdraw.instruments;

import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.util.Injector;
import org.mockito.Mockito;

public class TestInsertCodeGUI extends TestLatexdrawGUI {
	@Override
	protected Injector createInjector() {
		return new Injector() {
			@Override
			protected void configure() {
				bindToInstance(StatusBarController.class, Mockito.mock(StatusBarController.class));
				bindToInstance(IDrawing.class, Mockito.mock(IDrawing.class));
			}
		};
	}

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "/fxml/InsertCode.fxml";
	}
}
