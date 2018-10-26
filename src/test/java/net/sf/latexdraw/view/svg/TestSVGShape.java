package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.data.ParameteriseShapeData;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.view.PolymorphShapeTest;
import org.junit.jupiter.api.AfterAll;

public class TestSVGShape extends TestSVGBase<IShape> implements PolymorphShapeTest {
	@AfterAll
	public static void tearDownAll() {
		ParameteriseShapeData.INST.clearTempFolders();
	}
}
