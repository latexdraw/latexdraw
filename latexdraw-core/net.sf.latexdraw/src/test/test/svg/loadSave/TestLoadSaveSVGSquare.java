package test.svg.loadSave;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ISquare;

import org.junit.Before;

public class TestLoadSaveSVGSquare extends TestLoadSaveSVGSquaredShape<ISquare> {
	@Before
	public void setUp() {
		shape = ShapeFactory.createSquare();
	}
}
